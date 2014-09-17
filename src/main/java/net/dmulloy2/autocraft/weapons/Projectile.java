package net.dmulloy2.autocraft.weapons;

import lombok.Getter;
import net.dmulloy2.autocraft.AutoCraft;

/**
 * @author dmulloy2
 */

public abstract class Projectile {
	protected @Getter boolean exploded = false;
	// protected @Getter int moveSpeed = 0;

	protected final AutoCraft plugin;
	public Projectile(AutoCraft plugin, long updatePeriod) {
		this.plugin = plugin;
		new ProjectileUpdateThread(plugin, this, updatePeriod);
	}

	public abstract void move();
}