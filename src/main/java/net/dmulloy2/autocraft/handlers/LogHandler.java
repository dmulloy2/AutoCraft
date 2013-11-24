package net.dmulloy2.autocraft.handlers;

import java.util.logging.Level;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.util.FormatUtil;

/**
 * @author dmulloy2
 */

public class LogHandler {
	private final AutoCraft plugin;
	
	public LogHandler(AutoCraft plugin) {
		this.plugin = plugin;
	}

	public final void log(Level level, String msg, Object... objects) {
		plugin.getLogger().log(level, FormatUtil.format(msg, objects));
	}

	public final void log(String msg, Object... objects) {
		log(Level.INFO, msg, objects);
	}

	public final void debug(String msg, Object... objects) {
		if (plugin.getConfig().getBoolean("debug", false)) {
			log(msg, objects);
		}
	}
}