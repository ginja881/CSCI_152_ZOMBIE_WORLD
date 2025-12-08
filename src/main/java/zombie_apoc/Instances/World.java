/*
 * Author Names: Joseph Carter, Bennett, Gabe, and Aaron Raycove
 * Submission Date: 12/08/2025
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


     public Random random;
     
	 // Config settings pased down to objects
     private Ini.Section human_settings;
     private Ini.Section zombie_settings;
     
	 // Running days
     private int current_day;
	 // Max and current creatures
     private int max_creatures;
     private int current_creatures;
     
	 // Useful stat
	 private int visitor_count;
	 // Rates
     private double human_spawn_rate;
     private double zombie_spawn_rate;
     private double visitor_rate;
     private double norm_constant;
     private double super_creature_spawn_rate;
     
	 // Immutable world height and width
     public final int world_width;
     public final int world_height; 
     
	 // Immutable references to all humans and zombies
	 public final ArrayList<Human> humans;
	 public final ArrayList<Zombie> zombies;

	 // Object of friend class used to make creatures
     private CreatureManager creature_factory = new CreatureManager();
     
     private void generateWorld() {
		// Deciding how many creatures to place
        int max_attempts = this.world_width;
		int current_attempt = 0;
        while (current_attempt < max_attempts) {
			current_attempt++;
			// XY indices 
	        int x = this.random.nextInt(this.world_width);
		    int y = this.random.nextInt(this.world_height);
            

			// Skips if there is already a creature 
		    if (this.zone[y][x] != null)
			    continue;
			// Super creature spawn check
		    boolean is_super = (this.random.nextDouble() * this.norm_constant) <= this.super_creature_spawn_rate ? true : false;

			// Spawn zombie
		    if (this.random.nextDouble() * this.norm_constant <= this.zombie_spawn_rate) {
			    creature_factory.spawnZombie(x, y, is_super);
			}
			// Spawn human
		    else if (this.random.nextDouble() * this.norm_constant <= this.human_spawn_rate) {
			    creature_factory.spawnHuman(x, y, is_super);
			}
	    } 
     }
     public void introduceVisitor() {
	    // TODO: Implement method meant for spawning zombies or humans based off of visitor rates
        
		// Don't introduce visitor, so board doesn't overflow
	    if (current_creatures == max_creatures)
		    return;	
        
		// Rate
        if (this.random.nextDouble() * (this.norm_constant) > this.visitor_rate)
		    return;
        
		// Bounds for xy
	    int bounds_x[] = {0, world_width-1};
        int bounds_y[] = {0, world_height-1};
	    
		// Spawn chance 
	    double spawn_chance = this.random.nextDouble() * (this.norm_constant);
        boolean isSuper = (this.random.nextDouble() * (this.norm_constant) <= this.super_creature_spawn_rate ? true : false);

		// Random choice for x and y
	    int random_x = bounds_x[this.random.nextInt(2)];
	    int random_y = bounds_y[this.random.nextInt(2)];
		int chosen = 0; // used for top-to-bottom check as a random variable either mapped to this.random.nextInt() with world height or width as bound
        Creature chosen_cell = null;
		// Visitor should be on the top or bottom if true, but visitor should be on the rightmost or leftmost side of grid if false
        boolean top_to_bottom = this.random.nextInt(2) == 1 ? true : false;
		
		if (top_to_bottom) {
			// Chooses a random X coordinate when visitor would appear on top or bottom
		   chosen = this.random.nextInt(this.world_width); 

		   // Final location
		   chosen_cell = zone[random_y][chosen];
		   // Checking if its empty
		   if (chosen_cell != null) 
		        return;
	       else {
			    visitor_count++;
			    // spawn chance used for deciding which visitors spawn which
		        if (spawn_chance <= this.human_spawn_rate) 
			        creature_factory.spawnHuman(chosen, random_y, isSuper);
		        else if (spawn_chance <= this.zombie_spawn_rate)
                    creature_factory.spawnZombie(chosen, random_y, isSuper);
	       }
		}
		else {
			// Chooses a random Y coordinate when visitors would appear on the left or right
			chosen = this.random.nextInt(this.world_height);
			// Final location
			chosen_cell = zone[chosen][random_x];
			// Checking if empty
			if (chosen_cell != null) 
		        return;
	        else {
				// spawn chance used for deciding which visitors spawn which
				visitor_count++;
		        if (spawn_chance <= this.human_spawn_rate) 
			        creature_factory.spawnHuman(random_x, chosen, isSuper);
		        else if (spawn_chance <= this.zombie_spawn_rate)
                    creature_factory.spawnZombie(random_x, chosen, isSuper);
	        }

		}
		// Checks for spawning creature 

     }
    // friend class for managing creatures
    private class CreatureManager {
		    // spawning humans
	        public void spawnHuman(int x, int y, boolean isSuper) {
				// To prevent overwitting problems
	            if (zone[y][x] != null)
				     return;		
	            // Allocating new human object
		        Human human = new Human(human_settings, x, y, isSuper, current_day);
		        zone[y][x] = human;

		        // Incrementing current creatures
                current_creatures++;
			
				// Updating current_days for the sake of it to prevent visitors from having extra turns 

				humans.add(human);
		        
	        }
	        public void spawnZombie(int x, int y, boolean isSuper) {
				// To prevent overwitting problems
			    if (zone[y][x] != null)
				    return;		
	            
				// Allocating new zombie object
		        Zombie zombie = new Zombie(x, y, zombie_settings, isSuper, current_day);
                zone[y][x] = zombie;
				// Incrementing current creatures
		        current_creatures++;
				// Updating current_days for the sake of it to prevent visitors from having extra turns
		        zombies.add(zombie);
	        }
		 public <T extends Creature> void change_position(int new_x, int new_y, T creature) {
			// Clearing out old cell
			int old_x = creature.get_x();
			int old_y = creature.get_y();
			zone[old_y][old_x] = null;
			// Updating new cell with creature
            creature.update_xy(new_x, new_y);
			zone[new_y][new_x] = creature;
			
                       
		 }
		 public <T extends Creature> void remove_creature(T creature) {
			// Wiping out cell
			int creature_x = creature.get_x();
			int creature_y = creature.get_y();
			zone[creature_y][creature_x] = null;
			if (creature instanceof Human)
				humans.remove(creature);
			else if (creature instanceof Zombie)
				zombies.remove(creature);

			current_creatures--;
		 }
     }
     public void printWorld() {
		// Printing world
        for (int y = 0; y <  this.zone.length; y++) {
		    for (int x = 0; x < this.zone[y].length; x++) {
				// Conditional chain for printing humans and zombies
			    if (this.zone[y][x] instanceof Human) {
			         String cell = this.zone[y][x].isSuper() ? "|SH|" :  "| H|";
					 System.out.print(cell);
				}
		        else if (this.zone[y][x] instanceof Zombie) {
					String cell = this.zone[y][x].isSuper() ? "|SZ|" :  "| Z|";
					System.out.print(cell);
				}
				else if (this.zone[y][x] == null)
					System.out.print("|  |");
		        if (x == (this.zone[y].length - 1))
			        System.out.println();	  
		    }
	     }
     }
     public ArrayList<int[]> get_available_spots(Creature creature) {
		// Potential helper for finding empty neighbors
		ArrayList<int[]> empty_spots = new ArrayList<>();
	    for (int[] neighbor : creature.get_neighbors()) {
			if (this.zone[neighbor[1]][neighbor[0]] == null)
				empty_spots.add(neighbor);
		}
		return empty_spots;
     }
	 // Merely a wrapper function
     public void spawnCreature(int x, int y, boolean isZombie) {
		// Conditional to see if current creatures is above max creatures
        if (this.current_creatures >= this.max_creatures)
		     return;
        
		// Chances that creature is super
	    boolean isSuper = (this.random.nextDouble() * (this.norm_constant) <= this.super_creature_spawn_rate ? true : false);
	
        // Checker to see if a zombie or human should spawn
		if (!isZombie)
		    creature_factory.spawnHuman(x, y, isSuper);
        else
			creature_factory.spawnZombie(x, y, isSuper);
     }
	 // Generic wrapper for changing creature position
     public <T extends Creature> void changePosition(int x, int y, T creature) {
	     if (this.zone[y][x] != null)
		     return;
	     creature_factory.change_position(x, y, creature);
     }
	 // Wrapper for removing creatures at (x,y)
	 public <T extends Creature> void remove_creature(T creature) {
		creature_factory.remove_creature(creature);
	 }
	 // Getting creature at (x,y)
     public Creature getCreature(int x, int y) {return zone[y][x];};
	 // Updating current day
	 public void update_current_day(int actual_current_day) {
		this.current_day = actual_current_day;
	 }
	 public int get_visitor_count() {return this.visitor_count;}
     
	 public int get_current_creatures() {return this.current_creatures;}
	 // Main constructor
     public World(
		     Ini.Section world_info,
		     Ini.Section human_info,
		     Ini.Section zombie_info
     ) {
		 // Running day
         this.current_day = 0;
		 
		 // Rates and important attributes for world
	     this.human_spawn_rate = Double.parseDouble(world_info.get("human_spawn_rate"));
	     this.zombie_spawn_rate = Double.parseDouble(world_info.get("zombie_spawn_rate"));
	     this.visitor_rate = Double.parseDouble(world_info.get("visitor_rate"));
	     this.max_creatures = Integer.parseInt(world_info.get("max_creatures"));
	     this.current_creatures = 0;
	     this.world_height = Integer.parseInt(world_info.get("world_height"));
	     this.world_width = Integer.parseInt(world_info.get("world_width"));
	     this.norm_constant = Double.parseDouble(world_info.get("world_norm_constant"));
         		 
	     this.super_creature_spawn_rate = Double.parseDouble(world_info.get("super_creature_spawn_rate"));

		 // For neighbor calculation
         human_info.put("world_height", this.world_height);
	     human_info.put("world_width", this.world_width);
         this.human_settings = human_info;

	     zombie_info.put("world_height", this.world_height);
	     zombie_info.put("world_width", this.world_width);
         this.zombie_settings = zombie_info;
             
	     this.random = new Random(); // Used for events like spawning visitors and initial creatures
         this.zone = new Creature[this.world_height][this.world_width];
          
		 // Humans and zombies
		 this.humans = new ArrayList<>();
		 this.zombies = new ArrayList<>();

         //visitor_stat
		 this.visitor_count = 0;

	     generateWorld();
     }       
	 
} 
