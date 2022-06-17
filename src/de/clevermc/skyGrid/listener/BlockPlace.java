package de.clevermc.skyGrid.listener;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import de.clevermc.skyGrid.data.Protection;

public class BlockPlace implements Listener {

	@EventHandler
	public boolean onPlace(BlockPlaceEvent e) {
		if (e.getBlock().getLocation().getWorld().getEnvironment() == World.Environment.NORMAL) {
			if (!Protection.admin.contains(e.getPlayer())) {
				for (int x = e.getBlock().getX() - 8; x < e.getBlock().getX() + 9; x++) {
					for (int y = e.getBlock().getY() - 256; y < e.getBlock().getY() + 256; y++) {
						for (int z = e.getBlock().getZ() - 8; z < e.getBlock().getZ() + 9; z++) {
							if (e.getBlock().getLocation().getWorld().getBlockAt(x, y, z)
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
		if (e.getBlock().getType() == Material.SPONGE) {
			if (e.getBlock().getLocation().getWorld().getEnvironment() != World.Environment.NORMAL) {
				e.setCancelled(true);
				e.getPlayer().sendMessage("§4nur in der Normalen SkyGrid Welt platzierbar");
				return true;
			}
			if (!Protection.admin.contains(e.getPlayer())) {
				Protection.spongePlaced(e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ(), e.getPlayer());
				e.getPlayer().sendMessage("§cProtection erfolgreich platziert");
			} else {
				e.getPlayer().sendMessage("§cIm Admin modus nicht moeglich");
				e.setCancelled(true);
			}
			Protection.saveSponges();
			return true;
		}
		return false;
	}
}
