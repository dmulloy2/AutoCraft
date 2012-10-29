package com.minesworn.autocraft;

import com.minesworn.autocraft.commands.ACCommandRoot;
import com.minesworn.autocraft.ships.ACPropertiesManager;
import com.minesworn.autocraft.ships.ACShipManager;
import com.minesworn.core.SPlugin;

public class Autocraft extends SPlugin {

	public static Autocraft p;
	public static ACPropertiesManager propertiesmanager;
	public static ACShipManager shipmanager;
	
	@Override
	public void onEnable() {
		Autocraft.p = this;
		preEnable(this);
		
		Config.load();
		shipmanager = new ACShipManager();
		propertiesmanager = new ACPropertiesManager(p);
		commandRoot = new ACCommandRoot(p);

		registerEvents();
	}
	
	@Override
	public void onDisable() {
		preDisable();
	}
	
	public void registerEvents() {
		
	}
	
}
