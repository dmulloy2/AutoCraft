package com.minesworn.autocraft;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.minesworn.autocraft.commands.ACCommandRoot;
import com.minesworn.autocraft.listeners.PlayerListener;
import com.minesworn.autocraft.ships.ACPropertiesManager;
import com.minesworn.autocraft.ships.ACShipManager;
import com.minesworn.core.SPlugin;
import com.minesworn.core.commands.CmdHelp;
import com.minesworn.core.util.SLang;

public class Autocraft extends SPlugin {

	public static Autocraft p;
	public static ACPropertiesManager propertiesmanager;
	public static ACShipManager shipmanager;
	
	@Override
	public void onEnable() {
		Autocraft.p = this;
		preEnable();
		
		lang = new SLang(this);
		lang.load();
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
	
	@Override
	public void newHelpCommand(CommandSender sender, String[] args) {
		 new CmdHelp<Autocraft>().execute(sender, args);
	}
	
	public void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new PlayerListener(p), p);
	}
	
}
