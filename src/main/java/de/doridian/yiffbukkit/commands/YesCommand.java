package de.doridian.yiffbukkit.commands;

import de.doridian.yiffbukkit.util.Request;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

@BaseCommand.Name("yes")
public class YesCommand extends BaseCommand {
	@Override
	public boolean onCommandPlayer(Player player, Command command, String s, String[] strings) throws Exception {
		Player byPlayer = null;
		if(strings.length > 0) {
			byPlayer = getPlayerSingle(strings[0]);
		}
		Request req = Request.getRequest(player, byPlayer);
		if(req == null) {
			throw new Exception("Sorry, no pending request found!");
		} else {
			player.sendMessage("Request accepted!");
			req.run();
		}
		return true;
	}
}
