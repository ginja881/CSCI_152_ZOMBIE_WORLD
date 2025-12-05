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
     private double human_spawn_rate;
     private double zombie_spawn_rate;
     private double visitor_spawn_rate;
     private double norm_constant;

     private double super_creature_spawn_rate;
     public final int world_width;
     public final int world_height; 
    
     private CreatureManager creature_factory = new CreatureManager();

     private void generateWorld() {
        int max_initial_creatures = (int)Math.sqrt(this.world_width * this.world_height);
	int current_initial_creatures = 0;
        while (current_initial_creatures < max_initial_creatures) {
	         int x = this.random.nextInt(10);
		 int y = this.random.nextInt(10);

		 if (zone[y][x] != null)
			 continue;
                 double chance = this.random.nextDouble() * this.norm_constant;
		 boolean is_super = (this.random.nextDouble() * this.norm_constant) <= this.super_creature_spawn_rate ? true : false;
		 if (chance <= this.zombie_spawn_rate) 
			 creature_factory.spawnZombie(x, y, is_super);
		 else if (chance <= this.human_spawn_rate)
			 creature_factory.spawnHuman(x, y, is_super);
		 current_initial_creatures++;
		 

	} 
     }
     public void introduceVisitor() {
	     // TODO: Implement method meant for spawning zombies or humans based off of visitor rates

	    if (current_creatures == max_creatures)
		    return;	
            if (this.random.nextDouble() * (this.norm_constant) <= this.visitor_spawn_rate)
		    return;

	    int bounds[] = {0, world_width};

	    
	    double chance = this.random.nextDouble() * (this.norm_constant);
            boolean isSuper = (this.random.nextDouble() * (this.norm_constant) <= this.super_creature_spawn_rate ? true : false);
	    int bound_x = this.random.nextInt(2);
	    int bound_y = this.random.nextInt(2);
	    if (zone[bound_y][bound_x] != null) {
		    return;
	    }
	    else {
		    if (chance <= this.human_spawn_rate) 
			    creature_factory.spawnHuman(bound_x, bound_y, isSuper);
		    else if (chance <= this.zombie_spawn_rate)
                            creature_factory.spawnZombie(bound_x, bound_y, isSuper);
	    }

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
		 public <T extends Creature> void change_position(int new_x, int new_y, T creature) {
			
			int[] old_xy = creature.get_xy();
			int old_x = old_xy[0];
			int old_y = old_xy[1];
			zone[old_y][old_x] = null;
                        creature.update_xy(new_x, new_y);
			zone[new_y][new_x] = creature;
			
                       
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
     public ArrayList<int[]> get_available_spots(Creature creature) {
		ArrayList<int[]> empty_spots = new ArrayList<>();
	        for (int[] neighbor : creature.get_neighbors()) {
			if (this.zone[neighbor[1]][neighbor[0]] == null)
				empty_spots.add(neighbor);
		}
		return empty_spots;
     }
     public void spawnCreature(Creature creator) {
                if (this.current_creatures >= this.max_creatures)
			return;
                int[] available_spot = this.get_available_spot(creator);
	        boolean isSuper = (this.random.nextDouble() * (this.norm_constant) <= this.super_creature_spawn_rate ? true : false);
		if (creator instanceof Human)
		    creature_factory.spawnHuman(available_spot[0], available_spot[1], isSuper);
                else if (creator instanceof Zombie)
			creature_factory.spawnZombie(available_spot[0], available_spot[1], isSuper);
     }
     public <T extends Creature> void changePosition(int x, int y, T creature) {
	     if (this.zone[y][x] != null)
		     return;
	     creature_factory.change_position(x, y, creature);
     }
     public Creature getCreature(int x, int y) {return zone[y][x];};
     public World(
		     Ini.Section world_info,
		     Ini.Section human_info,
		     Ini.Section zombie_info
     ) {
	     
	     
             
	     this.human_spawn_rate = Double.parseDouble(world_info.get("human_spawn_rate"));
	     this.zombie_spawn_rate = Double.parseDouble(world_info.get("zombie_spawn_rate"));
	     this.visitor_rate = Double.parseDouble(world_info.get("visitor_rate"));
	     this.max_creatures = Integer.parseInt(world_info.get("max_creatures"));
	     this.current_creatures = 0;
	     this.world_height = Integer.parseInt(world_info.get("world_height"));
	     this.world_width = Integer.parseInt(world_info.get("world_width"));
	     this.norm_constant = Double.parseDouble(world_info.get("world_norm_constant"));
             
             human_info.put("world_height", this.world_height);
	     human_info.put("world_width", this.world_width);
             this.human_settings = human_info;

	     zombie_info.put("world_height", this.world_height);
	     zombie_info.put("world_width", this.world_width);
             this.zombie_settings = zombie_info;
             
	     this.random = new Random();
             this.zone = new Creature[this.world_height][this.world_width];
	     this.humans = new ArrayList<Human>();
	     this.zombies = new ArrayList<Zombie>();
	     this.super_creature_spawn_rate = Double.parseDouble(world_info.get("super_creature_spawn_rate"));

             
	     generateWorld();
     }       
	 
} 
