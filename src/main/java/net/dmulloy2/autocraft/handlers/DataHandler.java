package net.dmulloy2.autocraft.handlers;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.io.FileSerialization;
import net.dmulloy2.autocraft.types.Reloadable;
import net.dmulloy2.autocraft.types.ShipData;
import net.dmulloy2.autocraft.util.Util;

import org.apache.commons.lang.WordUtils;

/**
 * @author dmulloy2
 */

public class DataHandler implements Reloadable {
	private final AutoCraft plugin;
	private final File folder;
	private final String extension = ".yml";
	private final String folderName = "ships";

	private HashMap<String, ShipData> data;

	public DataHandler(AutoCraft plugin) {
		this.plugin = plugin;
		this.folder = new File(plugin.getDataFolder(), folderName);

		if (! folder.exists()) {
			folder.mkdir();
		}

		this.data = new HashMap<String, ShipData>();

		load();
	}

	public void load() {
		long start = System.currentTimeMillis();

		plugin.getLogHandler().log("Loading {0}...", folderName);

		File[] children = folder.listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {
				return file.getName().contains(extension);
			}

		});

		if (children.length == 0) {
			generateStockShips();
			children = folder.listFiles();
		}

		for (File file : children) {
			try {
				ShipData shipData = FileSerialization.load(file, ShipData.class);
				shipData.setShipType(trimFileExtension(file));
				data.put(shipData.getShipType(), shipData);
			} catch (Throwable ex) {
				plugin.getLogHandler().log(Level.SEVERE, Util.getUsefulStack(ex, "loading ship: " + file.getName()));
			}
		}

		plugin.getLogHandler().log("{0} loaded! [{1}ms]", WordUtils.capitalize(folderName), System.currentTimeMillis() - start);
	}

	public void save() {
		long start = System.currentTimeMillis();

		plugin.getLogHandler().log("Saving {0} to disk...", folderName);

		for (ShipData shipData : data.values()) {
			saveData(shipData);
		}

		plugin.getLogHandler().log("{0} saved! [{1}ms]", WordUtils.capitalize(folderName), System.currentTimeMillis() - start);
	}

	public void generateStockShips() {
		plugin.getLogHandler().log("Generating stock ships!");

		String[] stocks = new String[] { "airship", "base", "battle", "dreadnought", "pirate", "stealth", "titan", "turret" };

		for (String stock : stocks) {
			plugin.saveResource(folderName + File.separator + stock + extension, false);
		}
	}

	public void saveData(ShipData shipData) {
		try {
			File file = new File(folder, getFileName(shipData.getShipType()));
			FileSerialization.save(shipData, file);
		} catch (Throwable ex) {
			if (shipData != null) {
				plugin.getLogHandler().log(Level.SEVERE, Util.getUsefulStack(ex, "saving ship: " + shipData.getShipType()));
			}
		}
	}

	public void onDisable() {
		save();
		data.clear();
	}

	private String trimFileExtension(File file) {
		int index = file.getName().lastIndexOf(extension);
		return index > 0 ? file.getName().substring(0, index) : file.getName();
	}

	private String getFileName(String key) {
		return key + extension;
	}

	public ShipData getData(String key) {
		for (ShipData dat : data.values()) {
			if (dat.getShipType().equalsIgnoreCase(key))
				return dat;
		}

		return null;
	}

	public boolean isValidShip(String key) {
		return getData(key) != null;
	}

	public Set<String> getShips() {
		return data.keySet();
	}

	public Collection<ShipData> getData() {
		return data.values();
	}

	/**
	 * Forces the reloading of ship data
	 */
	@Override
	public void reload() {
		data.clear();
		load();
	}
}