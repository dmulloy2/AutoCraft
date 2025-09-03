package net.dmulloy2.autocraft.events;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class AutoCraftBlockDamageEvent extends Event implements Cancellable
{
	private static final HandlerList HANDLER_LIST = new HandlerList();

	@Getter
	private final Block block;

	@Getter
	@Setter
	private boolean cancelled = false;

	public AutoCraftBlockDamageEvent(Block block)
	{
		this.block = block;
	}

	@Override
	public @NotNull HandlerList getHandlers()
	{
		return HANDLER_LIST;
	}

	public static HandlerList getHandlerList()
	{
		return HANDLER_LIST;
	}
}
