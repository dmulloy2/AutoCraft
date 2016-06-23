package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.ship.ShipData;
import net.dmulloy2.autocraft.types.Permission;
import net.dmulloy2.types.StringJoiner;
import net.dmulloy2.util.MaterialUtil;

import org.apache.commons.lang.WordUtils;

/**
 * @author dmulloy2
 */

public class CmdAllowed extends AutoCraftCommand {

	public CmdAllowed(AutoCraft plugin) {
		super(plugin);
		this.name = "allowed";
		this.aliases.add("a");
		this.description = getMessage("allowed_description");
		this.addRequiredArg("ship type");
		this.permission = Permission.CMD_ALLOWED;
	}

	@Override
	public void perform() {
		String shipName = args[0].toLowerCase();

		if (! plugin.getDataHandler().isValidShip(shipName)) {
			err(getMessage("invalid_ship"), shipName);
			return;
		}

		ShipData data = plugin.getDataHandler().getData(shipName);

		sendMessage(getMessage("allowed_header"), WordUtils.capitalize(data.getShipType()));
		sendMessage(getMessage("allowed_tnt"), data.isFiresTnt());
		sendMessage(getMessage("allowed_torpedo"), data.isFiresTorpedo());
		sendMessage(getMessage("allowed_bombs"), data.isDropsBomb());
		sendMessage(getMessage("allowed_napalm"), data.isDropsNapalm());

		if (data.isFiresTnt() || data.isFiresTorpedo()) {
			sendMessage(getMessage("allowed_max_cannon_length"), data.getMaxCannonLength());
			sendMessage(getMessage("allowed_cannon_material"), MaterialUtil.getName(data.getCannonMaterial()));
		}

		sendMessage(getMessage("allowed_max_number_cannons"), data.getMaxNumberOfCannons());
		sendMessage(getMessage("allowed_min_blocks"), data.getMinBlocks());
		sendMessage(getMessage("allowed_max_blocks"), data.getMaxBlocks());
		sendMessage(getMessage("allowed_main_block"), MaterialUtil.getName(data.getMainType()));
		sendMessage(getMessage("allowed_blocks"), getAllowedList(data));
		sendMessage(getMessage("allowed_ignore_attachments"), data.isIgnoreAttachments());
	}

	public String getAllowedList(ShipData data) {
		StringJoiner joiner = new StringJoiner("&b, &e");

		for (String allowed : data.getAllowedTypes()) {
			joiner.append(MaterialUtil.getName(allowed));
		}

		return joiner.toString();
	}
}