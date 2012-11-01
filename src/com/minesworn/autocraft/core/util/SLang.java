package com.minesworn.autocraft.core.util;

import java.util.ArrayList;
import java.util.List;

import com.minesworn.autocraft.core.SPlugin;
import com.minesworn.autocraft.core.io.SPersist;

public class SLang {
	
	final transient SPlugin s;
	
	public List<String> errorMessages = new ArrayList<String>();
	public List<String> messages = new ArrayList<String>();

	public SLang(SPlugin p) {
		this.s = p;
		
		errorMessages.add("mustbeplayer: You must be a player to do this");
		errorMessages.add("argcount: Incorrect argument count: ");
		errorMessages.add("permission: You do not have sufficient permissions to use this");
	}
	
	public void load() {
		SPersist.load(s, this, SLang.class, "lang");
	}
	
	public String getErrorMessage(String s) {
		for (String ln : errorMessages) {
			if (ln.substring(0, ln.indexOf(": ")).equalsIgnoreCase(s)) {
				return ln.substring(ln.indexOf(": ") + 2);
			}
		}
		return null;
	}
	
	public String getMessage(String s) {
		for (String ln : messages) {
			if (ln.substring(0, ln.indexOf(": ")).equalsIgnoreCase(s)) {
				return ln.substring(ln.indexOf(": ") + 2);
			}
		}
		return null;	
	}

}
