package net.bunnehrealm.warpsandports.listeners;

import net.bunnehrealm.warpsandports.MainClass;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class RespawnListener implements Listener{
	MainClass plugin = MainClass.plugin;
	public RespawnListener(MainClass instance){
		this.plugin = instance;
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e){
		final Player p = e.getPlayer();
		if(plugin.warps.contains("Warps.spawn")){
			World world = Bukkit.getWorld(plugin.warps.getString("Warps.spawn.world")); 
			int x = plugin.warps.getInt("Warps.spawn.x");
			int y = plugin.warps.getInt("Warps.spawn.y");
			int z = plugin.warps.getInt("Warps.spawn.z");
			float yaw = plugin.warps.getLong("Warps.spawn.yaw");
			float pitch = plugin.warps.getLong("Warps.spawn.pitch");
			
			final Location loc = new Location(world, x, y, z, yaw, pitch);
			
			BukkitScheduler schedule = Bukkit.getScheduler();
			schedule.scheduleSyncDelayedTask(plugin, new Runnable(){

				public void run() {
					p.teleport(loc);
					
				}
				
			}, 1);
			
			
			
			
		}
	}

}
