package net.dmulloy2.autocraft.io.legacy;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

@Deprecated
public class SPersist {

	public static <T> void save(JavaPlugin p, T instance, Class<? extends T> clazz) {
		save(p, instance, clazz, clazz.getSimpleName().toLowerCase());
	}
	
	public static <T> void save(JavaPlugin p, Class<? extends T> clazz) {
		save(p, null, clazz);
	}
	
	public static <T> void save(JavaPlugin p, T instance, Class<? extends T> clazz, String fName) {
		save(p, instance, clazz, new File(p.getDataFolder(), fName));
	}
	
	public static <T> void save(JavaPlugin p, T instance, Class<? extends T> clazz, File file) {
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
				
			FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
				
			for (Field f : clazz.getDeclaredFields()) {
				if (!Modifier.isTransient(f.getModifiers())) {
					f.setAccessible(true);
					fc.set(f.getName(), f.get(instance));
				}
			}
			
			fc.save(file);
		} catch (Exception e) {
			p.getLogger().info("Exception ocurred while attempting to save file: " + file.getName());
			e.printStackTrace();
		}
	}
	
	public static <T> void load(JavaPlugin p, T instance, Class<? extends T> clazz) {
		load(p, instance, clazz, clazz.getSimpleName().toLowerCase());
	}
	
	public static <T> void load(JavaPlugin p, Class<? extends T> clazz) {
		load(p, null, clazz);
	}
	
	public static <T> void load(JavaPlugin p, T instance, Class<? extends T> clazz, String fName) {
		load(p, instance, clazz, new File(p.getDataFolder(), fName));
	}
	
	public static <T> void load(JavaPlugin p, T instance, Class<? extends T> clazz, File file) {
		try {
			if (!file.exists()) {
				save(p, instance, clazz, file);
			}
			
			FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
			
			for (Field f : clazz.getDeclaredFields()) {
				if (!Modifier.isTransient(f.getModifiers())) {
					if (fc.get(f.getName()) == null) {
						addDefaults(p, f, fc, instance);
					}
					f.setAccessible(true);
					f.set(instance, fc.get(f.getName()));
				}
			}
			
			fc.save(file);
		} catch (Exception e) {
			p.getLogger().info("Exception ocurred while attempting to load file: " + file.getName());
			e.printStackTrace();
		}
	}
	
	public static Object readLine(JavaPlugin p, File file, String f) {
		try {			
			FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
			return fc.get(f);
		} catch (Exception e) {
			p.getLogger().info("Exception ocurred while attempting to read: " + file.getName());
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> void loadDefaultsFromInstance(T instance, T defaults) {
		for (Field f : instance.getClass().getDeclaredFields()) {
			if (!Modifier.isTransient(f.getModifiers())) {
				f.setAccessible(true);
				try {
					f.set(instance, f.get(defaults));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static <T> void addDefaults(JavaPlugin p, Field f, FileConfiguration fc, T instance) {
		try {
			f.setAccessible(true);
			fc.set(f.getName(), f.get(instance));
		} catch (Exception e) {
			p.getLogger().info("Exception ocurred while attempting to add defaults to file.");
			e.printStackTrace();
		}
	}	
}