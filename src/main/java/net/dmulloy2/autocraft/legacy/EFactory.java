package net.dmulloy2.autocraft.legacy;

public interface EFactory<E extends Entity> {

	public abstract E newEntity();
	
}