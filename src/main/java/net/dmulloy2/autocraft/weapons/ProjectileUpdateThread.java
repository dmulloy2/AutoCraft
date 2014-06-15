package net.dmulloy2.autocraft.weapons;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.util.Util;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author dmulloy2
 */

public class ProjectileUpdateThread extends Thread {
	private final Projectile projectile;
	private final long updatePeriod;

	private final AutoCraft plugin;
	public ProjectileUpdateThread(AutoCraft plugin, Projectile projectile, long updatePeriod) {
		super("AutoCraft-ProjectileUpdateThread");
		this.setPriority(MIN_PRIORITY);
		this.start();

		this.plugin = plugin;
		this.projectile = projectile;
		this.updatePeriod = updatePeriod;
	}

	@Override
	public void run() {
		try {
			while (! projectile.isExploded()) {
				Thread.sleep(updatePeriod);

				new BukkitRunnable() {

					@Override
					public void run() {
						projectile.move();
					}

				}.runTask(plugin);
			}
		} catch (Throwable ex) {
			plugin.getLogHandler().debug(Util.getUsefulStack(ex, "running ProjectileUpdateThread"));
		}
	}
}