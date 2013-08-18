package net.dmulloy2.autocraft.ships.weapons;

import net.dmulloy2.autocraft.AutoCraft;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.TNTPrimed;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;

public class Torpedo extends Projectile {
	private final AutoCraft plugin;
	
	private Block[] torpedo = new Block[2];
	private BlockFace dir;
	
	private double yvelo;
	private double gravity;
	
	public Torpedo(AutoCraft plugin, Block dispenser, BlockFace dir) {
		super(plugin, 200L);
		this.dir = dir;
		
		this.plugin = plugin;
		
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
			this.gravity += this.gravity / 15.0 + 0.00125;
			this.yvelo += this.gravity;
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
				if (plugin.isFactionsEnabled() && !Board.getFactionAt(new FLocation(b.getLocation())).hasPlayersOnline()) {
					torpedo[0].setType(Material.AIR);
					torpedo[1].setType(Material.AIR);
					this.exploded = true;
					return;
				}
				
				if (b.getType().equals(Material.OBSIDIAN) || b.getType().equals(Material.ENCHANTMENT_TABLE))
					b.setType(Material.AIR);
				explode();
				this.exploded = true;
			}
		}
	}
	
	public void explode() {
		this.exploded = true;
		torpedo[0].setType(Material.AIR);
		torpedo[1].setType(Material.AIR);
		TNTPrimed tnt = torpedo[0].getWorld().spawn(torpedo[0].getLocation(), TNTPrimed.class);
		tnt.setYield(3.5f);
		tnt.setFuseTicks(0);	
	}
	
}
