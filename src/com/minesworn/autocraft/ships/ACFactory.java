package com.minesworn.autocraft.ships;

import com.minesworn.autocraft.core.io.EFactory;

public class ACFactory implements EFactory<ACProperties> {

	@Override
	public ACProperties newEntity() {
		return new ACProperties();
	}
	
}
