package net.dmulloy2.autocraft.types;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import lombok.Data;

/**
 * @author dmulloy2
 */

@Data
public class BlockWrapper {
	private BlockState state;
	private BlockData data;
	private ItemStack[] inventory;

	public BlockWrapper(Block block) {
		this.data = block.getBlockData();
		this.state = block.getState();

		if (this.state instanceof InventoryHolder) {
			this.inventory = ((InventoryHolder) this.state).getInventory().getContents();
		}
	}

	@Override
	public String toString() {
		return "BlockData[state=" + state + ", data=" + data + "]";
	}
}