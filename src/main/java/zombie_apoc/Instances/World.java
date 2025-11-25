/*
 * Author Name: Joseph Carter
 * Submission Date: 11/12/2025
 * Description: World class representation, for abstraction of probability and world management
 */

package Instances; 
import java.util.ArrayList;
import java.util.Random;

import org.ini4j.Ini;

import Instances.Creature;
import Instances.Zombie;

public class World {
     private Creature[][] zone;
     public ArrayList<Human> humans;
     public ArrayList<Zombie> zombies;


     public Random random;
     
     private Ini.Section human_settings;
     private Ini.Section zombie_settings;

     private int max_creatures;
     private int current_creatures;
     private double human_visitor_rate;
     private double zombie_vistor_rate;

     public final int world_width;
     public final int world_height; 
    
     private CreatureFactory creature_factory = new CreatureFactory();

     private void generateWorld() {
	    // TODO: Implement method that generates zone
		 
     }
     public void introduceVisitor(int x, int y) {
	     // TODO: Implement method meant for spawning zombies or humans based off of visitor rates

	     if (current_creatures == max_creatures)
		     return;	
     }

    class CreatureFactory {
	     public void spawnHuman(int x, int y) {
		    Human human = new Human(human_settings, x, y);
		    zone[y][x] = human;
            current_creatures++;
			humans.add(human);
	     }
	     public void spawnZombie(int x, int y) {
		    Zombie zombie = new Zombie(x, y, zombie_settings);
            zone[y][x] = zombie;
			current_creatures++;
			zombies.add(zombie);
	     }
     }
     public void printWorld() {
         for (int i = 0; i <  this.zone.length; i++) {
		     for (int j = 0; j < this.zone[i].length; j++) {
			 if (this.zone[i][j] instanceof Human)
			         System.out.print("\u263A");
		         else if (this.zone[i][j] instanceof Zombie)
			         System.out.print("\u2620");    
		         if (j < (this.zone.length - 1))
			          System.out.print(",");
		         else
			         System.out.println();	  
		    }
	     }
     }
    public Creature getCreature(int x, int y) {return this.zone[y][x];}
	public int[] get_available_spot(Creature creature) {
		for (int[] neighbor : creature.get_neighbors()) {
			if (this.getCreature(neighbor[0], neighbor[1]) == null)
				return neighbor;
		}
		return null;
	}
	public void spawnCreature(Creature creator) {
        if (this.current_creatures >= this.max_creatures)
			return;
        int[] available_spot = this.get_available_spot(creator);
		if (available_spot == null)
			return;

	
		if (creator instanceof Human)
		    creature_factory.spawnHuman(available_spot[0], available_spot[1]);
        else if (creator instanceof Zombie)
			creature_factory.spawnZombie(available_spot[0], available_spot[1]);
			
	 }
     public World(
		     Ini.Section human_info,
		     Ini.Section world_info,
		     Ini.Section zombie_info
     ) {
	     this.human_settings = human_info;
	     this.zombie_settings = zombie_info;

	     this.max_creatures = Integer.parseInt(world_info.get("max_creatures"));
	     this.current_creatures = 0;
	     this.world_height = Integer.parseInt(world_info.get("world_height"));
	     this.world_width = Integer.parseInt(world_info.get("world_width"));
	    
	     this.random = new Random();
         this.zone = new Creature[this.world_height][this.world_width];
	     this.humans = new ArrayList<Human>();
	     this.zombies = new ArrayList<Zombie>();
	     

	     generateWorld();
     }       
	 
} 
