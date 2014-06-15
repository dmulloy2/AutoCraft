/**
 * (c) 2014 dmulloy2
 */
package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;

/**
 * Represents a command that can have pages
 * 
 * @author dmulloy2
 */
public abstract class PaginatedCommand extends net.dmulloy2.commands.PaginatedCommand {
	protected final AutoCraft plugin;

	public PaginatedCommand(AutoCraft plugin) {
		super(plugin);
		this.plugin = plugin;
	}
}