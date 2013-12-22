package net.dmulloy2.autocraft.types;

import lombok.Data;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

/**
 * @author dmulloy2
 */

@Data
public class ACBlockState {
	private Material type;
	private BlockState state;
	private MaterialData data;
	private ItemStack[] inventory;

	public ACBlockState(BlockState state, MaterialData data, Material type) {
		this.type = type;
		this.data = data;
		this.state = state;

		if (state instanceof InventoryHolder) {
			this.inventory = ((InventoryHolder) state).getInventory().getContents();
		}
	}

	public ACBlockState(BlockState state, MaterialData data) {
		this(state, data, state.getType());
	}

	public ACBlockState(BlockState state, Material type) {
		this(state, state.getData(), type);
	}

	public ACBlockState(BlockState state) {
		this(state, state.getData());
	}
}