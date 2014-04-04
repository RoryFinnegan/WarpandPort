package com.gmail.bunnehrealm.warpsandports.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.bunnehrealm.warpsandports.MainClass;

public class Warp implements CommandExecutor{
	
	MainClass MainClass;
	
	public Warp(MainClass MainClass) {
		this.MainClass = MainClass;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd,
			String string, String[] args) {
			if(string.equalsIgnoreCase("warp")){
				if(!(cs instanceof Player)){
					cs.sendMessage("This command is only for players!");
					return false;
				}
				else{
					Player player = (Player) cs;
					if(player.hasPermission("warpsandports.warp") || player.isOp()){
						if(args.length != 1){
							player.sendMessage(ChatColor.RED
									+ "The correct usage is " + ChatColor.AQUA
									+ "/warp <Warp Name>");
						}
						else{
							if(!(MainClass.players.contains(player.getName() + ".Warps." + args[0]))){
								player.sendMessage(ChatColor.RED + "You do not have that Warp!");
							}
							else{
								MainClass.loadWarps();
								if(MainClass.warps.contains("Warps." + args[0])){
								
									World world = (World) MainClass.warps.get("Warps." + args[0] + ".world");
									Double x = MainClass.warps.getDouble("Warps." + args[0] + ".x");
									Double y = MainClass.warps.getDouble("Warps." + args[0] + ".y");
									Double z = MainClass.warps.getDouble("Warps." + args[0] + ".z");
								
								Location loc = new Location(world,x,y,z);
								player.teleport(loc);
								}
								else{
									player.sendMessage(ChatColor.RED + "That Warp does not exist!");
								}
							}
						}
					}
					else{
						player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
					}
				}
			}
		return false;
	}

}
