package de.doridian.yiffbukkit;

import de.doridian.yiffbukkit.commands.BaseCommand;
import de.doridian.yiffbukkit.util.Homes;
import de.doridian.yiffbukkit.util.Nicknames;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class YiffBukkitLite extends JavaPlugin {
	public static YiffBukkitLite instance;

	private YiffBukkitListener listener;
	public Nicknames nicknames;
	public Homes homes;

	public YiffBukkitLite() {
		super();
		instance = this;
	}

	@Override
	public void onDisable() {
		log("Plugin disabled!");
	}

	@Override
	public void onEnable() {
		listener = new YiffBukkitListener(this);
		nicknames = new Nicknames(this);
		homes = new Homes(this);
		BaseCommand.registerCommands();
		log("Plugin enabled!");
	}
	
	public void log(String msg) {
		log(Level.INFO, msg);
	}
	
	public void log(Level level, String msg) {
		getLogger().log(level, msg);
	}
}
