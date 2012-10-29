package com.minesworn.core.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;

import com.minesworn.core.SPlugin;

public class CmdHelp<S extends SPlugin> extends SCommand<S> {

	public CmdHelp() {
		this.name = "help";
		this.description = "Shows autocraft help.";
	}
	
	@Override
	public void perform() {
		for (String s : getPageLines()) {
			msg(s);
		}		
	}

	public ArrayList<String> getPageLines() {
		ArrayList<String> pageLines = new ArrayList<String>();
		pageLines.add(ChatColor.GOLD + "AutoCraft Help:");
		
		for (SCommand<?> command : s.commandRoot.commands) {
			if (command.hasPermission())
				pageLines.add(command.getUsageTemplate(true));
		}
		
		return pageLines;
	}
	
}
