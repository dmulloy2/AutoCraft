package net.dmulloy2.autocraft.types;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;

import net.dmulloy2.autocraft.AutoCraft;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ShipData {
	private transient AutoCraft plugin;
	
	private transient FileConfiguration fc;
	
	private transient boolean loaded;
	
	private int maxAltitude = 254;
	private int minAltitude = 2;
	private String shipType;
	private int maxBlocks;
	private int minBlocks;
	private int maxShipDimensions;
	private int mainType;
	private int moveSpeed;
	private boolean dropsNapalm;
	private boolean dropsBomb;
	private boolean firesTnt;
	private boolean firesTorpedo;
	private boolean ignoreAttachments = false;
	private int maxCannonLength;
	private int maxNumberOfCannons;
	private int cannonMaterial;
	private int fastFlyAtSize = 1000;
	private List<Integer> allowedBlocks = new ArrayList<Integer>();
	
	public ShipData(AutoCraft plugin) {
		this.plugin = plugin;
	}
	
	public ShipData(AutoCraft plugin, File file) {
		this.plugin = plugin;
		
		this.fc = YamlConfiguration.loadConfiguration(file);
		
		try {
			load();
		} catch (Exception e) {
			plugin.getLogHandler().log(Level.SEVERE, "Could not load ship {0}: {1}", file.getName(), e);
		} finally {
			plugin.getLogHandler().log("Loaded ship: {0}", shipType);
			loaded = true;
		}
	}
	
	public void load() throws Exception {
		for (Entry<String, Object> entry : fc.getValues(true).entrySet()) {
			for (Field f : getClass().getDeclaredFields()) {
				if (Modifier.isTransient(f.getModifiers()))
					continue;
				
				if (f.getName().equalsIgnoreCase(entry.getKey())) {
					f.set(f.getType(), entry.getValue());
				}
			}
		}
	}
	
	public void save() throws Exception {
		File file = new File(plugin.getDataFolder(), shipType + ".yml");
		for (Field f : getClass().getDeclaredFields()) {
			fc.set(f.getName(), f.get(f.getType()));
		}
		
		fc.save(file);
	}

	public int getMaxAltitude() {
		return maxAltitude;
	}

	public void setMaxAltitude(int maxAltitude) {
		this.maxAltitude = maxAltitude;
	}

	public int getMinAltitude() {
		return minAltitude;
	}

	public void setMinAltitude(int minAltitude) {
		this.minAltitude = minAltitude;
	}

	public String getShipType() {
		return shipType;
	}

	public void setShipType(String shipType) {
		this.shipType = shipType;
	}

	public int getMaxBlocks() {
		return maxBlocks;
	}

	public void setMaxBlocks(int maxBlocks) {
		this.maxBlocks = maxBlocks;
	}

	public int getMinBlocks() {
		return minBlocks;
	}

	public void setMinBlocks(int minBlocks) {
		this.minBlocks = minBlocks;
	}

	public int getMaxShipDimensions() {
		return maxShipDimensions;
	}

	public void setMaxShipDimensions(int maxShipDimensions) {
		this.maxShipDimensions = maxShipDimensions;
	}

	public int getMainType() {
		return mainType;
	}

	public void setMainType(int mainType) {
		this.mainType = mainType;
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public boolean isDropsNapalm() {
		return dropsNapalm;
	}

	public void setDropsNapalm(boolean dropsNapalm) {
		this.dropsNapalm = dropsNapalm;
	}

	public boolean isDropsBomb() {
		return dropsBomb;
	}

	public void setDropsBomb(boolean dropsBomb) {
		this.dropsBomb = dropsBomb;
	}

	public boolean isFiresTnt() {
		return firesTnt;
	}

	public void setFiresTnt(boolean firesTnt) {
		this.firesTnt = firesTnt;
	}

	public boolean isFiresTorpedo() {
		return firesTorpedo;
	}

	public void setFiresTorpedo(boolean firesTorpedo) {
		this.firesTorpedo = firesTorpedo;
	}

	public boolean isIgnoreAttachments() {
		return ignoreAttachments;
	}

	public void setIgnoreAttachments(boolean ignoreAttachments) {
		this.ignoreAttachments = ignoreAttachments;
	}

	public int getMaxCannonLength() {
		return maxCannonLength;
	}

	public void setMaxCannonLength(int maxCannonLength) {
		this.maxCannonLength = maxCannonLength;
	}

	public int getMaxNumberOfCannons() {
		return maxNumberOfCannons;
	}

	public void setMaxNumberOfCannons(int maxNumberOfCannons) {
		this.maxNumberOfCannons = maxNumberOfCannons;
	}

	public int getCannonMaterial() {
		return cannonMaterial;
	}

	public void setCannonMaterial(int cannonMaterial) {
		this.cannonMaterial = cannonMaterial;
	}

	public int getFastFlyAtSize() {
		return fastFlyAtSize;
	}

	public void setFastFlyAtSize(int fastFlyAtSize) {
		this.fastFlyAtSize = fastFlyAtSize;
	}

	public List<Integer> getAllowedBlocks() {
		return allowedBlocks;
	}

	public void setAllowedBlocks(List<Integer> allowedBlocks) {
		this.allowedBlocks = allowedBlocks;
	}
	
	public boolean isLoaded() {
		return loaded;
	}
}