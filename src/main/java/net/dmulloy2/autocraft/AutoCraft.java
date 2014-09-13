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

import java.io.File;
import java.util.List;

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
import net.dmulloy2.autocraft.commands.CmdVersion;
import net.dmulloy2.autocraft.handlers.DataHandler;
import net.dmulloy2.autocraft.handlers.ShipHandler;
import net.dmulloy2.autocraft.integration.FactionsHandler;
import net.dmulloy2.autocraft.listeners.PlayerListener;
import net.dmulloy2.autocraft.types.ShipData;
import net.dmulloy2.commands.CmdHelp;
import net.dmulloy2.handlers.CommandHandler;
import net.dmulloy2.handlers.LogHandler;
import net.dmulloy2.handlers.PermissionHandler;
import net.dmulloy2.handlers.ResourceHandler;
import net.dmulloy2.types.Reloadable;
import net.dmulloy2.util.FormatUtil;
import net.dmulloy2.util.MaterialUtil;

import org.bukkit.Material;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;

/**
 * @author dmulloy2
 */

public class AutoCraft extends SwornPlugin implements Reloadable {
	private @Getter ResourceHandler resourceHandler;
	private @Getter FactionsHandler factionsHandler;
	private @Getter DataHandler dataHandler;
	private @Getter ShipHandler shipHandler;

	private @Getter String prefix = FormatUtil.format("&6[&4&lAC&6] ");

	@Override
	public void onEnable() {
		long start = System.currentTimeMillis();

		// LogHandler first
		logHandler = new LogHandler(this);

		// Configuration
		saveDefaultConfig();
		reloadConfig();

		// Then messages
		File messages = new File(getDataFolder(), "messages.properties");
		if (messages.exists()) {
			messages.delete();
		}

		// saveResource("messages.properties", true);
		resourceHandler = new ResourceHandler(this);

		// Register other handlers
		permissionHandler = new PermissionHandler(this);
		commandHandler = new CommandHandler(this);
		dataHandler = new DataHandler(this);
		shipHandler = new ShipHandler();

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
		commandHandler.registerPrefixedCommand(new CmdVersion(this));

		// Listeners
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(this), this);

		// Integration
		factionsHandler = new FactionsHandler(this);

		// Permissions
		registerPermissions();

		logHandler.log(getMessage("log_enabled"), getDescription().getFullName(), System.currentTimeMillis() - start);
	}

	@Override
	public void onDisable() {
		long start = System.currentTimeMillis();

		getServer().getScheduler().cancelTasks(this);
		shipHandler.clearMemory();

		logHandler.log(getMessage("log_disabled"), getDescription().getFullName(), System.currentTimeMillis() - start);
	}

	public String getMessage(String key) {
		return resourceHandler.getMessage(key);
	}

	public void registerPermissions() {
		PluginManager pm = getServer().getPluginManager();
		for (ShipData data : dataHandler.getData()) {
			if (data.isNeedsPermission()) {
				String node = "autocraft." + data.getShipType().toLowerCase();
				Permission permission = pm.getPermission(node);
				if (permission == null) {
					permission = new Permission(node, PermissionDefault.OP);
					pm.addPermission(permission);
				}
			}
		}
	}

	private List<Material> torpedoMaterials;
	public List<Material> getTorpedoMaterials() {
		if (torpedoMaterials == null) {
			List<String> strings = getConfig().getStringList("materialsNeededForTorpedo");
			torpedoMaterials = MaterialUtil.fromStrings(strings);
		}

		return torpedoMaterials;
	}

	private List<Material> napalmMaterials;
	public List<Material> getNapalmMaterials() {
		if (napalmMaterials == null) {
			List<String> strings = getConfig().getStringList("materialsNeededForNapalm");
			napalmMaterials = MaterialUtil.fromStrings(strings);
		}

		return napalmMaterials;
	}

	@Override
	public void reload() {
		reloadConfig();
		dataHandler.reload();
	}
}