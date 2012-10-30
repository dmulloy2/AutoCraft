package com.minesworn.autocraft.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import com.minesworn.autocraft.Autocraft;

public class PlayerListener implements Listener {
	private Autocraft p;
	
	public PlayerListener(Autocraft p) {
		this.p = p;
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Autocraft.shipmanager.ships.remove(e.getPlayer().getName());
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (Autocraft.shipmanager.ships.containsKey(e.getPlayer().getName())) {
			if (!Autocraft.shipmanager.ships.get(e.getPlayer().getName()).isPassenger(e.getPlayer())) {
				Autocraft.shipmanager.ships.remove(e.getPlayer().getName());
				e.getPlayer().sendMessage(ChatColor.GRAY + "You have stepped off your ship, you have been unpiloted");
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (Autocraft.shipmanager.ships.containsKey(e.getPlayer().getName())) {
				Vector dir = e.getPlayer().getLocation().getDirection();
				
				if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && (
						e.getClickedBlock().getType().equals(Material.DISPENSER) || 
						e.getClickedBlock().getType().equals(Material.CHEST) ||
						e.getClickedBlock().getType().equals(Material.FURNACE) ||
						e.getClickedBlock().getType().equals(Material.LEVER) ||
						e.getClickedBlock().getType().equals(Material.STONE_BUTTON) ||
						e.getClickedBlock().getType().equals(Material.WORKBENCH)))
					return;
				
				Autocraft.shipmanager.ships.get(e.getPlayer().getName()).move(	(int) Math.round(dir.getX()), 
																				(int) Math.round(dir.getY()), 
																				(int) Math.round(dir.getZ()));
				e.setCancelled(true);
			}
		}
	}
}
