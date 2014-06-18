package net.bunnehrealm.warpsandports.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.bunnehrealm.warpsandports.MainClass;
import net.bunnehrealm.warpsandports.commands.WarpList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WnP implements CommandExecutor {
	MainClass MainClass;

	public WnP(MainClass MainClass) {
		this.MainClass = MainClass;
	}
	

	public boolean onCommand(CommandSender cs, Command cmd, String string,
			String[] args) {
		if (string.equalsIgnoreCase("wnp")) {
			if (args.length == 4 && args[0].equalsIgnoreCase("user")
					&& args[1].equalsIgnoreCase("add")) {
				try {
					addwarps(cs, cmd, string, args);
				} catch (Exception e) {
					System.out.println(e);

				}
			} else if (args.length == 4 && args[0].equalsIgnoreCase("user")
					&& args[1].equalsIgnoreCase("remove")) {
				try {
					removeWarpUser(cs, cmd, string, args);
				} catch (Exception e) {
					System.out.println(e);

				}
			} else if (args.length == 3 && args[0].equalsIgnoreCase("warp")
					&& args[1].equalsIgnoreCase("delete")) {
				try {
					delWarp(cs, cmd, string, args);
				} catch (Exception e) {
					System.out.println(e);

				}
			} else if (args.length == 3 && args[0].equalsIgnoreCase("warp")
					&& args[1].equalsIgnoreCase("add")) {
				try {
					setWarp(cs, cmd, string, args);
				} catch (Exception e) {
					System.out.println(e);

				}}
				else if (args.length == 1 && args[0].equalsIgnoreCase("list") && (cs.hasPermission("warpsandports.warp.list") || cs.isOp())) {
					try {
						WarpList wl = new WarpList(MainClass);
						wl.getList((Player) cs);
					} catch (Exception e) {
						System.out.println(e);

					}
			} else {
				cs.sendMessage(ChatColor.GOLD + "----------" + ChatColor.AQUA
						+ ChatColor.BOLD + " RealmWarpsandPorts Help "
						+ ChatColor.GOLD + "----------");
				if (cs.hasPermission("warpsandports.warp.add") || cs.isOp()) {
					cs.sendMessage(ChatColor.AQUA + "/wnp warp add <WarpName>");
					cs.sendMessage(ChatColor.GOLD
							+ "     - Adds a warp to the server.");
				}
				if (cs.hasPermission("warpsandports.warp.delete") || cs.isOp()) {
					cs.sendMessage(ChatColor.AQUA
							+ "/wnp warp delete <Warp Name>");
					cs.sendMessage(ChatColor.GOLD
							+ "     - Deletes a warp from the server.");
				}
				if (cs.hasPermission("warpsandports.user.add") || cs.isOp()) {
					cs.sendMessage(ChatColor.AQUA
							+ "/wnp user add <Player Name> <Warp Name>");
					cs.sendMessage(ChatColor.GOLD
							+ "     - Adds a warp to a user's warp list.");
				}
				if (cs.hasPermission("warpsandports.user.remove") || cs.isOp()) {
					cs.sendMessage(ChatColor.AQUA
							+ "/wnp user remove <Player Name> <Warp Name>");
					cs.sendMessage(ChatColor.GOLD
							+ "     - Remove a warp from a user's warp list.");
				}
				if (cs.hasPermission("warpsandports.warp.list") || cs.isOp()) {
					cs.sendMessage(ChatColor.AQUA + "/wnp list");
					cs.sendMessage(ChatColor.GOLD
							+ "     - Shows you a list of your warps.");
				}

			}
		}
		return false;
	}

	public void addwarps(CommandSender cs, Command cmd, String string,
			String[] args) {

		Player player = (Player) cs;
		if (player.hasPermission("warpsandports.user.add") || player.isOp()) {
			if (args.length == 4) {
				Player target = player.getServer().getPlayer(args[2]);
				if (!(MainClass.warps.contains("Warps." + args[3]))) {
					player.sendMessage(ChatColor.RED
							+ "That warp does not exist!");
				} else {
					try {
						MainClass.loadPlayers();
						List<String> warps = MainClass.players.getStringList(target.getUniqueId() + ".Warps");
						warps.add(args[3]);
						MainClass.players.set(target.getUniqueId() + ".Warps", warps);
					} catch (Exception e) {
						e.printStackTrace();
					}
					player.sendMessage(ChatColor.AQUA + target.getName()
							+ ChatColor.GREEN + " can now warp to "
							+ ChatColor.AQUA + args[3]);
					MainClass.savePlayers();
				}
			}

			else {
				player.sendMessage(ChatColor.RED + "The correct usage is "
						+ ChatColor.AQUA
						+ "/wnp user add <Player Name> <Warp Name>");
			}
		} else {
			player.sendMessage(ChatColor.RED
					+ "You don't have permission to use that command!");
		}
	}

	public boolean removeWarpUser(CommandSender cs, Command cmd, String string,
			String[] args) {
		Player player = (Player) cs;
		if (player.hasPermission("warspandports.user.remove") || player.isOp()) {
			if (args.length != 4) {
				player.sendMessage(ChatColor.RED + "Proper use is "
						+ ChatColor.AQUA
						+ "/wnp user remove <Player Name> <Warp Name>");
			} else {
				MainClass.loadPlayers();
				
				Player target = player.getServer().getPlayer(args[2]);
				List<String> warps = MainClass.players.getStringList(target.getUniqueId() + ".Warps");
				String warp = args[3];
				for(String s: warps){
					if(warps.contains(s)){
						warps.remove(args[3]);
						MainClass.savePlayers();
						player.sendMessage(ChatColor.GREEN + "The warp "
								+ ChatColor.AQUA + args[3] + ChatColor.GREEN
								+ " has been removed from the list of "
								+ ChatColor.AQUA + args[2]);
						return true;
					}
					player.sendMessage(ChatColor.RED + "That player does not have that warp!");
				}
			}
		}
		return false;
	}

	public boolean delWarp(CommandSender cs, Command cmd, String string,
			String[] args) {

		Player player = (Player) cs;
		if (!(player.hasPermission("warpsandports.warp.delete") || player
				.isOp())) {
			player.sendMessage(ChatColor.RED
					+ "You do not have permission to use this command!");
		} else {
			if (args.length != 3) {
				player.sendMessage(ChatColor.RED + "The correct use is "
						+ ChatColor.AQUA + "/wnp warp delete <Warp Name>");
				return false;
			} else {
				if (!(MainClass.warps.contains("Warps." + args[2]))) {
					player.sendMessage(ChatColor.RED
							+ "That warp does not exist!");
					return false;
				} else {
					MainClass.warps.set("Warps." + args[2], null);
					MainClass.saveWarps();
					player.sendMessage(ChatColor.GREEN + "The warp "
							+ ChatColor.AQUA + args[0] + ChatColor.GREEN
							+ " has been removed");
					return true;
				}
			}
		}

		return false;
	}

	public boolean setWarp(CommandSender cs, Command label, String string,
			String[] args) {
		Player player = (Player) cs;
		if (player.hasPermission("warpsandports.warp.add") || player.isOp()) {
			if (args.length == 0) {
				player.sendMessage(ChatColor.RED + "Correct use is "
						+ ChatColor.AQUA + "/wnp warp add <WarpName>");
				return false;
			}
			if (args.length == 3) {
				if (MainClass.warps.contains("Warps." + args[2])) {
					player.sendMessage(ChatColor.RED
							+ "That warp already exists!");
					return false;
				} else {

					Location loc = player.getLocation();

					int x = loc.getBlockX();
					int y = loc.getBlockY();
					int z = loc.getBlockZ();
					float yaw = loc.getYaw();
					float pitch = loc.getPitch();

					World world = player.getWorld();

					MainClass.warps.set("Warps." + args[2].toString()
							+ ".world", world.getName());
					MainClass.warps
							.set("Warps." + args[2].toString() + ".x", x);
					MainClass.warps
							.set("Warps." + args[2].toString() + ".y", y);
					MainClass.warps
							.set("Warps." + args[2].toString() + ".z", z);
					MainClass.warps.set("Warps." + args[2].toString() + ".yaw",
							yaw);
					MainClass.warps.set("Warps." + args[2].toString()
							+ ".pitch", pitch);

					MainClass.saveWarps();
					player.sendMessage(ChatColor.GREEN + "The warp point "
							+ ChatColor.AQUA + args[2] + ChatColor.GREEN
							+ " has been created!");

					return true;
				}
			} else {
				player.sendMessage(ChatColor.RED + "Correct use is "
						+ ChatColor.AQUA + "/wnp warp add <WarpName>");
			}
		} else {
			player.sendMessage(ChatColor.RED
					+ "You do not have permission to use this command!");
		}
		return false;

	}
}
