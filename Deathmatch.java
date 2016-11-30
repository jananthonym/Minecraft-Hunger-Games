package me.Jan.first;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

public class Deathmatch {
	public static First q;
	public int timerId;

	public Deathmatch(First instance){
		q = instance;
	}
	public int tot = 5;
	public void DMfreeze(){
		q.freeze = true;
		q.phase = "Deathmatch";
		q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
		{
			public void run()
			{
				q.time = "5m";
				Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 5 Seconds Left");
			}
		}, 20L);
		q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
		{
			public void run()
			{
				Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 4 Seconds Left");
			}
		}, 40L);
		q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
		{
			public void run()
			{
				Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 3 Seconds Left");
			}
		}, 60L);
		q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
		{
			public void run()
			{
				Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 2 Seconds Left");
			}
		}, 80L);
		q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
		{
			public void run()
			{
				Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 1 Second Left");
			}
		}, 100L);
		q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
		{
			public void run()
			{
				q.freeze = false;
				Bukkit.getServer().broadcastMessage(ChatColor.GREEN+"[ASS] FIGHT TO THE DEATH!!");
			}
		}, 120L);
		timerId = q.getServer().getScheduler().scheduleSyncRepeatingTask(q, new Runnable() {
			public void run(){
				q.time = Integer.toString(tot--)+"m";
			}
		}, 120L, 1200L);
		q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
		{
			public void run()
			{
				q.getServer().getScheduler().cancelTask(timerId);
				int p=0;
				for(Player t:q.getServer().getOnlinePlayers()){
					if(t.getGameMode()==GameMode.SURVIVAL)
						p++;
				}
				if(p>1){
					Bukkit.getServer().broadcastMessage(ChatColor.GREEN+"[ASS] No Winner For This Match!");
					for(Player t:q.getServer().getOnlinePlayers()){
						t.kickPlayer(ChatColor.DARK_AQUA+ "Server Is Restarting.");
					}
					Bukkit.shutdown();
				}
			}
		}, 6120L);
	}
	
	public void DMcountdown(){
		q.phase = "Pre-Deathmatch";
		q.time = "10s";
		q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
		{
			public void run()
			{
				Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 10 Seconds Till Deathmatch");
			}
		}, 1000L);
		q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
		{
			public void run()
			{
				Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 9 Seconds Till Deathmatch");
			}
		}, 1020L);
		q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
		{
			public void run()
			{
				Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 8 Seconds Till Deathmatch");
			}
		}, 1040L);
		q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
		{
			public void run()
			{
				Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 7 Seconds Till Deathmatch");
			}
		}, 1060L);
		q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
		{
			public void run()
			{
				Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 6 Seconds Till Deathmatch");
			}
		}, 1080L);
		q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
		{
			public void run()
			{
				Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 5 Seconds Till Deathmatch");
			}
		}, 1100L);
		q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
		{
			public void run()
			{
				Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 4 Seconds Till Deathmatch");
			}
		}, 1120L);
		q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
		{
			public void run()
			{
				Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 3 Seconds Till Deathmatch");
			}
		}, 1140L);
		q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
		{
			public void run()
			{
				Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 2 Seconds Till Deathmatch");
			}
		}, 1160L);
		q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
		{
			public void run()
			{
				Bukkit.getServer().broadcastMessage(ChatColor.YELLOW+"[ASS] 1 Seconds Till Deathmatch");
			}
		}, 1180L);
		q.getServer().getScheduler().scheduleSyncDelayedTask(q, new Runnable()
		{
			public void run()
			{
				WorldCreator.name(q.world).environment(org.bukkit.World.Environment.NORMAL).generateStructures(false).createWorld();
				int p=0;
				for(Player t:q.getServer().getOnlinePlayers()){
					t.closeInventory();
					t.teleport(q.spawn.getLoc(Bukkit.getWorld(q.world), p));
					p++;
				}
				World w = q.getServer().getWorld(q.world);
				w.setTime(0);
				DMfreeze();
			}
		}, 1200L);
	}
}
