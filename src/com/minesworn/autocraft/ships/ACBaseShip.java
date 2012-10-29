package com.minesworn.autocraft.ships;

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
	
	boolean stopped;
	Player player;

	public int maxBlocks;
	public int minBlocks;
	public boolean dropnapalm;
	public boolean dropbomb;
	public boolean firebomb;
	public boolean firetorpedo;
	public boolean allowflight;
	public boolean firearrows;
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
	public int maxShipWidth = 16;
	public int minx;
	public int maxx;
	public int miny;
	public int maxy;
	public int maxaltitude = 254;
	long lastmove = System.currentTimeMillis();
	Block[] blocks;
	Block mainblock;
	
	public ACBaseShip() {
		
	}
	
	public void move(int x, int y, int z) {
		// If the airship has been unpiloted then remove it from the mapping.
		if (this.stopped)
			Autocraft.shipmanager.ships.remove(player);
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
				else if (addBlocks()) {
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
			if (addBlocks())
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
	
	// Checks the block material against the AC properties for this ship
	public boolean isValidMaterial(Block block) {
		return (this.properties.MAIN_TYPE == block.getTypeId() || this.properties.ALLOWED_BLOCKS.contains(block.getTypeId()));
	}
	
	// Checks that the ship is within its size restraints as defined by its AC properties.
	public boolean addBlocks() {
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
	
	public boolean recurse(int start, Block block) {
		if (!this.stopped)
			if (this.blocks.length <= 4000) {
				ArrayList<Block> blockPopulation = new ArrayList<Block>();
				boolean ioml = isValidMaterial(block);
				if (checkblock(block) && ioml) {
					if (block.getType().equals(this.maintype))
						this.numMainBlocks++;
					if (!isRot(block)) {
						storeBlockData(block);
						blockPopulation.add(block);
						this.blocklist.
					}
				}
			}
	}
}
