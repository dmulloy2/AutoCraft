package com.minesworn.autocraft;

import com.minesworn.core.permissions.PermissionBase;

public class PermissionsManager extends PermissionBase {

	public enum Permission {
		;
		
		public final String node;
		Permission(final String node) {
			this.node = (Autocraft.p.getName() + "." + node).toLowerCase();
		}
	}
	
}
