package me.xbenz.Commands;

import me.xbenz.Cache;
import me.xbenz.Utils.Profile;
import me.xbenz.Utils.Rank;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class FindCommand extends Command {
	
	public FindCommand() {
		super("find");
	}

	public void execute(CommandSender sender, String[] args) {
		ProxiedPlayer staff = (ProxiedPlayer) sender;
		Cache c = new Cache(staff.getUniqueId());
		Profile p = c.getProfile();
		if (!p.getRank().isAboveOrEqual(Rank.HELPER)) {
			staff.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&cPermissions> &6You do not have permission!")));
			return;
		}
		if (args.length != 1) {
		   staff.sendMessage(new ComponentBuilder("Usage: /find <player>").color(ChatColor.RED).create());
		} else {
		   ProxiedPlayer player = ProxyServer.getInstance().getPlayer(args[0]);
		   if ((player == null) || (player.getServer() == null)) {
			   staff.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&cLocate> &e" + args[0] + " &6not found.")));
		   } else {
			   Profile pp = new Profile(player.getUniqueId());
			   TextComponent msg = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&cLocate> " + pp.getRank().getColor() + player.getName() + " &6is online at &e" + player.getServer().getInfo().getName()));
			   staff.sendMessage(msg);
		   }
	   }
   }
}
