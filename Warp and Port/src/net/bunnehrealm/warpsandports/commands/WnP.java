package net.bunnehrealm.warpsandports.commands;

import net.bunnehrealm.warpsandports.MainClass;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
public class WnP implements CommandExecutor{
	MainClass MainClass;
	AddWarp AddWarp;
	public WnP(MainClass MainClass){
		this.MainClass = MainClass;
	}
	public WnP(AddWarp AddWarp){
		this.AddWarp = AddWarp;
	}
	

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String string,
			String[] args) {
		if(string.equalsIgnoreCase("wnp")){
			if(args.length == 4 && args[0].equalsIgnoreCase("user") && args[1].equalsIgnoreCase("add")){
					try{
						AddWarp addWarp = new AddWarp();
						addWarp.addwarps(cs, cmd, string, args);
					}
					catch(Exception e){
						System.out.println(e);
					
				}
			}}
		return false;
	}

}
