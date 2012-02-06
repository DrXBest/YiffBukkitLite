package de.doridian.yiffbukkit.util;

import de.doridian.yiffbukkit.YiffBukkitLite;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Homes {
	public YiffBukkitLite plugin;
	HashMap<String, Location> homes;
	HashMap<String, String> homesStore;

	public Homes(YiffBukkitLite plugin) {
		this.plugin = plugin;

		homes = new HashMap<String, Location>();
		homesStore = new HashMap<String, String>();

		try {
			Map<String, Object> nodes = plugin.getConfiguration().getNode("player.homes").getAll();
			if(nodes == null || nodes.isEmpty()) return;

			for(Map.Entry<String, Object> node : nodes.entrySet()) {
				homes.put(node.getKey(), Utils.unserializeLocation(node.getValue().toString()));
				homesStore.put(node.getKey(), node.getValue().toString());
			}
		} catch(Exception e) { }
	}

	private void save() {
		plugin.getConfiguration().setProperty("player.homes", homesStore);
		plugin.getConfiguration().save();
	}

	private String transformName(Player ply) {
		return ply.getName().toLowerCase();
	}

	public void removeHome(Player ply) {
		String name = transformName(ply);
		homes.remove(name);
		homesStore.remove(name);
		save();
	}

	public void setHome(Player ply, Location home) {
		String name = transformName(ply);
		homes.put(name, home);
		homesStore.put(name, Utils.serializeLocation(home));
		save();
	}

	public Location getHome(Player ply) {
		return homes.get(transformName(ply));
	}
}
