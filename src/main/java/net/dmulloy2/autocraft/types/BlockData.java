package net.dmulloy2.autocraft.types;

import lombok.Data;

import net.dmulloy2.autocraft.util.FormatUtil;
import net.dmulloy2.autocraft.util.Util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

/**
 * @author dmulloy2
 */

@Data
public class BlockData {
	private Material type;
	private BlockState state;
	private MaterialData data;
	private ItemStack[] inventory;

	public BlockData(BlockState state, MaterialData data, Material type) {
		this.type = type;
		this.data = data;
		this.state = state;

		if (state instanceof InventoryHolder) {
			this.inventory = ((InventoryHolder) state).getInventory().getContents();
		}
	}

	public BlockData(BlockState state, MaterialData data) {
		this(state, data, state.getType());
	}

	public BlockData(BlockState state, Material type) {
		this(state, state.getData(), type);
	}

	public BlockData(BlockState state) {
		this(state, state.getData());
	}

	public BlockData(Block block) {
		this(block.getState());
	}

	@Override
	public String toString() {
		return "BlockData { type = " + FormatUtil.getFriendlyName(type) + ", state = " + Util.blockStateToString(state) + ", data = " + data
				+ " }";
	}
}