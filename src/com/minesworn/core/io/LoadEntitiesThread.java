package com.minesworn.core.io;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.minesworn.core.SPlugin;
import com.minesworn.core.util.SThread;

public class LoadEntitiesThread<E extends Entity> extends SThread {
	final SDatabase<E> database;
	final SPlugin s;
	public LoadEntitiesThread(final SDatabase<E> database, SPlugin p) {
		super();
		this.s = p;
		this.database = database;
	}
	
	@Override
	public void run() {
		long start = System.currentTimeMillis();
		Map<String, E> loadMap = new HashMap<String, E>();
		for (File file : database.FOLDER.listFiles()) {
			String name = file.getName();
			loadMap.put(name, database.loadEntity(name));
		}
		
		database.entities = Collections.unmodifiableMap(loadMap);
		database.loading = false;
		s.log(database.name + " loaded! [" + (System.currentTimeMillis() - start) + "ms]");
	}
}
