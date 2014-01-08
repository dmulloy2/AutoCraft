package net.dmulloy2.autocraft.types;

import lombok.Data;

import net.dmulloy2.autocraft.util.Util;

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
	private BlockState state;
	private MaterialData data;
	private ItemStack[] inventory;

	public BlockData(BlockState s) {
		this.data = s.getData();
		this.state = s;

		if (s instanceof InventoryHolder) {
			this.inventory = ((InventoryHolder) s).getInventory().getContents();
		}
	}

	public BlockData(Block b) {
		this(b.getState());
	}

	@Override
	public String toString() {
		return "BlockData { state = " + Util.blockStateToString(state) + ", data = " + data + " }";
	}
}