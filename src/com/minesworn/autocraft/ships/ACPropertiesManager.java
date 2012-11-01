package com.minesworn.autocraft.ships;

import java.io.File;
import java.util.Map;

import com.minesworn.autocraft.Autocraft;
import com.minesworn.autocraft.core.io.SCache;

public class ACPropertiesManager extends SCache<ACProperties, Autocraft> {

	public ACPropertiesManager(Autocraft p) {
		super(p, "ships", new ACFactory());
		for (File f : FOLDER.listFiles()) {
			if (f.getName().endsWith(".txt"))
				f.delete();
		}
		loadAllEntities();
	}
	
	public ACProperties getACProperties(String name) {
		if (getEntity(name) != null)
			getEntity(name).SHIP_TYPE = name;
		return getEntity(name);
	}
	
	public Map<String, ACProperties> getACs() {
		return getEntities();
	}

}
