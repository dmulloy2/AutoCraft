package net.dmulloy2.autocraft.io.legacy;

@Deprecated
public abstract class SThread implements Runnable {
	private Thread t;
	
	public SThread() {
		t = new Thread(this);
		t.start();
	}
}