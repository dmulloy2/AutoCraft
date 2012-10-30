package com.minesworn.autocraft.ships;

import com.minesworn.autocraft.Autocraft;
import com.minesworn.core.io.SPersist;

public class ACAirship extends ACProperties {

	public ACAirship() {
		this.ALLOWED_BLOCKS.add(5);
		this.ALLOWED_BLOCKS.add(17);
		this.ALLOWED_BLOCKS.add(35);
		this.MAX_CANNON_LENGTH = 0;
		this.CANNON_MATERIAL = 1;
		this.DROPS_BOMB = false;
		this.DROPS_NAPALM = false;
		this.FIRES_TNT = true;
		this.FIRES_TORPEDO = false;
		this.MAIN_TYPE = 35;
		this.MAX_BLOCKS = 50;
		this.MIN_BLOCKS = 10;
		this.MAX_NUMBER_OF_CANNONS = 1;
		this.MAX_SHIP_DIMENSIONS = 30;
		this.MOVE_SPEED = 5;
		this.SHIP_TYPE = "airship";
	}
	
	public void save() {
		SPersist.save(Autocraft.p, this, ACProperties.class, "airship");
	}
	
}
