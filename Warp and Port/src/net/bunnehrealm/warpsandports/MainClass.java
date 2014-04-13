package net.bunnehrealm.warpsandports;

import java.io.File;
import java.io.IOException;

import net.bunnehrealm.warpsandports.commands.Home;
import net.bunnehrealm.warpsandports.commands.HomeExecution;
import net.bunnehrealm.warpsandports.commands.SetHome;
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
	//initialize variables
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
	public Home home = new Home(this);
	public SetHome setHome = new SetHome(this);
	public HomeExecution homeExec = new HomeExecution(this);
	//^^^^^^^Make objects for these classes^^^^^^^^
	
	PluginManager pm = getServer().getPluginManager();

	//When the plugin gets disabled do this
	@Override
	public void onDisable() {
		
		if(playersFile.exists())
			savePlayers();
		//Save the players.yml file if it exists
			else{
				return;
			}
		if(warpsFile.exists())
			saveWarps();
		//Save the warps.yml file if it exists
			else{
				return;
			}
		if(portsFile.exists())
			savePorts();
		//Save the ports.yml file if it exists
			else{
				return;
			}
		getLogger().info(
				"[W&P] Warp and Port has been Disabled!");
		//Print to the server console ^^^^
	}
	//When the plugin is enabled do this
	@Override
	public void onEnable() {
		getLogger()
				.info("[W&P] Warp and Port has been " + ChatColor.GREEN
						+ " Enabled!");
		//Tell the console this on enable^
		
		getCommand("warp").setExecutor(warp);
		getCommand("wnp").setExecutor(wnp);
		getCommand("home").setExecutor(homeExec);
		getCommand("sethome").setExecutor(homeExec);
		/*Register the commands from each of their class fields
		  warp is an object of Warp.java
		  wnp is an object of WnP.java
		*/

		
		pm.registerEvents(warpSign, this);
		pm.registerEvents(portSign, this);
		/*Registers our listeners from WarpSigns.java and PortSigns.java
		  Again these are objects of those classes
		  */
		warpsFile = new File(getDataFolder(), "Warps.yml");
		warps = new YamlConfiguration();

		portsFile = new File(getDataFolder(), "Ports.yml");
		ports = new YamlConfiguration();
		
		playersFile = new File(getDataFolder(), "Players.yml");
		players = new YamlConfiguration();
		
		/*^^^These are creating the file location
		 *   and the configuration of the files. 
		 * */
		
		getConfig().options().copyDefaults(true);
		//^^Receive the Config
		
		if (!this.getConfig().isSet("WarpName")) {
			this.getConfig().set("WarpName", "Warp");
		}
		if (!this.getConfig().isSet("PortName")) {
			this.getConfig().set("PortName", "Port");
		}
		if (!this.getConfig().isSet("WarpColor")) {
			this.getConfig().set("WarpColor", "&b");
		}
		if (!this.getConfig().isSet("PortColor")) {
			this.getConfig().set("PortColor", "&d");
		}
		if (!this.getConfig().isSet("WarpDelaySeconds")) {
			this.getConfig().set("WarpDelaySeconds",5);
		}
		if (!this.getConfig().isSet("WarpCancelOnMove")) {
			this.getConfig().set("WarpCancelOnMove", false);
		}
		if (!this.getConfig().isSet("WarpCancelOnDamage")) {
			this.getConfig().set("WarpCancelOnDamage", true);
		}
		if (!this.getConfig().isSet("HomeDelaySeconds")) {
			this.getConfig().set("HomeDelaySeconds",5);
		}
		if (!this.getConfig().isSet("TpDelaySeconds")) {
			this.getConfig().set("TpDelaySeconds",5);
		}
		//^^^^These are all to add these paths to the config.yml if they do not already exist
		
		
		saveConfig();
		//^^^ Save the config
		try{
			firstPlayerRun();
			//Try to do the method firstPlayerRun()
		}
		catch(Exception e){
			e.printStackTrace();
			//If there is an error with firstPlayerRun print it to the console
			return;
		}

		loadPlayers();
		//Try to run the loadPlayers() method
		
		try{
			firstWarpRun();
			//Run the method firstWarpRun()
		}
		catch(Exception e){
			e.printStackTrace();
			//If there is a problem with firstWarpRun() print it to the console
			return;
		}

		loadWarps();
		
		try{
			firstPortRun();
			//Try to run the firstPortRun() method
		}
		catch(Exception e){
			e.printStackTrace();
			//If there is a problem with firstPortRun() print it to the console
			return;
		}

		loadPorts();
		saveWarps();
		savePlayers();
		savePorts();
		//^^ These are quite self-explanatory
	}
		
		private void firstWarpRun() throws Exception{
			if(!warpsFile.exists()){
				//^ If the warpsFile does not exist do this all
				//NOTE: ! basically means if does not.
				getLogger().info("Creating a warps.yml file");
				//^^Tell the console you are doing this
				warpsFile.getParentFile().mkdirs();
				//^^ Get the directory for the plugin data
				warpsFile.createNewFile();
				//^^ Create a new file
			}
		}
		public void loadWarps() {
	        try {
	            warps.load(warpsFile);
	            //^^ Try to load the warpsFile
	        } catch (Exception e) {e.printStackTrace();
	        	e.printStackTrace();
	        	//^^ If there is an error doing this print the error to the console
	            return;
	        }
	    }
		public void saveWarps(){
			try{
			warps.save(warpsFile);
			//Try to save the warpsFile
		}
			catch(IOException e){e.printStackTrace();
			//^^ If there is an error saving the warpsFile print to the console the error
				return;
			}
	}
		private void firstPlayerRun() throws Exception{
			if(!playersFile.exists()){
				//^^ If playersFile does not exist do this
				getLogger().info("Creating a players.yml file");
				//Tell the console you are creating a players.yml
				playersFile.getParentFile().mkdirs();
				//^^ Get the Directory for the new file.
				playersFile.createNewFile();
				//^^ Create the new File
			}
		}
		public void loadPlayers() {
	        try {
	            players.load(playersFile);
	            //Try to load the playersFile
	        } catch (Exception e) {e.printStackTrace();
	        //^^ If there is an error loading the file print the error to the console.    
	        return;
	        }
	    }
		public void savePlayers(){
			try{
			players.save(playersFile);
			//Try to save the playersFile
		}
			catch(IOException e){e.printStackTrace();
			//If there is an IOException print to the console the problem.
				return;
			}
	}
		
		private void firstPortRun() throws Exception{
			if(!portsFile.exists()){
				//^^ If portsFile does not exist do this
				getLogger().info("Creating a ports.yml file");
				//^^ Tell the server you are creating a ports.yml file
				portsFile.getParentFile().mkdirs();
				//^^ Get the directory for the file
				portsFile.createNewFile();
				//^^ Create the file
			}
		}
		public void loadPorts() {
	        try {
	            ports.load(portsFile);
	          //^^ Try to lad the portsFile
	        } catch (Exception e) {e.printStackTrace();
	        //^^ If an error occurs while trying to lad the portsFile print to the console the problem    
	        return;
	        }
	    }
		public void savePorts(){
			try{
			ports.save(portsFile);
			//^^ Save the portsFile
		}
			catch(IOException e){e.printStackTrace();
			//^^ If an IOException occurs in saving the portsFile print to the console the error.
				return;
			}
	}

}
