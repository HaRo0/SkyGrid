package de.clevermc.skyGrid.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String arg2, String[] arg3) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			Location location = new Location(Bukkit.getWorld("world"), 100.5, 100.5, 100.5);
			p.teleport(location);
			return true;
		} else {
			sender.sendMessage("Nur fuer Spieler");
			return true;
		}
	}
}