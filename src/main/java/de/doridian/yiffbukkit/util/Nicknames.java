package de.doridian.yiffbukkit.util;

import de.doridian.yiffbukkit.YiffBukkitLite;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Nicknames {
	public YiffBukkitLite plugin;
	HashMap<String, String> nicknames;

	public Nicknames(YiffBukkitLite plugin) {
		this.plugin = plugin;

		nicknames = new HashMap<String, String>();

		try {
			Map<String, Object> nodes = plugin.getConfiguration().getNode("player.nicks").getAll();
			if(nodes == null || nodes.isEmpty()) return;

			for(Map.Entry<String, Object> node : nodes.entrySet()) {
				nicknames.put(node.getKey(), node.getValue().toString());
			}
		} catch(Exception e) { }
	}

	private void save() {
		plugin.getConfiguration().setProperty("player.nicks", nicknames);
		plugin.getConfiguration().save();
	}
	
	private String transformName(Player ply) {
		return ply.getName().toLowerCase();
	}

	public void removeNickname(Player ply) {
		ply.setDisplayName(ply.getName());
		nicknames.remove(transformName(ply));
		save();
	}

	public void setNickname(Player ply) {
		String name = getNickname(ply);
		if(name != null) {
			ply.setDisplayName(name);
		} else {
			ply.setDisplayName(ply.getName());
		}
	}

	public void setNickname(Player ply, String nick) {
		ply.setDisplayName(nick);
		nicknames.put(transformName(ply), nick);
		save();
	}
	
	public String getNickname(Player ply) {
		String name = transformName(ply);
		if(nicknames.containsKey(name)) {
			return nicknames.get(name);
		}
		return null;
	}
}
