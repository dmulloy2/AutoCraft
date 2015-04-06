/**
 * AutoCraft - a Bukkit plugin
 * Copyright (C) 2015 dmulloy2
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.dmulloy2.autocraft.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.dmulloy2.reflection.ReflectionUtil;
import net.dmulloy2.util.FormatUtil;

import org.apache.commons.lang.Validate;
import org.bukkit.block.Banner;
import org.bukkit.block.BlockState;
import org.bukkit.block.CommandBlock;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Furnace;
import org.bukkit.block.Jukebox;
import org.bukkit.block.NoteBlock;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.inventory.Inventory;

/**
 * Utility methods for dealing with BlockStates.
 * @author dmulloy2
 */

public final class BlockStates {
	private BlockStates() { }

	/**
	 * Serializes a given BlockState into a map. This method ignores
	 * inventories, which should be handled separately.
	 *
	 * @param state BlockState to serialize
	 * @return Serialized block state
	 */
	public static Map<String, Object> serialize(BlockState state) {
		Map<String, Object> ret = new HashMap<>();

		for (Method method : ReflectionUtil.getMethods(state.getClass())) {
			try {
				String name = method.getName();
				if (name.startsWith("get") && method.getParameterTypes().length == 0) {
					name = name.substring(3, name.length());
					Object value = method.invoke(state);
					if (! (value instanceof Inventory)) {
						ret.put(name, value);
					}
				}
			} catch (Throwable ex) {
			}
		}

		return ret;
	}

	/**
	 * Applies serialized data from {@link #serialize(BlockState)} back to a
	 * BlockState.
	 * 
	 * @param state State to apply data to
	 * @param values Data to apply
	 */
	public static void deserialize(BlockState state, Map<String, Object> values) {
		for (Method method : ReflectionUtil.getMethods(state.getClass())) {
			try {
				String name = method.getName();
				if (name.startsWith("set") && method.getParameterTypes().length == 1) {
					name = name.substring(3, name.length());
					if (values.containsKey(name)) {
						method.invoke(state, values.get(name));
					}
				}
			} catch (Throwable ex) {
			}
		}
	}

	/**
	 * Returns a <code>String</code> representation of a {@link BlockState},
	 * since BlockStates do not define a <code>toString()</code> method.
	 *
	 * @param state BlockState to represent
	 * @return The string representation
	 */
	public static String toString(BlockState state) {
		Validate.notNull(state, "state cannot be null!");

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
			return "Skull[type=" + FormatUtil.getFriendlyName(skull.getSkullType()) + ", owner=" + skull.getOwner() + "]";
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
}