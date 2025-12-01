/*
 * Author Name: Joseph Carter
 * Submission Date: 11/12/2025
 * Description: World class representation, for abstraction of probability and world management
 */

package zombie_apoc.Instances; 
import java.util.ArrayList;
import java.util.Random;

import org.ini4j.Ini;

import zombie_apoc.Instances.Creature;
import zombie_apoc.Instances.Zombie;

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

	 private double super_creature_spawn_rate;
     public final int world_width;
     public final int world_height; 
    
     private CreatureManager creature_factory = new CreatureManager();

     private void generateWorld() {
        int max_initial_creatures = (int)Math.sqrt(this.world_width * this.world_height);
		int current_initial_humans = 0;
		int current_initial_zombies = 0;

	    for (int i = 0; i < world_height; i++) {
			for (int j = 0; j < world_width; j++) {
				double chance = this.random.nextDouble() * (75);
                boolean isSuper = (this.random.nextDouble() <= this.super_creature_spawn_rate ? true : false);
				if (chance <= 50.0 && current_initial_humans <= max_initial_creatures)  {
					
					creature_factory.spawnHuman(j, i, isSuper);
					current_initial_humans++;
				}
				else if (current_initial_zombies <= max_initial_creatures) {
					creature_factory.spawnZombie(j, i, isSuper);
					current_initial_zombies++;
				}
			}
		}		 
     }
     public void introduceVisitor(int x, int y) {
	     // TODO: Implement method meant for spawning zombies or humans based off of visitor rates

	    if (current_creatures == max_creatures)
		    return;	
		
		
     }

    class CreatureManager {
	     public void spawnHuman(int x, int y, boolean isSuper) {
	        if (zone[y][x] != null)
				return;		
	
		    Human human = new Human(human_settings, x, y, isSuper);
		    zone[y][x] = human;
            current_creatures++;
			humans.add(human);
	     }
	     public void spawnZombie(int x, int y, boolean isSuper) {
			 if (zone[y][x] != null)
				return;		
	
		    Zombie zombie = new Zombie(x, y, zombie_settings, isSuper);
            zone[y][x] = zombie;
			current_creatures++;
			zombies.add(zombie);
	     }
		 public void change_position(int x, int y) {
            
		 }
		 public void remove_creature(int y, int x) {
			zone[y][x] = null;
		 }
     }
     public void printWorld() {
         for (int i = 0; i <  this.zone.length; i++) {
		     for (int j = 0; j < this.zone[i].length; j++) {
			 if (this.zone[i][j] instanceof Human)
			         System.out.print("H");
		         else if (this.zone[i][j] instanceof Zombie)
			         System.out.print("Z");    
		         if (j < (this.zone.length - 1))
			          System.out.print(",");
		         else
			         System.out.println();	  
		    }
	     }
     }
	public int[] get_available_spot(Creature creature) {
		for (int[] neighbor : creature.get_neighbors()) {
			if (this.zone[neighbor[1]][neighbor[0]] == null)
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

	    boolean isSuper = (this.random.nextDouble() <= this.super_creature_spawn_rate ? true : false);
		if (creator instanceof Human) {
			
		    creature_factory.spawnHuman(available_spot[0], available_spot[1], isSuper);
		}
        else if (creator instanceof Zombie)
			creature_factory.spawnZombie(available_spot[0], available_spot[1], isSuper);
			
	 }
     public World(
		     Ini.Section world_info,
		     Ini.Section human_info,
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
	     this.super_creature_spawn_rate = Double.parseDouble(world_info.get("super_creature_spawn_rate"));


	     generateWorld();
     }       
	 
} 
