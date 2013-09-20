package net.dmulloy2.autocraft.types;

public enum Permission {
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
	Permission(final String node) {
		this.node = node;
	}
	
	public String getNode() {
		return node;
	}
}