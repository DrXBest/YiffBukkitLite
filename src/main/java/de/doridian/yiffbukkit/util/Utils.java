package de.doridian.yiffbukkit.util;

import de.doridian.yiffbukkit.YiffBukkitLite;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Utils {
	public static String concatArray(String[] array, int start, String def) {
		if (array.length <= start) return def;
		if (array.length <= start + 1) return array[start];
		StringBuilder ret = new StringBuilder(array[start]);
		for(int i=start+1;i<array.length;i++) {
			ret.append(' ');
			ret.append(array[i]);
		}
		return ret.toString();
	}

	public static String serializeLocation(Location loc) {
		return loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getYaw() + ";" + loc.getPitch() + ";" + loc.getWorld().getName();
	}

	public static Location unserializeLocation(String str) {
		String[] split = str.split(";");
		return new Location(Bukkit.getWorld(split[5]), Double.valueOf(split[0]), Double.valueOf(split[1]), Double.valueOf(split[2]), Float.valueOf(split[3]), Float.valueOf(split[4]));
	}
}
