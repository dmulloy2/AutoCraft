/**
 * (c) 2014 dmulloy2
 */
package net.dmulloy2.autocraft.intrgration;

import lombok.Getter;
import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.integration.IntegrationHandler;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.types.Relation;

/**
 * @author dmulloy2
 */

@Getter
public class FactionsHandler extends IntegrationHandler {
	private boolean factionsEnabled;
	private boolean swornNationsEnabled;
	private final AutoCraft plugin;

	public FactionsHandler(AutoCraft plugin) {
		this.plugin = plugin;
		this.setup();
	}

	@Override
	public final void setup() {
		try {
			PluginManager pm = plugin.getServer().getPluginManager();
			if (plugin.getConfig().getBoolean("factionsProtectionsEnabled", false)) {
				factionsEnabled = pm.getPlugin("Factions") != null;
				swornNationsEnabled = pm.getPlugin("SwornNations") != null;
	
				if (factionsEnabled) {
					plugin.getLogHandler().log(plugin.getMessage("log_factions_found"));
				} else {
					plugin.getLogHandler().log(plugin.getMessage("log_factions_notfound"));
				}
			}
		} catch (Throwable ex) {
			factionsEnabled = false;
			swornNationsEnabled = false;
		}
	}

	@Override
	public final boolean isEnabled() {
		return factionsEnabled;
	}

	public final boolean canPlayerUseWeapon(Player player) {
		if (! factionsEnabled) {
			return true;
		}

		try {
			FLocation loc = new FLocation(player.getLocation());
			FPlayer fme = FPlayers.i.get(player);
			Faction myFaction = fme.getFaction();
			Faction them = Board.getFactionAt(loc);
			if (swornNationsEnabled) {
				them = Board.getAbsoluteFactionAt(loc);
			}

			// Allow in Wilderness
			if (them.isNone()) {
				return true;
			}

			// Deny in war/safe zones
			if (! them.isNormal()) {
				fme.msg("<i>You cannot use weapons in %s.", them.getTag(fme));
				return false;
			}

			Relation rel = myFaction.getRelationTo(them);

			// Deny if allies
			if (rel.isAtLeast(Relation.ALLY)) {
				fme.msg("<i>You cannot do that in the territory of %s.", them.getTag(fme));
				return false;
			}
		} catch (Throwable ex) {
		}

		return true;
	}
}