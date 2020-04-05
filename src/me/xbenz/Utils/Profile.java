package me.xbenz.Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import me.xbenz.Database;
import me.xbenz.Main;

public class Profile {
	
	private UUID uuid;
	private String username;
	private String savedName;
	private boolean PM;
	private Rank rank;
	  
	Date now = new Date();
	SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
	  
	public Profile(UUID uuid) {
		this.uuid = uuid;
		try {
			PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM playerData WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				this.savedName = rs.getString("Username");
				this.PM = rs.getBoolean("TogglePM");
				this.rank = Rank.getRankOrDefault(rs.getString("Rank"));
			} else {
				this.savedName = "";
				this.PM = true;
				this.rank = Rank.DEFAULT;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
  
	public Profile(UUID uuid, String username) {
		this.username = username;
	 	this.uuid = uuid;
	    try {
			PreparedStatement ps = Database.getConnection().prepareStatement("SELECT * FROM playerData WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				this.savedName = rs.getString("Username");
				this.PM = rs.getBoolean("TogglePM");
				this.rank = Rank.getRankOrDefault(rs.getString("Rank"));
			} else {
				this.savedName = "";
				this.PM = true;
				this.rank = Rank.DEFAULT;
			}
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	

	public boolean isCreated() {
	    try {
	      ResultSet results = Database.getStatement().executeQuery("SELECT * FROM playerData WHERE UUID='" + this.uuid + "'");
	      if (results.next()) {
	        return true;
	      }
	    } catch (SQLException e){
	      e.printStackTrace();
	    }
	    return false;
	}
	
	public void createProfile() {
		Main.getInstance().getProxy().getScheduler().runAsync(Main.getInstance(), () -> {
			try {
				PreparedStatement statement = Database.getConnection().prepareStatement("INSERT INTO playerData(UUID, Username, TogglePM, Rank) VALUES (?,?,?,?)");
			    statement.setString(1, String.valueOf(uuid));
			    statement.setString(2, "");
			    statement.setBoolean(3, true);
			    statement.setString(4, Rank.DEFAULT.toString());
			    statement.executeUpdate();
			 } catch (SQLException e) {
			    e.printStackTrace();
			 }
		});
	}
	
	public long getTime() {
		try {
			PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM playerTime WHERE UUID = ?");
			statement.setString(1, uuid.toString());
			ResultSet rs = statement.executeQuery();
			rs.next();
			return rs.getLong("time");
		} catch (SQLException e) {
			return 0;
		}
  }
  
  
  public String getUsername() {
	  return username;
  }
  
  public String getSavedUsername() {
	  return savedName;
  }
  
  public boolean hasMessages() {
	  return PM;
  }

  public Rank getRank() {
	  return rank;
  }
  
  
  public boolean isDonator() {
    return (getRank().isAboveOrEqual(Rank.VIP)) && (getRank().isBelowOrEqual(Rank.MVP));
  }
  
  public boolean isMedia() {
    return getRank().isAboveOrEqual(Rank.YT);
  }
  
  
}
