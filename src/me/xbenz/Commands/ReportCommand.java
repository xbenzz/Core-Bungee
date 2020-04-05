package me.xbenz.Commands;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.xbenz.Cache;
import me.xbenz.Main;
import me.xbenz.Reports.ReportManager;
import me.xbenz.Utils.Profile;
import me.xbenz.Utils.Rank;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReportCommand extends Command {
	
	public ReportCommand() {
		super("report", "Core.Player", new String[0]);
	}
		  
	public void execute(CommandSender sender, String[] args) {
		  if (!(sender instanceof ProxiedPlayer)) {
		      return;
		   }
		   ProxiedPlayer player = (ProxiedPlayer)sender;
		   if (args.length < 2) {
			   player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&cUsage: /report <player> <reason>")));
			   return;
		   }
		   ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
		   if (target == null) {
			   player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&cServer> &e" + args[0] + " &6not found.")));
			   return;
		   }
		   if (target.getName() == player.getName()) {
			   player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&cReport> &6You cannot report yourself!")));
			   return;
		   }
		   Cache c = new Cache(target.getUniqueId());
		   Profile profile = c.getProfile();
		   if (profile.getRank().isAboveOrEqual(Rank.HELPER)) {
			   player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&cReport> &6You cannot report staff members!")));
			   return;
		   }
	       String reason = "";
	       for (int i = 1; i < args.length; i++) {
	          reason = reason + args[i] + " ";
	       }
	       
	       final String r = reason;
	       Main.getInstance().getProxy().getScheduler().runAsync(Main.getInstance(), () -> {
	    	   ReportManager rp = new ReportManager();
	    	   if (rp.alreadyReported(target.getUniqueId())) {
	    		    player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&cReport> &e" + target.getName() + " &6already has a report open on them!")));
			   		return;
	    	   }
			
	    	   Date d = new Date();
	    	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    	   rp.openReport(target.getUniqueId(), player.getUniqueId(), r, format.format(d));
	    	   player.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&cReport> &6You have now opened a report on &e" + target.getName())));
	       });
	  }
}
