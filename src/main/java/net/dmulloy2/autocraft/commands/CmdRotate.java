package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.ship.Ship;
import net.dmulloy2.autocraft.types.Permission;
import net.dmulloy2.autocraft.types.TurnDirection;

/**
 * @author dmulloy2
 */

public class CmdRotate extends AutoCraftCommand {

	public CmdRotate(AutoCraft plugin) {
		super(plugin);
		this.name = "rotate";
		this.aliases.add("r");
		this.aliases.add("turn");
		this.aliases.add("t");
		this.description = "Turns your airship left or right.";
		this.addRequiredArg("left/right");
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
		switch (direction) {
			case "left":
			case "l":
			case "-90":
				ship.rotate(TurnDirection.LEFT);
				break;
			case "right":
			case "r":
			case "90":
				ship.rotate(TurnDirection.RIGHT);
				break;
			case "180":
				// Turn twice for a 180
				ship.rotate(TurnDirection.RIGHT);
				ship.rotate(TurnDirection.RIGHT);
				break;
			default:
				err("{0} is not a valid direction!", direction);
				break;
		}
	}
}