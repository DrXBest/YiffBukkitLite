package de.doridian.yiffbukkit.commands;

import de.doridian.yiffbukkit.YiffBukkitLite;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.List;

public abstract class BaseCommand implements CommandExecutor {
	protected final YiffBukkitLite plugin;
	protected BaseCommand() {
		plugin = YiffBukkitLite.instance;
	}

	@Retention(RetentionPolicy.RUNTIME) protected @interface Name { String value(); }

	@Override
	public final boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		try {
			return onCommandAll(commandSender, command, s, strings);
		} catch(Exception e) {
			commandSender.sendMessage("Error: " + e.getMessage());
			return false;
		}
	}

	public boolean onCommandAll(CommandSender commandSender, Command command, String s, String[] strings) throws Exception {
		if(commandSender instanceof Player) {
			return onCommandPlayer((Player)commandSender, command, s, strings);
		} else {
			return onCommandConsole(commandSender, command, s, strings);
		}
	}
	
	public boolean onCommandConsole(CommandSender commandSender, Command command, String s, String[] strings) throws Exception {
		commandSender.sendMessage("Sorry, this command can not be used from the console!");
		return true;
	}

	public boolean onCommandPlayer(Player player, Command command, String s, String[] strings) throws Exception {
		player.sendMessage("Sorry, this command can not be used by a player!");
		return true;
	}

	public static void registerCommands() {
		registerCommand(ListCommand.class);
		registerCommand(SetNickCommand.class);
		registerCommand(MeCommand.class);

		registerCommand(YesCommand.class);
		registerCommand(NoCommand.class);
		
		registerCommand(HomeCommand.class);
		registerCommand(SetHomeCommand.class);

		registerCommand(TpCommand.class);
		registerCommand(SummonCommand.class);
	}

	private static void registerCommand(Class<? extends BaseCommand> commandClass) {
		try {
			Constructor<? extends BaseCommand> ctor = commandClass.getConstructor();
			BaseCommand command = ctor.newInstance();
			if(!commandClass.isAnnotationPresent(Name.class)) return;
			YiffBukkitLite.instance.getCommand(commandClass.getAnnotation(Name.class).value()).setExecutor(command);
		} catch(Exception e) { }
	}
	
	protected Player getPlayerSingle(String name) throws Exception {
		List<Player> ret = plugin.getServer().matchPlayer(name);
		if(ret == null || ret.isEmpty()) {
			throw new Exception("Sorry, no player found!");
		} else if(ret.size() > 1) {
			throw new Exception("Sorry, multiple players found!");
		}
		return ret.get(0);
	}
}
