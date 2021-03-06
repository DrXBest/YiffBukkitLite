package de.doridian.yiffbukkit;

import de.doridian.yiffbukkit.request.Request;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
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
		event.setQuitMessage(playerDisconnect(event.getPlayer()));
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		event.setLeaveMessage(playerDisconnect(event.getPlayer()));
	}

	private String playerDisconnect(Player player) {
		Request.removeAllRequests(player);
		return ChatColor.DARK_RED + "[-] " + ChatColor.GRAY + player.getDisplayName() + ChatColor.YELLOW + " disconnected!";
	}

	@EventHandler
	public void onPlayerChat(PlayerChatEvent event) {
		event.setFormat(ChatColor.GRAY + "%s:" + ChatColor.WHITE + " %s");
	}

	private boolean isAdjacentLava(Block block, BlockFace face) {
		Material type = block.getRelative(face).getType();
		return (type ==  Material.LAVA || type == Material.STATIONARY_LAVA);
	}

	private boolean isAdjacentLavaOrSolid(Block block, BlockFace face) {
		Material type = block.getRelative(face).getType();
		return (type ==  Material.LAVA || type == Material.STATIONARY_LAVA || (type != Material.AIR)); //there cannot be water in the nether!
	}

	private void fixLava(Block block) {
		Material type = block.getType();
		if(type == Material.LAVA || type == Material.STATIONARY_LAVA) {
			byte data = block.getData();
			if(data != 0 && (data & 8) == 0 && isAdjacentLava(block, BlockFace.DOWN) && isAdjacentLavaOrSolid(block, BlockFace.EAST) && isAdjacentLavaOrSolid(block, BlockFace.NORTH) && isAdjacentLavaOrSolid(block, BlockFace.SOUTH) && isAdjacentLavaOrSolid(block, BlockFace.WEST)) {
				block.setData((byte)0);
			}
		}
	}

	@EventHandler
	public void onBlockFromTo(BlockFromToEvent event) {
		Block block = event.getBlock();
		if(block.getWorld().getEnvironment() == World.Environment.NETHER) {
			fixLava(block);
			fixLava(event.getToBlock());
		}
	}

	@Override
	public void run() {
		Request.timeoutCheck();
	}
}
