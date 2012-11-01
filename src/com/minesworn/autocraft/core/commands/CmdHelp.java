package com.minesworn.autocraft.core.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;

import com.minesworn.autocraft.Autocraft;
import com.minesworn.autocraft.core.SPlugin;

public class CmdHelp<S extends SPlugin> extends SCommand<S> {

	public CmdHelp() {
		this.name = "help";
		this.description = "Shows autocraft help.";
		this.permission = "";
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
		
		for (SCommand<?> command : Autocraft.p.commandRoot.commands) {
			if (command.hasPermission())
				pageLines.add(command.getUsageTemplate(true));
		}
		
		return pageLines;
	}
	
}
