package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.types.Permission;

import org.bukkit.util.Vector;

/**
 * @author dmulloy2
 */

public class CmdMove extends AutoCraftCommand {

	public CmdMove(AutoCraft plugin) {
		super(plugin);
		this.name = "move";
		this.aliases.add("m");
		this.description = "Moves your ship in direction you are facing.";
		this.permission = Permission.CMD_MOVE;
		
		this.mustBePlayer = true;
		this.mustBePiloting = true;
	}
	
	@Override
	public void perform() {
		Vector dir = player.getLocation().getDirection();
			
		plugin.getShipHandler().getShip(player).move( 
				(int) Math.round(dir.getX()),
				(int) Math.round(dir.getY()),
				(int) Math.round(dir.getZ()));
	}	
}