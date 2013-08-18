package net.dmulloy2.autocraft.types;

import org.bukkit.block.BlockState;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class ACBlockState {
	public MaterialData data;
	public ItemStack[] inv;
	public BlockState state;
	
	public ACBlockState(BlockState s) {
		this.data = s.getData();
		this.state = s;
		
		if (s instanceof InventoryHolder) {
			this.inv = ((InventoryHolder) s).getInventory().getContents();
		}
	}	
}