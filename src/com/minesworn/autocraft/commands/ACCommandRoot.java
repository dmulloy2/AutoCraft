package com.minesworn.autocraft.commands;

import com.minesworn.autocraft.Autocraft;
import com.minesworn.core.commands.SCommandRoot;

public class ACCommandRoot extends SCommandRoot<Autocraft> {

	public CmdPilot CMD_PILOT = new CmdPilot();
	
	public ACCommandRoot(Autocraft s) {
		super(s);
		addCommand(CMD_PILOT);
	}
	
}
