package com.minesworn.autocraft.core.commands;

import java.util.HashSet;
import java.util.Set;

import com.minesworn.autocraft.core.SPlugin;

public class SCommandRoot<S extends SPlugin> {

	public S s;
	public Set<SCommand<S>> commands = new HashSet<SCommand<S>>();
	
	private CmdHelp<S> CMD_HELP = new CmdHelp<S>();
	
	public SCommandRoot(S s) {
		this.s = s;
		addCommand(CMD_HELP);
	}
	
	public void addCommand(SCommand<S> command) {
		command.s = s;
		commands.add(command);
	}
	
}
