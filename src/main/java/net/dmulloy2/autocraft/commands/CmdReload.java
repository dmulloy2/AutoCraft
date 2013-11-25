package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.types.Permission;
import net.dmulloy2.autocraft.types.Reloadable;

/**
 * @author dmulloy2
 */

public class CmdReload extends AutoCraftCommand implements Reloadable {

	public CmdReload(AutoCraft plugin) {
		super(plugin);
		this.name = "reload";
		this.aliases.add("rl");
		this.optionalArgs.add("force");
		this.description = "Reloads AutoCraft plugin.";
		this.permission = Permission.CMD_RELOAD;
	}
	
	@Override
	public void perform() {
		reload();
	}

	@Override
	public void reload() {
		plugin.reload();

		// Forces the reloading of ship data
		if (args[0].equalsIgnoreCase("true")) {
			plugin.getDataHandler().reload();
		}
		
		sendpMessage("&aReload complete!");
		
		plugin.getLogHandler().log("{0} has reloaded AutoCraft!", sender.getName());
	}
}