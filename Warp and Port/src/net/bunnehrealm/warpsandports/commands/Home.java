package net.bunnehrealm.warpsandports.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import net.bunnehrealm.warpsandports.MainClass;

public class Home {

	MainClass MainClass;
	int id;

	public Home(MainClass MainClass) {
		this.MainClass = MainClass;
	}

	public void teleportHome(CommandSender cs, Command cmd, String string,
			final String[] args) {
		if (args.length == 0) {
			final Player player = (Player) cs;
			if (player.hasPermission("warpsandports.homes.tp.default") || player.isOp()) {
				
				if(MainClass.players
						.contains(player.getUniqueId()
								+ ".Homes.0")){}
				else{
					player.sendMessage(ChatColor.RED + "That home is not set!");
					return;
				}

				player.sendMessage(ChatColor.GREEN + "Teleporting you in "
						+ ChatColor.AQUA
						+ MainClass.getConfig().getLong("HomeDelaySeconds")
						+ ChatColor.GREEN + " seconds!");
				BukkitScheduler scheduler = Bukkit.getScheduler();
				id = scheduler.scheduleSyncDelayedTask(MainClass,
						new Runnable() {

							@Override
							public void run() {
								Location loc = new Location(
										Bukkit.getWorld(MainClass.players
												.getString(player.getUniqueId()
														+ ".Homes.0.world")),
										MainClass.players.getInt(player
												.getUniqueId() + ".Homes.0.x"),
										MainClass.players.getInt(player
												.getUniqueId() + ".Homes.0.y"),
										MainClass.players.getInt(player
												.getUniqueId() + ".Homes.0.z"),
										MainClass.players.getInt(player
												.getUniqueId() + ".Homes.0.yaw"),
										MainClass.players.getInt(player
												.getUniqueId()
												+ ".Homes.0.pitch"));
								player.teleport(loc);
								player.sendMessage(ChatColor.GREEN
										+ "Welcome home!");

							}

						}, 20 * MainClass.getConfig().getLong("HomeDelaySeconds"));
			} else {
				player.sendMessage(ChatColor.RED
						+ "You do not have permission to do that!");
			}
		} else if (args.length == 1) {

			final Player player = (Player) cs;
			try {
				Integer.parseInt(args[0]);
			} catch (Exception e) {
				player.sendMessage(ChatColor.RED + "Correct usage "
						+ ChatColor.AQUA + "/setwarp <Number>");
				return;
			}
			if (player.hasPermission("warpsandports.homes.tp." + args[0]) || player.isOp()) {
				if(MainClass.players
						.contains(player.getUniqueId()
								+ ".Homes." + args[0])){}
				else{
					player.sendMessage(ChatColor.RED + "That home is not set!");
					return;
				}
				player.sendMessage(ChatColor.GREEN + "Teleporting you in "
						+ ChatColor.AQUA
						+ MainClass.getConfig().getLong("HomeDelaySeconds")
						+ ChatColor.GREEN + " seconds!");

				BukkitScheduler scheduler = Bukkit.getScheduler();
				id = scheduler
						.scheduleSyncDelayedTask(MainClass, new Runnable() {

							@Override
							public void run() {
								Location loc = new Location(Bukkit
										.getWorld(MainClass.players
												.getString(player.getUniqueId()
														+ ".Homes." + args[0]
														+ ".world")),
										MainClass.players.getInt(player
												.getUniqueId()
												+ ".Homes."
												+ args[0] + ".x"),
										MainClass.players.getInt(player
												.getUniqueId()
												+ ".Homes."
												+ args[0] + ".y"),
										MainClass.players.getInt(player
												.getUniqueId()
												+ ".Homes."
												+ args[0] + ".z"),
										MainClass.players.getInt(player
												.getUniqueId()
												+ ".Homes."
												+ args[0] + ".yaw"),
										MainClass.players.getInt(player
												.getUniqueId()
												+ ".Homes."
												+ args[0] + ".pitch"));
								player.teleport(loc);
								player.sendMessage(ChatColor.GREEN
										+ "Welcome home!");

							}

						}, 20 * MainClass.getConfig().getLong("HomeDelaySeconds"));
			}

		} else {
			cs.sendMessage(ChatColor.RED + "Correct usage " + ChatColor.AQUA
					+ "/home [Number]");
		}
	}
}
