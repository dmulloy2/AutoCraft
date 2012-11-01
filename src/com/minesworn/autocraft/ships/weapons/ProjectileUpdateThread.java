package com.minesworn.autocraft.ships.weapons;

import org.bukkit.Bukkit;

import com.minesworn.autocraft.Autocraft;
import com.minesworn.autocraft.core.util.SThread;

public class ProjectileUpdateThread extends SThread {
	final Projectile p;
	final long updatePeriod;
	public ProjectileUpdateThread(Projectile p, long updatePeriod) {
		super();
		this.p = p;
		this.updatePeriod = updatePeriod;
	}
	
	public void run() {
		try {
			while (!p.isExploded()) {
				Thread.sleep(updatePeriod);
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(Autocraft.p, new Runnable() {
					public void run() {
						p.move();
					}
				});
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
