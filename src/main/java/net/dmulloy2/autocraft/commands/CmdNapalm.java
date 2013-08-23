package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.permissions.Permission;

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
		if (! plugin.getShipManager().isPilotingShip(player)) {
			err("You are not piloting a ship!");
			return;
		}

		plugin.getShipManager().getShip(player).dropNapalm();
	}
}