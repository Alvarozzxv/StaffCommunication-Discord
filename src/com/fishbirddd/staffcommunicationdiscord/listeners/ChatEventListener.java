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

import net.dv8tion.jda.api.EmbedBuilder;
import java.awt.Color;

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
	
	private void sendDiscordMessage(TextChannel channel, String message, Player sender) {
    EmbedBuilder embed = new EmbedBuilder();
    embed.setColor(new Color(0, 153, 204)); // Establece el color del embed
    embed.setDescription(message); // Establece la descripción con el mensaje

    // Añade más campos al embed si es necesario
    embed.addField("Minecraft", sender.getName(), true);
    String discId = DiscordSRV.getPlugin().getAccountLinkManager().getDiscordId(sender.getUniqueId());
    if (discId != null) {
        String discName = DiscordUtil.getMemberById(discId).getEffectiveName();
        embed.addField("Discord", discName, true);
    }

    // Envía el mensaje embed al canal de Discord
    channel.sendMessageEmbeds(embed.build()).queue();
}

	}

}
