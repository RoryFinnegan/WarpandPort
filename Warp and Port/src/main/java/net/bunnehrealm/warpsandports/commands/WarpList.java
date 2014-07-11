package net.bunnehrealm.warpsandports.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.bunnehrealm.warpsandports.RealmWarpsandPorts;

public class WarpList {
	RealmWarpsandPorts plugin = RealmWarpsandPorts.plugin;
	
	public WarpList(RealmWarpsandPorts instance){
		this.plugin = instance;
	}
	
	public void getList(Player p){
		List<String> warps = plugin.players.getStringList(p.getUniqueId() + ".Warps");
		p.sendMessage(ChatColor.AQUA + "Your " + plugin.getConfig().getString("Warps.WarpName") + "s are:" + ChatColor.GREEN + warps.toString().replace('[', ' ').replace(']', ' '));
	}
}
