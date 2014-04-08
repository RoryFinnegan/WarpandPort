package com.gmail.bunnehrealm.warpsandports.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.gmail.bunnehrealm.warpsandports.*;

public class WarpSigns implements Listener {

	MainClass MainClass;

	public WarpSigns(MainClass MainClass) {
		this.MainClass = MainClass;
	}

	@EventHandler
	public void onSignPlace(SignChangeEvent e) {
		Player player = e.getPlayer();
		if (e.getLine(1).equalsIgnoreCase(
				"[" + MainClass.getConfig().getString("WarpName") + "]")
				&& (player.hasPermission("warpsandports.signs.create") || player
						.isOp())) {
			String signline = e.getLine(2);

			e.setLine(1, ChatColor.BLUE + "["
					+ MainClass.getConfig().getString("WarpName") + "]");
			e.setLine(2, MainClass.getConfig().getString("WarpColor")
					.replaceAll("(&([a-f0-9]))", "\u00A7$2")
					+ signline);
			Location loc = e.getBlock().getLocation();

			World world = player.getWorld();
			int x = loc.getBlockX();
			int y = loc.getBlockY();
			int z = loc.getBlockZ();
			MainClass.ports.set("Signs." + world.getName() + "/" + x + "/" + y
					+ "/" + z, signline);
			MainClass.savePorts();
		}
	}

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		Location loc = e.getClickedBlock().getLocation();
		if (e.getClickedBlock().getType() == Material.SIGN
				|| e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.WALL_SIGN) {
			Sign sign = (Sign) e.getClickedBlock().getState();
			World world = player.getWorld();
			int x = loc.getBlockX();
			int y = loc.getBlockY();
			int z = loc.getBlockZ();
			if (MainClass.ports.contains("Signs." + world.getName() + "/" + x
					+ "/" + y + "/" + z)) {
				String line = new String();
				line = sign.getLine(2);
				MainClass.players.set(
						player.getUniqueId() + ".Warps." + ChatColor.stripColor(line), "");
				MainClass.savePlayers();
				player.sendMessage(ChatColor.GREEN + "The warp "
						+ ChatColor.AQUA + sign.getLine(2) + ChatColor.GREEN
						+ " has been added to your warp list!");
				e.setCancelled(true);
			}
		}
	}

}
