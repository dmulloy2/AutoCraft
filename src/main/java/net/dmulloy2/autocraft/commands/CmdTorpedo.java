package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.types.Permission;

/**
 * @author dmulloy2
 */

public class CmdTorpedo extends AutoCraftCommand {

	public CmdTorpedo(AutoCraft plugin) {
		super(plugin);
		this.name = "torpedo";
		this.aliases.add("to");
		this.description = "Fire a torpedo from your ship";
		this.permission = Permission.CMD_TORPEDO;

		this.mustBePlayer = true;
		this.mustBePiloting = true;
	}
	
	@Override
	public void perform() {
		plugin.getShipHandler().getShip(player).fireTorpedo();	
	}
}