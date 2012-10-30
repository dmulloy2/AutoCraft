package com.minesworn.autocraft.commands;

import com.minesworn.autocraft.Autocraft;
import com.minesworn.autocraft.PermissionsManager.Permission;

public class CmdDismount extends ACCommand {

	public CmdDismount() {
		this.name = "dismount";
		this.aliases.add("x");
		this.description = "Dismount your airship.";
		this.mustBePlayer = true;
		this.permission = Permission.CMD_DISMOUNT.node;
	}
	
	@Override
	public void perform() {
		if (Autocraft.shipmanager.ships.containsKey(player.getName())) {
			Autocraft.shipmanager.ships.remove(player.getName());
			confirmMessage("You have stopped piloting this ship.");
		}
	}

}
