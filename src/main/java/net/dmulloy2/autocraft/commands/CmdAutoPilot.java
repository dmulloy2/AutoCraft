/**
 * (c) 2016 dmulloy2
 */
package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.ship.Ship;
import net.dmulloy2.autocraft.types.Permission;

/**
 * @author dmulloy2
 */

public class CmdAutoPilot extends AutoCraftCommand {

	public CmdAutoPilot(AutoCraft plugin) {
		super(plugin);
		this.name = "autopilot";
		this.aliases.add("ap");
		this.description = "Puts your ship into auto pilot";
		this.permission = Permission.CMD_PILOT;
	}

	@Override
	public void perform() {
		Ship ship = plugin.getShipHandler().getShip(player);
		if (ship == null) {
			err("You must be piloting a ship!");
			return;
		}

		if (ship.isAutoPilot()) {
			ship.stopAutoPilot();
			sendpMessage("&7Auto pilot has been disengaged.");
		} else {
			ship.startAutoPilot();
			sendpMessage("&7Auto pilot has been engaged.");
		}
	}
}