package com.minesworn.autocraft.ships;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.minesworn.autocraft.Autocraft;

public class ACBaseShip {
	public ACProperties properties;
	
	// Changes to true if the ship has a problem creating.
	boolean stopped;
	Player player;

	public int maxBlocks;
	public int minBlocks;

	public boolean allowflight;

	public int cannonlength;
	public int maxcannon;
	public Material maintype;
	public int torpedoTNT = 16;
	public int cannontTNT = 4;
	public int dropTNT = 2;
	public int cannonmaterial = 42;
	public int torpedomaterial = 41;
	public int cooldowntime = 6;
	public int cooldowntimeDROP = 6;
	public int cooldowntimeTORPEDO = 6;
	public int cooldowntimeNAPALM = 6;
	public int cooldowntimeARROW = 6;
	public boolean canmove = true;
	public List<Integer> torpedoAdditional;
	public List<Integer> napalmAdditional;
	public int movespeed;
	public int numMainBlocks = 0;
	long lastmove = System.currentTimeMillis();
	Block[] blocks;
	Block mainblock;
	
	public ACBaseShip() {
		
	}
	
	public void startship() {
		updateMainBlock();
		boolean can = beginRecursion(this.mainblock);
		if (areBlocksValid()) {
			for (ACBaseShip othership : Autocraft.shipmanager.ships.values()) {
				if ()
			}
		}
	}
	
	public void move(int x, int y, int z) {
		// If the airship has been stopped then remove it from the mapping.
		if (this.stopped)
			Autocraft.shipmanager.ships.remove(player.getName());
		else if (this.canmove && this.allowflight) {
			// Check that the ship hasn't already moved within the last second.
			if (System.currentTimeMillis() - this.lastmove > 1000L) {
				// Reduce the matrix given to identities only.
				if (Math.abs(x) > 1)
					x /= Math.abs(x);
				if (Math.abs(y) > 1)
					y /= Math.abs(y);
				if (Math.abs(z) > 1)
					z /= Math.abs(z);
				
				// Set last move to current time.
				this.lastmove = System.currentTimeMillis();
				boolean obstruction = false;
				
				Block[] newBlocksPosition = blocks.clone();
				
				// Check each block's new position for obstructions
				for (int i = 0; i < newBlocksPosition.length; i++) {
					Block block = newBlocksPosition[i].getRelative(x, y, z);
					if (block.getLocation().getBlockY() + y > this.maxaltitude)
						obstruction = true;
					if (block.getType().equals(Material.AIR)|| blockBelongsToShip(block, newBlocksPosition))
						continue;
					obstruction = true;
				}
				
				// Can't move :/
				if (obstruction)
					this.player.sendMessage(ChatColor.YELLOW + "Obstruction" + ChatColor.RED + " - Cannot move any further in this direction.");
				// Lets move this thing :D
				else if (areBlocksValid()) {
					Location loc = this.player.getLocation().clone().add(0, -1, 0);
					Block blockon = this.player.getWorld().getBlockAt(player.getLocation());
					if (!blockon.getType().equals(Material.AIR))
						if (blockBelongsToShip(blockon, this.blocks)) {
							newBlocksPosition = null;
							domove("move", x, y, z);
						} else
							this.player.sendMessage(ChatColor.GRAY + "You are not on a ship");
				}
			}
		}
	}
	
	public void rotate(TurnDirection dir) {
		Block[] newBlocksPosition;
		int count = 0;
		try {
			if (areBlocksValid())
				try {
					this.blocks = null;
					this.numMainBlocks = 0;
					updateMainBlock();
					recurse(0, this.mainblock);
					
					int t = x;
					if (dir = TurnDirection.LEFT) {
						x = -z;
						z = x;
					} else {
						x = z;
						z = -t;
					}
					
					if (id == 23) {
						byte dat = 
					}
				}
		}
	}
	
	public void domove(String movetype, int x, int y, int z) {
		List<Player> passengers = getPassengers();
		
		this.canmove = false;
		
		boolean recurse = false;
		
	}
	
	// Update main block with which block the player is standing on.
	public void updateMainBlock() {
		this.mainblock = player.getWorld().getBlockAt(player.getLocation().add(0, -1, 0));
	}
	
	// Checks the block material against the AC properties for this ship
	public boolean isValidMaterial(Block block) {
		return (this.properties.MAIN_TYPE == block.getTypeId() || this.properties.ALLOWED_BLOCKS.contains(block.getTypeId()));
	}
	
	// Checks that the ship is within its size restraints as defined by its AC properties.
	public boolean areBlocksValid() {
		if (!isValidMaterial(this.mainblock))
			this.player.sendMessage(ChatColor.RED + "Please stand on a valid block for this type of ship");
		else {
			if (this.blocks.length > this.properties.MAX_BLOCKS)
				this.player.sendMessage(ChatColor.RED + "Your ship has " + ChatColor.YELLOW + this.blocks.length + ChatColor.RED + "/" + ChatColor.YELLOW + this.properties.MAX_BLOCKS + ChatColor.RED + " blocks. Please delete some.");
			else if (this.numMainBlocks < this.properties.MIN_BLOCKS)
				this.player.sendMessage(ChatColor.RED + "Your ship has " + ChatColor.YELLOW + this.numMainBlocks + ChatColor.RED + "/" + ChatColor.YELLOW + this.properties.MIN_BLOCKS + ChatColor.AQUA + " " + getMainType() + ChatColor.RED + " blocks. Please add more.");
			else
				return true;
		}
		return false;
	}
	
	// Returns a string name for the main material of this ship.
	public String getMainType() {
		return Material.getMaterial(this.properties.MAIN_TYPE).toString().replace("_", " ").toLowerCase();
	}
	
	// Checks that the block being queried belongs to the block list for this ship.
	public boolean blockBelongsToShip(Block block, Block[] blocks) {
		if (blocks == null)
			blocks = this.blocks.clone();
		for (int i = 0; i < blocks.length; i++)
			if (blocks[i].equals(block))
				return true;
		return false;
	}
	
	// Checks all online players to find which are passengers on this ship.
	public List<Player> getPassengers() {
		List<Player> ret = new ArrayList<Player>();
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (isPassenger(p))
				ret.add(p);
		}
		return ret;
	}
	
	// Checks if player is on any block on the ship and returns true if they are. 
	public boolean isPassenger(Player player) {
		Block[] blocks = this.blocks.clone();
		for (int i = 0; i < blocks.length; i++) {
			Block block = blocks[i];
			if (block.getType().equals(Material.AIR))
				continue;
			Block blockon = player.getWorld().getBlockAt(player.getLocation().add(0, -1, 0));
			Block blockon2 = player.getWorld().getBlockAt(player.getLocation().add(0, -2, 0));
			if (blockon.getLocation().equals(block.getLocation()) || blockon2.getLocation().equals(block.getLocation()))
				return true;
		}
		return false;
	}
	
	public boolean beginRecursion(Block block) {
		ArrayList<Block> blockList = new ArrayList<Block>();
		blockList = recurse(block, blockList);
		
		if (blockList != null) {
			blocks = blockList.toArray(new Block[0]);
			return true;
		}
		return false;
	}
	
	// Recursively call this method for every block relative to the starting block.
	public ArrayList<Block> recurse(Block block, ArrayList<Block> blockList) {
		boolean original = (blockList.isEmpty());
		
		if (!this.stopped) {
			if (blockList.size() <= ACProperties.MAX_SHIP_SIZE) {
				// If this new block to be checked doesn't already belong to the ship and is a valid material, accept it.
				if (!blockBelongsToShip(block, blockList.toArray(new Block[0])) && isValidMaterial(block)) {
					// If its material is same as main type than add to number of main block count.
					if (block.getTypeId() == this.properties.MAIN_TYPE)
						this.numMainBlocks++;
					
					// Add current block to recursing block list.
					blockList.add(block);
					
					// Recurse for each block around this block and in turn each block around them before eventually returning to this method instance.
					for (RelativePosition dir : RelativePosition.values()) {
						blockList = recurse(block.getRelative(dir.x, dir.y, dir.z), blockList);
					}
				// Otherwise if the block isn't a block that the ship is allowed to touch then stop creating the ship.
				} else if (!isValidMaterial(block) && !block.getType().equals(Material.AIR) 
						&& !block.getType().equals(Material.BEDROCK) 
						&& !block.getType().equals(Material.WATER) 
						&& !block.getType().equals(Material.STATIONARY_WATER)) {
					
					Autocraft.shipmanager.ships.remove(this.player.getName());
					player.sendMessage(ChatColor.DARK_RED + "This ship needs to be floating!");
					String str = "problem at (" + String.valueOf(block.getX()) + "," + 
													String.valueOf(block.getY()) + "," + 
													String.valueOf(block.getZ()) + ") ITS ON " + 
													block.getType().toString();
					player.sendMessage(str);
					System.out.println("PLAYER " + this.player.getName() + " HAD PROBLEM FLYING AIRSHIP. " + str);
					this.stopped = true;
					return null;
				}
			} else {
				// Ship is too large as defined by built in limit
				Autocraft.shipmanager.ships.remove(this.player.getName());
				player.sendMessage(ChatColor.GRAY + "This ship has over " + ACProperties.MAX_SHIP_SIZE + " blocks!");
				this.stopped = true;
				return null;
			}
		}
		
		if (original) {
			// Check each direction for if the ship is larger than specified ship dimensions.
			for (Direction dir : Direction.values()) {
				double min = block.getLocation().toVector().dot(dir.v);
				double max = min;
				for (Block b : blockList) {
					double bLoc = b.getLocation().toVector().dot(dir.v);
					if (bLoc < min)
						min = bLoc;
					if (bLoc > max)
						max = bLoc;
				}
				if (max - min > this.properties.MAX_SHIP_DIMENSIONS) {
					Autocraft.shipmanager.ships.remove(this.player.getName());
					player.sendMessage(ChatColor.RED + "This ship is either too long, too tall or too wide!");
					this.stopped = true;
					return null;					
				}
			}
		}
		
		return blockList;
	}
}
