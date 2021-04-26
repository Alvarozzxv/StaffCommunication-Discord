package com.fishbirddd.staffcommunicationdiscord.listeners;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.fishbirddd.staffcommunicationdiscord.StaffCommunicationDiscord;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.DiscordGuildMessageReceivedEvent;
import github.scarsz.discordsrv.util.DiscordUtil;

public class DiscordChatEvent {
	
	private StaffCommunicationDiscord getInstance() {
		
		return StaffCommunicationDiscord.instance;
		
	}
	
	@Subscribe
	public void on(DiscordGuildMessageReceivedEvent e) {
				
		Map<String, UUID> linked = DiscordSRV.getPlugin().getAccountLinkManager().getLinkedAccounts();
		
		if (this.getInstance().getConfig().getBoolean("needlink")) {
			
			if (linked.containsKey(e.getAuthor().getId())) {
			
				sendMessage(e);
			
			} else {
				
				DiscordUtil.sendMessage(e.getChannel(), e.getAuthor().getAsMention() + " ERROR: You must link your Minecraft and Discord accounts to use the staff chat. Use /discord link in-game.");
			}
		
		} else { sendMessage(e); }
					
	}
	
	private void sendMessage(DiscordGuildMessageReceivedEvent e) {
		
		String message = e.getMessage().getContentDisplay();
		String discSender = e.getMember().getNickname();
		UUID uuid = DiscordSRV.getPlugin().getAccountLinkManager().getUuid(e.getAuthor().getId());
		String mcSender = uuid != null ? Bukkit.getPlayer(uuid).getName() : this.getInstance().getConfig().getString("messages.defaults.minecraft");
		
		if (DiscordSRV.getPlugin().getDestinationGameChannelNameForTextChannel(e.getChannel()).equalsIgnoreCase("staffchat")) {
		
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				
				if (p.hasPermission("staffcommunication.sc.see")) {
					
					p.sendMessage(StaffCommunicationDiscord.color(this.getInstance().getConfig().getString("messages.minecraft.staff")
							.replaceAll("%message%", message)
							.replaceAll("%discord%", discSender)
							.replaceAll("%minecraft%", mcSender)));
					
				}
				
			}
			
		} else if (DiscordSRV.getPlugin().getDestinationGameChannelNameForTextChannel(e.getChannel()).equalsIgnoreCase("adminchat")) {
			
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				
				if (p.hasPermission("staffcommunication.ac.see")) {
					
					p.sendMessage(StaffCommunicationDiscord.color(this.getInstance().getConfig().getString("messages.minecraft.admin")
							.replaceAll("%message%", message)
							.replaceAll("%discord%", discSender)
							.replaceAll("%minecraft%", mcSender)));
					
				}
				
			}
			
		}
		
	}
	
}
