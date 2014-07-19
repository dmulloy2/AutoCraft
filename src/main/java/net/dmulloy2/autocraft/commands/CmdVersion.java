/**
 * (c) 2014 dmulloy2
 */
package net.dmulloy2.autocraft.commands;

import net.dmulloy2.autocraft.AutoCraft;

/**
 * @author dmulloy2
 */

public class CmdVersion extends AutoCraftCommand {

	public CmdVersion(AutoCraft plugin) {
		super(plugin);
		this.name = "version";
		this.aliases.add("v");
		this.description = "Displays version information";
	}

	@Override
	public void perform() {
		sendMessage("&3====[ &eAutoCraft &3]====");
		sendMessage("&bVersion&e: {0}", plugin.getDescription().getVersion());
		sendMessage("&bAuthor&e: dmulloy2");
		sendMessage("&bIssues&e: https://github.com/MineSworn/AutoCraft/issues");
	}
}