package me.xbenz.Commands;

import net.md_5.bungee.api.plugin.Command;
import java.util.Map;

import me.xbenz.Cache;
import me.xbenz.Utils.Profile;
import me.xbenz.Utils.Rank;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;


public class ServerCmd extends Command {
	
	  public ServerCmd() {
	    super("server", "Core.Player", new String[0]);
	  }
	  
	  public void execute(CommandSender sender, String[] args) {
	    if (!(sender instanceof ProxiedPlayer)) {
	      return;
	    }
	    ProxiedPlayer player = (ProxiedPlayer)sender;
		Cache c = new Cache(player.getUniqueId());
		Profile p = c.getProfile();
	    Map<String, ServerInfo> servers = ProxyServer.getInstance().getServers();
	    if (args.length == 0) {
	      player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cServer> &6You are currently connected to &e" + player.getServer().getInfo().getName()));
	    } else {
	      ServerInfo server = (ServerInfo)servers.get(args[0]);
	      if (server == null) {
	    	  player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cServer> &6Server does not exist!"));
	      } else if (server.getName().equalsIgnoreCase("Staff") && !p.getRank().isAboveOrEqual(Rank.HELPER)) {
	    	  player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cServer> &6You don't have permission to access that server!"));
	      } else {
	    	  player.connect(server);
	      }
	    }
	  }
	 

}
