package net.dmulloy2.autocraft.types;

import lombok.Getter;

import org.bukkit.util.Vector;

/**
 * @author dmulloy2
 */

@Getter
public enum Direction {
	X(1, 0, 0), 
	Y(0, 1, 0),
	Z(0, 0, 1);

	private Vector vector;

	Direction(int x, int y, int z) {
		vector = new Vector(x, y, z);
	}
}