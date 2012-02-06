package de.doridian.yiffbukkit;

import de.doridian.yiffbukkit.util.Request;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class YiffBukkitListener implements Listener, Runnable {
	final YiffBukkitLite plugin;
	public YiffBukkitListener(YiffBukkitLite plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		plugin.nicknames.setNickname(event.getPlayer());
		event.setJoinMessage(ChatColor.DARK_GREEN + "[+] " + ChatColor.GRAY + event.getPlayer().getDisplayName() + ChatColor.YELLOW + " joined!");
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		event.setQuitMessage(ChatColor.DARK_RED + "[-] " + ChatColor.GRAY + event.getPlayer().getDisplayName() + ChatColor.YELLOW + " disconnected!");
		Request.removeAllRequests(event.getPlayer());
	}

	@EventHandler
	public void onPlayerChat(PlayerChatEvent event) {
		event.setFormat(ChatColor.GRAY + "%s:" + ChatColor.WHITE + " %s");
	}

	@Override
	public void run() {
		Request.timeoutCheck();
	}
}
