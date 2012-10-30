package com.minesworn.autocraft.ships.weapons;

public abstract class Projectile {
	boolean exploded = false;
	int movespeed = 0;
	
	public Projectile(long updatePeriod) {
		new ProjectileUpdateThread(this, updatePeriod);
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
