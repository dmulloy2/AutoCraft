package com.minesworn.autocraft;

import com.minesworn.autocraft.commands.ACCommandRoot;
import com.minesworn.autocraft.core.SPlugin;
import com.minesworn.autocraft.ships.ACPropertiesManager;
import com.minesworn.autocraft.ships.ACShipManager;

public class Autocraft extends SPlugin {

	public static ACPropertiesManager propertiesmanager;
	public static ACShipManager shipmanager;
	
	@Override
	public void onEnable() {
		preEnable();
		
		Config.load();
		shipmanager = new ACShipManager();
		propertiesmanager = new ACPropertiesManager();
		commandRoot = new ACCommandRoot();

		registerEvents();
	}
	
	@Override
	public void onDisable() {
		preDisable();
	}
	
	public void registerEvents() {
		
	}
	
}
