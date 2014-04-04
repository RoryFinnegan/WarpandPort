package com.gmail.bunnehrealm.warpsandports.commands;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.bunnehrealm.warpsandports.MainClass;

public class AddWarp implements CommandExecutor {

	MainClass MainClass;

	public AddWarp(MainClass MainClass) {
		this.MainClass = MainClass;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command label, String string,
			String[] args) {

		if (string.equalsIgnoreCase("addwarp")) {
			if (!(cs instanceof Player)) {
				return false;
			} else {
				Player player = (Player) cs;
				if (player.hasPermission("warpsandports.addwarp")
						|| player.isOp()) {
					if (args.length != 2) {
						player.sendMessage(ChatColor.RED
								+ "The correct usage is " + ChatColor.AQUA
								+ "/addwarp <Player Name> <Warp Name>");
					} else {
						Player target = player.getServer().getPlayer(args[0]);
						if (!(target.isOnline())) {
							player.sendMessage(ChatColor.RED
									+ "That player is not online!");
							return false;
						} else {
							if (!(MainClass.warps.contains("Warps." + args[1]))) {
								player.sendMessage(ChatColor.RED
										+ "That warp does not exist!");
								return false;
							} else {
								MainClass.players.set(target.getName()
										+ ".Warps." + args[1].toString() , "");
								MainClass.savePlayers();
								return true;
							}
						}
					}
				} else {
					player.sendMessage(ChatColor.RED
							+ "You do not have permission to use this command!");
					return false;
				}
			}
		}

		return false;
	}

}
