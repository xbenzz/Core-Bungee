package me.xbenz.Commands;

import net.md_5.bungee.api.plugin.Command;
import java.util.Map;
import java.util.Random;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;


public class Hub extends Command {
	
	 public Hub() {
	    super("hub", "Core.Player", new String[0]);
	 }
	  
	 public void execute(CommandSender sender, String[] args) {
	    if (!(sender instanceof ProxiedPlayer)) {
	      return;
	    }
	    ProxiedPlayer player = (ProxiedPlayer)sender;
	    Random rand = new Random();
	    int randomNum = rand.nextInt((5 - 1) + 1) + 1;
	    String s = "Lobby-" + String.valueOf(randomNum);
	    Map<String, ServerInfo> servers = ProxyServer.getInstance().getServers();
	    ServerInfo server = (ServerInfo)servers.get(s);
	    player.connect(server);
	 }
}
