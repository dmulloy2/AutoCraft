/**
 * (c) 2014 dmulloy2
 */
package net.dmulloy2.autocraft.integration;

import java.util.logging.Level;

import net.dmulloy2.SwornPlugin;
import net.dmulloy2.integration.DependencyProvider;
import net.dmulloy2.swornnations.SwornNations;
import net.dmulloy2.util.Util;

import org.bukkit.entity.Player;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Relation;

/**
 * @author dmulloy2
 */

public class SwornNationsHandler extends DependencyProvider<SwornNations> {

	public SwornNationsHandler(SwornPlugin handler) {
		super(handler, "SwornNations");
	}

	public final boolean canUseWeapon(Player player) {
		if (! isEnabled()) {
			return true;
		}

		try {
			FLocation loc = new FLocation(player.getLocation());
			FPlayer fme = FPlayers.i.get(player);
			Faction myFaction = fme.getFaction();
			Faction them = Board.getAbsoluteFactionAt(loc);

			// Allow in Wilderness
			if (them.isNone()) {
				return true;
			}

			// Deny in war/safe zones
			if (! them.isNormal()) {
				fme.msg("<i>You cannot use weapons in %s.", them.getTag(fme));
				return false;
			}

			// Deny if allies
			Relation rel = myFaction.getRelationTo(them);
			if (rel.isAtLeast(Relation.ALLY)) {
				fme.msg("<i>You cannot do that in the territory of %s.", them.getTag(fme));
				return false;
			}
		} catch (Throwable ex) {
			handler.getLogHandler().debug(Level.WARNING, Util.getUsefulStack(ex, "canUseWeapon(" + player.getName() + ")"));
		}

		return true;
	}
}