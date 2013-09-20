package net.dmulloy2.autocraft.types;

import lombok.Getter;

import org.bukkit.block.BlockState;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

@Getter
public class ACBlockState {
	private BlockState state;
	private MaterialData data;
	private ItemStack[] inventory;
	
	public ACBlockState(BlockState s) {
		this.data = s.getData();
		this.state = s;
		
		if (s instanceof InventoryHolder) {
			this.inventory = ((InventoryHolder) s).getInventory().getContents();
		}
	}
	
	
}