package me.xbenz;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import me.xbenz.Utils.Profile;

public class Cache {
	
	private UUID uuid;
	private static ConcurrentHashMap<UUID, Profile> PROFILES = new ConcurrentHashMap<UUID, Profile>();
	
	public Cache(UUID uuid) {
		this.uuid = uuid;
	}
	
	public Cache() {
		
	}
	
	public void add() {
		Main.getInstance().getProxy().getScheduler().runAsync(Main.getInstance(), () -> {
			Profile p = new Profile(uuid);
			if (!PROFILES.containsKey(uuid)) {
				PROFILES.put(uuid, p);
			}
		});
	}
	
	public void save() {
		if (PROFILES.containsKey(uuid)) {
			PROFILES.remove(uuid);
		}
	}
	
	public Profile getProfile() {
		return PROFILES.get(uuid);
	}

}