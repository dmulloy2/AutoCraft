package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.permissions.Permission;

public class CmdList extends AutoCraftCommand {

	public CmdList(AutoCraft plugin) {
		super(plugin);
		this.name = "list";
		this.aliases.add("ls");
		this.description = "List the AutoCraft ship types";
		this.permission = Permission.CMD_LIST;
	}
	
	@Override
	public void perform() {
		sendMessage("&3====[ &eAutoCraft Ship Types &3]====");
		
		for (String type : plugin.getDataHandler().getShips()) {
			sendMessage("&b - &e{0}", type);
		}
	}

}
