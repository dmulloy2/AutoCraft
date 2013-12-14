package net.dmulloy2.autocraft.types;

import lombok.Data;

import org.bukkit.block.BlockState;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

/**
 * @author dmulloy2
 */

@Data
public class ACBlockState {
	private BlockState state;
	private MaterialData data;
	private ItemStack[] inventory;

	public ACBlockState(BlockState state, MaterialData data) {
		this.data = data;
		this.state = state;

		if (state instanceof InventoryHolder) {
			this.inventory = ((InventoryHolder) state).getInventory().getContents();
		}
	}

	public ACBlockState(BlockState state) {
		this(state, state.getData());
	}
}