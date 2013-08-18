package net.dmulloy2.autocraft.types;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class ShipData implements ConfigurationSerializable {

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

	public ShipData() {
		
	}
	
	public ShipData(Map<String, Object> args) {
		for (Entry<String, Object> entry : args.entrySet()) {
			try {
				for (Field field : getClass().getDeclaredFields()) {
					if (field.getName().equals(entry.getKey())) {
						boolean accessible = field.isAccessible();
						if (!accessible)
							field.setAccessible(true);
												
						field.set(this, entry.getValue());
												
						if (!accessible)
							field.setAccessible(false);
					}
				}
			} catch (Throwable ex) {
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> data = new HashMap<String, Object>();
		
		for (Field field : getClass().getDeclaredFields()) {
			if (Modifier.isTransient(field.getModifiers()))
				continue;
			
			try {
				boolean accessible = field.isAccessible();
				
				if (!accessible)
					field.setAccessible(true);
				
				if (field.getType().equals(Integer.TYPE)) {
					if (field.getInt(this) != 0)
						data.put(field.getName(), field.getInt(this));
				} else if (field.getType().equals(Long.TYPE)) {
					if (field.getLong(this) != 0)
						data.put(field.getName(), field.getLong(this));
				} else if (field.getType().equals(Boolean.TYPE)) {
					if (field.getBoolean(this))
						data.put(field.getName(), field.getBoolean(this));
				} else if (field.getType().isAssignableFrom(Collection.class)) {
					if (!((Collection) field.get(this)).isEmpty())
						data.put(field.getName(), field.get(this));
				} else if (field.getType().isAssignableFrom(String.class)) {
					if (((String) field.get(this)) != null)
						data.put(field.getName(), field.get(this));
				} else if (field.getType().isAssignableFrom(Map.class)) {
					if (!((Map) field.get(this)).isEmpty())
						data.put(field.getName(), field.get(this));
				} else {
					if (field.get(this) != null)
						data.put(field.getName(), field.get(this));
				}
								
				if (!accessible)
					field.setAccessible(false);
				
			} catch (Throwable ex) {
			}
		}
		
		return data;
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
}