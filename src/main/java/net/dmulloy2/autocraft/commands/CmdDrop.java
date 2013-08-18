package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.permissions.Permission;

public class CmdDrop extends AutoCraftCommand {

	public CmdDrop(AutoCraft plugin) {
		super(plugin);
		this.name = "drop";
		this.aliases.add("d");
		this.mustBePlayer = true;
		this.permission = Permission.CMD_DROP;
		this.description = "Drop a bomb";
	}
	
	@Override
	public void perform() {
		if (! plugin.getShipManager().ships.containsKey(player.getName())) {
			err("You are not piloting a ship!");
			return;
		}
		
		plugin.getShipManager().ships.get(player.getName()).drop();
	}
}