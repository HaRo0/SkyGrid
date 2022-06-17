package de.clevermc.skyGrid.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.clevermc.skyGrid.Main;

public class Protection {

	private static FileConfiguration spongs;
	private static File sponge;
	public static Map<String, Map<String, List<String>>> sponges=new HashMap<String, Map<String, List<String>>>();
	public static List<Player> admin = new ArrayList<Player>();

	@SuppressWarnings("unchecked")
	public static void loadSponges(Main main) {
		sponge = new File(main.getDataFolder(), "sponges.yml");
		spongs =(FileConfiguration) YamlConfiguration.loadConfiguration(sponge);
		if (!sponge.exists()) {
			try {
				spongs.addDefault("sponges", "");
				spongs.options().copyDefaults(true);
				spongs.save(sponge);
			} catch (IOException e) {
			}
		}
		sponges =(Map<String, Map<String, List<String>>>) spongs.getMapList("sponges").get(0);
	}

	public static void saveSponges() {
		ArrayList<Map<?,?>> spong = new ArrayList<Map<?,?>>();
		spong.add(sponges);
		spongs.set("sponges", spong);
		try {
			spongs.save(sponge);
		} catch (IOException e) {
		}
	}

	public static void spongePlaced(int x, int y, int z, Player p) {

		Map<String, List<String>> trusted = new HashMap<String, List<String>>();
		List<String> owner = new ArrayList<String>();
		owner.add(p.getUniqueId().toString());
		trusted.put("owner", owner);
		trusted.put("trusted", new ArrayList<String>());
		sponges.put(x + "," + y + "," + z, trusted);

	}

	public static boolean spongeBreak(int x, int y, int z, Player p) {
		if (sponges.get(x + "," + y + "," + z).get("owner").contains(p.getUniqueId().toString())) {
			sponges.remove(x + "," + y + "," + z);
			return true;
		}
		return false;
	}
	
	public static void  adminBreak(int x,int y,int z) {
		sponges.remove(x + "," + y + "," + z);
	}

	public static boolean spongeTrusted(int x, int y, int z, Player p) {
		if (!sponges.containsKey(x + "," + y + "," + z)) {
			return true;
		}
		if (sponges.get(x + "," + y + "," + z).get("owner").contains(p.getUniqueId().toString())
				|| sponges.get(x + "," + y + "," + z).get("trusted").contains(p.getUniqueId().toString())) {
			return true;
		}
		return false;
	}

	public static void trust(int x, int y, int z, Player p, Player p2) {
		if (sponges.get(x + "," + y + "," + z).get("owner").contains(p.getUniqueId().toString())) {
			if (!sponges.get(x + "," + y + "," + z).get("trusted").contains(p2.getUniqueId().toString())) {
				sponges.get(x + "," + y + "," + z).get("trusted").add(p2.getUniqueId().toString());
			}
		}
	}

	public static void untrust(int x, int y, int z, Player p, Player p2) {
		if (sponges.get(x + "," + y + "," + z).get("owner").contains(p.getUniqueId().toString())) {
			if (sponges.get(x + "," + y + "," + z).get("trusted").contains(p2.getUniqueId().toString())) {
				sponges.get(x + "," + y + "," + z).get("trusted").remove(p2.getUniqueId().toString());
			}
		}
	}

}
