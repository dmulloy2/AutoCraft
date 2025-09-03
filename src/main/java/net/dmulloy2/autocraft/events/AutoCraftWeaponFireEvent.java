package net.dmulloy2.autocraft.events;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class AutoCraftWeaponFireEvent extends Event implements Cancellable
{
	private static final HandlerList HANDLER_LIST = new HandlerList();

	@Getter
	private final Player player;

	@Getter
	@Setter
	private boolean cancelled = false;

	public AutoCraftWeaponFireEvent(Player player)
	{
		this.player = player;
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
