package net.dmulloy2.autocraft.ships.weapons;

import net.dmulloy2.autocraft.AutoCraft;

public abstract class Projectile {
	boolean exploded = false;
	int movespeed = 0;
	
	public Projectile(AutoCraft plugin, long updatePeriod) {
		new ProjectileUpdateThread(plugin, this, updatePeriod);
	}
	
	public int getMoveSpeed() {
		return movespeed;
	}
	
	public boolean isExploded() {
		return exploded;
	}
	
	public void move() {
		
	}
}