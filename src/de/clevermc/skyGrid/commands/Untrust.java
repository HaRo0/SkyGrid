package de.clevermc.skyGrid.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.clevermc.skyGrid.data.Protection;

public class Untrust implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String arg2, String[] arg3) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only for players");
			return true;
		}
		if (arg3.length == 1) {
			Player p = (Player) sender;
			if (p.getLocation().getWorld().getEnvironment() == World.Environment.NORMAL) {
				Player p2 = null;
				for (Player all : Bukkit.getOnlinePlayers()) {
					if (all.getDisplayName().equalsIgnoreCase(arg3[0])) {
						p2 = all;
					}
				}
				if (p2 == null) {
					p.sendMessage("Spieler nicht online");
					return true;
				}
				for (int x = (int) (p.getLocation().getX()) - 8; x < (int) (p.getLocation().getX()) + 9; x++) {
					for (int y = (int) (p.getLocation().getY()) - 256; y < (int) (p.getLocation().getY()) + 256; y++) {
						for (int z = (int) (p.getLocation().getZ()) - 8; z < (int) (p.getLocation().getZ()) + 9; z++) {
							if (p.getLocation().getWorld().getBlockAt(x, y, z).getType() == Material.SPONGE) {
								Protection.untrust(x, y, z, p, p2);
								Protection.saveSponges();
							}
						}
					}
				}
			}else {
				p.sendMessage("§4Befehl kann nur in der SkyGrid-Welt ausgefuehrt werden");
			}
			return true;
		} else {
			sender.sendMessage("§4/untrust [Player]");
			return true;
		}

	}
	
}
