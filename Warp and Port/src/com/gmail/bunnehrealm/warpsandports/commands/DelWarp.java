package com.gmail.bunnehrealm.warpsandports.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.gmail.bunnehrealm.warpsandports.MainClass;

public class DelWarp implements CommandExecutor {

	MainClass MainClass;
	
	public DelWarp(MainClass MainClass) {
		this.MainClass = MainClass;
	}
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String string,
			String[] args) {
		
			if(string.equalsIgnoreCase("delwarp")){
				if(!(cs instanceof Player)){
					cs.sendMessage("That command is player only!");
				}
				else{
					Player player = (Player) cs;
					if(!(player.hasPermission("warpsandports.deletewarp") || player.isOp())){
						player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
					}
					else{
						if(args.length != 1){
							player.sendMessage(ChatColor.RED + "The correct use is " + ChatColor.AQUA + "/delwarp <Warp Name>");
							return false;
						}
						else{
							if(!(MainClass.warps.contains("Warps." + args[0]))){
								player.sendMessage(ChatColor.RED + "That warp does not exist!");
								return false;
							}
							else{
								MainClass.warps.set("Warps." + args[0], null);
								MainClass.saveWarps();
								player.sendMessage(ChatColor.GREEN + "The warp " + ChatColor.AQUA + args[0] + ChatColor.GREEN + " has been removed" );
								return true;
							}
						}
					}
				}
			}
		
		return false;
	}

}
