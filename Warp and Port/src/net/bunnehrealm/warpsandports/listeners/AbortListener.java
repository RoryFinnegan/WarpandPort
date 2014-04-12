package net.bunnehrealm.warpsandports.listeners;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.bunnehrealm.warpsandports.MainClass;
import net.bunnehrealm.warpsandports.commands.Warp;

public class AbortListener implements Listener {
	MainClass MainClass;
	Warp Warp;

	public AbortListener(MainClass MainClass) {
		this.MainClass = MainClass;
	}

	public AbortListener(Warp Warp) {
		this.Warp = Warp;
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		}
	}
