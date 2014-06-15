package net.dmulloy2.autocraft.handlers;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.types.ShipData;
import net.dmulloy2.io.FileSerialization;
import net.dmulloy2.types.Reloadable;
import net.dmulloy2.util.MaterialUtil;
import net.dmulloy2.util.Util;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

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
		this.load();
	}

	public final void load() {
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
			ShipData shipData = loadData(file);
			if (shipData != null) {
				data.put(shipData.getShipType(), shipData);
			}
		}

		plugin.getLogHandler().log("{0} loaded! [{1}ms]", WordUtils.capitalize(folderName), System.currentTimeMillis() - start);
	}

	public ShipData loadData(File file) {
		try {
			// Load the values
			FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
			Map<String, Object> map = fc.getValues(true);

			// New versioning system
			if (! map.containsKey("version")) {
				if (map.containsKey("allowedTypes")) {
					map.put("version", 2);
				} else {
					map.put("version", 1);
				}
			}

			boolean save = false;
			int version = (int) map.get("version");
			if (version < 3) {
				// Convert mainType and cannonMaterial
				List<String> toConvert = Arrays.asList(new String[] { "mainType", "cannonMaterial" });
				for (Entry<String, Object> entry : new HashMap<String, Object>(map).entrySet()) { // Damn you, CME's
					String key = entry.getKey();
					if (toConvert.contains(key)) {
						Object value = entry.getValue();
						if (value instanceof Integer) {
							Integer i = (Integer) value;
							map.remove(key);
							Material mat = MaterialUtil.getMaterial(i.intValue());
							if (mat != null) {
								map.put(key, mat.toString());
							} else {
								map.put(key, i.toString());
							}
						}
					}
				}

				// Convert allowedBlocks to allowedTypes
				if (version < 2) {
					@SuppressWarnings("unchecked")
					List<Integer> allowedBlocks = (List<Integer>) map.get("allowedBlocks");
					List<String> allowedTypes = new ArrayList<String>();
					for (int id : allowedBlocks) {
						Material mat = MaterialUtil.getMaterial(id);
						if (mat != null) {
							allowedTypes.add(mat.toString());
						} else {
							allowedTypes.add(Integer.toString(id));
						}
					}

					map.remove("allowedBlocks");
					map.put("allowedTypes", allowedTypes);
				}

				// Latest version
				map.put("version", ShipData.LATEST_VERSION);
				save = true;
			}

			// Deserialize so we're actually working with the ShipData object
			ShipData data = (ShipData) ConfigurationSerialization.deserializeObject(map, ShipData.class);

			// Set the ship's type
			data.setShipType(trimFileExtension(file));

			// Save if applicable
			if (save) {
				saveData(data);
			}

			return data;
		} catch (Throwable ex) {
			plugin.getLogHandler().log(Level.SEVERE, Util.getUsefulStack(ex, "loading ship " + file.getName()));
			return null;
		}
	}

	private final void generateStockShips() {
		plugin.getLogHandler().log("Generating stock ships!");

		String[] stocks = new String[] { "airship", "base", "battle", "dreadnought", "pirate", "stealth", "titan", "turret" };

		for (String stock : stocks) {
			plugin.saveResource(folderName + File.separator + stock + extension, false);
		}
	}

	private final void saveData(ShipData shipData) {
		try {
			File file = new File(folder, getFileName(shipData.getShipType()));
			FileSerialization.save(shipData, file);
		} catch (Throwable ex) {
			if (shipData != null) {
				plugin.getLogHandler().log(Level.SEVERE, Util.getUsefulStack(ex, "saving ship " + shipData.getShipType()));
			}
		}
	}

	private final String trimFileExtension(File file) {
		int index = file.getName().lastIndexOf(extension);
		return index > 0 ? file.getName().substring(0, index) : file.getName();
	}

	private final String getFileName(String key) {
		return key + extension;
	}

	public final ShipData getData(String key) {
		for (ShipData dat : data.values()) {
			if (dat.getShipType().equalsIgnoreCase(key))
				return dat;
		}

		return null;
	}

	public final boolean isValidShip(String key) {
		return getData(key) != null;
	}

	public final Set<String> getShips() {
		return data.keySet();
	}

	public final Collection<ShipData> getData() {
		return data.values();
	}

	@Override
	public void reload() {
		data.clear();
		load();
	}
}