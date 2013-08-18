package net.dmulloy2.autocraft.util;

import java.util.logging.Level;

import net.dmulloy2.autocraft.AutoCraft;

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
}