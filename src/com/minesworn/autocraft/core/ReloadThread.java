package com.minesworn.autocraft.core;

import com.minesworn.autocraft.core.util.SThread;

public class ReloadThread extends SThread {
	final SPlugin s;
	public ReloadThread(SPlugin p) {
		this.s = p;
	}
	
	public void run() {
		try {
			s.onDisable();
			Thread.sleep(2000);
			s.onEnable();
			s.afterReload();
		} catch (InterruptedException e) {}
	}

}
