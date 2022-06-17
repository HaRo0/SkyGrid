package de.clevermc.skyGrid.commands;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RandomTP implements CommandExecutor {

	private Random r = new Random();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String arg2, String[] arg3) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			double x = r.nextInt(50001) + 5000000/4, y = r.nextInt(64), z = r.nextInt(50001) + 5000000/4;
			x *= 4;
			y *= 4;
			z *= 4;
			y++;
			Location location = new Location(Bukkit.getWorld("worlds"), x+0.5, y, z+0.5);
			p.teleport(location);
		} else {
			sender.sendMessage("Nur fuer Spieler");
			return true;
		}

		return false;
	}

}
