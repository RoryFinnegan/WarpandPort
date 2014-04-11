package net.bunnehrealm.warpsandports;

import java.io.File;
import java.io.IOException;

import net.bunnehrealm.warpsandports.commands.Warp;
import net.bunnehrealm.warpsandports.commands.WnP;
import net.bunnehrealm.warpsandports.listeners.PortSigns;
import net.bunnehrealm.warpsandports.listeners.WarpSigns;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin {
	public MainClass MainClass;
	
	public File warpsFile;
	public FileConfiguration warps;
	public File portsFile;
	public FileConfiguration ports;
	
	public File playersFile;
	public FileConfiguration players;
	
	public Warp warp = new Warp(this);
	public WnP wnp = new WnP(this);
	public WarpSigns warpSign = new WarpSigns(this);
	public PortSigns portSign = new PortSigns(this);
	
	PluginManager pm = getServer().getPluginManager();

	@Override
	public void onDisable() {
		if(playersFile.exists())
			savePlayers();
			else{
				return;
			}
		if(warpsFile.exists())
			saveWarps();
			else{
				return;
			}
		if(portsFile.exists())
			savePorts();
			else{
				return;
			}
		getLogger().info(
				"[W&P] Warp and Port has been Disabled!");
	}

	public void onEnable() {
		getLogger()
				.info("[W&P] Warp and Port has been " + ChatColor.GREEN
						+ " Enabled!");
		
		getCommand("warp").setExecutor(warp);
		getCommand("wnp").setExecutor(wnp);

		
		pm.registerEvents(warpSign, this);
		pm.registerEvents(portSign, this);

		warpsFile = new File(getDataFolder(), "Warps.yml");
		warps = new YamlConfiguration();

		portsFile = new File(getDataFolder(), "Ports.yml");
		ports = new YamlConfiguration();
		
		playersFile = new File(getDataFolder(), "Players.yml");
		players = new YamlConfiguration();
		
		getConfig().options().copyDefaults(true);
		
		if (!this.getConfig().isSet("WarpName")) {
			this.getConfig().set("WarpName", "Warp");
		}
		if (!this.getConfig().isSet("PortName")) {
			this.getConfig().set("PortName", "Port");
		}
		if (!this.getConfig().isSet("WarpDelaySeconds")) {
			this.getConfig().set("WarpDelaySeconds",5);
		}
		
		if (!this.getConfig().isSet("WarpColor")) {
			this.getConfig().set("WarpColor", "&b");
		}
		if (!this.getConfig().isSet("PortColor")) {
			this.getConfig().set("PortColor", "&d");
		}
		
		saveConfig();
		try{
			firstPlayerRun();
		}
		catch(Exception e){
			e.printStackTrace();			
			return;
		}

		loadPlayers();
		
		try{
			firstWarpRun();
		}
		catch(Exception e){
			e.printStackTrace();
			return;
		}

		loadWarps();
		
		try{
			firstPortRun();
		}
		catch(Exception e){
			e.printStackTrace();
			return;
		}

		loadPorts();
		saveWarps();
		savePlayers();
		savePorts();
	}
		
		private void firstWarpRun() throws Exception{
			if(!warpsFile.exists()){
				getLogger().info("Creating a warps.yml file");
				warpsFile.getParentFile().mkdirs();
				warpsFile.createNewFile();
			}
		}
		public void loadWarps() {
	        try {
	            warps.load(warpsFile);
	        } catch (Exception e) {e.printStackTrace();
	        	e.printStackTrace();
	            return;
	        }
	    }
		public void saveWarps(){
			try{
			warps.save(warpsFile);
		}
			catch(IOException e){e.printStackTrace();
				return;
			}
	}
		private void firstPlayerRun() throws Exception{
			if(!playersFile.exists()){
				getLogger().info("Creating a players.yml file");
				playersFile.getParentFile().mkdirs();
				playersFile.createNewFile();
			}
		}
		public void loadPlayers() {
	        try {
	            players.load(playersFile);
	        } catch (Exception e) {e.printStackTrace();
	            return;
	        }
	    }
		public void savePlayers(){
			try{
			players.save(playersFile);
		}
			catch(IOException e){e.printStackTrace();
				return;
			}
	}
		
		private void firstPortRun() throws Exception{
			if(!portsFile.exists()){
				getLogger().info("Creating a ports.yml file");
				portsFile.getParentFile().mkdirs();
				portsFile.createNewFile();
			}
		}
		public void loadPorts() {
	        try {
	            ports.load(portsFile);
	        } catch (Exception e) {e.printStackTrace();
	            return;
	        }
	    }
		public void savePorts(){
			try{
			ports.save(portsFile);
		}
			catch(IOException e){e.printStackTrace();
				return;
			}
	}

}
