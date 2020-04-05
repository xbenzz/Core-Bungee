package me.xbenz;

import java.util.concurrent.TimeUnit;

import me.xbenz.Time.TimeManager;
import me.xbenz.Commands.AlertCommand;
import me.xbenz.Commands.BungeeCmd;
import me.xbenz.Commands.FindCommand;
import me.xbenz.Commands.ReportCommand;
import me.xbenz.Commands.ServerCmd;
import me.xbenz.Time.OnlineTimeEvent;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {
	
  public static Main instance;
  
  public void onEnable() {
	  instance = this;
      Database.openConnection();
      getProxy().getPluginManager().registerListener(this, new OnlineTimeEvent());
      getProxy().getPluginManager().registerCommand(this, new BungeeCmd());
      getProxy().getPluginManager().registerCommand(this, new ServerCmd());
      getProxy().getPluginManager().registerCommand(this, new FindCommand());
      getProxy().getPluginManager().registerCommand(this, new AlertCommand());  
      getProxy().getPluginManager().registerCommand(this, new ReportCommand());
      startCounter();
  }
  
  public void onDisable() {
	  Database.closeConnection();
  }
  
  public static Main getInstance() {
	  return instance;
  }
  
  public void startCounter() {
	  getProxy().getScheduler().schedule(this, () -> {
		  if (getProxy().getPlayers().size() > 0) {
			  TimeManager.saveTimes();
		  }
	  }, 1L, 1L, TimeUnit.MINUTES);
  }
  
}
