package net.dmulloy2.autocraft;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import net.dmulloy2.autocraft.types.ShipData;
import net.dmulloy2.autocraft.util.FileSerialization;

public class DataHandler {
	private HashMap<String, ShipData> data;
	
	private final AutoCraft plugin;
	
	public DataHandler(AutoCraft plugin) {
		this.plugin = plugin;
		
		this.data = new HashMap<String, ShipData>();
	}
	
	public void load() {
		File shipsFolder = new File(plugin.getDataFolder(), "ships");
		if (! shipsFolder.exists()) {
			shipsFolder.mkdir();
			
			generateStockShips();
		}
		
		int loadedShips = 0;
		
		File[] children = shipsFolder.listFiles();
		for (File file : children) {
			ShipData shipData = FileSerialization.load(file, ShipData.class);
			data.put(shipData.getShipType(), shipData);
			loadedShips++;
		}
		
		plugin.getLogHandler().log("Loaded {0} ships!", loadedShips);
	}
	
	// TODO
	public void generateStockShips() {
		
	}
	
	public ShipData getData(String key) {
		return data.get(key);
	}
	
	public boolean isValidShip(String key) {
		return getData(key) != null;
	}
	
	public void clearMemory() {
		data.clear();
	}
	
	public Set<String> getAvailableShips() {
		return data.keySet();
	}
}