package net.dmulloy2.autocraft.weapons;

import net.dmulloy2.autocraft.AutoCraft;

public class ProjectileUpdateThread extends Thread implements Runnable {
	private final AutoCraft plugin;
	
	private Thread t;
	
	private final Projectile p;
	private final long updatePeriod;
	
	public ProjectileUpdateThread(AutoCraft plugin, Projectile p, long updatePeriod) {
		this.t = new Thread(this);
		
		t.start();
		
		this.plugin = plugin;
		
		this.p = p;
		this.updatePeriod = updatePeriod;
	}
	
	@Override
	public void run() {
		try {
			while (!p.isExploded()) {
				Thread.sleep(updatePeriod);
				
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					
					@Override
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