package com.minesworn.autocraft.core.io;

public abstract class Entity {

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	transient boolean modified = false;
	
}
