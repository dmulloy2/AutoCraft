/**
 * Copyright (C) 2012 t7seven7t
 */
package net.dmulloy2.autocraft.util;

import java.text.DecimalFormat;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * @author t7seven7t
 */
public final class Util {

	public static Player matchPlayer(String p) {
		List<Player> players = Bukkit.matchPlayer(p);
		
		if (players.size() >= 1)
			return players.get(0);
		
		return null;
	}
	
	public static OfflinePlayer matchOfflinePlayer(String p) {
		if (matchPlayer(p) != null)
			return matchPlayer(p);
		
		for (OfflinePlayer o : Bukkit.getOfflinePlayers()) {
			if (o.getName().equalsIgnoreCase(p))
				return o;
		}
		
		return null;
	}
	
	public static boolean isBanned(OfflinePlayer p) {
		for (OfflinePlayer banned : Bukkit.getBannedPlayers()) {
			if (p.getName().equalsIgnoreCase(banned.getName()))
				return true;
		}
		return false;
	}
	
	public static double roundNumDecimals(double d, int num) {
		StringBuilder format = new StringBuilder("#.");
		for (int i = 0; i < num; i++)
			format.append("#");
        DecimalFormat f = new DecimalFormat(format.toString());
        return Double.valueOf(f.format(d));
	}
	
}
