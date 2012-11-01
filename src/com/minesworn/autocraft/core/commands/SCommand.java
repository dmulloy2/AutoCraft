package com.minesworn.autocraft.core.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minesworn.autocraft.Autocraft;
import com.minesworn.autocraft.core.SPlugin;
import com.minesworn.autocraft.core.permissions.PermissionBase;
import com.minesworn.autocraft.core.util.Util;

public abstract class SCommand<S extends SPlugin> implements ISCommand {

	S s;
	
	public CommandSender sender;
	public Player player;
	public String args[];
	public boolean isPlayer = false;
	public boolean mustBePlayer = false;
	
	public String permission = new String();
	public String name = new String();
	public String description = new String();
	
	public List<String> requiredArgs = new ArrayList<String>(2);
	public List<String> optionalArgs = new ArrayList<String>(2);
	public List<String> aliases = new ArrayList<String>(2);
	
	public void execute(CommandSender sender, String[] args) {
		this.sender = sender;
		this.args = args;
		if (sender instanceof Player) {
			isPlayer = true;
			player = (Player) sender;
		}
		
		if (mustBePlayer && !isPlayer) {
			errorMessage(s.lang.getErrorMessage("mustbeplayer"));
			return;
		}
		
		if (requiredArgs.size() > args.length) {
			errorMessage(s.lang.getErrorMessage("argcount") + getUsageTemplate(false));
			return;
		}
		
		if (hasPermission()) {
			perform();
		} else
			errorMessage(Autocraft.p.lang.getErrorMessage("permission"));
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<String> getAliases() {
		return this.aliases;
	}
	
	public boolean hasPermission() {
		return (permission == null || this.permission == "") ? true : PermissionBase.hasPermission(sender, permission);
	}
	
	public void msg(String msg) {
		sender.sendMessage(msg);
	}
	
	public void errorMessage(String msg, Object... args) {
		sender.sendMessage(ChatColor.RED + "Error: "+ Util.parseMsg(msg, args));
	}
	
	public void confirmMessage(String msg, Object... args) {
		sender.sendMessage(ChatColor.YELLOW + Util.parseMsg(msg, args));
	}
	
	public String getUsageTemplate(boolean help) {
		StringBuilder ret = new StringBuilder();
		ret.append(ChatColor.RED + "/");
		
		if (s.getCommandPrefix() != null)
			ret.append(s.getCommandPrefix() + " ");
		
		ret.append( name);
		
		if (aliases.size() != 0) {
			ret.append(ChatColor.GOLD + "(");
			for (int i = 0; i < aliases.size(); i++) {
				ret.append(aliases.get(i) + ((i == aliases.size() - 1) ? "" : ", "));
			}
			ret.append(")");
		}
		
		ret.append(ChatColor.DARK_RED + " ");
		
		for (String s : requiredArgs) {
			ret.append("<" + s + "> ");
		}
		
		for (String s : optionalArgs) {
			ret.append("[" + s + "] ");
		}
		
		if (help) {
			ret.append(ChatColor.YELLOW + description);
		}
		
		return ret.toString();
	}
	
}
