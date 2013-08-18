package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.permissions.Permission;
import net.dmulloy2.autocraft.types.ShipData;
import net.dmulloy2.autocraft.util.FormatUtil;

import org.bukkit.Material;

public class CmdAllowed extends AutoCraftCommand {

	public CmdAllowed(AutoCraft plugin) {
		super(plugin);
		this.name = "allowed";
		this.aliases.add("a");
		this.description = "Displays ship information.";
		this.requiredArgs.add("ship type");
		this.permission = Permission.CMD_ALLOWED;
	}
	
	@Override
	public void perform() {
		String shipName = args[0].toLowerCase();
		
		if (! plugin.getDataHandler().isValidShip(shipName)) {
			err("Ship {0} does not exist!", shipName);
			return;
		}
		
		ShipData data = plugin.getDataHandler().getData(shipName);
		
		sendMessage("&3====[ &e{0} &3]====", data.getShipType());
		sendMessage("&bCan fire TNT: &e{0}", data.isFiresTnt());
		sendMessage("&bCan fire Torpedo: &e{0}", data.isFiresTorpedo());
		sendMessage("&bCan drop Bombs: &e{0}", data.isDropsBomb());
		sendMessage("&bCan drop Napalm: &e{0}", data.isDropsNapalm());
		
		if (data.isFiresTnt() || data.isFiresTorpedo()) {
			sendMessage("&bMax cannon length: &e{0}", data.getMaxCannonLength());
			sendMessage("&bCannon material: &e{0}", 
					FormatUtil.getFriendlyName(Material.getMaterial(data.getCannonMaterial())));
		}
		
		sendMessage("&bMax number of cannons: &e{0}", data.getMaxNumberOfCannons());
		sendMessage("&bMinimum blocks: &e{0}", data.getMinBlocks());
		sendMessage("&bMaximum blocks: &e{0}", data.getMaxBlocks());
		sendMessage("&bMain block: &e{0}",
				FormatUtil.getFriendlyName(Material.getMaterial(data.getMainType())));
		sendMessage("&bAllowed block(s): &e{0}", getAllowedList(data));
		sendMessage("&bIgnore attachments: &e{0}", data.isIgnoreAttachments());
	}
	
	public String getAllowedList(ShipData data) {
		StringBuilder ret = new StringBuilder();
		
		for (int id : data.getAllowedBlocks()) {
			Material mat = Material.getMaterial(id);
			ret.append("&e" + FormatUtil.getFriendlyName(mat) + "&b, ");
		}
		
		ret.delete(ret.lastIndexOf("&b,"), ret.lastIndexOf(" "));
		
		return ret.toString();
	}
}