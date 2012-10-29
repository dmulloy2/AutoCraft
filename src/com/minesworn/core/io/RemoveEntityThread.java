package com.minesworn.core.io;

import com.minesworn.core.util.SThread;

public class RemoveEntityThread<E extends Entity> extends SThread {
	final SDatabase<E> database;
	final String name;
	public RemoveEntityThread(SDatabase<E> database, String name) {
		super();
		this.database = database;
		this.name = name;
	}
	
	@Override
	public void run() {
		try {
			while (database.loading)
				Thread.sleep(1000);
			
			database.remove(name);
		} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	
}
