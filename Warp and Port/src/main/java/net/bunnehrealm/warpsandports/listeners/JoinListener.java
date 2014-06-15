package net.bunnehrealm.warpsandports.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.bunnehrealm.warpsandports.MainClass;

/**
 * Created by Rory Finnegan on 6/14/14.
 */
public class JoinListener implements Listener {
    MainClass plugin = MainClass.plugin;

    public JoinListener(MainClass instance){
        this.plugin = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(!(plugin.players.contains(p.getUniqueId() + "hasjoined"))){
            Location loc = new Location();
            loc.setX(plugin.players.getInt(plugin.warps.getInt("Warps.firstspawn.x")));
        }
    }
}
