package com.minesworn.autocraft.ships.weapons;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class Torpedo extends Projectile {

	Block[] torpedo = new Block[2];
	BlockFace dir;
	
	double yvelo = 0.00125;
	
	public Torpedo(Block dispenser, BlockFace dir) {
		super(200L);
		this.dir = dir;
		
		torpedo[0] = dispenser.getRelative(dir.getModX() * 2, 0, dir.getModZ() * 2);
		torpedo[1] = dispenser.getRelative(dir.getModX(), 0, dir.getModZ());
		
		if (torpedo[0].getType().equals(Material.AIR) && 
			torpedo[1].getType().equals(Material.AIR)) {
			torpedo[0].setType(Material.DIAMOND_BLOCK);
			torpedo[1].setType(Material.WOOL);
			torpedo[1].setData((byte) 14);
		} else {
			this.exploded = true;
			explode();
		}
		
	}

	@Override
	public void move() {
		if (!isExploded()) {
			this.yvelo = Math.sqrt(this.yvelo) + this.yvelo;
			if (this.yvelo > 20.0D)
				this.yvelo = 20.0D;
			Block b = torpedo[0].getRelative(dir.getModX(), (int) -this.yvelo, dir.getModZ());
			if (b.getType().equals(Material.AIR) || b.getType().equals(Material.WATER) || b.getType().equals(Material.STATIONARY_WATER)) {
				torpedo[0].setType(Material.WOOL);
				torpedo[0].setData((byte) 14);
				torpedo[1].setType(Material.AIR);
				torpedo[1] = torpedo[0];
				torpedo[0] = b;
				torpedo[0].setType(Material.DIAMOND_BLOCK);
			} else {
				if (b.getType().equals(Material.OBSIDIAN) || b.getType().equals(Material.ENCHANTMENT_TABLE))
					b.setType(Material.AIR);
				this.exploded = true;
				explode();
			}
		}
	}
	
	public void explode() {
		this.exploded = true;
		torpedo[0].setType(Material.AIR);
		torpedo[1].setType(Material.AIR);
		torpedo[0].getWorld().createExplosion(torpedo[0].getLocation(), 3.5f);
	}
	
}
