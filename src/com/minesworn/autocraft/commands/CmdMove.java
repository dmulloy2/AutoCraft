/**
 * Copyright (C) 2012 t7seven7t
 */
package com.minesworn.autocraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.util.Vector;

import com.minesworn.autocraft.Autocraft;
import com.minesworn.autocraft.PermissionsManager.Permission;

/**
 * @author t7seven7t
 */
public class CmdMove extends ACCommand {

	public CmdMove() {
		this.name = "move";
		this.aliases.add("m");
		this.permission = Permission.CMD_MOVE.node;
		this.description = "Moves your ship in direction you are facing.";
		this.mustBePlayer = true;
	}
	
	public void perform() {
		if (Autocraft.shipmanager.ships.containsKey(player.getName())) {
			Vector dir = player.getLocation().getDirection();
			
			Autocraft.shipmanager.ships.get(player.getName()).move( (int) Math.round(dir.getX()),
																	(int) Math.round(dir.getY()),
																	(int) Math.round(dir.getZ()));
		} else {
			msg(ChatColor.GRAY + "You are not piloting a ship.");
		}
	}
	
}
