package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.types.Permission;

/**
 * @author dmulloy2
 */

public class CmdDrop extends AutoCraftCommand {

	public CmdDrop(AutoCraft plugin) {
		super(plugin);
		this.name = "drop";
		this.aliases.add("d");
		this.description = "Drop a bomb";
		this.permission = Permission.CMD_DROP;
		
		this.mustBePlayer = true;
		this.mustBePiloting = true;
	}
	
	@Override
	public void perform() {
		plugin.getShipHandler().getShip(player).drop();
	}
}