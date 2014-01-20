package net.dmulloy2.autocraft.util;

import java.text.MessageFormat;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;

/**
 * Util used for general formatting
 * 
 * @author dmulloy2
 */

public class FormatUtil {

	/**
	 * Formats a given string and its object
	 * 
	 * @param format
	 *        - Base string
	 * @param objects
	 *        - Objects to format in
	 * @return Formatted string
	 */
	public static String format(String format, Object... objects) {
		String ret = MessageFormat.format(format, objects);
		return ChatColor.translateAlternateColorCodes('&', ret);
	}

	/**
	 * Returns the "friendly" text version of an enum constant
	 * 
	 * @param en
	 *        - Enum constant
	 * @return The "friendly" text version of the given enum constant
	 */
	public static String getFriendlyName(Enum<?> en) {
		return getFriendlyName(en.toString());
	}

	/**
	 * Returns the "Friendly" version of a given string
	 * 
	 * @param mat
	 *        - String to get the "friendly" version for
	 * @return The "friendly" version of the given string
	 */
	public static String getFriendlyName(String string) {
		String ret = string.toLowerCase();
		ret = ret.replaceAll("_", " ");
		return WordUtils.capitalize(ret);
	}

	/**
	 * Returns the proper article of a given string
	 * 
	 * @param string
	 *        - String to get the article for
	 * @return The article that should go with the string
	 */
	public static String getArticle(String string) {
		string = string.toLowerCase();
		if (string.startsWith("a") || string.startsWith("e") || string.startsWith("i") || string.startsWith("o") || string.startsWith("u"))
			return "an";

		return "a";
	}
}