package com.minesworn.autocraft.core.io;

import java.io.File;

import com.minesworn.autocraft.core.util.SThread;

public class SaveEntityThread<E extends Entity> extends SThread {
	final SCache<E> cache;
	final String name;
	final E e;
	public SaveEntityThread(SCache<E> cache, String name, E e) {
		super();
		this.cache = cache;
		this.name = name;
		this.e = e;
	}
	
	@Override
	public void run() {
		SPersist.save(e, e.getClass(), new File(cache.FOLDER, name));
	}

}
