package net.bunnehrealm.warpsandports.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.bunnehrealm.warpsandports.MainClass;

public class SetHome {
	MainClass MainClass;
	HomeExecution HomeExecution;

	public SetHome(MainClass MainClass) {
		this.MainClass = MainClass;
	}

	public SetHome(HomeExecution HomeExecution) {
		this.HomeExecution = HomeExecution;
	}

	public void setHome(CommandSender cs, Command cmd, String string,
			String[] args) {
		Player player = (Player) cs;
		if (args.length == 0) {

			if (player.hasPermission("warpsandports.homes.set.default") || player.isOp()) {

				Location loc = player.getLocation();
				String world = loc.getWorld().getName();
				int x = loc.getBlockX();
				int y = loc.getBlockY();
				int z = loc.getBlockZ();
				float yaw = loc.getYaw();
				float pitch = loc.getPitch();

				MainClass.players.set(player.getUniqueId() + ".Homes.0.world",
						world);
				MainClass.players.set(player.getUniqueId() + ".Homes.0.x", x);
				MainClass.players.set(player.getUniqueId() + ".Homes.0.y", y);
				MainClass.players.set(player.getUniqueId() + ".Homes.0.z", z);
				MainClass.players.set(player.getUniqueId() + ".Homes.0.yaw",
						yaw);
				MainClass.players.set(player.getUniqueId() + ".Homes.0.pitch",
						pitch);
				
				player.sendMessage(ChatColor.GREEN + "Your home has been set!");

			}
		} else if (args.length == 1) {
			try {
				Integer.parseInt(args[0]);
			} catch (Exception e) {
				player.sendMessage(ChatColor.RED + "Correct usage "
						+ ChatColor.AQUA + "/setwarp <Number>");
				return;
			}
			if (player.hasPermission("warpsandports.homes.set." + args[0]) || player.isOp()) {

				Location loc = player.getLocation();
				String world = loc.getWorld().getName();
				int x = loc.getBlockX();
				int y = loc.getBlockY();
				int z = loc.getBlockZ();
				float yaw = loc.getYaw();
				float pitch = loc.getPitch();

				MainClass.players.set(player.getUniqueId() + ".Homes."
						+ args[0] + ".world", world);
				MainClass.players.set(player.getUniqueId() + ".Homes."
						+ args[0] + ".x", x);
				MainClass.players.set(player.getUniqueId() + ".Homes."
						+ args[0] + ".y", y);
				MainClass.players.set(player.getUniqueId() + ".Homes."
						+ args[0] + ".z", z);
				MainClass.players.set(player.getUniqueId() + ".Homes."
						+ args[0] + ".yaw", yaw);
				MainClass.players.set(player.getUniqueId() + ".Homes."
						+ args[0] + ".pitch", pitch);
				
				player.sendMessage(ChatColor.GREEN + "Home " + ChatColor.AQUA + args[0] + ChatColor.GREEN + " has been set!");

			}
		} else {
			player.sendMessage(ChatColor.RED + "Correct usage "
					+ ChatColor.AQUA + "/setwarp <Number>");
		}

	}
}
