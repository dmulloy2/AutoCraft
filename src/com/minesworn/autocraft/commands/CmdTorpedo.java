package com.minesworn.autocraft.commands;

import com.minesworn.autocraft.Autocraft;
import com.minesworn.autocraft.PermissionsManager.Permission;

public class CmdTorpedo extends ACCommand {

	public CmdTorpedo() {
		this.name = "torpedo";
		this.aliases.add("to");
		this.description = "Fire a torpedo from your ship";
		this.permission = Permission.CMD_TORPEDO.node;
		this.mustBePlayer = true;
	}
	
	@Override
	public void perform() {
		if (Autocraft.shipmanager.ships.containsKey(player.getName())) {
			Autocraft.shipmanager.ships.get(player.getName()).fireTorpedo();
		}		
	}

}
