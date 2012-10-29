package com.minesworn.autocraft.core;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.minesworn.autocraft.core.commands.CmdHelp;
import com.minesworn.autocraft.core.commands.SCommand;
import com.minesworn.autocraft.core.commands.SCommandRoot;
import com.minesworn.autocraft.core.util.SLang;
import com.minesworn.autocraft.core.util.Util;

public abstract class SPlugin extends JavaPlugin implements ISPlugin {
	private static String PLUGIN_NAME;
	private static ArrayList<String> COMMAND_PREFIXES = new ArrayList<String>();
	private static ArrayList<String> enabledSoftDependPlugins = new ArrayList<String>();
	
	public static SPlugin p;
	public static SLang lang;
	public static SCommandRoot commandRoot;
	public volatile static boolean enabled;
	
	public boolean preEnable() {
		p = this;
		PLUGIN_NAME = this.getName();
		
		if (this.getDescription().getDepend() != null)
			for (String s : this.getDescription().getDepend())
				if (!Bukkit.getPluginManager().isPluginEnabled(s)) {
					log(s + " not found. Disabling " + PLUGIN_NAME + ".");
					return false;
				}
		
		if (this.getDescription().getSoftDepend() != null)
			for (String s : this.getDescription().getSoftDepend()) {
				if (!Bukkit.getPluginManager().isPluginEnabled(s))
					log(s + " not found. Disabling " + s + " related features.");
				else {
					log(s + " was found! Enabling " + s + " related features.");
					enabledSoftDependPlugins.add(s);
				}
			}
		
		if (this.getDescription().getCommands().size() >= 1)
			for (Entry<String, Map<String, Object>> entry : this.getDescription().getCommands().entrySet())
				COMMAND_PREFIXES.add(entry.getKey());
		
		if (!this.getDataFolder().exists())
			this.getDataFolder().mkdirs();
		
		enabled = true;
		return true;
	}
	
	@Override
	public void onEnable() {
		preEnable();
		lang = new SLang();
		SLang.load();
		commandRoot = new SCommandRoot();
	}
	
	public void preDisable() {
		enabled = false;
	}
	
	@Override
	public void onDisable() {
		preDisable();
	}
	
	public void reload() {
		new ReloadThread();
	}
	
	public void afterReload() {
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {		
		return onCommand(sender, cmd, lbl, args, true);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args, boolean helpIfNone) {
		String cmdlbl = "";
		ArrayList<String> argList = new ArrayList<String>();
		if (getCommandPrefix() == null) {
			cmdlbl = cmd.getName();
			for (int i = 0; i < args.length; i++) {
				argList.add(args[i]);
			}
		} else if (args.length > 0) {
			cmdlbl = args[0];
			for (int i = 1; i < args.length; i++) {
				argList.add(args[i]);
			}
		}
		
		for (SCommand<?> command : commandRoot.commands) {	
			if (cmdlbl.equalsIgnoreCase(command.getName()) || command.getAliases().contains(cmdlbl.toLowerCase())) {				
				args = argList.toArray(new String[0]);				
				
				command.execute(sender, args);
				return true;
			}
		}
		
		if (helpIfNone) {
			new CmdHelp().execute(sender, args);
			return true;
		}
		return false;
	}
	
	public static String getCommandPrefix() {return (!COMMAND_PREFIXES.isEmpty()) ? COMMAND_PREFIXES.get(0) : null;}	
	public static boolean isPluginEnabled(String s) {return enabledSoftDependPlugins.contains(s);}
	
	public static void log(String msg) {
		log(Level.INFO, msg);
	}
	
	public static void log(Level level, String msg) {
		Bukkit.getLogger().log(level, "[" + PLUGIN_NAME + "] " + msg);
	}
	
	public static void log(String msg, Object... args) {
		log(Level.INFO, Util.parseMsg(msg, args));
	}
	

}
