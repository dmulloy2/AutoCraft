package net.dmulloy2.autocraft.legacy;

public abstract class SThread implements Runnable {
	Thread t;
	public SThread() {
		t = new Thread(this);
		t.start();
	}
}
