package net.dmulloy2.autocraft.ships.weapons;

import net.dmulloy2.autocraft.AutoCraft;

public class ProjectileUpdateThread extends Thread {
	private final AutoCraft plugin;
	
	private Thread t;
	
	private final Projectile p;
	private final long updatePeriod;
	
	public ProjectileUpdateThread(AutoCraft plugin, Projectile p, long updatePeriod) {
		t = new Thread(this);
		t.start();
		
		this.plugin = plugin;
		
		this.p = p;
		this.updatePeriod = updatePeriod;
	}
	
	public void run() {
		try {
			while (!p.isExploded()) {
				Thread.sleep(updatePeriod);
				
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					
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