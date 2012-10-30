package com.minesworn.autocraft.ships;

import org.bukkit.block.BlockState;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class ACBlockState {

	MaterialData data;
	ItemStack[] inv;
	
	public ACBlockState(BlockState s) {
		this.data = s.getData();
		
		if (s instanceof InventoryHolder) {
			this.inv = ((InventoryHolder) s).getInventory().getContents();
		}
	}
	
}
