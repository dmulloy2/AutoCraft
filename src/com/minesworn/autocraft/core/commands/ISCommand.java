package com.minesworn.autocraft.core.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

public interface ISCommand {
	
	public void perform();
	
	public void execute(CommandSender sender, String[] args);
	
	public String getName();
	
	public List<String> getAliases();
	
	public String getUsageTemplate(boolean help);
	
	public boolean hasPermission();
	
}
