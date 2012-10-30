package com.minesworn.autocraft.commands;

import com.minesworn.autocraft.Autocraft;
import com.minesworn.core.commands.SCommandRoot;

public class ACCommandRoot extends SCommandRoot<Autocraft> {

	public CmdAllowed CMD_ALLOWED = new CmdAllowed();
	public CmdDismount CMD_DISMOUNT = new CmdDismount();
	public CmdInfo CMD_INFO = new CmdInfo();
	public CmdList CMD_LIST = new CmdList();
	public CmdPilot CMD_PILOT = new CmdPilot();
	public CmdRotate CMD_ROTATE = new CmdRotate();
	public CmdFire CMD_FIRE = new CmdFire();
	public CmdDrop CMD_DROP = new CmdDrop();
	public CmdNapalm CMD_NAPALM = new CmdNapalm();
	public CmdTorpedo CMD_TORPEDO = new CmdTorpedo();
	
	public ACCommandRoot(Autocraft s) {
		super(s);
		addCommand(CMD_ALLOWED);
		addCommand(CMD_DISMOUNT);
		addCommand(CMD_INFO);
		addCommand(CMD_LIST);
		addCommand(CMD_PILOT);
		addCommand(CMD_ROTATE);
		addCommand(CMD_FIRE);
		addCommand(CMD_DROP);
		addCommand(CMD_NAPALM);
		addCommand(CMD_TORPEDO);
	}
	
}
