package com.minesworn.autocraft.commands;

import org.bukkit.ChatColor;

import com.minesworn.autocraft.Autocraft;
import com.minesworn.autocraft.PermissionsManager.Permission;

public class CmdList extends ACCommand {

	public CmdList() {
		this.name = "list";
		this.aliases.add("ls");
		this.description = "List the AutoCraft ship types";
		this.permission = Permission.CMD_LIST.node;
	}
	
	@Override
	public void perform() {
		confirmMessage(ChatColor.GOLD + "AUTOCRAFT SHIP TYPES");
		
		for (String shipType : Autocraft.propertiesmanager.getACs().keySet()) {
			confirmMessage(shipType);
		}
	}

}
