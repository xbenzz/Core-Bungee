package me.xbenz.Time;

import me.xbenz.Cache;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class OnlineTimeEvent implements Listener {
	
	@EventHandler
	public void on(PostLoginEvent e) {
		ProxiedPlayer player = e.getPlayer();
		TimeManager time = new TimeManager();
		if (!time.hasTime(player.getUniqueId())) {
			time.createPlayer(player.getUniqueId());
		}
		Cache c = new Cache(player.getUniqueId());
		c.add();
	}
	
	@EventHandler
	public void leave(PlayerDisconnectEvent e) {
		Cache c = new Cache(e.getPlayer().getUniqueId());
		c.save();
	}
}
