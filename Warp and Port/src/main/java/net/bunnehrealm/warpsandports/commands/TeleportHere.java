package net.bunnehrealm.warpsandports.commands;

import net.bunnehrealm.warpsandports.MainClass;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class TeleportHere {

	int id;
	MainClass MainClass;

	public TeleportHere(MainClass MainClass) {
		this.MainClass = MainClass;
	}

	TpExecutor TpExecutor;

	public TeleportHere(TpExecutor TpExecutor) {
		this.TpExecutor = TpExecutor;
	}

	public void teleport(CommandSender cs, Command cmd, String string,
			String[] args) {
		final Player player = (Player) cs;

		if (player.hasPermission("warpsandports.teleport.tphere")
				|| player.isOp()) {
			if (args.length == 1) {
				final Player target = player.getServer().getPlayer(args[0]);
				player.sendMessage(ChatColor.AQUA + target.getDisplayName()
						+ ChatColor.GREEN + " will be teleported to you in "
						+ MainClass.getConfig().getInt("Teleports.TpDelaySeconds")
						+ ChatColor.GREEN + " seconds!");
				
				BukkitScheduler scheduler = Bukkit.getScheduler();
				id = scheduler
						.scheduleSyncDelayedTask(MainClass, new Runnable() {

							@Override
							public void run() {
								target.teleport(player);
								player.sendMessage(ChatColor.AQUA
										+ target.getDisplayName()
										+ ChatColor.GREEN
										+ " has been teleported to you!");
							}

						}, 20 * MainClass.getConfig().getLong("Teleports.TpDelaySeconds"));
			}

			else {
				player.sendMessage(ChatColor.RED + "Correct Usage "
						+ ChatColor.AQUA + "/tphere <Player>");
			}
		} else {
			player.sendMessage(ChatColor.RED
					+ "You do not have permission to do that!");
		}

	}

}
