/**
 * (c) 2016 dmulloy2
 */
package net.dmulloy2.autocraft.tasks;

import lombok.AllArgsConstructor;
import net.dmulloy2.autocraft.ship.Ship;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author dmulloy2
 */

@AllArgsConstructor
public class SinkingTask extends BukkitRunnable {
	private final Ship ship;

	@Override
	public void run() {
		if (ship.isSinking()) {
			ship.move(0, -1, 0);
		} else {
			cancel();
		}
	}
}