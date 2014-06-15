package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.types.Permission;
import net.dmulloy2.autocraft.types.Ship;

import org.bukkit.Location;

/**
 * @author dmulloy2
 */

public class CmdInfo extends AutoCraftCommand {

	public CmdInfo(AutoCraft plugin) {
		super(plugin);
		this.name = "info";
		this.aliases.add("i");
		this.description = "View your ship's info.";
		this.permission = Permission.CMD_INFO;
		this.mustBePlayer = true;
	}

	@Override
	public void perform() {
		if (! isPiloting()) {
			err("You must be piloting a ship to do this!");
			return;
		}

		Ship ship = plugin.getShipHandler().getShip(player);

		sendMessage("&3====[ &eYour Ship Data &3]====");
		sendMessage("&bLocation: ");

		Location loc = player.getLocation();
		sendMessage("&bX: &e{0}", loc.getBlockX());
		sendMessage("&bY: &e{0}", loc.getBlockY());
		sendMessage("&bZ: &e{0}", loc.getBlockZ());
		sendMessage("&bWorld: &e{0}", loc.getWorld().getName());

		sendMessage("");

		sendMessage("&bBlocks: &e{0}&b/&e{1}", ship.getBlocks().length, ship.getData().getMaxBlocks());
		sendMessage("&bCannons: &e{0}&b/&e{1}", ship.getCannons().length, ship.getData().getMaxNumberOfCannons());
	}
}