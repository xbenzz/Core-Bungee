package me.xbenz.Time;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import me.xbenz.Database;
import me.xbenz.Main;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TimeManager {
	
	public boolean hasTime(UUID uuid) {
		try {
			PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM playerTime WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void createPlayer(UUID uuid) {
		Main.getInstance().getProxy().getScheduler().runAsync(Main.getInstance(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("INSERT INTO playerTime (UUID, time) VALUES (?,?)");
				ps.setString(1, uuid.toString());
				ps.setLong(2, 0);
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			
		});
	}
	
	public static void saveTimes() {
		Main.getInstance().getProxy().getScheduler().runAsync(Main.getInstance(), () -> {
			try {
				PreparedStatement ps = Database.getConnection().prepareStatement("UPDATE playerTime SET time = time + 1000 WHERE UUID = ?");
				for (ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
					if (!all.getServer().getInfo().getName().contains("Lobby")) {
						ps.setString(1, all.getUniqueId().toString());
						ps.addBatch();
					}
				}
				ps.executeBatch();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
  
}
