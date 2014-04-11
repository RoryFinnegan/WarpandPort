package net.bunnehrealm.warpsandports.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.bunnehrealm.warpsandports.MainClass;

public class PortSigns implements Listener {
	MainClass MainClass;
	public Location dest;
	public int destx;
	public int desty;
	public int destz;
	public World destworld;

	public PortSigns(MainClass MainClass) {
		this.MainClass = MainClass;
	}

	@EventHandler
	public void onSignPlace(SignChangeEvent e) {
		Player player = e.getPlayer();
		if (e.getLine(1).equalsIgnoreCase(
				"[" + MainClass.getConfig().getString("PortName") + "]")
				&& (player.hasPermission("warpsandports.port.signs.create") || player
						.isOp())) {
			System.out.println("1");
			String signline = e.getLine(2);
			e.setLine(1, ChatColor.BLUE + "["
					+ MainClass.getConfig().getString("PortName") + "]");
			e.setLine(2, MainClass.getConfig().getString("PortColor")
					.replaceAll("(&([a-f0-9]))", "\u00A7$2")
					+ signline);
			Location loc = e.getBlock().getLocation();

			World world = player.getWorld();
			int x = loc.getBlockX();
			int y = loc.getBlockY();
			int z = loc.getBlockZ();
			MainClass.ports.set("Signs.Port." + world.getName() + "/" + x + "/"
					+ y + "/" + z, signline);
			System.out.println("2");
			MainClass.savePorts();
		}
	}

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Action action = e.getAction();
		if (action == Action.RIGHT_CLICK_BLOCK) {
			Player player = e.getPlayer();
			Location loc = e.getClickedBlock().getLocation();
			if (e.getClickedBlock().getType() == Material.SIGN
					|| e.getClickedBlock().getType() == Material.SIGN_POST
					|| e.getClickedBlock().getType() == Material.WALL_SIGN) {
				player.sendMessage("1");
				Sign sign = (Sign) e.getClickedBlock().getState();
				World world = player.getWorld();
				int x = loc.getBlockX();
				int y = loc.getBlockY();
				int z = loc.getBlockZ();
				if (MainClass.ports.contains("Signs.Port." + world.getName()
						+ "/" + x + "/" + y + "/" + z)) {
					player.sendMessage("2");
					if (MainClass.warps.contains("Warps."
							+ ChatColor.stripColor(sign.getLine(2)))) {
						player.sendMessage("3");
						player.sendMessage("4");
						World destworld = Bukkit.getWorld(MainClass.warps
								.getString("Warps." + sign.getLine(2)
										+ ".world"));
						 destx = MainClass.warps.getInt("Warps."
								+ sign.getLine(2) + ".x");
						 desty = MainClass.warps.getInt("Warps."
								+ sign.getLine(2) + ".y");
						 destz = MainClass.warps.getInt("Warps."
								+ sign.getLine(2) + ".z");
						
						dest.setWorld(destworld);
						dest.setX(destx);
						dest.setY(desty);
						dest.setZ(destz);

						player.sendMessage("5");
						
						player.teleport(dest);
						player.sendMessage("6");
					}
				}
			}
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		if (player.hasPermission("warpsandports.signs.destroy")
				|| player.isOp()) {
			Block block = e.getBlock();
			if (block.getType() == Material.SIGN
					|| block.getType() == Material.SIGN_POST
					|| block.getType() == Material.WALL_SIGN) {
				Sign sign = (Sign) block;
				Location loc = sign.getLocation();
				World world = player.getWorld();
				int x = loc.getBlockX();
				int y = loc.getBlockY();
				int z = loc.getBlockZ();
				if (MainClass.ports.contains("Signs.Port" + world.getName()
						+ "/" + x + "/" + y + "/" + z)) {
					MainClass.ports.set("Signs.Port" + world.getName() + "/"
							+ x + "/" + y + "/" + z, null);
					MainClass.savePorts();

				}
			}
		} else {
			player.sendMessage(ChatColor.RED
					+ "You do not have permission to destroy "
					+ MainClass.getConfig().getString("PortName") + "s!");
			e.setCancelled(true);
		}
	}
}
