package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.permissions.Permission;

import org.bukkit.util.Vector;

public class CmdMove extends AutoCraftCommand {

	public CmdMove(AutoCraft plugin) {
		super(plugin);
		this.name = "move";
		this.aliases.add("m");
		this.permission = Permission.CMD_MOVE;
		this.description = "Moves your ship in direction you are facing.";
		this.mustBePlayer = true;
	}
	
	@Override
	public void perform() {
		if (! plugin.getShipManager().ships.containsKey(player.getName())) {
			err("You are not piloting a ship!");
			return;
		}

		Vector dir = player.getLocation().getDirection();
			
		plugin.getShipManager().ships.get(player.getName()).move( 
				(int) Math.round(dir.getX()),
				(int) Math.round(dir.getY()),
				(int) Math.round(dir.getZ()));
	}	
}