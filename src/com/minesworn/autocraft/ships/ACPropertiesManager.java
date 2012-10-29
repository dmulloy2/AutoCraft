package com.minesworn.autocraft.ships;

import java.util.Map;

import com.minesworn.autocraft.core.io.SCache;

public class ACPropertiesManager extends SCache<ACProperties> {

	public ACPropertiesManager() {
		super("ships", new ACFactory());
	}
	
	public ACProperties getACProperties(String name) {
		return getEntity(name);
	}
	
	public Map<String, ACProperties> getACs() {
		return getEntities();
	}

}
