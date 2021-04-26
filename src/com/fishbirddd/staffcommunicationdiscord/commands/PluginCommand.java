package com.fishbirddd.staffcommunicationdiscord.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.fishbirddd.staffcommunicationdiscord.StaffCommunicationDiscord;

public class PluginCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length >= 1) {
			
			if (args[0].equalsIgnoreCase("reload")) {
				
				sender.sendMessage(ChatColor.GREEN + "StaffCommunication-Discord by FishbirdDD version " + StaffCommunicationDiscord.VERSION + " reloaded.");
				
				StaffCommunicationDiscord.instance.reloadConfig();
				
			} else if (args[0].equalsIgnoreCase("version")) {
				
				sender.sendMessage(ChatColor.GREEN + "StaffCommunication-Discord by FishbirdDD version " + StaffCommunicationDiscord.VERSION);
				
			} else { sender.sendMessage(ChatColor.RED + "/scd <reload|version>"); }
			
		} else { sender.sendMessage(ChatColor.RED + "/scd <reload|version>"); }
		
		return true;
		
	}
	
}
