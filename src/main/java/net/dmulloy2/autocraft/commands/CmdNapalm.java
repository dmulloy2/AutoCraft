package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.types.Permission;

/**
 * @author dmulloy2
 */

public class CmdNapalm extends AutoCraftCommand {

	public CmdNapalm(AutoCraft plugin) {
		super(plugin);
		this.name = "napalm";
		this.aliases.add("n");
		this.description = "Drop napalm from your ship's cannons";
		this.permission = Permission.CMD_NAPALM;
		this.mustBePlayer = true;
	}

	@Override
	public void perform() {
		if (! isPiloting()) {
			err("You must be piloting a ship to do this!");
			return;
		}

		plugin.getShipHandler().getShip(player).dropNapalm();
	}
}