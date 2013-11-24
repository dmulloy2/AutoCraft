package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.types.Permission;

/**
 * @author dmulloy2
 */

public class CmdFire extends AutoCraftCommand {

	public CmdFire(AutoCraft plugin) {
		super(plugin);
		this.name = "fire";
		this.aliases.add("f");
		this.description = "Fire your ship's tnt cannons";
		this.permission = Permission.CMD_FIRE;
		
		this.mustBePlayer = true;
		this.mustBePiloting = true;
	}
	
	@Override
	public void perform() {
		plugin.getShipHandler().getShip(player).fire();
	}
}