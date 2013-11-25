package net.dmulloy2.autocraft.commands;

import java.util.ArrayList;
import java.util.List;

import net.dmulloy2.autocraft.AutoCraft;
import net.dmulloy2.autocraft.util.FormatUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor {
	private final AutoCraft plugin;
	private List<AutoCraftCommand> registeredCommands;

	public CommandHandler(AutoCraft plugin) {
		this.plugin = plugin;
		this.registeredCommands = new ArrayList<AutoCraftCommand>();
	}

	public void registerCommand(AutoCraftCommand command) {
		registeredCommands.add(command);
	}

	public List<AutoCraftCommand> getRegisteredCommands() {
		return registeredCommands;
	}

	public void setCommandPrefix(String commandPrefix) {
		plugin.getCommand(commandPrefix).setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> argsList = new ArrayList<String>();

		if (args.length > 0) {
			String commandName = args[0];
			for (int i = 1; i < args.length; i++)
				argsList.add(args[i]);

			for (AutoCraftCommand command : registeredCommands) {
				if (commandName.equalsIgnoreCase(command.getName()) || command.getAliases().contains(commandName.toLowerCase())) {
					command.execute(sender, argsList.toArray(new String[0]));
					return true;
				}
			}

			sender.sendMessage(plugin.getPrefix() + FormatUtil.format(plugin.getMessage("unknown_command"), args[0]));
		} else {
			new CmdHelp(plugin).execute(sender, args);
		}

		return true;
	}
}