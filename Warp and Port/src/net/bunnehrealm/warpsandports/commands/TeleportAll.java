package net.bunnehrealm.warpsandports.commands;

import net.bunnehrealm.warpsandports.MainClass;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class TeleportAll {
	int id;
	MainClass MainClass;

	public TeleportAll(MainClass MainClass) {
		this.MainClass = MainClass;
	}

	TpExecutor TpExecutor;

	public TeleportAll(TpExecutor TpExecutor) {
		this.TpExecutor = TpExecutor;
	}

	public void teleport(CommandSender cs, Command cmd, String string,
			String[] args) {
		final Player player = (Player) cs;
		
		if (player.hasPermission("warpsandports.teleport.tp") || player.isOp()) {
			if(args.length == 0){
			player.sendMessage(ChatColor.GREEN
					+ "All players will be teleported to you in "
					+ ChatColor.AQUA + MainClass.getConfig().getInt("TpDelaySeconds")
					+ ChatColor.GREEN + " seconds!");

			BukkitScheduler scheduler = Bukkit.getScheduler();
			id = scheduler.scheduleSyncDelayedTask(MainClass, new Runnable() {

				@Override
				public void run() {
					for (Player targets : Bukkit.getOnlinePlayers()) {
						targets.teleport(player);
					}
					player.sendMessage(ChatColor.GREEN
							+ "All players have been teleported to you!");
				}

			}, 20 * MainClass.getConfig().getLong("TpDelaySeconds"));
			}
			else{
				player.sendMessage(ChatColor.RED + "Correct Usage " + ChatColor.AQUA + "/tpall");
			}
		} else {
			player.sendMessage(ChatColor.RED
					+ "You do not have permission to do that!");
		}

	}
}
