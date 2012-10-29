package com.minesworn.autocraft.ships;

import org.bukkit.util.Vector;

public enum Direction {
	X(1, 0, 0), Y(0, 1, 0), Z(0, 0, 1);
	
	public Vector v;
	Direction(int x, int y, int z) {
		v = new Vector(x, y, z);
	}
}
