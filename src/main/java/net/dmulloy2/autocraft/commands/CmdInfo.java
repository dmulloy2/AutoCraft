package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.permissions.Permission;
import net.dmulloy2.autocraft.ships.Ship;

public class CmdInfo extends AutoCraftCommand {

	public CmdInfo(AutoCraft plugin) {
		super(plugin);
		this.name = "info";
		this.aliases.add("i");
		this.description = "View your ship's info.";
		this.mustBePlayer = true;
		this.permission = Permission.CMD_INFO;
	}
	
	@Override
	public void perform() {
		if (! plugin.getShipManager().ships.containsKey(player.getName())) {
			err("You are not piloting a ship!");
			return;
		}
		
		Ship ship = plugin.getShipManager().ships.get(player.getName());
		
		sendMessage("&3====[ &eYour Ship Data &3]====");
		sendMessage("&bLocation: ");
		sendMessage("&bX: &e{0}");
		sendMessage("&bY: &e{0}");
		sendMessage("&bZ: &e{0}");
		
		sendMessage("");
		
		sendMessage("&bBlocks: &e{0}&b/&e{1}", ship.getBlocks().length, ship.getData().getMaxBlocks());
		sendMessage("&bCannons: &e{0}&b/&e{1}", ship.getCannons().length, ship.getData().getMaxNumberOfCannons());
	}
}