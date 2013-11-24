package net.dmulloy2.autocraft.commands;

import java.util.ArrayList;
import java.util.List;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.types.Permission;
import net.dmulloy2.autocraft.util.FormatUtil;
import net.dmulloy2.autocraft.util.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author dmulloy2
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
	protected boolean mustBePiloting;
	
	protected List<String> requiredArgs;
	protected List<String> optionalArgs;
	protected List<String> aliases;
	
	protected boolean usesPrefix;
	
	public AutoCraftCommand(AutoCraft plugin) {
		this.plugin = plugin;
		this.requiredArgs = new ArrayList<String>(2);
		this.optionalArgs = new ArrayList<String>(2);
		this.aliases = new ArrayList<String>(2);
	}
	
	public abstract void perform();
	
	@Override
	public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		execute(sender, args);
		return true;
	}
	
	public final void execute(CommandSender sender, String[] args) {
		this.sender = sender;
		this.args = args;
		if (sender instanceof Player)
			player = (Player) sender;
		
		if (mustBePlayer && ! isPlayer()) {
			err(plugin.getMessage("error_must_be_player"));
			return;
		}
		
		if (mustBePiloting && ! isPiloting()) {
			err("You are not piloting a ship!");
			return;
		}
		
		if (requiredArgs.size() > args.length) {
			err(plugin.getMessage("error_arg_count"), getUsageTemplate(false));
			return;
		}
		
		if (! hasPermission()) {
			err(plugin.getMessage("error_insufficient_permissions"));
			return;
		}

		try {
			perform();
		} catch (Throwable e) {
			err("Error executing command: {0}", e.getMessage());
			plugin.getLogHandler().debug(Util.getUsefulStack(e, "executing command " + name));
		}
	}
	
	protected final boolean isPlayer() {
		return player != null;
	}
	
	protected final boolean isPiloting() {
		return plugin.getShipHandler().isPilotingShip(player);
	}
	
	private final boolean hasPermission() {
		return plugin.getPermissionHandler().hasPermission(sender, permission);
	}
	
	protected final boolean argMatchesAlias(String arg, String... aliases) {
		for (String s : aliases) {
			if (arg.equalsIgnoreCase(s)) {
				return true;
			}
		}
		
		return false;
	}
	
	protected final void err(String msg, Object... args) {
		sendpMessage("&c" + msg, args);
	}
	
	protected final void sendpMessage(String msg, Object... args) {
		sender.sendMessage(plugin.getPrefix() + FormatUtil.format(msg, args));
	}
	
	protected final void sendMessage(String msg, Object... args) {
		sender.sendMessage(FormatUtil.format(msg, args));
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
	
	protected final String getMessage(String string) {
		return plugin.getMessage(string);
	}
}