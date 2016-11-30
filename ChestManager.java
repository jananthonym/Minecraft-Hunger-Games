package me.Jan.first;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

public class ChestManager {
	private int[] first = {
			280, 2,
			261, 1,
			262, 5,
			268, 1,
			271, 1,
			297, 3,
			319, 2,
			357, 3,
			332, 3,
			371, 2,
			391, 2,
			298, 1,
			299, 1,
			300, 1,
			301, 1,
			322, 1,
			349, 4,
			346, 1
	};
	
	private int[] second = {
			261, 1,
			262,10,
			266, 1,
			272, 1,
			275, 1,
			280, 1,
			282, 1,
			283, 1,
			287, 1,
			297, 5,
			302, 1,
			303, 1,
			304, 1,
			305, 1,
			319, 3,
			320, 1,
			363, 1,
			396, 1,
			346, 1,
			350, 1
	};
	
	private int[] third = {
			261, 1,
			262, 20,
			264, 1,
			265, 1,
			267, 1,
			275, 1,
			280, 1,
			282, 2,
			306, 1,
			307, 1,
			308, 1,
			309, 1,
			319, 4,
			320, 2,
			332, 3,
			346, 1,
			349, 3
	};
	
	private int[] fourth = {
			258, 1,
			265, 2,
			264, 1,
			266, 1,
			280, 1,
			282, 4,
			310, 1,
			313, 1,
			320, 2,
			322, 1,
			332, 5,
			346, 1,
			366, 2,
			354, 1
	};
	
	private int[] fifth = {
			264, 1,
			311, 1,
			312, 1,
			262, 20,
			261, 1,
			368, 2

	};
	
	private ArrayList<Location> chestList = new ArrayList<Location>(0);
	
	public ChestManager(){
		
	}
	
	public void resetChests(){
		chestList.clear();
	}
	
	private ArrayList<ItemStack> createStuff(int[] X){
		ArrayList<ItemStack> x = new ArrayList<ItemStack>(0);
		for(int q = 0; q <X.length; q=q+2) {
			if(1 + (int)(Math.random() * (3))==1)
				x.add(new ItemStack(Material.getMaterial(X[q]), X[q+1]));
		}
		return x;
	}
	
	private ArrayList<ItemStack> stuff(){
		if(1 + (int)(Math.random() * (2))==1){
			return createStuff(first);
		}else if(1 + (int)(Math.random() * (2))==1){
			return createStuff(second);
		}else if(1 + (int)(Math.random() * (2))==1){
			return createStuff(third);
		}else if(1 + (int)(Math.random() * (2))==1){
			return createStuff(fourth);
		}else if(1 + (int)(Math.random() * (2))==1){
			return createStuff(fifth);
		}else
			return createStuff(first);
	}
	
	
	public void fillChest(Chest c){
		if(!chestList.contains(c.getLocation())){
			for(ItemStack i:stuff()){
				if(c instanceof Chest){
					int q = (int)(Math.random() * (27));
					while(c.getInventory().getItem(q)!=null){
						q = (int)(Math.random() * (27));
					}
					c.getInventory().setItem(q, i);
				}else{
					int q = (int)(Math.random() * (54));
					while(c.getInventory().getItem(q)!=null){
						q = (int)(Math.random() * (54));
					}
					c.getInventory().setItem(q, i);
				}
			}
			chestList.add(c.getLocation());
		}
	}
		
}
