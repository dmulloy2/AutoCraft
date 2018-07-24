package net.dmulloy2.autocraft.weapons;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.Config;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;

/**
 * @author dmulloy2
 */

public class Napalm extends Projectile {
	private Block napalm;

	public Napalm(AutoCraft plugin, Block dispenser) {
		super(plugin, 200L);

		napalm = dispenser.getRelative(0, -1, 0);
		napalm.setType(Material.GREEN_WOOL);
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
		int burnRadius = Config.napalmBurnRadius;
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
					|| b.isLiquid()
					|| b.getType().equals(Material.FIRE)
					|| b.getType().equals(Material.LADDER)
					|| b.getType().equals(Material.IRON_DOOR)
					|| b.getType().equals(Material.ENCHANTING_TABLE)
					|| b.getType().equals(Material.END_PORTAL)
					|| b.getType().equals(Material.NETHER_PORTAL)
					|| b.getType().equals(Material.END_STONE)
					|| b.getType().equals(Material.END_PORTAL_FRAME)) {
				napalm.setType(Material.AIR);
				napalm = b;

				napalm.setType(Material.GREEN_WOOL);
			} else {
				if (plugin.isSwornNationsEnabled() && plugin.getSwornNationsHandler().isFactionOffline(b)) {
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