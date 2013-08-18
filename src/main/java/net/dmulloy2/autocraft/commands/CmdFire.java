package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.permissions.Permission;

public class CmdFire extends AutoCraftCommand {

	public CmdFire(AutoCraft plugin) {
		super(plugin);
		this.name = "fire";
		this.aliases.add("f");
		this.mustBePlayer = true;
		this.permission = Permission.CMD_FIRE;
		this.description = "Fire your ship's tnt cannons";
	}
	
	@Override
	public void perform() {
		if (! plugin.getShipManager().ships.containsKey(player.getName())) {
			err("You are not piloting a ship!");
			return;
		}
		
		plugin.getShipManager().ships.get(player.getName()).fire();
	}
}