package com.minesworn.autocraft.core.util;

import java.text.MessageFormat;

import org.bukkit.ChatColor;

public class FormatUtil {

	public static String format(String format, Object... objects) {
		String ret = MessageFormat.format(format, objects);
		return ChatColor.translateAlternateColorCodes('&', ret);
	}
	
}
