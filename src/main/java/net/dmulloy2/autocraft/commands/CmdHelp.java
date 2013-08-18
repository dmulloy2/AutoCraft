package net.dmulloy2.autocraft.commands;

import java.util.ArrayList;
import java.util.List;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.util.FormatUtil;

/**
 * Help command that shows descriptions of other commands. 
 * Has pagination.
 */
public class CmdHelp extends PaginatedCommand {

	public CmdHelp(AutoCraft plugin) {
		super(plugin);
		this.name = "help";
		this.description = "Shows " + plugin.getName() + " help.";
		this.optionalArgs.add("page");
		this.linesPerPage = 6;
	}

	@Override
	public int getListSize() {
		return plugin.getCommandHandler().getRegisteredCommands().size();
	}

	@Override
	public String getHeader(int index) {
		return FormatUtil.format("&2{0} Help (&ePage {1}/{2}&2):", plugin.getName(), index, getPageCount());
	}

	@Override
	public List<String> getLines(int startIndex, int endIndex) {
		List<String> lines = new ArrayList<String>();
		for (int i = startIndex; i < endIndex && i < getListSize(); i++) {
			AutoCraftCommand command;
			command = plugin.getCommandHandler().getRegisteredCommands().get(i);
			
			if (plugin.getPermissionHandler().hasPermission(sender, permission))
				lines.add(command.getUsageTemplate(true));
		}
		return lines;
	}

	
	@Override
	public String getLine(int index) {
		// Unnecessary since we override getLines();
		return null;
	}


}
