package net.dmulloy2.autocraft.io.legacy;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.plugin.java.JavaPlugin;

@Deprecated
public class SCache<E extends Entity, S extends JavaPlugin> {
	protected final S s;
	protected final File FOLDER;
	
	protected volatile transient Map<String, E> entities = new HashMap<String, E>();
	
	protected final String name;
	protected final EFactory<E> factory;

	public SCache(S p, String name, EFactory<E> factory) {
		this.s = p;
		this.name = name;
		this.factory = factory;
		this.FOLDER = new File(s.getDataFolder(), this.name);
		init();
	}
	
	private void init() {
		if (!this.FOLDER.exists())
			this.FOLDER.mkdir();
	}
	
	// Load all entities in the cache's serialization directory
	protected void loadAllEntities() {
		Map<String, E> loadMap = new HashMap<String, E>();
		for (File file : FOLDER.listFiles()) {
			String name = file.getName();
			loadMap.put(name, loadEntity(name));
		}
		
		entities = Collections.unmodifiableMap(loadMap);
	}
	
	// Loads entity from file or creates a new file for them if none exists.
	protected E loadEntity(final String name) {
		E e = this.factory.newEntity();
		SPersist.load(s, e, e.getClass(), new File(this.FOLDER, name));
		return e;
	}
	
	// Returns entity.
	protected E getEntity(final String name) {
		E e = this.entities.get(name);
		if (e == null) {
			File f = new File(FOLDER, name);
			if (f.exists())
				e = addEntity(name);
		}
		
		return e;
	}
	
	// Adds entity to cache for future use and also returns entity.
	protected E addEntity(final String name) {
		E e = loadEntity(name);	
		add(name, e);
		return e;
	}

	protected Map<String, E> getEntities() {
		return Collections.unmodifiableMap(this.entities);
	}
	
	// Saves all entities currently stored in the collection.
	public void save() {
		s.getLogger().info("Saving " + this.name + " to disk...");
		try {
			cleanupEntities();
			for (Entry<String, E> entry : getEntities().entrySet()) {
				SPersist.save(s, entry.getValue(), entry.getValue().getClass(), new File(FOLDER, entry.getKey()));
			}
		} catch (Exception e) {
			s.getLogger().info("Cannot save " + this.name + " before they have even loaded!");
		}
	}
	
	// Override this to add cleanup for entities. Be sure to use the removeEntity method when using this so as to save the entities on unload.
	public void cleanupEntities() {
		
	}
		
	synchronized void add(final String name, final E e) {
		Map<String, E> copy = new HashMap<String, E>();
		copy.putAll(this.entities);
		copy.put(name, e);
		
		this.entities = Collections.unmodifiableMap(copy);
	}
	
	synchronized void remove(final String name) {
		Map<String, E> copy = new HashMap<String, E>();
		copy.putAll(this.entities);
		copy.remove(name);
		
		this.entities = Collections.unmodifiableMap(copy);
	}	
}