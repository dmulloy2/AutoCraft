package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.types.Permission;
import net.dmulloy2.autocraft.types.Ship;
import net.dmulloy2.autocraft.types.TurnDirection;

public class CmdRotate extends AutoCraftCommand {

	public CmdRotate(AutoCraft plugin) {
		super(plugin);
		this.name = "rotate";
		this.aliases.add("r");
		this.aliases.add("turn");
		this.aliases.add("t");
		this.description = "Turns your airship left or right.";
		this.requiredArgs.add("left/right");
		this.mustBePlayer = true;
		this.permission = Permission.CMD_ROTATE;
	}

	@Override
	public void perform() {
		if (! plugin.getShipHandler().isPilotingShip(player)) {
			err("You are not piloting a ship!");
			return;
		}
		
		Ship ship = plugin.getShipHandler().getShip(player);
		
		String direction = args[0].toLowerCase();
		if (direction.equals("left") || direction.equals("l") || direction.equals("-90")) {
			ship.rotate(TurnDirection.LEFT);
		} else if (direction.equals("right") || direction.equals("r") || direction.equals("90")) {
			ship.rotate(TurnDirection.RIGHT);
		} else {
			err("{0} is not a valid direction!", direction);
		}
	}	
}