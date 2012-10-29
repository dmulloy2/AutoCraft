package com.minesworn.autocraft;

import com.minesworn.autocraft.core.SPlugin;
import com.minesworn.autocraft.core.permissions.PermissionBase;

public class PermissionsManager extends PermissionBase {

	public enum Permission {
		;
		
		public final String node;
		Permission(final String node) {
			this.node = (SPlugin.p.getName() + "." + node).toLowerCase();
		}
	}
	
}
