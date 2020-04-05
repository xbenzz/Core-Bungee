package me.xbenz.Commands;

import me.xbenz.Cache;
import me.xbenz.Utils.Profile;
import me.xbenz.Utils.Rank;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class AlertCommand extends Command {
	
	public AlertCommand() {
		super("alert");
	}
  
	public void execute(CommandSender sender, String[] args) {
		if (!(sender instanceof ProxiedPlayer)) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.RED + "Usage: /alert <message>");
			} else {
				String reason = "";
				for (int i = 0; i < args.length; i++) {
					reason = reason + args[i] + " ";
				}
				TextComponent msg = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&cAlert> &6&l" + reason));
		      
		        ProxyServer.getInstance().broadcast(msg);
		        return;
			}
		}
	
		ProxiedPlayer player = (ProxiedPlayer) sender;
		Cache c = new Cache(player.getUniqueId());
		Profile p = c.getProfile();
		if (!p.getRank().isAboveOrEqual(Rank.ADMIN)) {
			player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&cPermissions> &6You do not have permission!")));
			return;
		}
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Usage: /alert <message>");
		} else {
			String reason = "";
			for (int i = 0; i < args.length; i++) {
				reason = reason + args[i] + " ";
			}
			TextComponent msg = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&cAlert> &6&l" + reason));
			msg.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Alert sent by &c" + player.getName())).create()));
      
			ProxyServer.getInstance().broadcast(msg);
		}
	}	
}
