package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.types.Permission;

public class CmdNapalm extends AutoCraftCommand {

	public CmdNapalm(AutoCraft plugin) {
		super(plugin);
		this.name = "napalm";
		this.aliases.add("n");
		this.description = "Drop napalm from your ship's cannons";
		this.permission = Permission.CMD_NAPALM;
		
		this.mustBePlayer = true;
		this.mustBePiloting = true;
	}
	
	@Override
	public void perform() {
		plugin.getShipHandler().getShip(player).dropNapalm();
	}
}