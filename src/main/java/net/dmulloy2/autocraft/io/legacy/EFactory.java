package net.dmulloy2.autocraft.io.legacy;

@Deprecated
public interface EFactory<E extends Entity> {

	public abstract E newEntity();
	
}