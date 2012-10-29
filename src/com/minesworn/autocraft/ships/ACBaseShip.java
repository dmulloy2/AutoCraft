package com.minesworn.autocraft.ships;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import com.minesworn.autocraft.Autocraft;

public class ACBaseShip {
	public ACProperties properties;
	
	// Changes to true if the ship has a problem creating.
	boolean stopped;
	Player player;
	
	// Used for checking if weapon cooldown is over.
	long lastFired;
	
	// Total number of the main block on the ship.
	int numMainBlocks = 0;
	
	// Used for checking if move cooldown is over.
	long lastmove;
	
	// Hold all blocks part of this ship.
	Block[] blocks;
	// Place holder for the block the player is standing on. Probably unnecessary but haven't removed it in this update TODO: remove this.
	Block mainblock;
	
	public ACBaseShip(Player player, ACProperties properties) {
		this.player = player;
		this.properties = properties;
		System.out.println("PLAYER " + player.getName() + " HAS STARTED FLYING " + 
					this.properties.SHIP_TYPE + " AT (" + 
					player.getLocation().getBlockX() + "," + 
					player.getLocation().getBlockY() + "," + 
					player.getLocation().getBlockZ() + ")");
		
		startship();
	}
	
	public void startship() {
		updateMainBlock();
		if (beginRecursion(this.mainblock)) {
			if (areBlocksValid()) {
				if (isShipAlreadyPiloted()) {
					player.sendMessage(ChatColor.RED + "This ship is already being piloted");
				} else {
					Autocraft.shipmanager.ships.put(player.getName(), this);
					player.sendMessage(ChatColor.GRAY + "You are in control of this ship");
					player.sendMessage(ChatColor.GRAY + "\tUse the right mouse to guide the ship");
				}
			}
		}
	}
	
	// This method doesn't actually move the ship, only prepares to before it calls domove().
	public void move(int dx, int dy, int dz) {
		// If the airship has been stopped then remove it from the mapping.
		if (this.stopped)
			Autocraft.shipmanager.ships.remove(player.getName());
		else {
			// Check that the ship hasn't already moved within the last second.
			if (System.currentTimeMillis() - this.lastmove > 1000L) {
				System.out.println(dx + "," + dy + "," + dz);
				// Reduce the matrix given to identities only.
				if (Math.abs(dx) > 1)
					dx /= Math.abs(dx);
				if (Math.abs(dy) > 1)
					dy /= Math.abs(dy);
				if (Math.abs(dz) > 1)
					dz /= Math.abs(dz);
				
				dx *= this.properties.MOVE_SPEED;
				dy *= this.properties.MOVE_SPEED;
				dz *= this.properties.MOVE_SPEED;
				
				System.out.println(dx + "," + dy + "," + dz);
				
				// Set last move to current time.
				this.lastmove = System.currentTimeMillis();
				boolean obstruction = false;
				
				Block[] blocks = this.blocks.clone();
				
				// Check each block's new position for obstructions
				for (int i = 0; i < blocks.length; i++) {
					Block block = blocks[i].getRelative(dx, dy, dz);
					if (block.getLocation().getBlockY() + dy > ACProperties.MAX_ALTITUDE)
						obstruction = true;
					if (block.getType().equals(Material.AIR) || blockBelongsToShip(block, blocks))
						continue;
					obstruction = true;
				}
				
				// Can't move :/
				if (obstruction)
					this.player.sendMessage(ChatColor.YELLOW + "Obstruction" + ChatColor.RED + " - Cannot move any further in this direction.");
				// Lets move this thing :D
				else
					domove(dx, dy, dz);
			}
		}
	}
	
	public void rotate(TurnDirection dir) {	
		if (this.stopped)
			Autocraft.shipmanager.ships.remove(player.getName());
		else {
			// Check that the ship hasn't moved within the last second.
			if (System.currentTimeMillis() - this.lastmove > 1000L) {
				this.lastmove = System.currentTimeMillis();
				boolean obstruction = false;
				
				Block[] blocks = this.blocks.clone();
				
				Vector center = getCenter();
				
				// Check each block's new position for obstructions
				for (int i = 0; i < blocks.length; i++) {
					Block block = blocks[i].getWorld().getBlockAt(
									getRotatedVector(	blocks[i].getLocation().toVector(), 
														center, 
														dir)
										.toLocation(blocks[i].getWorld()));
					if (block.getType().equals(Material.AIR) || blockBelongsToShip(block, blocks))
						continue;
					obstruction = true;
				}
				
				// Can't move :/
				if (obstruction)
					this.player.sendMessage(ChatColor.YELLOW + "Obstruction" + ChatColor.RED + " - Cannot rotate in this direction.");
				else
					dorotate(dir);
			}
		}
	
	}
	
	// Rotate the ship and all passengers in the specified direction
	public void dorotate(TurnDirection dir) {
		List<Player> passengers = getPassengers();
		BlockState[] temp = new BlockState[blocks.length];
		
		// First remove all blocks from the scene
		for (int i = 0; i < blocks.length; i++) {
			temp[i] = blocks[i].getState();
			blocks[i].setType(Material.AIR);
		}
		
		Vector center = getCenter();
		
		// Make new blocks in their new respective positions
		for (int i = 0; i < blocks.length; i++) {			
			blocks[i] = blocks[i].getWorld().getBlockAt(
							getRotatedVector(	blocks[i].getLocation().toVector(), 
												center, 
												dir)
								.toLocation(blocks[i].getWorld()));
			setBlock(blocks[i], temp[i], dir);			
		}
		
		for (Player p : passengers) {
			Location l = getRotatedVector(p.getLocation().toVector(), center, dir).toLocation(p.getWorld());
			l.setYaw(l.getYaw() + ((dir == TurnDirection.LEFT) ? -90 : 90)); 
			p.teleport(l);
		}
	}
	
	// Returns the coordinates of the position rotated around the center in the specified direction
	public Vector getRotatedVector(Vector v, Vector center, TurnDirection dir) {
		int dz = v.getBlockX() - center.getBlockX();
		int dx = v.getBlockZ() - center.getBlockZ();
		
		if (dir == TurnDirection.LEFT)
			dx *= -1;
		else
			dz *= -1;
		
		return new Vector(center.getBlockX() + dx, v.getBlockY(), center.getBlockZ() + dz);
	}
	
	// Move the ship and all passengers the specified distance
	public void domove(int dx, int dy, int dz) {
		List<Player> passengers = getPassengers();
		BlockState[] temp = new BlockState[blocks.length];
		
		// First remove all blocks from the scene
		for (int i = 0; i < blocks.length; i++) {
			temp[i] = blocks[i].getState();
			blocks[i].setType(Material.AIR);
		}
		
		// Make new blocks in their new respective positions
		for (int i = 0; i < blocks.length; i++) {
			blocks[i] = blocks[i].getRelative(dx, dy, dz);
			setBlock(blocks[i], temp[i], (byte) -1);
		}
		
		// Teleport players back on to the ship
		for (Player p : passengers) {
			p.teleport(p.getLocation().clone().add(dx, dy, dz));
		}
	}
	
	@SuppressWarnings("incomplete-switch")
	public void setBlock(Block to, BlockState from, TurnDirection dir) {
		MaterialData data = from.getData();
		if (data instanceof Directional) {
			switch (((Directional) data).getFacing()) {
			case NORTH:
				if (dir.equals(TurnDirection.LEFT))
					((Directional) data).setFacingDirection(BlockFace.WEST);
				else
					((Directional) data).setFacingDirection(BlockFace.EAST);
				break;
			case EAST:
				if (dir.equals(TurnDirection.LEFT))
					((Directional) data).setFacingDirection(BlockFace.SOUTH);
				else
					((Directional) data).setFacingDirection(BlockFace.NORTH);
				break;
			case SOUTH:
				if (dir.equals(TurnDirection.LEFT))
					((Directional) data).setFacingDirection(BlockFace.EAST);
				else
					((Directional) data).setFacingDirection(BlockFace.WEST);
				break;
			case WEST:
				if (dir.equals(TurnDirection.LEFT))
					((Directional) data).setFacingDirection(BlockFace.NORTH);
				else
					((Directional) data).setFacingDirection(BlockFace.SOUTH);
				break;
			}
		}
		setBlock(to, from, data.getData());
	}
	
	public void setBlock(Block to, BlockState from, byte data) {
		to.setTypeIdAndData(from.getTypeId(), (data == (byte) -1) ? from.getData().getData() : data, false);
		// Check if have to update block states.
		if (from instanceof InventoryHolder) {
			((InventoryHolder) to.getState()).getInventory().setContents(((InventoryHolder) from).getInventory().getContents());
		} else if (from instanceof Sign) {
			for (int j = 0; j < 4; j++) {
				((Sign) to.getState()).setLine(j, ((Sign) from).getLine(j));
			}
		}
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
	
	// Checks if ship is already being piloted
	public boolean isShipAlreadyPiloted() {		
		for (ACBaseShip othership : Autocraft.shipmanager.ships.values()) {
			for (int i = 0; i < othership.blocks.length; i++) {
				if (blockBelongsToShip(othership.blocks[i], blocks))
					return true;
			}
		}
		return false;
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
	
	public Vector getCenter() {
		List<Integer> c = new ArrayList<Integer>(3);
		for (Direction dir : Direction.values()) {
			double min = mainblock.getLocation().toVector().dot(dir.v);
			double max = min;
			for (Block b : blocks) {
				double bLoc = b.getLocation().toVector().dot(dir.v);
				if (bLoc < min)
					min = bLoc;
				if (bLoc > max)
					max = bLoc;
			}
			c.add((int) (min + (max - min) / 2));	
		}
		return new Vector(c.get(0), c.get(1), c.get(2));
	}
	
	public boolean beginRecursion(Block block) {
		ArrayList<Block> blockList = new ArrayList<Block>(ACProperties.MAX_SHIP_SIZE);
		blockList = recurse(block, blockList);
		
		if (blockList != null) {
			blocks = blockList.toArray(new Block[0]);
			return true;
		}
		return false;
	}
	
	// Recursively call this method for every block relative to the starting block.
	public ArrayList<Block> recurse(Block block, ArrayList<Block> blockList) {
		boolean original = ((blockList != null) ? blockList.isEmpty() : false);
		
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
