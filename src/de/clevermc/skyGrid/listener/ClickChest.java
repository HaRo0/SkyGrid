package de.clevermc.skyGrid.listener;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.clevermc.skyGrid.data.Protection;

public class ClickChest implements Listener {

	@EventHandler
	public boolean onClick(PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return false;
		}
		if (e.getClickedBlock().getLocation().getWorld().getEnvironment() == World.Environment.NORMAL) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK && !e.getPlayer().isSneaking()) {
				if (e.getClickedBlock().getType() == Material.CHEST
						|| e.getClickedBlock().getType() == Material.TRAPPED_CHEST) {
					if (!Protection.admin.contains(e.getPlayer())) {
						for (int x = e.getClickedBlock().getX() - 8; x < e.getClickedBlock().getX() + 9; x++) {
							for (int y = e.getClickedBlock().getY() - 256; y < e.getClickedBlock().getY() + 256; y++) {
								for (int z = e.getClickedBlock().getZ() - 8; z < e.getClickedBlock().getZ() + 9; z++) {
									if (e.getClickedBlock().getLocation().getWorld().getBlockAt(x, y, z)
											.getType() == Material.SPONGE) {
										if (!Protection.spongeTrusted(x, y, z, e.getPlayer())) {
											e.setCancelled(true);
											return true;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

}
