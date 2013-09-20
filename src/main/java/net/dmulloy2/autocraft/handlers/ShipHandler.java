package net.dmulloy2.autocraft.handlers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.dmulloy2.autocraft.types.Ship;

import org.bukkit.entity.Player;

public class ShipHandler {
	private Map<String, Ship> ships;
	
	public ShipHandler() {
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