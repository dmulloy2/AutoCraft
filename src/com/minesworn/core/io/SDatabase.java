package com.minesworn.core.io;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.minesworn.core.SPlugin;

public abstract class SDatabase<E extends Entity> {
	final SPlugin s;
	final File FOLDER;
	
	volatile transient Map<String, E> entities = new HashMap<String, E>();
	
	boolean loading;
	protected String name;
	private EFactory<E> factory;

	public SDatabase(SPlugin p, String name, EFactory<E> factory) {
		this.s = p;
		this.name = name;
		this.factory = factory;
		this.FOLDER = new File(s.getDataFolder(), this.name);
		init();
	}
	
	private void init() {
		if (!this.FOLDER.exists())
			this.FOLDER.mkdir();
		this.loading = true;
		loadAllEntities();
	}
	
	private void loadAllEntities() {
		new LoadEntitiesThread<E>(this, s);
	}
	
	protected E loadEntity(final String name) {
		E e = this.factory.newEntity();
		SPersist.load(s, e, e.getClass(), new File(this.FOLDER, name));
		return e;
	}
	
	protected E getEntity(final String name) {
		return this.entities.get(name);
	}
	
	protected void addEntity(final String name, final E e) {
		new AddEntityThread<E>(this, name, e);
	}
	
	protected void removeEntity(final String name, final E e) {
		new RemoveEntityThread<E>(this, name);
	}
	
	protected Map<String, E> getEntities() throws Exception {
		if (this.loading)
			throw new Exception("loading");
		else
			return Collections.unmodifiableMap(this.entities);
	}
	
	public void save() {
		s.log("Saving " + this.name + " to disk...");
		try {
			long start = System.currentTimeMillis();
			for (Entry<String, E> entry : getEntities().entrySet()) {
				SPersist.save(s, entry.getValue(), entry.getValue().getClass(), new File(FOLDER, entry.getKey()));
			}
			s.log(this.name + " saved! [" + (System.currentTimeMillis() - start) + "ms]");
		} catch (Exception e) {
			s.log("Cannot save " + this.name + " before they have even loaded!");
		}
	}
	
	public boolean isLoading() {return loading;}
	
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
