package de.doridian.yiffbukkit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@BaseCommand.Name("summon")
public class SummonCommand extends TpCommand {
	@Override
	public boolean onCommandConsole(CommandSender commandSender, Command command, String s, String[] strings) throws Exception {
		teleport(strings[1], strings[0]);
		return true;
	}

	@Override
	public boolean onCommandPlayer(Player player, Command command, String s, String[] strings) throws Exception {
		Player other = getPlayerSingle(strings[0]);
		requestTeleport(player, other, other, player, "%2$s wants to summon you");
		return true;
	}
}
