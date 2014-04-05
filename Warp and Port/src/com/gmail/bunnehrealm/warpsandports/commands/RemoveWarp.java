package com.gmail.bunnehrealm.warpsandports.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.gmail.bunnehrealm.warpsandports.MainClass;

public class RemoveWarp implements CommandExecutor {

	MainClass MainClass;

	public RemoveWarp(MainClass MainClass) {
		this.MainClass = MainClass;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String string,
			String[] args) {
		if (string.equalsIgnoreCase("removewarpuser")) {
			if (!(cs instanceof Player)) {
				cs.sendMessage("This command is only for players!");
			} else {
				Player player = (Player) cs;
				if (player.hasPermission("warspandports.removewarpuser")
						|| player.isOp()) {
					if (args.length != 2) {
						player.sendMessage(ChatColor.RED + "Proper use is "
								+ ChatColor.AQUA + "/removewarpuser <Player Name> <Warp Name>");
					} else {
						Player target = player.getServer().getPlayer(args[0]);
							if (!(MainClass.players.contains(target.getName() + ".Warps." + args[1]))) {
								player.sendMessage(ChatColor.RED
										+ "That player does not have that warp!");
							} else {
								MainClass.players.set(player.getName() + ".Warps." + args[1], null);
								MainClass.savePlayers();
								player.sendMessage(ChatColor.GREEN
										+ "The warp " + ChatColor.AQUA
										+ args[1] + ChatColor.GREEN
										+ " has been removed from the list of "
										+ ChatColor.AQUA + args[0]);
								return true;
							}
						}
					}
				}
			}
		

		return false;
	}

}
