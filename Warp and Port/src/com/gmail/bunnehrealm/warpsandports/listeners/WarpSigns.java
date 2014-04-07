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
		player.sendMessage("1");
		Location loc = e.getClickedBlock().getLocation();
		player.sendMessage("2");
		if (e.getClickedBlock().getType() == Material.SIGN
				|| e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.WALL_SIGN) {
			player.sendMessage("3");
			Sign sign = (Sign) e.getClickedBlock().getState();
			player.sendMessage("4");
			World world = player.getWorld();
			player.sendMessage("5");
			int x = loc.getBlockX();
			int y = loc.getBlockY();
			int z = loc.getBlockZ();
			player.sendMessage("6");
			if (MainClass.ports.contains("Signs." + world.getName() + "/" + x
					+ "/" + y + "/" + z)) {
				player.sendMessage("7");
				String line = new String();
				line = sign.getLine(2);
				player.sendMessage("8");
				MainClass.players.set(
						player.getName() + ".Warps." + ChatColor.stripColor(line), "");
				player.sendMessage("9");
				MainClass.savePlayers();
				player.sendMessage(ChatColor.GREEN + "The warp "
						+ ChatColor.AQUA + sign.getLine(2) + ChatColor.GREEN
						+ " has been added to your warp list!");
				player.sendMessage("10");
				e.setCancelled(true);
				player.sendMessage("11");
			}
		}
	}

}
