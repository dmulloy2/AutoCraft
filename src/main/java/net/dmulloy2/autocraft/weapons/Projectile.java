package net.dmulloy2.autocraft.weapons;

import lombok.Getter;
import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.util.Util;

import org.bukkit.block.Block;
import org.bukkit.material.MaterialData;

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

	@SuppressWarnings("deprecation")
	protected final void setData(Block block, MaterialData data) {
		Util.setData(block, data);
	}

	public abstract void move();
}