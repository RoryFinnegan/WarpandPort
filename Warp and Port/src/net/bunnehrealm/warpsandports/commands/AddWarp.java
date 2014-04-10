package net.bunnehrealm.warpsandports.commands;

import net.bunnehrealm.warpsandports.MainClass;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddWarp {

	MainClass MainClass;
	public WnP WnP;

	public AddWarp(MainClass MainClass) {
		this.MainClass = MainClass;
	}

	public AddWarp(WnP WnP) {
		this.WnP = WnP;
	}

	public AddWarp() {
	}

	public void addwarps(CommandSender cs, Command cmd, String string,
			String[] args) {
		Player player = (Player) cs;
		if (args.length == 4) {
			Player target = player.getServer().getPlayer(args[2]);
			if (!(MainClass.warps.contains("Warps." + args[3]))) {
				player.sendMessage(ChatColor.RED + "That warp does not exist!");
			} else {
				MainClass.players.set(target.getUniqueId() + ".Warps."
						+ args[3].toString(), "");
				player.sendMessage(ChatColor.AQUA + target.getName()
						+ ChatColor.GREEN + " can now warp to "
						+ ChatColor.AQUA + args[3]);
				MainClass.savePlayers();
			}
		}

		else{
			player.sendMessage(ChatColor.RED
					+ "The correct usage is " + ChatColor.AQUA
					+ "/wnp user add <Player Name> <Warp Name>");
		}}

}
