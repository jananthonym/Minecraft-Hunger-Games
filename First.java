package me.Jan.first;


import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class First extends JavaPlugin{
	public final Logger logger = Logger.getLogger("Minecraft");
	public static First plugin;
	First q = this;
	public boolean freeze = false;
	public boolean br = false;
	public boolean start = false;
	public boolean canVote = true;
	public final myListener ml = new myListener(this);
	public String world = "world_h";
	public Vote vote = new Vote();
	public Spawn spawn = new Spawn();	
	public int task;
	public String time = "2m";
	public String phase = "Lobby";
	public int timerID;
	
	@Override
	public void onDisable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info("["+pdfFile.getName()+"]" + " Disabled");
	}

	@Override
	public void onEnable(){
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(ml, q);
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info("["+pdfFile.getName()+"]" + " Enabled");
		task = getServer().getScheduler().scheduleSyncDelayedTask(this, new Startup(), 10);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("break")){ 
			if(br){
				br=false;
			}else{
				br=true;
			}
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("v") && canVote){ 
			Player player = (Player) sender;
			if(args.length<1 || args.length>1){
				player.sendMessage(ChatColor.DARK_BLUE+ "[ASS] Please enter the map number of the map you want to vote for.");
			}else{
				if(vote.addVote(Integer.parseInt(args[0])-1, player)){
					player.sendMessage(ChatColor.DARK_BLUE+ "[ASS] You voted for the map: "+ (vote.map(Integer.parseInt(args[0])-1)).replace("_", " "));
					vote.voted(player);
				}else
					player.sendMessage(ChatColor.DARK_BLUE+ "[ASS] You already voted for a map, or you entered and invalid map number.");
			}
		}
		if(cmd.getName().equalsIgnoreCase("q")){ 
			Bukkit.broadcastMessage(args[0]);
		}
		return false; 
	}
	
	class Startup implements Runnable{
	public final Deathmatch dm = new Deathmatch(q);	
	public boolean tp = true;
	
		public void Lobby(){
			canVote = true;
			q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
			{
				public void run()
				{
					phase = "Lobby";
					time = "2m";
					vote.announceVote();
					Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 2 Minutes Left Till End of Lobby");
				}
			}, 400L);
			q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
			{
				public void run()
				{
					vote.announceVote();
				}
			}, 1000L);
			q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
			{
				public void run()
				{
					time = "1m";
					vote.announceVote();
					Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 1 Minute Left Till End of Lobby");
				}
			}, 1600L);
			q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
			{
				public void run()
				{
					vote.announceVote();
				}
			}, 2200L);
			q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
			{
				public void run()
				{
					time = "10s";
					Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 10 Seconds Left Till End of Lobby");
				}
			}, 2600L);
			q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
			{
				public void run()
				{
					time = "5s";
					Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 5 Seconds Left Till End of Lobby");
				}
			}, 2700L);
			q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
			{
				public void run()
				{
					time = "4s";
					Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 4 Seconds Left Till End of Lobby");
				}
			}, 2720L);
			q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
			{
				public void run()
				{
					time = "3s";
					start = true;
					Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 3 Seconds Left Till End of Lobby");
				}
			}, 2740L);
			q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
			{
				public void run()
				{
					time = "2s";
					Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 2 Seconds Left Till End of Lobby");
				}
			}, 2760L);
			q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
			{
				public void run()
				{
					time = "1s";
					canVote = false;
					Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 1 Second Left Till End of Lobby");
				}
			}, 2780L);
		}
		
		public int tot = 30;
		public int tott = 30;
		public void teleportPlayers(){
			phase = "Live Game";
			time = "30m";
			world = vote.getMap();
			WorldCreator.name(world).environment(org.bukkit.World.Environment.NORMAL).generateStructures(false).createWorld();
			int p=0;
			for(Player t:q.getServer().getOnlinePlayers()){
				t.closeInventory();
				t.teleport(spawn.getLoc(Bukkit.getWorld(world), p));
				t.setHealth(20);
				t.setFoodLevel(20);
				t.setLevel(0);
				t.setSaturation(5);
				t.getInventory().clear();
				t.setExp(0);
				t.getInventory().setArmorContents(new ItemStack[] { null, null, null, null } );
				p++;
			}
			World w = q.getServer().getWorld(world);
			w.setTime(0);
			Bukkit.broadcastMessage(ChatColor.DARK_AQUA+ "[ASS] Welcome to "+(vote.getMap().replace("_", " ")+ChatColor.DARK_AQUA+"!"));
			freezeCountDown();
			timerID = q.getServer().getScheduler().scheduleSyncRepeatingTask(q, new Runnable() {
				public void run(){
					time = Integer.toString(--tot)+"m";
					if(tott-tot == 5){
						tott=tot;
						Bukkit.broadcastMessage(ChatColor.DARK_AQUA+ "[ASS] Having Fun? Don't forget to vote for ASS on "+ChatColor.AQUA+"findmcservers.com");
					}
				}
			}, 600L, 1200L);
			q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
			{
				public void run()
				{
					freeze = false;
					Bukkit.getServer().broadcastMessage(ChatColor.GREEN+"[ASS] START!!!!!!!!!!!");
				}
			}, 620L);
			q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
			{
				public void run()
				{
					ChestManager CM = new ChestManager();
					CM.resetChests();
					Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] The Chests Have Been Refilled.");
				}
			}, 12620L);
			q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
			{
				public void run()
				{
					Bukkit.getServer().getScheduler().cancelTask(timerID);
					dm.DMcountdown();
					Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] Deathmatch Starts In One Minute.");
				}
			}, 36620L);
			
		}
		
		public void freezeCountDown(){
			freeze = true;
			q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
			{
				public void run()
				{
					Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 30 Seconds Left");
				}
			}, 20L);
			q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
			{
				public void run()
				{
					Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 10 Seconds Left");
				}
			}, 420L);
			q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
			{
				public void run()
				{
					Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 5 Seconds Left");
				}
			}, 520L);
			q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
			{
				public void run()
				{
					Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 4 Seconds Left");
				}
			}, 540L);
			q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
			{
				public void run()
				{
					Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 3 Seconds Left");
				}
			}, 560L);
			q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
			{
				public void run()
				{
					Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 2 Seconds Left");
				}
			}, 580L);
			q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
			{
				public void run()
				{
					Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 1 Second Left");
				}
			}, 600L);

		}
		
		public void start(){
			Lobby();
			q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
			{
				public void run()
				{
					Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 0 Seconds Left Till End of Lobby");
					int f=0;
					for(Player t:q.getServer().getOnlinePlayers()){
							f++;
					}
					if(f<2){
						Bukkit.getServer().broadcastMessage(ChatColor.DARK_AQUA+"[ASS] Not Enough Players, Restarting Countdown.");
						Bukkit.getServer().getScheduler().cancelTask(q.task);
						start = false;
						tp = false;
						br = false;
						canVote = true;
						vote.resetVotes();
						getServer().getScheduler().scheduleSyncDelayedTask(q, new Startup(), 0);
					}
					if(tp)
						teleportPlayers();
				}
			}, 2800L);
		}

		public void run(){
			start();
		}
	}

}
