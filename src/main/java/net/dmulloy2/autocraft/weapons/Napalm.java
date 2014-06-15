package net.dmulloy2.autocraft.weapons;

import net.dmulloy2.autocraft.AutoCraft;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.material.Wool;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;

/**
 * @author dmulloy2
 */

public class Napalm extends Projectile {
	private Block napalm;

	public Napalm(AutoCraft plugin, Block dispenser) {
		super(plugin, 200L);

		napalm = dispenser.getRelative(0, -1, 0);

		// Set type
		napalm.setType(Material.WOOL);

		// Set color
		Wool wool = new Wool();
		wool.setColor(DyeColor.GREEN);
		setData(napalm, wool);

		// Update
		napalm.getState().update();
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
		int burnRadius = plugin.getConfig().getInt("napalmBurnRadius");
		for (int i = -burnRadius; i <= burnRadius; i++) {
			for (int j = -burnRadius; j <= burnRadius; j++) {
				for (int k = -burnRadius; k <= burnRadius; k++) {
					Block b = napalm.getRelative(i, j, k);
					if (b.getType().equals(Material.AIR) && !b.getRelative(0, -1, 0).getType().equals(Material.AIR))
						b.setType(Material.FIRE);
				}
			}
		}
	}

	@Override
	public void move() {
		if (! isExploded()) {
			Block b = napalm.getRelative(0, -1, 0);
			if (b.getType().equals(Material.AIR)
					|| b.getType().equals(Material.WATER)
					|| b.getType().equals(Material.STATIONARY_WATER)
					|| b.getType().equals(Material.LAVA)
					|| b.getType().equals(Material.STATIONARY_LAVA)
					|| b.getType().equals(Material.FIRE)
					|| b.getType().equals(Material.LADDER)
					|| b.getType().equals(Material.IRON_DOOR_BLOCK)
					|| b.getType().equals(Material.ENCHANTMENT_TABLE)
					|| b.getType().equals(Material.PORTAL)
					|| b.getType().equals(Material.ENDER_PORTAL)
					|| b.getType().equals(Material.ENDER_STONE)
					|| b.getType().equals(Material.ENDER_PORTAL_FRAME)) {
				napalm.setType(Material.AIR);
				napalm = b;

				napalm.setType(Material.WOOL);

				Wool wool = new Wool();
				wool.setColor(DyeColor.GREEN);
				setData(napalm, wool);

				napalm.getState().update();
			} else {
				if (plugin.isFactionsEnabled() 
						&& ! Board.getFactionAt(new FLocation(b.getLocation())).isNone()
						&& ! Board.getFactionAt(new FLocation(b.getLocation())).hasPlayersOnline()) {
					this.exploded = true;
					return;
				}

				if (b.getType() != Material.BEDROCK) {
					b.setType(Material.AIR);
				}

				explode();
			}
		}
	}
}