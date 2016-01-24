package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.ship.Ship;
import net.dmulloy2.autocraft.types.Permission;

/**
 * @author dmulloy2
 */

public class CmdPilot extends AutoCraftCommand {

	public CmdPilot(AutoCraft plugin) {
		super(plugin);
		this.name = "pilot";
		this.aliases.add("p");
		this.addRequiredArg("ship type");
		this.description = "Use to pilot ships";
		this.permission = Permission.CMD_PILOT;

		this.mustBePlayer = true;
	}

	@Override
	public void perform() {
		if (plugin.getShipHandler().isPilotingShip(player)) {
			err("You are already piloting a ship!");
			return;
		}

		String shipName = args[0].toLowerCase();
		if (! plugin.getDataHandler().isValidShip(shipName)) {
			err("Could not find a ship by the name of {0}", shipName);
			return;
		}

		if (! player.hasPermission("autocraft." + shipName)) {
			err("You do not have permission to fly this ship!");
			return;
		}

		new Ship(player, plugin.getDataHandler().getData(shipName), plugin);
	}
}