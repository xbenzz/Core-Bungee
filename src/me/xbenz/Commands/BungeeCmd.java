package me.xbenz.Commands;

import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeCmd extends Command {
	
	public BungeeCmd() {
		super("bungee", "Core.Player", new String[0]);
	}
	  
	public void execute(CommandSender sender, String[] args) {
	    if (!(sender instanceof ProxiedPlayer)) {
	      return;
	    }
	    ProxiedPlayer player = (ProxiedPlayer)sender;
	    player.sendMessage(ChatColor.BLUE + "This server is running VendettaPvP version Bungeecord 1.8x");
	}
}
