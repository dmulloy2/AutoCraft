package com.minesworn.core.io;

public interface EFactory<E extends Entity> {

	public abstract E newEntity();
	
}
