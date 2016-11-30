package me.Jan.first;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Vote {
	private int[] A = {0,0,0,0};
	private int numMaps = 4;
	private Player[] players = new Player[24];
	private int pIndex = 0;
	
	public Vote(){
		
	}
	
	public void announceVote(){
		Bukkit.broadcastMessage(ChatColor.DARK_AQUA+ "-----------------------------" );
		Bukkit.broadcastMessage(ChatColor.DARK_AQUA+ "Please Vote for a Map with /v");
		Bukkit.broadcastMessage(ChatColor.DARK_AQUA+ "  1: "+A[0]+" votes: Breeze Island");
		Bukkit.broadcastMessage(ChatColor.DARK_AQUA+ "  2: "+A[1]+" votes: Holiday Resort");
		Bukkit.broadcastMessage(ChatColor.DARK_AQUA+ "  3: "+A[2]+" votes: Survival Kingdom");
		Bukkit.broadcastMessage(ChatColor.DARK_AQUA+ "  4: "+A[3]+" votes: Treacherous Heights");
		Bukkit.broadcastMessage(ChatColor.DARK_AQUA+ "-----------------------------" );

	}
	
	public void resetVotes(){
		A = new int[] {0,0,0,0};
		players = new Player[24];
		pIndex = 0;
	}
	
	public void voted(Player p){
		players[pIndex++] = p;
	}
	
	public boolean addVote(int i, Player p){
		if(i<0 || i>numMaps-1) return false;
		if(!Arrays.asList(players).contains(p)){
			A[i]++;
			return true;
		}else
			return false;
	}
	
	public String getMap(){
		int y = 0;
		for(int x = 1; x<numMaps; x++){
			if(A[x]>A[y]){
				y = x;
			}
		}
		return map(y);
	}
	
	public String map(int i){
		String r = "";
		switch (i){
		case 0:
			return "Breeze_Island";
		case 1:
			return "Holiday_Resort";	
		case 2:
			return "Survival_Kingdom";	
		case 3:
			return "Treacherous_Heights";		
		}
		return r;	
	}
}
