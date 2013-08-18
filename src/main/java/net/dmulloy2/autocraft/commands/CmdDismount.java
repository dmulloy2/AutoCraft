package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.permissions.Permission;

public class CmdDismount extends AutoCraftCommand {

	public CmdDismount(AutoCraft plugin) {
		super(plugin);
		this.name = "dismount";
		this.aliases.add("x");
		this.description = "Dismount your airship.";
		this.mustBePlayer = true;
		this.permission = Permission.CMD_DISMOUNT;
	}
	
	@Override
	public void perform() {
		if (! plugin.getShipManager().ships.containsKey(player.getName())) {
			err("You are not piloting a ship!");
			return;
		}
		
		plugin.getShipManager().ships.remove(player.getName());
		sendMessage("&7You have stopped piloting this ship.");
	}
}