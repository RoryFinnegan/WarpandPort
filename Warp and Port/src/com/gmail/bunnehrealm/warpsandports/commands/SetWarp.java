package com.gmail.bunnehrealm.warpsandports.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.bunnehrealm.warpsandports.MainClass;

public class SetWarp implements CommandExecutor {
	public MainClass MainClass;

	public SetWarp(MainClass MainClass) {
		this.MainClass = MainClass;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command label, String string,
			String[] args) {
		if (string.equalsIgnoreCase("setwarp")) {
			if (!(cs instanceof Player)) {
				return false;
			} else {
				Player player = (Player) cs;
				if (player.hasPermission("warpsandports.setwarp")
						|| player.isOp()) {
					if (args.length == 0) {
						player.sendMessage(ChatColor.RED + "Correct use is "
								+ ChatColor.AQUA + "/setwarp <WarpName>");
						return false;
					}
					if (args.length == 1) {
						if(MainClass.warps.contains("Warps." + args[0])){
							player.sendMessage(ChatColor.RED + "That warp already exists!");
							return false;
						}
						else{
						
						Location loc = player.getLocation();
						
						int x = loc.getBlockX();
						int y = loc.getBlockY();
						int z = loc.getBlockZ();
						
						World world = player.getWorld();
						
						MainClass.warps.set("Warps." + args[0].toString()
								+ ".world", world.getName());
						MainClass.warps.set("Warps." + args[0].toString()
								+ ".x", x);
						MainClass.warps.set("Warps." + args[0].toString()
								+ ".y", y);
						MainClass.warps.set("Warps." + args[0].toString()
								+ ".z", z);

						MainClass.saveWarps();
						player.sendMessage(ChatColor.GREEN + "The warp point "
								+ ChatColor.AQUA + args[0] + ChatColor.GREEN
								+ " has been created!");
						
						return true;
						}
					} else {
						player.sendMessage(ChatColor.RED + "Correct use is "
								+ ChatColor.AQUA + "/setwarp <WarpName>");
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
