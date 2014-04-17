package net.bunnehrealm.warpsandports.listeners;

import net.bunnehrealm.warpsandports.*;

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

public class WarpSigns implements Listener {

	MainClass MainClass;

	public WarpSigns(MainClass MainClass) {
		this.MainClass = MainClass;
	}

	@EventHandler
	public void onSignPlace(SignChangeEvent e) {
		Player player = e.getPlayer();
		if (e.getLine(1).equalsIgnoreCase(
				"[" + MainClass.getConfig().getString("Warps.WarpName") + "]")
				&& (player.hasPermission("warpsandports.warp.signs.create") || player
						.isOp())) {
			String signline = e.getLine(2);

			e.setLine(1, ChatColor.BLUE + "["
					+ MainClass.getConfig().getString("Warps.WarpName") + "]");
			e.setLine(2, MainClass.getConfig().getString("Warps.WarpColor")
					.replaceAll("(&([a-f0-9]))", "\u00A7$2")
					+ signline);
			Location loc = e.getBlock().getLocation();

			World world = player.getWorld();
			int x = loc.getBlockX();
			int y = loc.getBlockY();
			int z = loc.getBlockZ();
			MainClass.ports.set("Signs.Warp." + world.getName() + "/" + x + "/"
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
				if (MainClass.ports.contains("Signs.Warp" + world.getName()
						+ "/" + x + "/" + y + "/" + z)) {
					String line = new String();
					line = ChatColor.stripColor(sign.getLine(2));
					MainClass.players.set(player.getUniqueId() + ".Warps."
							+ ChatColor.stripColor(line), "");
					MainClass.savePlayers();
					player.sendMessage(ChatColor.GREEN + "The warp "
							+ ChatColor.AQUA + sign.getLine(2)
							+ ChatColor.GREEN
							+ " has been added to your warp list!");
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		if (player.hasPermission("warpsandports.warp.signs.destroy")
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
				if (MainClass.ports.contains("Signs.Warp" + world.getName()
						+ "/" + x + "/" + y + "/" + z)) {
					MainClass.ports.set("Signs.Warp" + world.getName() + "/"
							+ x + "/" + y + "/" + z, null);
					MainClass.savePorts();

				}
			}
			else {
				player.sendMessage(ChatColor.RED
						+ "You do not have permission to destroy "
						+ MainClass.getConfig().getString("Warps.WarpName") + "s!");
				e.setCancelled(true);
			}
		} 
	}

}
