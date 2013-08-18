package net.dmulloy2.autocraft.legacy;

import java.io.File;
import java.util.Map;

import net.dmulloy2.autocraft.AutoCraft;

public class ACPropertiesManager extends SCache<ACProperties, AutoCraft> {

	public ACPropertiesManager(AutoCraft p) {
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
