package com.minesworn.autocraft.core.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Util {

	public static Player matchPlayer(String p) {
		List<Player> players = Bukkit.matchPlayer(p);
		
		if (players.size() >= 1) {
			return players.get(0);
		}
		
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
	
	public static String parseMsg(String msg, Object... args) {		
		return String.format(msg, args);
	}
	
	public static String formatTimeDifference(long t1, long t2) {
		return formatTime(getTimeDifference(t1, t2));
	}
	
	public static long getTimeDifference(long t1, long t2) {
		return (t2 - t1);
	}
	
	public static String formatTime(long time) {
		StringBuilder ret = new StringBuilder();
		int days = (int) Math.floor(time / (1000*3600*24));
		int hours = (int) Math.floor((time % (1000*3600*24)) / (1000*3600));
		int minutes = (int) Math.floor((time % (1000*3600*24)) % (1000*3600) / (1000*60));
		int seconds = (int) Math.floor(time % (1000*3600*24) % (1000*3600) % (1000*60) / (1000));
		
		if (days != 0)
			ret.append(days + "d");
		if (hours != 0 || days != 0)
			ret.append(hours + "h");
		if (minutes != 0 || hours != 0 || days != 0)
			ret.append(minutes + "m");
		ret.append(seconds + "s");
		
		return ret.toString();
	}
	
	public static String getLongDateCurr() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm");
		Date date = new Date();
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat.format(date);
	}
	
	public static String getSimpleDate(long time) {
		DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		Date date = new Date(time);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat.format(date);
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
	
	public static long parseTime(String t) throws Exception {		
		try {
			if (!t.matches("[0-9]+d[a-z]*"))
				return Math.round(Double.parseDouble(t) * 60) * 1000;
			else {
				Pattern dayPattern = Pattern.compile("([0-9]+)d[a-z]*", Pattern.CASE_INSENSITIVE);
				Matcher m = dayPattern.matcher(t);
				
				if (m.matches()) {
					return Integer.parseInt(m.group(1)) * 24 * 60 * 60 * 1000;
				}
			}
		} catch (NumberFormatException e) {
			Pattern hourPattern = Pattern.compile("([0-9]+)h[a-z]*", Pattern.CASE_INSENSITIVE);
			Pattern minPattern = Pattern.compile("([0-9]+)m[a-z]*", Pattern.CASE_INSENSITIVE);
			Pattern secPattern = Pattern.compile("([0-9]+)s[a-z]*", Pattern.CASE_INSENSITIVE);
		
			Matcher m = hourPattern.matcher(t);
			
			if (m.matches()) {
				return Integer.parseInt(m.group(1)) * 60 * 60 * 1000;
			}
			
			m = minPattern.matcher(t);
			
			if (m.matches()) {
				return Integer.parseInt(m.group(1)) * 60 * 1000;
			}
			
			m = secPattern.matcher(t);
			
			if (m.matches()) {
				return Integer.parseInt(m.group(1)) * 1000;
			}
		}
		throw new Exception("badtime");
	}
	
}
