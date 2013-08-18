package net.dmulloy2.autocraft.legacy;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

public class SaveEntityThread<E extends Entity, S extends JavaPlugin> extends SThread {
	final S s;
	final SCache<E, S> cache;
	final String name;
	final E e;
	public SaveEntityThread(S p, SCache<E, S> cache, String name, E e) {
		super();
		this.s = p;
		this.cache = cache;
		this.name = name;
		this.e = e;
	}
	
	public void run() {
		SPersist.save(s, e, e.getClass(), new File(cache.FOLDER, name));
	}
}