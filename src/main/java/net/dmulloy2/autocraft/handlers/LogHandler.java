package net.dmulloy2.autocraft.handlers;

import java.util.logging.Level;

import net.dmulloy2.autocraft.util.FormatUtil;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author dmulloy2
 */

public class LogHandler {
	private final JavaPlugin plugin;

	public LogHandler(JavaPlugin plugin) {
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
			log("[Debug] " + msg, objects);
		}
	}
}