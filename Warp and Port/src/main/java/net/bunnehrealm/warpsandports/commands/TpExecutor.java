package net.bunnehrealm.warpsandports.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.bunnehrealm.warpsandports.MainClass;

public class TpExecutor implements CommandExecutor{
	MainClass MainClass;
	Teleport Teleport;
	TeleportAll TeleportAll;
	TeleportHere TeleportHere;
	
	public TpExecutor(MainClass MainClass){
		this.MainClass = MainClass;
	}
	public TpExecutor(Teleport Teleport){
		this.Teleport = Teleport;
	}
	public TpExecutor(TeleportHere TeleportHere){
		this.TeleportHere = TeleportHere;
	}
	public TpExecutor(TeleportAll TeleportAll){
		this.TeleportAll = TeleportAll;
	}
	
	Teleport tpObj = new Teleport(MainClass);
	TeleportHere tphereObj = new TeleportHere(MainClass);
	TeleportAll tpallObj = new TeleportAll(MainClass);
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String string,
			String[] args) {
		if(!(cs instanceof Player)){
			cs.sendMessage("This is not a console command!");
			return false;
		}
			if(string.equalsIgnoreCase("tp")){
				Teleport teleportObj = new Teleport(MainClass);
				teleportObj.teleport(cs, cmd, string, args);
			}
			else if(string.equalsIgnoreCase("tphere")){
				TeleportHere teleporthereObj = new TeleportHere(MainClass);
				teleporthereObj.teleport(cs, cmd, string, args);
			}
			else if(string.equalsIgnoreCase("tpall")){
				TeleportAll tpAll = new TeleportAll(MainClass);
				tpAll.teleport(cs, cmd, string, args);
			}
		return false;
	}
	

}
