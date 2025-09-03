package net.dmulloy2.autocraft.weapons;

import lombok.Getter;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.dmulloy2.autocraft.AutoCraft;

/**
 * @author dmulloy2
 */

public abstract class Projectile extends BukkitRunnable {
	protected @Getter boolean exploded = false;
	protected BukkitTask task;

	protected final AutoCraft plugin;

	public Projectile(AutoCraft plugin, long updatePeriod) {
		this.plugin = plugin;
		this.task = runTaskTimer(plugin, updatePeriod, updatePeriod);
	}

	public abstract void move();

	public void markExploded() {
		this.exploded = true;
		cancel();
	}

	@Override
	public void run() {
		if (exploded) {
			cancel();
			return;
		}

		move();
	}
}