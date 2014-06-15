package net.dmulloy2.autocraft.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dmulloy2.types.IPermission;

/**
 * @author dmulloy2
 */

@Getter
@AllArgsConstructor
public enum Permission implements IPermission {
	CMD_ALLOWED("allowed"),
	CMD_DISMOUNT("dismount"),
	CMD_DROP("drop"),
	CMD_FIRE("fire"),
	CMD_INFO("info"),
	CMD_LIST("list"),
	CMD_MOVE("move"),
	CMD_NAPALM("napalm"),
	CMD_PILOT("pilot"),
	CMD_RELOAD("reload"),
	CMD_ROTATE("rotate"),
	CMD_TORPEDO("torpedo");
	
	private final String node;
}