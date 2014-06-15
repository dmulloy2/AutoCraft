/**
 * AutoCraft - a Bukkit plugin 
 * Copyright (C) 2011 - 2014 MineSworn and Affiliates
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.dmulloy2.autocraft;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.logging.Level;

import lombok.Getter;
import net.dmulloy2.SwornPlugin;
import net.dmulloy2.autocraft.commands.CmdAllowed;
import net.dmulloy2.autocraft.commands.CmdDismount;
import net.dmulloy2.autocraft.commands.CmdDrop;
import net.dmulloy2.autocraft.commands.CmdFire;
import net.dmulloy2.autocraft.commands.CmdInfo;
import net.dmulloy2.autocraft.commands.CmdList;
import net.dmulloy2.autocraft.commands.CmdMove;
import net.dmulloy2.autocraft.commands.CmdNapalm;
import net.dmulloy2.autocraft.commands.CmdPilot;
import net.dmulloy2.autocraft.commands.CmdReload;
import net.dmulloy2.autocraft.commands.CmdRotate;
import net.dmulloy2.autocraft.commands.CmdTorpedo;
import net.dmulloy2.autocraft.handlers.DataHandler;
import net.dmulloy2.autocraft.handlers.ShipHandler;
import net.dmulloy2.autocraft.listeners.PlayerListener;
import net.dmulloy2.autocraft.types.ShipData;
import net.dmulloy2.commands.CmdHelp;
import net.dmulloy2.handlers.CommandHandler;
import net.dmulloy2.handlers.LogHandler;
import net.dmulloy2.handlers.PermissionHandler;
import net.dmulloy2.handlers.ResourceHandler;
import net.dmulloy2.types.Reloadable;
import net.dmulloy2.util.FormatUtil;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;

/**
 * @author dmulloy2
 */

public class AutoCraft extends SwornPlugin implements Reloadable {
	private @Getter ResourceHandler resourceHandler;
	private @Getter DataHandler dataHandler;
	private @Getter ShipHandler shipHandler;

	private @Getter boolean factionsEnabled;

	private @Getter String prefix = FormatUtil.format("&6[&4&lAC&6] ");

	@Override
	public void onEnable() {
		long start = System.currentTimeMillis();

		// LogHandler first
		logHandler = new LogHandler(this);

		// Then messages
		saveResource("messages.properties", true);
		resourceHandler = new ResourceHandler(this, getClassLoader());

		// Register other handlers
		permissionHandler = new PermissionHandler(this);
		commandHandler = new CommandHandler(this);
		dataHandler = new DataHandler(this);
		shipHandler = new ShipHandler();

		// Configuration
		saveDefaultConfig();
		reloadConfig();

		// Register commands
		commandHandler.setCommandPrefix("ac");
		commandHandler.registerPrefixedCommand(new CmdAllowed(this));
		commandHandler.registerPrefixedCommand(new CmdDismount(this));
		commandHandler.registerPrefixedCommand(new CmdDrop(this));
		commandHandler.registerPrefixedCommand(new CmdFire(this));
		commandHandler.registerPrefixedCommand(new CmdHelp(this));
		commandHandler.registerPrefixedCommand(new CmdInfo(this));
		commandHandler.registerPrefixedCommand(new CmdList(this));
		commandHandler.registerPrefixedCommand(new CmdMove(this));
		commandHandler.registerPrefixedCommand(new CmdNapalm(this));
		commandHandler.registerPrefixedCommand(new CmdPilot(this));
		commandHandler.registerPrefixedCommand(new CmdReload(this));
		commandHandler.registerPrefixedCommand(new CmdRotate(this));
		commandHandler.registerPrefixedCommand(new CmdTorpedo(this));

		// Listeners
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(this), this);

		// Factions integration
		if (getConfig().getBoolean("factionsProtectionsEnabled", false)) {
			factionsEnabled = pm.getPlugin("SwornNations") != null;

			if (factionsEnabled) {
				logHandler.log(getMessage("log_factions_found"));
			} else {
				logHandler.log(getMessage("log_factions_notfound"));
			}
		}

		// Permissions
		registerPermissions();

		logHandler.log(getMessage("log_enabled"), getDescription().getFullName(), System.currentTimeMillis() - start);
	}

	@Override
	public void onDisable() {
		long start = System.currentTimeMillis();

		getServer().getScheduler().cancelTasks(this);
		shipHandler.clearMemory();
		permissions.clear();

		logHandler.log(getMessage("log_disabled"), getDescription().getFullName(), System.currentTimeMillis() - start);
	}

	public String getMessage(String string) {
		try {
			return resourceHandler.getMessages().getString(string);
		} catch (MissingResourceException ex) {
			logHandler.log(Level.WARNING, getMessage("log_message_missing"), string);
			return null;
		}
	}

	private List<Permission> permissions;

	public void registerPermissions() {
		this.permissions = new ArrayList<Permission>();

		for (ShipData data : dataHandler.getData()) {
			PermissionDefault def = data.isNeedsPermission() ? PermissionDefault.FALSE : PermissionDefault.TRUE;
			Permission perm = new Permission("autocraft." + data.getShipType().toLowerCase(), def);
			getServer().getPluginManager().addPermission(perm);
			permissions.add(perm);
		}
	}

	@Override
	public void reload() {
		reloadConfig();
		dataHandler.reload();
	}
}