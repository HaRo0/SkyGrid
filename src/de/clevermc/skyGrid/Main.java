package de.clevermc.skyGrid;

import org.bukkit.Bukkit;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import de.clevermc.skyGrid.commands.*;
import de.clevermc.skyGrid.data.Protection;
import de.clevermc.skyGrid.generator.SkyGridGenerator;
import de.clevermc.skyGrid.listener.*;

public class Main extends JavaPlugin{

	@Override
	public void onEnable() {
		super.onEnable();
		Protection.loadSponges(this);
		listener();
		commands();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		Protection.saveSponges();
	}
	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new SkyGridGenerator();
	}
	
	private void listener() {
		Bukkit.getPluginManager().registerEvents(new BlockPlace(), this);
		Bukkit.getPluginManager().registerEvents(new ClickChest(), this);
		Bukkit.getPluginManager().registerEvents(new BlockBreak(), this);
	}
	
	private void commands() {
		Bukkit.getPluginCommand("trust").setExecutor(new Trust());
		Bukkit.getPluginCommand("untrust").setExecutor(new Untrust());
		Bukkit.getPluginCommand("skygrid").setExecutor(new RandomTP());
		Bukkit.getPluginCommand("spawn").setExecutor(new Spawn());
		Bukkit.getPluginCommand("admin").setExecutor(new Admin());
	}

}