package com.minesworn.autocraft.core.permissions;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PermissionBase {
	
	public static boolean hasPermission(CommandSender sender, String permission) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPermission(permission) || p.isOp())
				return true;
		} else
			return true;
		
		return false;
	}
	
}
