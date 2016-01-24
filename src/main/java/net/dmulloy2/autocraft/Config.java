/**
 * (c) 2016 dmulloy2
 */
package net.dmulloy2.autocraft;

import static net.dmulloy2.util.ListUtil.toList;

import java.util.List;
import java.util.concurrent.TimeUnit;

import net.dmulloy2.config.ConfigParser;
import net.dmulloy2.config.Key;
import net.dmulloy2.config.ValueOptions;
import net.dmulloy2.config.ValueOptions.ValueOption;

import org.bukkit.Material;

/**
 * @author dmulloy2
 */

public class Config {

	public static void load(AutoCraft plugin) {
		ConfigParser.parse(plugin, Config.class);
	}

	@Key("weaponCooldownTime")
	@ValueOptions(ValueOption.SECOND_TO_MILLIS)
	public static long weaponCooldownTime = TimeUnit.SECONDS.toMillis(6);

	@Key("numTntToFireNormal")
	public static int numTntToFireNormal = 4;

	@Key("numTntToFireTorpedo")
	public static int numTntToFireTorpedo = 8;

	@Key("numTntToDropBomb")
	public static int numTntToDropBomb = 2;

	@Key("numTntToDropNapalm")
	public static int numTntToDropNapalm = 4;

	@Key("materialsNeededForTorpedo")
	@ValueOptions(ValueOption.PARSE_MATERIALS)
	public static List<Material> materialsNeededForTorpedo = toList(Material.DIAMOND, Material.FLINT_AND_STEEL);

	@Key("materialsNeededForNapalm")
	@ValueOptions(ValueOption.PARSE_MATERIALS)
	public static List<Material> materialsNeededForNapalm = toList(Material.SLIME_BALL, Material.FLINT_AND_STEEL);

	@Key("napalmBurnRadius")
	public static int napalmBurnRadius = 6;

	@Key("factionsProtectionsEnabled")
	public static boolean factionsProtectionsEnabled = false;

	@Key("recursion.cap")
	public static boolean recursionCap = false;

	@Key("recursion.time")
	@ValueOptions(ValueOption.SECOND_TO_MILLIS)
	public static int maxRecursionTime = -1;

	@Key("sinking.enabled")
	public static boolean sinkingEnabled = true;

	@Key("sinking.interval")
	@ValueOptions(ValueOption.SECOND_TO_MILLIS)
	public static long sinkingInterval = TimeUnit.SECONDS.toMillis(10);

	@Key("debug")
	public static boolean debug = false;
}