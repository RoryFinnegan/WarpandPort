package net.bunnehrealm.warpsandports.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.bunnehrealm.warpsandports.MainClass;

/**
 * Created by Rory Finnegan on 6/14/14.
 */
public class JoinListener implements Listener {
	MainClass plugin = MainClass.plugin;

	public JoinListener(MainClass instance) {
		this.plugin = instance;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!(plugin.players.contains(p.getUniqueId() + ".hasjoined"))) {
			World world = Bukkit.getWorld(plugin.warps.getString("Warps.firstspawn.world"));
			double x = (plugin.warps.getInt("Warps.firstspawn.x"));
			double y = (plugin.warps.getInt("Warps.firstspawn.y"));
			double z = (plugin.warps.getInt("Warps.firstspawn.z"));
			float yaw = (plugin.warps.getLong("Warps.firstspawn.yaw"));
			float pitch = (plugin.warps.getLong("Warps.firstspawn.pitch"));
			
			Location loc = new Location(world, x, y, z, yaw, pitch);
			p.teleport(loc);
			plugin.players.set(p.getUniqueId() + ".hasjoined", true);

		}
	}
}
