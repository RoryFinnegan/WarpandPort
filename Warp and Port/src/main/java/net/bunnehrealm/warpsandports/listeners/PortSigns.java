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
	public Location dest = null;
	public int destx;
	public int desty;
	public int destyaw;
	public int destpitch;
	public int destz;

	public PortSigns(MainClass MainClass) {
		this.MainClass = MainClass;
	}

	@EventHandler
	public void onSignPlace(SignChangeEvent e) {
		Player player = e.getPlayer();
		if (e.getLine(1).equalsIgnoreCase(
				"[" + MainClass.getConfig().getString("Ports.PortName") + "]")
				&& (player.hasPermission("warpsandports.port.signs.create") || player
						.isOp())) {
			String signline = e.getLine(2);
			e.setLine(1, ChatColor.BLUE + "["
					+ MainClass.getConfig().getString("Ports.PortName") + "]");
			e.setLine(2, MainClass.getConfig().getString("Ports.PortColor")
					.replaceAll("(&([a-f0-9]))", "\u00A7$2")
					+ signline);
			Location loc = e.getBlock().getLocation();

			World world = player.getWorld();
			int x = loc.getBlockX();
			int y = loc.getBlockY();
			int z = loc.getBlockZ();
			MainClass.ports.set("Signs.Port." + world.getName() + "/" + x + "/"
					+ y + "/" + z, signline);
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
				Sign sign = (Sign) e.getClickedBlock().getState();
				World world = player.getWorld();
				int x = loc.getBlockX();
				int y = loc.getBlockY();
				int z = loc.getBlockZ();
				destx = MainClass.warps.getInt("Warps."
						+ ChatColor.stripColor(sign.getLine(2)).toString()
						+ ".x");
				desty = MainClass.warps.getInt("Warps."
						+ ChatColor.stripColor(sign.getLine(2)).toString()
						+ ".y");
				destz = MainClass.warps.getInt("Warps."
						+ ChatColor.stripColor(sign.getLine(2)).toString()
						+ ".z");
				destyaw = MainClass.warps.getInt("Warps."
						+ ChatColor.stripColor(sign.getLine(2)).toString()
						+ ".yaw");
				destpitch = MainClass.warps.getInt("Warps."
						+ ChatColor.stripColor(sign.getLine(2)).toString()
						+ ".pitch");
				final World finalWorld = Bukkit.getWorld(MainClass.warps
						.getString("Warps."
								+ ChatColor.stripColor(sign.getLine(2))
										.toString() + ".world"));

				if (MainClass.ports.contains("Signs.Port." + world.getName()
						+ "/" + x + "/" + y + "/" + z)) {
					if (MainClass.warps.contains("Warps."
							+ ChatColor.stripColor(sign.getLine(2)))) {

						dest = new Location(finalWorld, destx, desty, destz,
								destyaw, destpitch);

						player.teleport(dest);
						e.setCancelled(true);

					}
				}
			}
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();

		Block block = e.getBlock();
		if (block.getType() == Material.SIGN
				|| block.getType() == Material.SIGN_POST
				|| block.getType() == Material.WALL_SIGN) {
			Sign sign = (Sign) block.getState();
			Location loc = sign.getLocation();
			World world = player.getWorld();
			int x = loc.getBlockX();
			int y = loc.getBlockY();
			int z = loc.getBlockZ();
			if (player.hasPermission("warpsandports.signs.destroy")
					|| player.isOp() && ChatColor.stripColor(sign.getLine(1)).equalsIgnoreCase(ChatColor.stripColor("[" + MainClass.getConfig().getString("Ports.PortName") + "]"))) {
				if (MainClass.ports.contains("Signs.Port" + world.getName()
						+ "/" + x + "/" + y + "/" + z)) {
					MainClass.ports.set("Signs.Port" + world.getName() + "/"
							+ x + "/" + y + "/" + z, null);
					MainClass.savePorts();

				}

			} else if(!(player.hasPermission("warpsandports.signs.destroy")
					|| player.isOp()) && ChatColor.stripColor(sign.getLine(1)).equalsIgnoreCase(ChatColor.stripColor("[" + MainClass.getConfig().getString("Ports.PortName") + "]"))) {
				player.sendMessage(ChatColor.RED
						+ "You do not have permission to destroy "
						+ MainClass.getConfig().getString("Ports.PortName")
						+ "s!");
				e.setCancelled(true);
			}
		}
	}
}
