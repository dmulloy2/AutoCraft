/**
* AutoCraft - a Bukkit plugin
* Copyright (C) 2011-2013 MineSworn
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*/
package net.dmulloy2.autocraft;

import java.util.MissingResourceException;
import java.util.logging.Level;

import lombok.Getter;
import net.dmulloy2.autocraft.commands.CmdAllowed;
import net.dmulloy2.autocraft.commands.CmdDismount;
import net.dmulloy2.autocraft.commands.CmdDrop;
import net.dmulloy2.autocraft.commands.CmdFire;
import net.dmulloy2.autocraft.commands.CmdHelp;
import net.dmulloy2.autocraft.commands.CmdInfo;
import net.dmulloy2.autocraft.commands.CmdList;
import net.dmulloy2.autocraft.commands.CmdMove;
import net.dmulloy2.autocraft.commands.CmdNapalm;
import net.dmulloy2.autocraft.commands.CmdPilot;
import net.dmulloy2.autocraft.commands.CmdReload;
import net.dmulloy2.autocraft.commands.CmdRotate;
import net.dmulloy2.autocraft.commands.CmdTorpedo;
import net.dmulloy2.autocraft.commands.CommandHandler;
import net.dmulloy2.autocraft.io.DataHandler;
import net.dmulloy2.autocraft.io.FileConverter;
import net.dmulloy2.autocraft.io.ResourceHandler;
import net.dmulloy2.autocraft.listeners.PlayerListener;
import net.dmulloy2.autocraft.permissions.PermissionHandler;
import net.dmulloy2.autocraft.ships.ShipManager;
import net.dmulloy2.autocraft.util.FormatUtil;
import net.dmulloy2.autocraft.util.LogHandler;

import org.bukkit.plugin.java.JavaPlugin;

public class AutoCraft extends JavaPlugin {
	private @Getter PermissionHandler permissionHandler;
	private @Getter ResourceHandler resourceHandler;
	private @Getter CommandHandler commandHandler;
	private @Getter LogHandler logHandler;

	private @Getter DataHandler dataHandler;
	private @Getter ShipManager shipManager;
	private @Getter boolean factionsEnabled;
	
	private @Getter String prefix = FormatUtil.format("&4[&6&lAC&4]&6 ");
	
	@Override
	public void onEnable() {
		long start = System.currentTimeMillis();
		
		saveDefaultConfig();
		
		permissionHandler = new PermissionHandler(this);
		commandHandler = new CommandHandler(this);
		logHandler = new LogHandler(this);
		
		// Converts files to 3.x format
		new FileConverter(this).run();
		
		saveResource("messages.properties", true);
		resourceHandler = new ResourceHandler(this, getClassLoader());
		
		shipManager = new ShipManager();
		dataHandler = new DataHandler(this);
		
		commandHandler.setCommandPrefix("ac");
		commandHandler.registerCommand(new CmdAllowed(this));
		commandHandler.registerCommand(new CmdDismount(this));
		commandHandler.registerCommand(new CmdDrop(this));
		commandHandler.registerCommand(new CmdFire(this));
		commandHandler.registerCommand(new CmdHelp(this));
		commandHandler.registerCommand(new CmdInfo(this));
		commandHandler.registerCommand(new CmdList(this));
		commandHandler.registerCommand(new CmdMove(this));
		commandHandler.registerCommand(new CmdNapalm(this));
		commandHandler.registerCommand(new CmdPilot(this));
		commandHandler.registerCommand(new CmdReload(this));
		commandHandler.registerCommand(new CmdRotate(this));
		commandHandler.registerCommand(new CmdTorpedo(this));
		
		if (getConfig().getBoolean("factionsProtectionsEnabled") && 
				(getServer().getPluginManager().isPluginEnabled("SwornNations") || 
						getServer().getPluginManager().isPluginEnabled("Factions"))) {
			
			factionsEnabled = true;
			
		}
		
		if (factionsEnabled) {
			logHandler.log(getMessage("log_factions_found"));
		} else  {
			logHandler.log(getMessage("log_factions_notfound"));
		}

		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		
		long finish = System.currentTimeMillis();
		
		logHandler.log(getMessage("log_enabled"), getDescription().getFullName(), finish - start);
	}
	
	@Override
	public void onDisable() {
		long start = System.currentTimeMillis();
		
		dataHandler.clearMemory();
		shipManager.clearMemory();
		
		long finish = System.currentTimeMillis();
		
		logHandler.log(getMessage("log_disabled"), getDescription().getFullName(), finish - start);
	}

	public String getMessage(String string) {
		try {
			return resourceHandler.getMessages().getString(string);
		} catch (MissingResourceException ex) {
			logHandler.log(Level.WARNING, getMessage("log_message_missing"), string);
			return null;
		}
	}
}