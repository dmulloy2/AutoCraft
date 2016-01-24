package net.dmulloy2.autocraft.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dmulloy2.autocraft.Config;
import net.dmulloy2.autocraft.ship.Ship;

import org.bukkit.entity.Player;

/**
 * @author dmulloy2
 */

public class ShipHandler {
	private Map<String, Ship> ships;
	private List<Ship> sinking;

	public ShipHandler() {
		ships = new HashMap<>();
		sinking = new ArrayList<>();
	}

	public void putShip(Player player, Ship ship) {
		ships.put(player.getName(), ship);
	}

	public Ship getShip(Player player) {
		return ships.get(player.getName());
	}

	public Collection<Ship> getShips() {
		return ships.values();
	}

	public boolean isPilotingShip(Player player) {
		return ships.containsKey(player.getName());
	}

	public void unpilotShip(Player player) {
		Ship ship = ships.remove(player.getName());
		if (ship != null) {
			if (ship.isAutoPilot()) {
				ship.stopAutoPilot();
			}

			if (! ship.creationFailed() && Config.sinkingEnabled) {
				ship.startSinking();
			}
		}
	}

	public void clearMemory() {
		ships.clear();
		sinking.clear();
	}

	public List<Ship> getSinking() {
		return sinking;
	}
}