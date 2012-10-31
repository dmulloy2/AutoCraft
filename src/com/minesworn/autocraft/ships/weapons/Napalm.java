package com.minesworn.autocraft.ships.weapons;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;

import com.minesworn.autocraft.Config;

public class Napalm extends Projectile {

	Block napalm;
	
	public Napalm(Block dispenser) {
		super(200L);
				
		napalm = dispenser.getRelative(0, -1, 0);
		napalm.setType(Material.WOOL);
		napalm.setData((byte) 14);
	}

	public void explode() {
		this.exploded = true;
		TNTPrimed tnt = napalm.getWorld().spawn(napalm.getLocation(), TNTPrimed.class);
		tnt.setYield(3.0f);
		tnt.setFuseTicks(0);
		napalm.setType(Material.AIR);
		burn();
	}
	
	public void burn() {
		for (int i = -Config.NAPALM_BURN_RADIUS; i <= Config.NAPALM_BURN_RADIUS; i++) {
			for (int j = -Config.NAPALM_BURN_RADIUS; j <= Config.NAPALM_BURN_RADIUS; j++) {
				for (int k = -Config.NAPALM_BURN_RADIUS; k <= Config.NAPALM_BURN_RADIUS; k++) {
					Block b = napalm.getRelative(i, j, k);
					if (b.getType().equals(Material.AIR) && !b.getRelative(0, -1, 0).getType().equals(Material.AIR))
						b.setType(Material.FIRE);
				}
			}
		}
	}
	
	@Override
	public void move() {
		if (!isExploded()) {
			Block b = napalm.getRelative(0, -1, 0);
			if (b.getType().equals(Material.AIR) ||
				b.getType().equals(Material.WATER) ||
				b.getType().equals(Material.STATIONARY_WATER) ||
				b.getType().equals(Material.LAVA) ||
				b.getType().equals(Material.STATIONARY_LAVA) ||
				b.getType().equals(Material.FIRE) ||
				b.getType().equals(Material.LADDER) ||
				b.getType().equals(Material.IRON_DOOR_BLOCK) ||
				b.getType().equals(Material.ENCHANTMENT_TABLE) ||
				b.getType().equals(Material.PORTAL) ||
				b.getType().equals(Material.ENDER_PORTAL) ||
				b.getType().equals(Material.ENDER_STONE) ||
				b.getType().equals(Material.ENDER_PORTAL_FRAME)) {
				b.setType(Material.WOOL);
				napalm.setType(Material.AIR);
				b.setData((byte) 13);
				napalm = b;
			} else {
				b.setType(Material.AIR);
				this.exploded = true;
				explode();
			}
		}
	}
	
}
