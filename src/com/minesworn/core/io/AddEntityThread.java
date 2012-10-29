package com.minesworn.core.io;

import com.minesworn.core.util.SThread;

public class AddEntityThread<E extends Entity> extends SThread {
	final SDatabase<E> database;
	final String name;
	final E e;
	public AddEntityThread(SDatabase<E> database, final String name, final E e) {
		super();
		this.database = database;
		this.name = name;
		this.e = e;
	}
	
	@Override
	public synchronized void run() {
		try {
			while (database.loading)
				Thread.sleep(1000);
			
			database.add(name, e);
		} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	
}
