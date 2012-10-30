package com.minesworn.autocraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.minesworn.autocraft.Autocraft;
import com.minesworn.autocraft.PermissionsManager.Permission;
import com.minesworn.autocraft.ships.ACProperties;

public class CmdAllowed extends ACCommand {

	public CmdAllowed() {
		this.name = "allowed";
		this.aliases.add("a");
		this.description = "List block types allowed on type of ship.";
		this.requiredArgs.add("ship type");
		this.permission = Permission.CMD_ALLOWED.node;
	}
	
	@Override
	public void perform() {
		if (Autocraft.propertiesmanager.getACs().containsKey(args[0].toLowerCase())) {
			confirmMessage(ChatColor.GOLD + "Allowed block types for " + ChatColor.YELLOW + args[0].toLowerCase());
			
			ACProperties shipproperties = Autocraft.propertiesmanager.getACProperties(args[0].toLowerCase());
			confirmMessage("CAN FIRE TNT (cannon): " + ChatColor.GREEN + shipproperties.FIRES_TNT);
			confirmMessage("CAN FIRE TORPEDO: " + ChatColor.GREEN + shipproperties.FIRES_TORPEDO);
			confirmMessage("CAN DROP BOMBS: " + ChatColor.GREEN + shipproperties.DROPS_BOMB);
			confirmMessage("CAN DROP NAPALM: " + ChatColor.GREEN + shipproperties.DROPS_NAPALM);
			if (shipproperties.FIRES_TNT || shipproperties.FIRES_TORPEDO) {
				confirmMessage("MAX CANNON LENGTH: " + ChatColor.GREEN + shipproperties.MAX_CANNON_LENGTH);
				confirmMessage("CANNON MATERIAL: " + ChatColor.GREEN + Material.getMaterial(shipproperties.CANNON_MATERIAL).toString());
			}
			confirmMessage("MIN BLOCKS: " + ChatColor.GREEN + shipproperties.MIN_BLOCKS);
			confirmMessage("MAX BLOCKS: " + ChatColor.GREEN + shipproperties.MAX_BLOCKS);
			confirmMessage("MAIN BLOCK: " + ChatColor.GREEN + Material.getMaterial(shipproperties.MAIN_TYPE).toString());
			confirmMessage("ALLOWED BLOCK(s): ");
			StringBuilder ret = new StringBuilder();
			for (int id : shipproperties.ALLOWED_BLOCKS) {
				ret.append(Material.getMaterial(id).toString() + ", ");
			}
			if (ret.lastIndexOf(",") != -1)
				ret.deleteCharAt(ret.lastIndexOf(","));
			confirmMessage(ChatColor.GREEN + ret.toString());
		} else
			errorMessage("No such ship");
	}

}
