package net.bunnehrealm.warpsandports.commands;

import net.bunnehrealm.warpsandports.MainClass;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeExecution implements CommandExecutor{
	MainClass MainClass;
	Home Home;
	SetHome SetHome;
	HomeExecution HomeExecution;
	
	public HomeExecution(MainClass MainClass){
		this.MainClass = MainClass;
	}
	public HomeExecution(Home Home){
		this.Home = Home;
	}
	public HomeExecution(HomeExecution HomeExecution){
		this.HomeExecution = HomeExecution;
	}
	public HomeExecution(SetHome SetHome){
		this.SetHome = SetHome;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd,
			String string, String[] args) {
		Home Home = new Home(MainClass);
		SetHome SetHome = new SetHome(MainClass);
		if(string.equalsIgnoreCase("sethome")){
			if(!(cs instanceof Player)){
				return false;
			}
			else
			SetHome.setHome(cs, cmd, string, args);
		}
		else if(string.equalsIgnoreCase("home")){
			if(!(cs instanceof Player)){
				return false;
			}
			else
			Home.teleportHome(cs, cmd, string, args);
		}
			
		return false;
	}
}
