package com.minesworn.core;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.minesworn.core.commands.CmdHelp;
import com.minesworn.core.commands.SCommand;
import com.minesworn.core.commands.SCommandRoot;
import com.minesworn.core.util.SLang;
import com.minesworn.core.util.Util;

public abstract class SPlugin extends JavaPlugin implements ISPlugin {
	private String PLUGIN_NAME;
	private ArrayList<String> COMMAND_PREFIXES = new ArrayList<String>();
	private ArrayList<String> enabledSoftDependPlugins = new ArrayList<String>();
	
	public SPlugin p;
	public SLang lang;
	public SCommandRoot<?> commandRoot;
	public volatile boolean enabled;
		
	public boolean preEnable(SPlugin p) {		
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
		
		if (this.getDescription().getCommands().size() == 1)
			for (Entry<String, Map<String, Object>> entry : this.getDescription().getCommands().entrySet())
				COMMAND_PREFIXES.add(entry.getKey());
		
		if (!this.getDataFolder().exists())
			this.getDataFolder().mkdirs();
		
		enabled = true;
		return true;
	}
	
	@Override
	public void onEnable() {
		preEnable(this);
		lang = new SLang(p);
		lang.load();
		commandRoot = new SCommandRoot<SPlugin>(p);
	}
	
	public void preDisable() {
		enabled = false;
	}
	
	@Override
	public void onDisable() {
		preDisable();
	}
	
	public void reload() {
		new ReloadThread(p);
	}
	
	public void afterReload() {
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {		
		String cmdlbl;
		if (getCommandPrefix() == null)
			cmdlbl = cmd.getName();
		else
			cmdlbl = args[0];
		
		for (SCommand<?> command : commandRoot.commands) {	
			if (cmdlbl.equalsIgnoreCase(command.getName()) || command.getAliases().contains(cmdlbl.toLowerCase())) {
				ArrayList<String> argList = new ArrayList<String>();
				for (int i = 0; i < args.length; i++) {
					argList.add(args[i]);
				}
				
				args = argList.toArray(new String[0]);				
				
				command.execute(sender, args);
				return true;
			}
		}
		
		new CmdHelp().execute(sender, args);
		return true;
	}
	
	public String getCommandPrefix() {return (!COMMAND_PREFIXES.isEmpty()) ? COMMAND_PREFIXES.get(0) : null;}	
	public boolean isPluginEnabled(String s) {return enabledSoftDependPlugins.contains(s);}
	
	public void log(String msg) {
		log(Level.INFO, msg);
	}
	
	public void log(Level level, String msg) {
		Bukkit.getLogger().log(level, "[" + PLUGIN_NAME + "] " + msg);
	}
	
	public void log(String msg, Object... args) {
		log(Level.INFO, Util.parseMsg(msg, args));
	}	

}
