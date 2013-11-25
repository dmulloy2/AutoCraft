package net.dmulloy2.autocraft.weapons;

import net.dmulloy2.autocraft.AutoCraft;

/**
 * @author dmulloy2
 */

public abstract class Projectile {
	protected boolean exploded = false;
	protected int movespeed = 0;

	public Projectile(AutoCraft plugin, long updatePeriod) {
		new ProjectileUpdateThread(plugin, this, updatePeriod);
	}

	public int getMoveSpeed() {
		return movespeed;
	}

	public boolean isExploded() {
		return exploded;
	}

	public abstract void move();
}