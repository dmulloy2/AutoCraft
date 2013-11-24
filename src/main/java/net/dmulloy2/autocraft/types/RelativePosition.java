package net.dmulloy2.autocraft.types;

import lombok.Getter;

/**
 * @author dmulloy2
 */

@Getter
public enum RelativePosition {
	TOP(0, 1, 0),
	BOTTOM(0, -1, 0),
	LEFT(1, 0, 0),
	RIGHT(-1, 0, 0),
	IN(0, 0, 1),
	OUT(0, 0, -1);
	
	private int x, y, z;
	RelativePosition(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}