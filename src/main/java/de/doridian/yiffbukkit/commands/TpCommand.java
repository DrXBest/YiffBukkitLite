package de.doridian.yiffbukkit.commands;

import de.doridian.yiffbukkit.util.Request;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@BaseCommand.Name("tp")
public class TpCommand extends BaseCommand {
	@Override
	public boolean onCommandConsole(CommandSender commandSender, Command command, String s, String[] strings) throws Exception {
		teleport(strings[0], strings[1]);
		return true;
	}

	@Override
	public boolean onCommandPlayer(Player player, Command command, String s, String[] strings) throws Exception {
		Player other = getPlayerSingle(strings[0]);
		requestTeleport(player, other, player, other, "%2$s wants to teleport to you");
		return true;
	}
	
	protected void teleport(String toTeleport, String target) {
		plugin.getServer().getPlayerExact(toTeleport).teleport(plugin.getServer().getPlayerExact(target));
	}
	
	protected void requestTeleport(final Player byPlayer, final Player forPlayer, final Player toTeleport, final Player target, final String msg) {
		Request request = new Request(forPlayer, byPlayer, new Runnable() {
			@Override
			public void run() {
				toTeleport.teleport(target);
				byPlayer.sendMessage("Your teleportation request was accepted!");
			}
		});
		request.add(msg);
	}
}
