package net.bunnehrealm.warpsandports.commands;

import java.util.List;

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

public class Warp implements CommandExecutor {

	MainClass MainClass;
	public int id;

	public Warp(MainClass MainClass) {
		this.MainClass = MainClass;
	}

	public boolean onCommand(CommandSender cs, Command cmd, String string,
			final String[] args) {
		
		if (string.equalsIgnoreCase("warp") || string.equalsIgnoreCase(MainClass.getConfig().getString("Warps.WarpName")) ) {
			if (!(cs instanceof Player)) {
				cs.sendMessage("This command is only for players!");
				return false;
			} else {
				final Player player = (Player) cs;
				List<String> warps = MainClass.players.getStringList(player
						.getUniqueId() + ".Warps");
				if (player.hasPermission("warpsandports.warp") || player.isOp()) {
					if (args.length != 1) {
						player.sendMessage(ChatColor.RED
								+ "The correct usage is " + ChatColor.AQUA
								+ "/warp <Warp Name>");
					} else {
						if ((player.isOp() || player
								.hasPermission("warpsandports.warp." + args[0]))
								&& (MainClass.warps
										.contains("Warps." + args[0]))) {
							final World world = Bukkit.getWorld(MainClass.warps
									.getString("Warps." + args[0] + ".world"));
							final Double x = MainClass.warps.getDouble("Warps."
									+ args[0] + ".x");
							final Double y = MainClass.warps.getDouble("Warps."
									+ args[0] + ".y");
							final Double z = MainClass.warps.getDouble("Warps."
									+ args[0] + ".z");
							final Float yaw = (float) MainClass.warps
									.getInt("Warps." + args[0] + ".yaw");
							final Float pitch = (float) MainClass.warps
									.getInt("Warps." + args[0] + ".pitch");

							BukkitScheduler scheduler = Bukkit.getServer()
									.getScheduler();
							try {
								player.sendMessage(ChatColor.GREEN
										+ "Teleporting, please wait "
										+ ChatColor.AQUA
										+ MainClass.getConfig().getLong(
												"Warps.WarpDelaySeconds")
										+ ChatColor.GREEN + " seconds!");
							} catch (Exception c) {
								System.out.println(c);
							}
							id = scheduler.scheduleSyncDelayedTask(
									MainClass,
									new Runnable() {

										public void run() {

											Location loc = new Location(world,
													x, y, z, yaw, pitch);
											player.teleport(loc);
											player.sendMessage(ChatColor.GREEN
													+ "You have been teleported to "
													+ ChatColor.AQUA + args[0]);
										}

									},
									20 * MainClass.getConfig().getLong(
											"Warps.WarpDelaySeconds"));
							return true;
						} else if (!(MainClass.players.contains(player
								.getUniqueId() + ".Warps." + args[0]))) {
							player.sendMessage(ChatColor.RED
									+ "You do not have that Warp!");
							return false;
						} else {
							MainClass.loadWarps();
							if (MainClass.warps.contains("Warps." + args[0])) {

								final World world = Bukkit
										.getWorld(MainClass.warps
												.getString("Warps." + args[0]
														+ ".world"));
								final Double x = MainClass.warps
										.getDouble("Warps." + args[0] + ".x");
								final Double y = MainClass.warps
										.getDouble("Warps." + args[0] + ".y");
								final Double z = MainClass.warps
										.getDouble("Warps." + args[0] + ".z");

								BukkitScheduler scheduler = Bukkit.getServer()
										.getScheduler();
								player.sendMessage(ChatColor.GREEN
										+ "Teleporting, please wait "
										+ ChatColor.AQUA
										+ MainClass.getConfig().getLong(
												"Warps.WarpDelaySeconds")
										+ ChatColor.GREEN + " seconds!");
								scheduler.scheduleSyncDelayedTask(
										MainClass,
										new Runnable() {

											public void run() {

												Location loc = new Location(
														world, x, y, z);
												player.teleport(loc);
												player.sendMessage(ChatColor.GREEN
														+ "You have been teleported to "
														+ ChatColor.AQUA
														+ args[0]);
											}

										},
										20 * MainClass.getConfig().getLong(
												"Warps.WarpDelaySeconds"));
							} else {
								player.sendMessage(ChatColor.RED
										+ "That Warp does not exist!");
							}
						}
					}
				} else {
					player.sendMessage(ChatColor.RED
							+ "You do not have permission to use this command!");
				}
			}
			return false;
		}
		return false;

	}
}
