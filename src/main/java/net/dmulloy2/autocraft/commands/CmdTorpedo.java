package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.permissions.Permission;

public class CmdTorpedo extends AutoCraftCommand {

	public CmdTorpedo(AutoCraft plugin) {
		super(plugin);
		this.name = "torpedo";
		this.aliases.add("to");
		this.description = "Fire a torpedo from your ship";
		this.permission = Permission.CMD_TORPEDO;
		this.mustBePlayer = true;
	}
	
	@Override
	public void perform() {
		if (! plugin.getShipManager().ships.containsKey(player.getName())) {
			err("You are not piloting a ship!");
			return;
		}
		
		plugin.getShipManager().ships.get(player.getName()).fireTorpedo();	
	}
}