package net.dmulloy2.autocraft.handlers;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.io.FileSerialization;
import net.dmulloy2.autocraft.types.ShipData;

import org.apache.commons.lang.WordUtils;

public class DataHandler {
	private final AutoCraft plugin;
	private final File folder;
	private final String extension = ".yml";
	private final String folderName = "ships";

	private HashMap<String, ShipData> data;
	
	public DataHandler(AutoCraft plugin) {
		this.plugin = plugin;
		this.folder = new File(plugin.getDataFolder(), folderName);
		
		if (! folder.exists())
			folder.mkdir();

		this.data = new HashMap<String, ShipData>();
		
		load();
	}
	
	public void load() {
		plugin.getLogHandler().log("Loading {0}...", folderName);
		
		long start = System.currentTimeMillis();
		
		File[] children = folder.listFiles();
		if (children.length == 0) {
			generateStockShips();

			children = folder.listFiles();
		}
		
		for (File file : children) {
			ShipData shipData = FileSerialization.load(file, ShipData.class);
			shipData.setShipType(trimFileExtension(file));
			data.put(shipData.getShipType(), shipData);
		}
		
		plugin.getLogHandler().log("{0} loaded! [{1}ms]", WordUtils.capitalize(folderName), System.currentTimeMillis() - start);
	}
	
	public void save() {
		plugin.getLogHandler().log("Saving {0} to disk...", folderName);
		
		long start = System.currentTimeMillis();

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
		File file = new File(folder, getFileName(shipData.getShipType()));
		FileSerialization.save(shipData, file);
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
		return data.get(key);
	}
	
	public boolean isValidShip(String key) {
		return getData(key) != null;
	}
	
	public Set<String> getShips() {
		return data.keySet();
	}
}