/**
 * Copyright (C) 2012 t7seven7t
 */
package net.dmulloy2.autocraft.commands;

import java.util.ArrayList;
import java.util.List;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.permissions.Permission;
import net.dmulloy2.autocraft.util.FormatUtil;
import net.dmulloy2.autocraft.util.Util;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author t7seven7t
 */
public abstract class AutoCraftCommand implements CommandExecutor {
	protected final AutoCraft plugin;
	
	protected CommandSender sender;
	protected Player player;
	protected String args[];
	
	protected String name;
	protected String description;
	protected Permission permission;
	
	protected boolean mustBePlayer;
	protected List<String> requiredArgs;
	protected List<String> optionalArgs;
	protected List<String> aliases;
	
	protected boolean usesPrefix;
	
	public AutoCraftCommand(AutoCraft plugin) {
		this.plugin = plugin;
		requiredArgs = new ArrayList<String>(2);
		optionalArgs = new ArrayList<String>(2);
		aliases = new ArrayList<String>(2);
	}
	
	public abstract void perform();
	
	public final boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		execute(sender, args);
		return true;
	}
	
	public final void execute(CommandSender sender, String[] args) {
		this.sender = sender;
		this.args = args;
		if (sender instanceof Player)
			player = (Player) sender;
		
		if (mustBePlayer && !isPlayer()) {
			err(plugin.getMessage("error_must_be_player"));
			return;
		}
		
		if (requiredArgs.size() > args.length) {
			err(plugin.getMessage("error_arg_count"), getUsageTemplate(false));
			return;
		}
		
		if (hasPermission())
			perform();
		else
			err(plugin.getMessage("error_insufficient_permissions"));
	}
	
	protected final boolean isPlayer() {
		return (player != null);
	}
	
	private final boolean hasPermission() {
		return (plugin.getPermissionHandler().hasPermission(sender, permission));
	}
	
	protected final boolean argMatchesAlias(String arg, String... aliases) {
		for (String s : aliases)
			if (arg.equalsIgnoreCase(s))
				return true;
		return false;
	}
	
	protected final void err(String msg, Object... args) {
		sendMessage(plugin.getMessage("error"), FormatUtil.format(msg, args));
	}
	
	protected final void sendMessage(String msg, Object... args) {
		sender.sendMessage(ChatColor.YELLOW + FormatUtil.format(msg, args));
	}

	public final String getName() {
		return name;
	}

	public final List<String> getAliases() {
		return aliases;
	}

	public final String getUsageTemplate(final boolean displayHelp) {
		StringBuilder ret = new StringBuilder();
		ret.append("&b/ac ");

		ret.append(name);
		
		for (String s : aliases)
			ret.append("," + s);
		
		ret.append("&3 ");
		for (String s : requiredArgs)
			ret.append(String.format("<%s> ", s));
		
		for (String s : optionalArgs)
			ret.append(String.format("[%s] ", s));
		
		if (displayHelp)
			ret.append("&e" + description);
		
		return FormatUtil.format(ret.toString());
	}
	
	protected OfflinePlayer getTarget(String name) {
		OfflinePlayer target = Util.matchOfflinePlayer(name);
		if (target == null)
			err(plugin.getMessage("error_player_not_found"), name);
		return target;
	}
}