package me.xbenz.Reports;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import me.xbenz.Database;
import me.xbenz.Main;
import me.xbenz.Utils.UUIDConverter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ReportManager {
	
	public boolean alreadyReported(UUID offender) {
		try {     				
			PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM reports WHERE player = ?");
			statement.setString(1, offender.toString());
			ResultSet rs = statement.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<String> getLogs(UUID player, String date) {
		ArrayList<String> msgs = new ArrayList<String>();
		try {     				
			PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM chat WHERE player = ? AND time = ?");
			statement.setString(1, player.toString());
			statement.setString(2, date);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				String name = UUIDConverter.getNameFromUUID(player);
				if (rs.getString("type").equalsIgnoreCase("CHAT"))
					msgs.add("{" + rs.getString("date") + "} {" + rs.getString("server") + "} " + name + ": " + rs.getString("message"));
				else
					msgs.add("{" + rs.getString("date") + "} {" + rs.getString("server") + "} " + name + " issued command: " + rs.getString("message"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return msgs;
	}
	
	public void openReport(UUID offender, UUID reporter, String reason, String date) {
		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(reporter);
		ProxiedPlayer target = ProxyServer.getInstance().getPlayer(offender);

		Main.getInstance().getProxy().getScheduler().runAsync(Main.getInstance(), new Runnable() {
			public void run() {
				try {     				
					PreparedStatement statement = Database.getConnection().prepareStatement("INSERT INTO reports(player, reporter, reason, date) VALUES (?,?,?,?)");
					statement.setString(1, offender.toString());
					statement.setString(2, reporter.toString());
					statement.setString(3, reason);
					statement.setString(4, date);
					statement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		
				UUID id = UUID.randomUUID();
				try {
					File statText = new File("/root/reports/" + id + ".txt");
					FileOutputStream is = new FileOutputStream(statText);
					OutputStreamWriter osw = new OutputStreamWriter(is);    
					Writer w = new BufferedWriter(osw);
					w.write("--------------------------\n");
					w.write("Reported Player: " + target.getName() + "\n");
					w.write("Reported By: " + player.getName() + "\n");
            		w.write("Reason: " + reason + "\n");
            		w.write("--------------------------\n\n");
            		for (String e : getLogs(offender, date)) {
            			w.write(e + "\n");
            		}
            		w.close();
 
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
				try {     				
					PreparedStatement statement = Database.getConnection().prepareStatement("INSERT INTO reportsLogs(player, reporter, reason, date, log) VALUES (?,?,?,?,?)");
					statement.setString(1, offender.toString());
					statement.setString(2, reporter.toString());
					statement.setString(3, reason);
					statement.setString(4, date);
					statement.setString(5, id.toString());
					statement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	    
	}

}
