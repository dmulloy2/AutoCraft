package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.commands.Command;

/**
 * @author dmulloy2
 */

public abstract class AutoCraftCommand extends Command {
	protected final AutoCraft plugin;

	public AutoCraftCommand(AutoCraft plugin) {
		super(plugin);
		this.plugin = plugin;
		this.usesPrefix = true;
	}

	protected final boolean isPiloting() {
		return plugin.getShipHandler().isPilotingShip(player);
	}

	protected final String getMessage(String string) {
		return plugin.getMessage(string);
	}
}