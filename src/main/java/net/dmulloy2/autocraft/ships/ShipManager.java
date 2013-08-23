package net.dmulloy2.autocraft.ships;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class ShipManager {
	private Map<String, Ship> ships;
	
	public ShipManager() {
		ships = new HashMap<String, Ship>();
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
		ships.remove(player.getName());
	}
	
	public void clearMemory() {
		ships.clear();
	}
}