package com.fishbirddd.staffcommunicationdiscord;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.fishbirddd.staffcommunicationdiscord.commands.PluginCommand;
import com.fishbirddd.staffcommunicationdiscord.listeners.ChatEventListener;
import com.fishbirddd.staffcommunicationdiscord.listeners.DiscordChatEvent;

import github.scarsz.discordsrv.DiscordSRV;
import net.md_5.bungee.api.ChatColor;

public class StaffCommunicationDiscord extends JavaPlugin {

	public static String VERSION = "1.0.0";
	
	public static String color(String s) {
		
		return ChatColor.translateAlternateColorCodes('&', s);
		
	}
	
	public static StaffCommunicationDiscord instance;
	
	@Override
	public void onEnable() {
		
		this.commands();
		this.listeners();
		instance = this;
		
		this.saveDefaultConfig();
		
	}
	
	private void commands() {
		
		super.getCommand("staffcommunicationdiscord").setExecutor(new PluginCommand());
		
	}
	
	private void listeners() {
		
		super.getServer().getPluginManager().registerEvents((Listener) new ChatEventListener(), this);
		DiscordSRV.api.subscribe(new DiscordChatEvent());
		
	}
	
}
