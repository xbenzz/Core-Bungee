package me.xbenz.Utils;

import com.google.gson.Gson;
import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Functions to get UUIDs to player names. This library doesn't cache the results! You will have to do this on your own!
 * Works with Bukkit and BungeeCord!
 */
public final class UUIDConverter {

	private static final Gson GSON = new Gson();

	/**
	 * Gets the current name of the player from the Mojang servers.
	 * Only works for Mojang-UUIDs, not for Bukkit-Offline-UUIDs.
	 *
	 * @param uuid The UUID of the player.
	 * @return The name of the player.
	 */
	public static String getNameFromUUID(UUID uuid)
	{
		return getNameFromUUID(uuid.toString());
	}

	/**
	 * Gets the current name of the player from the Mojang servers.
	 * Only works for Mojang-UUIDs, not for Bukkit-Offline-UUIDs.
	 *
	 * @param uuid The UUID of the player.
	 * @return The name of the player.
	 */
	public static String getNameFromUUID(String uuid)
	{
		NameChange[] names = getNamesFromUUID(uuid);
		return names[names.length - 1].name;
	}

	/**
	 * Gets the name history of a player from the Mojang servers.
	 * Only works for Mojang-UUIDs, not for Bukkit-Offline-UUIDs.
	 *
	 * @param uuid The UUID of the player.
	 * @return The names and name change dates of the player.
	 */
	public static NameChange[] getNamesFromUUID(UUID uuid)
	{
		return getNamesFromUUID(uuid.toString());
	}

	/**
	 * Gets the name history of a player from the Mojang servers.
	 * Only works for Mojang-UUIDs, not for Bukkit-Offline-UUIDs.
	 *
	 * @param uuid The UUID of the player.
	 * @return The names and name change dates of the player.
	 */
	public static NameChange[] getNamesFromUUID(String uuid)
	{
		NameChange[] names = null;
		try
		{
			Scanner jsonScanner = new Scanner((new URL("https://api.mojang.com/user/profiles/" + uuid.replaceAll("-", "") + "/names")).openConnection().getInputStream(), "UTF-8");
			names = GSON.fromJson(jsonScanner.next(), NameChange[].class);
			jsonScanner.close();
		}
		catch(IOException e)
		{
			System.out.println("Looks like there is a problem with the connection with Mojang. Please retry later.");
			if(e.getMessage().contains("HTTP response code: 429"))
			{
				System.out.println("You have reached the request limit of the Mojang api! Please retry later!");
			}
			else
			{
				e.printStackTrace();
			}
		}
		catch(Exception e)
		{
			System.out.println("Looks like there is no player with this uuid!\n UUID: \"" + uuid + "\"");
			e.printStackTrace();
		}
		return names;
	}
	
	/**
	 * A helper class to store the name changes and dates
	 */
	public static class NameChange
	{
		/**
		 * The name to which the name was changed
		 */
		public String name;

		/**
		 * DateTime of the name change in UNIX time (without milliseconds)
		 */
		public long changedToAt;

		/**
		 * Gets the date of a name change
		 *
		 * @return Date of the name change
		 */
		public Date getChangeDate()
		{
			return new Date(changedToAt);
		}
	}
	
}