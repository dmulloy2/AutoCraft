package com.minesworn.autocraft.commands;

import org.bukkit.ChatColor;

import com.minesworn.autocraft.Autocraft;
import com.minesworn.autocraft.PermissionsManager.Permission;
import com.minesworn.autocraft.ships.ACBaseShip;

public class CmdInfo extends ACCommand {

	public CmdInfo() {
		this.name = "info";
		this.aliases.add("i");
		this.description = "View your ship's info.";
		this.mustBePlayer = true;
		this.permission = Permission.CMD_INFO.node;
	}
	
	@Override
	public void perform() {
		if (Autocraft.shipmanager.ships.containsKey(player.getName())) {
			ACBaseShip ship = Autocraft.shipmanager.ships.get(player.getName());
			confirmMessage("YOUR SHIP DATA:");
			confirmMessage("<x> " + 		ChatColor.GREEN + player.getLocation().getBlockX());
			confirmMessage("<y> " + 		ChatColor.GREEN + player.getLocation().getBlockY());
			confirmMessage("<z> " + 		ChatColor.GREEN + player.getLocation().getBlockZ());
			confirmMessage("<# blocks> " + 	ChatColor.GREEN + ship.getBlocks().length + 
											ChatColor.YELLOW + "/" + 
											ChatColor.GREEN + ship.properties.MAX_BLOCKS);
			confirmMessage("<# cannons> " + ChatColor.GREEN + ship.getCannons().length +
											ChatColor.YELLOW + "/" +
											ChatColor.GREEN + ship.properties.MAX_NUMBER_OF_CANNONS);
		} else
			errorMessage("You need to be piloting a ship to do this");
	}

}
