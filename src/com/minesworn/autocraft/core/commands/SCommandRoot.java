package com.minesworn.autocraft.core.commands;

import java.util.HashSet;
import java.util.Set;

public class SCommandRoot {

	public Set<SCommand<?>> commands = new HashSet<SCommand<?>>();
	
	private CmdHelp CMD_HELP = new CmdHelp();
	
	public SCommandRoot() {
		addCommand(CMD_HELP);
	}
	
	public void addCommand(SCommand<?> command) {
		commands.add(command);
	}
	
}
