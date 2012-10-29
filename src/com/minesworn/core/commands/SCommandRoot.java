package com.minesworn.core.commands;

import java.util.HashSet;
import java.util.Set;

import com.minesworn.core.SPlugin;

public class SCommandRoot<S extends SPlugin> {

	SPlugin s;
	public Set<SCommand<?>> commands = new HashSet<SCommand<?>>();
	
	private CmdHelp CMD_HELP = new CmdHelp();
	
	public SCommandRoot(SPlugin p) {
		this.s = p;
		addCommand(CMD_HELP);
	}
	
	public void addCommand(SCommand<?> command) {
		command.s = s;
		commands.add(command);
	}
	
}
