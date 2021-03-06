package de.doridian.yiffbukkit.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

@BaseCommand.Name("home")
public class HomeCommand extends BaseCommand {
	@Override
	public boolean onCommandPlayer(Player player, Command command, String s, String[] strings) throws Exception {
		Location location = plugin.homes.getHome(player);
		if(location == null) {
			throw new Exception("Sorry, you don't have a home point set!");
		}
		player.teleport(location);
		sendResponse(player, "Teleported home");
		return true;
	}
}
