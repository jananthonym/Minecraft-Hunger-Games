package me.Jan.first;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;


public class myListener implements Listener{
	ChestManager CM = new ChestManager();
	public static First plugin;
	
	public myListener(First instance){
		plugin = instance;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		Player player = e.getPlayer();
		if(plugin.freeze){
			if(player.getGameMode()==GameMode.SURVIVAL){
				if (((e.getTo().getX() != e.getFrom().getX()) || (e.getTo().getZ() != e.getFrom().getZ()))) {
					e.setTo(e.getFrom());
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void onplace(BlockPlaceEvent e){
		if(!plugin.br){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void ChestInterract(PlayerInteractEvent event) {
		if (!event.isCancelled()){ 
			if(event.getAction() == Action.LEFT_CLICK_BLOCK && event.getPlayer().getGameMode()==GameMode.ADVENTURE) {
				event.setCancelled(true);
			}
			if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if(event.getPlayer().getGameMode()==GameMode.ADVENTURE){
					event.setCancelled(true);
				}else{
					if(event.getClickedBlock().getType().equals(Material.CHEST)){
						Chest chest = (Chest) event.getClickedBlock().getState();
						CM.fillChest(chest);
					}   
				}
			}
		} 
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onbreak(BlockBreakEvent e){
		if(e.getBlock().getType()==Material.LEAVES || e.getBlock().getType()==Material.VINE){
			e.setCancelled(false);
		}else{
			if(!plugin.br){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e){
		if(plugin.start)
			e.disallow(Result.KICK_OTHER, ChatColor.DARK_AQUA+"Can't Join During Game.");
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e){
		if (e.getEntity() instanceof Player && !plugin.start) 
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager().getType() == EntityType.PLAYER) {
			Player damager = (Player) event.getDamager();
			if(damager.getGameMode() == GameMode.ADVENTURE){
				event.setCancelled(true);

			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		Player p = event.getPlayer();
		for(int i=0; i < 20; i ++)
		{
			p.sendMessage("");
		}
		World e = plugin.getServer().getWorld("lobbyy");
		p.teleport(new Location(e, 670, 5, 411, 180, 0));
		e.setTime(0);
		p.setGameMode(GameMode.SURVIVAL);
		p.sendMessage(ChatColor.GREEN+ "-------------------------------");
		p.sendMessage(ChatColor.GREEN+ "|      Welcome to Survival Games       |");
		p.sendMessage(ChatColor.GREEN+ "|               Author:                      |");
		p.sendMessage(ChatColor.GREEN+ "|             RuDaRecon                   |");
		p.sendMessage(ChatColor.GREEN+ "|               Enjoy!                       |");
		p.sendMessage(ChatColor.GREEN+ " -------------------------------");
		p.setHealth(20);
		p.setFoodLevel(20);
		p.setLevel(0);
		p.setSaturation(20);
		p.getInventory().clear();
		p.setExp(0);
		p.getInventory().setArmorContents(new ItemStack[] { null, null, null, null } );
	}
	
	@EventHandler
	public void onPickupItem(PlayerPickupItemEvent e){
		if(e.getPlayer().getGameMode()==GameMode.ADVENTURE){
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        if (!p.isOp()){
        	if(!e.getMessage().startsWith("/v"))
        		e.setCancelled(true);
        }
    }
	
	@EventHandler
    public void onServerListPing(ServerListPingEvent e) {
		//e.setMotd("PMC883abaa2b2904a98d96a36eeb721575b");
        e.setMotd(ChatColor.DARK_AQUA+"[ASS]: "+ChatColor.GREEN+plugin.phase+" "+ChatColor.DARK_AQUA+plugin.time+ " Remains");
    }
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuit(PlayerQuitEvent e){
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
		{
			public void run()
			{
				int f=0;
				if(plugin.start){
					for(Player t:plugin.getServer().getOnlinePlayers()){
						if(t.getGameMode()==GameMode.SURVIVAL){
							f++;
						}
					}
					if(f<2){
						Bukkit.getServer().broadcastMessage("Quitting");
						for(Player t:plugin.getServer().getOnlinePlayers()){
							t.kickPlayer(ChatColor.DARK_AQUA+ "Not Enough Players, Server Is Restarting.");
						}
						Bukkit.shutdown();
					}
				}			
			}
		}, 20L);
	}
	
	@EventHandler
	public void onKill(PlayerDeathEvent e)
	{
		Player r = e.getEntity();
		r.getWorld().strikeLightningEffect(r.getLocation());
		for(Player p : plugin.getServer().getOnlinePlayers()){
			p.hidePlayer(r);
		}
		r.getInventory().clear();
		r.getInventory().setArmorContents(new ItemStack[] { null, null, null, null } );
		r.setHealth(20);
		r.setFireTicks(0);
		r.setSaturation(20);
		r.setGameMode(GameMode.ADVENTURE);
		r.setAllowFlight(true);
		r.setFlying(true);
		int f=0;
		String winner = "";
		for(Player t:plugin.getServer().getOnlinePlayers()){
			if(t.getGameMode()==GameMode.SURVIVAL){
				f++;
				winner = t.getName();
			}
		}
		if(f==1){
			Bukkit.broadcastMessage(ChatColor.GREEN + "[ASS] " + ChatColor.GREEN + winner + " Has Won!!");
			plugin.getServer().getPlayer(winner).sendMessage(ChatColor.GREEN+ "------------------------");
			plugin.getServer().getPlayer(winner).sendMessage(ChatColor.GREEN+ "| Conragulations on Winning! |");
			plugin.getServer().getPlayer(winner).sendMessage(ChatColor.GREEN+ "|      You Are Awesome!      |");
			plugin.getServer().getPlayer(winner).sendMessage(ChatColor.GREEN+ "------------------------");
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
			{
				public void run()
				{
					for(Player t:plugin.getServer().getOnlinePlayers()){
						t.kickPlayer(ChatColor.DARK_AQUA+ "Server Is Restarting.");
					}
					Bukkit.shutdown();
				}
			}, 200L);
		}else if(f<=4){
			new Deathmatch(plugin).DMcountdown();
		}
	}
	
}
