package net.bunnehrealm.warpsandports.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import net.bunnehrealm.warpsandports.MainClass;

public class Teleport {
	int id;
	MainClass MainClass;

	public Teleport(MainClass MainClass) {
		this.MainClass = MainClass;
	}

	TpExecutor TpExecutor;

	public Teleport(TpExecutor TpExecutor) {
		this.TpExecutor = TpExecutor;
	}

	public void teleport(CommandSender cs, Command cmd, String string,
			String[] args) {
		final Player player = (Player) cs;
		if (player.hasPermission("warpsandports.teleport.tp") || player.isOp()) {
			if (args.length == 1) {
				final Player target = player.getServer().getPlayer(args[0]);
				player.sendMessage(MainClass.getConfig().getString("TpDelaySeconds"));
				player.sendMessage(ChatColor.GREEN
						+ "You will be teleported to " + ChatColor.AQUA
						+ target.getDisplayName() + ChatColor.GREEN + " in "
						+ ChatColor.AQUA
						+ MainClass.getConfig().getString("TpDelaySeconds")
						+ ChatColor.GREEN + " seconds!");
				BukkitScheduler scheduler = Bukkit.getScheduler();
				id = scheduler
						.scheduleSyncDelayedTask(MainClass, new Runnable() {

							@Override
							public void run() {
								player.teleport(target);
								player.sendMessage(ChatColor.GREEN
										+ "You have been teleported to "
										+ ChatColor.AQUA
										+ target.getDisplayName());
							}

						}, 20 * MainClass.getConfig().getLong("TpDelaySeconds"));
			} 
			else if(string.length() == 2){
				if(!(player.hasPermission("warpsandports.teleport.others") || player.isOp())){
					player.sendMessage(ChatColor.RED + "You do not have permission to do that!");
					return;
				}
				final Player target1 = player.getServer().getPlayer(args[0]);
				final Player target2 = player.getServer().getPlayer(args[1]);
				player.sendMessage(ChatColor.AQUA + target1.getDisplayName()
						
						+ " will be teleported to " + ChatColor.AQUA + target2.getDisplayName() + ChatColor.GREEN + " in " + ChatColor.AQUA
						+ MainClass.getConfig().getInt("TpDelaySeconds")
						+ ChatColor.GREEN + " seconds!");

				BukkitScheduler scheduler = Bukkit.getScheduler();
				id = scheduler
						.scheduleSyncDelayedTask(MainClass, new Runnable() {

							@Override
							public void run() {
								target1.teleport(target2);
								player.sendMessage(ChatColor.AQUA
										+ target1.getDisplayName()
										+ ChatColor.GREEN
										+ " has been teleported to " + ChatColor.AQUA + target2.getDisplayName());
							}

						}, 20 * MainClass.getConfig().getLong("TpDelaySeconds"));
			}
			else{
				player.sendMessage(ChatColor.RED + "Correct Usage " + ChatColor.AQUA + "/tp <Player Name>");
			}
		}
		else {
			player.sendMessage(ChatColor.RED
					+ "You do not have permission to do that!");
		}
	}
}
