package net.dmulloy2.autocraft.io.legacy;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class ACProperties extends Entity {
	public static final transient int MAX_ALTITUDE = 254;
	public static final transient int MIN_ALTITUDE = 2;
	public transient String SHIP_TYPE;
	public int MAX_BLOCKS;
	public int MIN_BLOCKS;
	public int MAX_SHIP_DIMENSIONS;
	public int MAIN_TYPE;
	public int MOVE_SPEED;
	public boolean DROPS_NAPALM;
	public boolean DROPS_BOMB;
	public boolean FIRES_TNT;
	public boolean FIRES_TORPEDO;
	public boolean IGNORE_ATTACHMENTS = false;
	public int MAX_CANNON_LENGTH;
	public int MAX_NUMBER_OF_CANNONS;
	public int CANNON_MATERIAL;
	public int FAST_FLY_AT_SIZE = 1000;
	public List<Integer> ALLOWED_BLOCKS = new ArrayList<Integer>();
}