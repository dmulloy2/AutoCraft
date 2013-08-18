package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.permissions.Permission;

public class CmdReload extends AutoCraftCommand {

	public CmdReload(AutoCraft plugin) {
		super(plugin);
		this.name = "reload";
		this.aliases.add("rl");
		this.description = "Reloads AutoCraft plugin.";
		this.mustBePlayer = false;
		this.permission = Permission.CMD_RELOAD;
	}
	
	@Override
	public void perform() {
		plugin.onDisable();
		plugin.onEnable();
		
		sendMessage("&aReload complete!");
		
		plugin.getLogHandler().log("{0} has reloaded AutoCraft!", sender.getName());
	}
}