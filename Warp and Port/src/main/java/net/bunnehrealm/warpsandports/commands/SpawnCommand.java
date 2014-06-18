package net.bunnehrealm.warpsandports.commands;

import net.bunnehrealm.warpsandports.MainClass;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class SpawnCommand implements CommandExecutor {
	MainClass plugin = MainClass.plugin;

	public SpawnCommand(MainClass instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender cs, Command command, String label,
			String[] args) {
		final Player p = (Player) cs;
		if (p.hasPermission("warpsandports.spawn") || p.isOp()) {
			if (plugin.warps.contains("Warps.spawn")) {
				World world = Bukkit.getWorld(plugin.warps
						.getString("Warps.spawn.world"));
				int x = plugin.warps.getInt("Warps.spawn.x");
				int y = plugin.warps.getInt("Warps.spawn.y");
				int z = plugin.warps.getInt("Warps.spawn.z");
				float yaw = plugin.warps.getLong("Warps.spawn.yaw");
				float pitch = plugin.warps.getLong("Warps.spawn.pitch");

				final Location loc = new Location(world, x, y, z, yaw, pitch);

				p.sendMessage(ChatColor.GREEN
						+ "You will be teleported to spawn in "
						+ ChatColor.AQUA
						+ plugin.getConfig().getInt("Spawn.delay")
						+ ChatColor.GREEN + " seconds!");
				BukkitScheduler schedule = Bukkit.getScheduler();
				schedule.scheduleSyncDelayedTask(plugin, new Runnable() {

					public void run() {
						p.teleport(loc);

					}

				}, 20 * plugin.getConfig().getInt("Spawn.delay"));
			}
			return false;
		}
		return false;

	}
}
