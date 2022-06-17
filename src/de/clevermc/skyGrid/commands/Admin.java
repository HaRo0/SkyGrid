package de.clevermc.skyGrid.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.clevermc.skyGrid.data.Protection;

public class Admin implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String arg2, String[] arg3) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("SkyGrid.Admin")) {
				if(Protection.admin.contains(p)) {
					Protection.admin.remove(p);
					p.sendMessage("§cAdmin modus deaktiviert");
				}else {
					Protection.admin.add(p);
					p.sendMessage("§bAdmin modus aktiviert");
				}
			}else {
				p.sendMessage("§cnicht genug rechte");
			}
			return true;
		}else {
			sender.sendMessage("Nur fuer spieler");
			return true;
		}
	}

}
