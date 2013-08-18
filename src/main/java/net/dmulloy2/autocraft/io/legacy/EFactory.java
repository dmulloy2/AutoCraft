package net.dmulloy2.autocraft.io.legacy;

public interface EFactory<E extends Entity> {

	public abstract E newEntity();
	
}