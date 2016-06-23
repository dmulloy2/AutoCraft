package net.dmulloy2.autocraft.types;

import java.util.Arrays;

import lombok.Data;
import net.dmulloy2.util.FormatUtil;

import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CommandBlock;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Furnace;
import org.bukkit.block.Jukebox;
import org.bukkit.block.NoteBlock;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
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

	private String toString(BlockState state) {
		if (state instanceof Sign) {
			Sign sign = (Sign) state;
			return "Sign[lines=" + Arrays.toString(sign.getLines()) + "]";
		} else if (state instanceof CommandBlock) {
			CommandBlock cmd = (CommandBlock) state;
			return "CommandBlock[command=" + cmd.getCommand() + ", name=" + cmd.getName() + "]";
		} else if (state instanceof Jukebox) {
			Jukebox jukebox = (Jukebox) state;
			return "Jukebox[playing=" + FormatUtil.getFriendlyName(jukebox.getPlaying()) + "]";
		} else if (state instanceof NoteBlock) {
			NoteBlock note = (NoteBlock) state;
			return "NoteBlock[note=" + note.getNote() + "]";
		} else if (state instanceof Skull) {
			Skull skull = (Skull) state;
			return "Skull[type=" + FormatUtil.getFriendlyName(skull.getSkullType()) + ", owner=" + skull.getOwningPlayer() + "]";
		} else if (state instanceof Furnace) {
			Furnace furnace = (Furnace) state;
			return "Furnace[burnTime=" + furnace.getBurnTime() + ", cookTime=" + furnace.getCookTime() + "]";
		} else if (state instanceof Banner) {
			Banner banner = (Banner) state;
			return "Banner[baseColor=" + FormatUtil.getFriendlyName(banner.getBaseColor()) + ", patterns=" + banner.getPatterns() + "]";
		} else if (state instanceof CreatureSpawner) {
			CreatureSpawner spawner = (CreatureSpawner) state;
			return "CreatureSpawner[spawnedType=" + FormatUtil.getFriendlyName(spawner.getSpawnedType()) + "]";
		} else {
			return "BlockState[type=" + FormatUtil.getFriendlyName(state.getType()) + "]";
		}
	}

	@Override
	public String toString() {
		return "BlockData[state=" + toString(state) + ", data=" + data + "]";
	}
}