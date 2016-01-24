/**
 * (c) 2016 dmulloy2
 */
package net.dmulloy2.autocraft.tasks;

import lombok.AllArgsConstructor;
import net.dmulloy2.autocraft.ship.Ship;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * @author dmulloy2
 */

@AllArgsConstructor
public class AutoPilotTask extends BukkitRunnable {
	private final Ship ship;

	@Override
	public void run() {
		if (ship.isAutoPilot()) {
			Player player = ship.getPlayer();
			Vector vec = player.getLocation().getDirection();
			ship.move((int) Math.round(vec.getX()), (int) Math.round(vec.getY()), (int) Math.round(vec.getZ()));
		} else {
			cancel();
		}
	}
}