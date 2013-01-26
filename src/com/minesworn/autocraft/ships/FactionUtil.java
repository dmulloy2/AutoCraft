/**
 * Copyright (C) 2012 t7seven7t
 */
package com.minesworn.autocraft.ships;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Relation;
import com.minesworn.autocraft.Autocraft;

/**
 * @author t7seven7t
 */
public class FactionUtil {

	public static boolean canPlayerUseWeapon(Player player) {
		PluginManager pm = Autocraft.p.getServer().getPluginManager();
		FLocation loc = new FLocation(player.getLocation());
		FPlayer fme = FPlayers.i.get(player);
		Faction myFaction = fme.getFaction();
		Faction them = Board.getFactionAt(loc);
		if (pm.isPluginEnabled("SwornNations"))
			them = Board.getAbsoluteFactionAt(loc);
		
		// Allow in Wilderness
		if (them.isNone())
			return true;
		
		// Deny in war/safe zones
		if (!them.isNormal()) {
			fme.msg("<i>You cannot use weapons in %s.", them.getTag(fme));
			return false;
		}
		
		Relation rel = myFaction.getRelationTo(them);
		// Deny if allies
		if (rel.isAtLeast(Relation.ALLY)) {
			fme.msg("<i>You cannot do that in the territory of %s.", them.getTag(fme));
			return false;
		}
	
		return true;
	}
	
}
