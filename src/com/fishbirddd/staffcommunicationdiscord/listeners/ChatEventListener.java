package com.fishbirddd.staffcommunicationdiscord.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.fishbirddd.staffcommunication.ChatType;
import com.fishbirddd.staffcommunication.events.StaffCommunicationChatEvent;
import com.fishbirddd.staffcommunicationdiscord.StaffCommunicationDiscord;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.util.DiscordUtil;

public class ChatEventListener implements Listener {
	
	private StaffCommunicationDiscord getInstance() {
		
		return StaffCommunicationDiscord.instance;
		
	}
	
	@EventHandler
	public void on(StaffCommunicationChatEvent e) {
		
		ChatType type = e.getChatType();
		
		FileConfiguration config = this.getInstance().getConfig();
		
		Player sender = e.getSender();
		String mcName = e.getSender().getName();
		String discId = DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(sender.getUniqueId());
		String discName = discId != null ? DiscordUtil.getMemberById(discId).getNickname() : config.getString("messages.defaults.discord");
		
		switch (type) {
		
		case STAFF_CHAT:
			sendDiscordMessage(DiscordSRV.getPlugin().getDestinationTextChannelForGameChannelName("staffchat"), 
					config.getString("messages.discord.staff")
					.replaceAll("%message%", e.getMessage())
					.replaceAll("%discord%", discName)
					.replaceAll("%minecraft%", mcName)
					);
			break;
		case ADMIN_CHAT:
			sendDiscordMessage(DiscordSRV.getPlugin().getDestinationTextChannelForGameChannelName("adminchat"), 
					config.getString("messages.discord.admin")
					.replaceAll("%message%", e.getMessage())
					.replaceAll("%discord%", discName)
					.replaceAll("%minecraft%", mcName)
					);
			break;
		case STAFF_BROADCAST:
			sendDiscordMessage(DiscordSRV.getPlugin().getDestinationTextChannelForGameChannelName("staffchat"), 
					config.getString("messages.discord.staffbc")
					.replaceAll("%message%", e.getMessage())
					.replaceAll("%discord%", discName)
					.replaceAll("%minecraft%", mcName)
					);
			break;
		case ADMIN_BROADCAST:
			sendDiscordMessage(DiscordSRV.getPlugin().getDestinationTextChannelForGameChannelName("adminchat"), 
					config.getString("messages.discord.staffbc")
					.replaceAll("%message%", e.getMessage())
					.replaceAll("%discord%", discName)
					.replaceAll("%minecraft%", mcName)
					);
			break;
		case BROADCAST:
			sendDiscordMessage(DiscordSRV.getPlugin().getDestinationTextChannelForGameChannelName("broadcast"), 
					config.getString("messages.discord.broadcast")
					.replaceAll("%message%", e.getMessage())
					.replaceAll("%discord%", discName)
					.replaceAll("%minecraft%", mcName)
					);
			break;
		default:
			// Other chat types are not sent to discord.
			break;
		
		}
		
	}
	
	private void sendDiscordMessage(TextChannel channel, String message) {
		
		DiscordUtil.sendMessage(channel, message);
		
	}

}
