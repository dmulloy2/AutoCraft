package net.dmulloy2.autocraft.io;

import java.io.File;
import java.util.Map.Entry;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.io.legacy.ACProperties;
import net.dmulloy2.autocraft.io.legacy.ACPropertiesManager;
import net.dmulloy2.autocraft.types.ShipData;

import com.google.common.io.Files;

@SuppressWarnings("deprecation")
public class FileConverter {
	private final AutoCraft plugin;
	private final File folder;
	private final String extension = ".yml";
	private final String folderName = "ships";
	
	public FileConverter(AutoCraft plugin) {
		this.plugin = plugin;
		this.folder = new File(plugin.getDataFolder(), folderName);
		
		if (! folder.exists())
			folder.mkdir();
		
		run();
	}
	
	public void run() {
		if (! needsConversion()) {
			return;
		}
		
		plugin.getLogHandler().log("Beginning File conversion task.");
		
		long start = System.currentTimeMillis();
		
		File dataFolder = plugin.getDataFolder();
		
		File archiveFile = new File(dataFolder, "archive");
		archiveFile.mkdir();
		
		for (File file : dataFolder.listFiles()) {
			if (file.isDirectory()) {
				continue;
			}
			
			File to = new File(archiveFile, file.getName());
			if (! to.exists()) {
				try {
					to.createNewFile();
					
					Files.move(file, to);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		ACPropertiesManager manager = new ACPropertiesManager(plugin);
		for (Entry<String, ACProperties> ships : manager.getACs().entrySet()) {
			convertShip(ships.getValue(), ships.getKey());
		}
		
		File shipArchive = new File(archiveFile, "ships");
		shipArchive.mkdir();
		
		for (File file : folder.listFiles()) {
			if (file.getName().endsWith(extension)) {
				continue;
			}
			
			File to = new File(shipArchive, file.getName());
			if (! to.exists()) {
				try {
					to.createNewFile();
					
					Files.move(file, to);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		plugin.getLogHandler().log("File conversion task completed! [{0} ms]", System.currentTimeMillis() - start);
	}
	
	public void convertShip(ACProperties ship, String name) {
		ShipData data = new ShipData();
		
		data.setAllowedBlocks(ship.ALLOWED_BLOCKS);
		data.setCannonMaterial(ship.CANNON_MATERIAL);
		data.setDropsBomb(ship.DROPS_BOMB);
		data.setDropsNapalm(ship.DROPS_NAPALM);
		data.setFastFlyAtSize(ship.FAST_FLY_AT_SIZE);
		data.setFiresTnt(ship.FIRES_TNT);
		data.setFiresTorpedo(ship.FIRES_TORPEDO);
		data.setIgnoreAttachments(ship.IGNORE_ATTACHMENTS);
		data.setMainType(ship.MAIN_TYPE);
		data.setMaxAltitude(ACProperties.MAX_ALTITUDE);
		data.setMaxBlocks(ship.MAX_BLOCKS);
		data.setMaxCannonLength(ship.MAX_CANNON_LENGTH);
		data.setMaxNumberOfCannons(ship.MAX_NUMBER_OF_CANNONS);
		data.setMaxShipDimensions(ship.MAX_SHIP_DIMENSIONS);
		data.setMinAltitude(ACProperties.MIN_ALTITUDE);
		data.setMinBlocks(ship.MIN_BLOCKS);
		data.setMoveSpeed(ship.MOVE_SPEED);
		data.setShipType(name);

		saveData(data);
	}

	public void saveData(ShipData shipData) {
		File file = new File(folder, getFileName(shipData.getShipType()));
		FileSerialization.save(shipData, file);
	}

	private String getFileName(String key) {
		return key + extension;
	}
	
	public boolean needsConversion() {
		File dataFolder = plugin.getDataFolder();
		for (File file : dataFolder.listFiles()) {
			if (file.isDirectory()) {
				continue;
			}
			
			if (! file.getName().endsWith(extension) && ! file.getName().endsWith(".properties")) {
				return true;
			}
		}
		
		return false;
	}
}