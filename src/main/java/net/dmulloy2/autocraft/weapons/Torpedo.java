package net.dmulloy2.autocraft.weapons;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.events.AutoCraftBlockDamageEvent;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.TNTPrimed;

/**
 * @author dmulloy2
 */

public class Torpedo extends Projectile {
	private Block[] torpedo = new Block[2];
	private BlockFace dir;
	private double yvelo;
	private double gravity;

	public Torpedo(AutoCraft plugin, Block dispenser, BlockFace dir) {
		super(plugin, 200L);
		this.dir = dir;

		torpedo[0] = dispenser.getRelative(dir.getModX() * 2, 0, dir.getModZ() * 2);
		torpedo[1] = dispenser.getRelative(dir.getModX(), 0, dir.getModZ());

		if (torpedo[0].getType().equals(Material.AIR) && torpedo[1].getType().equals(Material.AIR)) {
			torpedo[0].setType(Material.DIAMOND_BLOCK);
			torpedo[1].setType(Material.RED_WOOL);
		} else {
			markExploded();
		}
	}

	@Override
	public void move() {
		if (isExploded()) {
			return;
		}

		gravity += gravity / 15.0 + 0.00125;
		yvelo += gravity;
		if (yvelo > 20.0D) {
			yvelo = 20.0D;
		}

		Block b = torpedo[0].getRelative(dir.getModX(), (int) -yvelo, dir.getModZ());
		if (b.getType().equals(Material.AIR) || b.getType().equals(Material.WATER)) {
			torpedo[0].setType(Material.RED_WOOL);

			torpedo[1].setType(Material.AIR);
			torpedo[1] = torpedo[0];

			torpedo[0] = b;
			torpedo[0].setType(Material.DIAMOND_BLOCK);
		} else {
			AutoCraftBlockDamageEvent blockDamageEvent = new AutoCraftBlockDamageEvent(b);
			plugin.getServer().getPluginManager().callEvent(blockDamageEvent);
			if (blockDamageEvent.isCancelled()) {
				torpedo[0].setType(Material.AIR);
				torpedo[1].setType(Material.AIR);
				markExploded();
				return;
			}

			if (b.getType().equals(Material.OBSIDIAN) || b.getType().equals(Material.ENCHANTING_TABLE)) {
				b.setType(Material.AIR);
			}

			explode();
		}
	}

	public void explode() {
		markExploded();
		torpedo[0].setType(Material.AIR);
		torpedo[1].setType(Material.AIR);
		TNTPrimed tnt = torpedo[0].getWorld().spawn(torpedo[0].getLocation(), TNTPrimed.class);
		tnt.setYield(3.5f);
		tnt.setFuseTicks(0);
	}
}