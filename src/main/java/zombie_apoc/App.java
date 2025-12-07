package zombie_apoc;

// Necessary custom instances defined in Instances directory
import zombie_apoc.Instances.Human;
import zombie_apoc.Instances.World;
import zombie_apoc.Instances.Zombie;
import zombie_apoc.Instances.Creature;

// TODO: Implement visualization layer


// Helpers
import java.util.ArrayList; // Useful data structure
import java.util.Random; // Maybe? 
import java.util.concurrent.TimeUnit; // Time macros used for frame delay/proper animations


import java.io.IOException; // IO exception, may occur when parsing issues for ini config files happen
import java.io.InputStream; // Useful for reading ini file

// Parsing object for ini config files
import org.ini4j.Wini;

class App {
	// Config file name (Located at ../resources/config.ini)
	private static String configFile = "config.ini";
    

	// Useful variables for indexing neighbors/coordinates as arrays
    private static int X_INDEX = 0;
	private static int Y_INDEX = 1;
    
	// Important variables
	private static int running_days = 0; // total iterations of the main loop
    private static Wini ini = null; // object used for parsing config file
	private static World world = null; // world singleton 
	private static Random rng = new Random();
 
	// Given how different zombie and human are, I decided to divide their rolling into separate functions
	public static void handleZombie(Zombie current_zombie) {
		
		// Current zombie coordinates + key used for turns hashmap when done iterating through all neighbors
        int current_zombie_x = current_zombie.get_x();
		int current_zombie_y = current_zombie.get_y();	    
        
		current_zombie.update_ticker();
        // Kill zombie if ticker is less than or equal to 0 or continue by updating current day
		if (current_zombie.get_ticker() <= 0)
			world.remove_creature(current_zombie_x, current_zombie_y);
		else
			current_zombie.update_current_day();
		// Handling loop, occurrences are based off neighbors
        for (int[] neighbor : current_zombie.get_neighbors()) {
			// Neighbor coordinates {x_coordinate, y_coordinate}
			int neighbor_x = neighbor[X_INDEX];
			int neighbor_y = neighbor[Y_INDEX];
            
			// Creature object
			Creature neighbor_creature = world.getCreature(neighbor_x, neighbor_y);
            
			// Code for move occurrence
			if (neighbor_creature == null && current_zombie.move()) {
				// Updating position and current_zombie coordinates
				world.changePosition(neighbor_y, neighbor_x, current_zombie);
				break; // turn is over
			}
			// Code for battle occurrence
			else if (neighbor_creature instanceof Human) {
				// enemy
				Human enemy_human = (Human)neighbor_creature;
				// Check to see if they are both engaged and ready to fight
				if (!(enemy_human.engage_battle() && current_zombie.engage_battle()))
                   continue;
				// Boolean variables used in result of fight
				boolean enemy_win = enemy_human.battle();
				boolean zombie_win = current_zombie.battle();
                
                enemy_human.update_current_day();
				// Results
				if (zombie_win && !enemy_win) {
					// If zombie won, figure out if the enemy human gets infected or just dies
					boolean infected = current_zombie.infect();
					world.remove_creature(neighbor_x, neighbor_y);
					if (infected)
						world.spawnCreature(neighbor_x, neighbor_y, true);
					break; // turn over
				}
				else if (enemy_win && !zombie_win) {
					// If the enemy won, but zombie did not. The zombie would just die
					world.remove_creature(current_zombie_x, current_zombie_y);
					return;
				}
				else 
					continue;

				
			}
		}
     
	
		
	}
	public static void handleHuman(Human current_human) {
        // Child object if current_human ever reproduces
		// Coordinates of current human
		int current_human_x = current_human.get_x();
		int current_human_y = current_human.get_y();
        
        current_human.update_current_day();

		// Event loop by checking neighbors
		for (int[] neighbor : current_human.get_neighbors()) {
			// current neighbor x and y
			int neighbor_x = neighbor[X_INDEX];
			int neighbor_y = neighbor[Y_INDEX];
            
			// Getting neighbor
			Creature neighbor_creature = world.getCreature(neighbor_x, neighbor_y);
            
			// Move
			if (neighbor_creature == null && current_human.move()) {
				// Change position
				
	            world.changePosition(neighbor_y, neighbor_x, current_human);
                break;		       
			}
			else if (neighbor_creature instanceof Human && current_human.reproduce((Human)neighbor_creature)) {
				// Reproduce

				// Empty spots for spawning child
                ArrayList<int[]> partner_spots = world.get_available_spots(neighbor_creature);
		        ArrayList<int[]> current_human_spots = world.get_available_spots(neighbor_creature);

				// random variable for deciding empty spot
				int random_index = 0;
                
				// Updating current day of neighbor
                neighbor_creature.update_current_day();
				// Checking if partner_spots can be used for spawning child
				if (!partner_spots.isEmpty())  {
					// Selecting random spot and spawning child
					random_index = rng.nextInt(partner_spots.size());
					int[] coordinate = partner_spots.get(random_index);
					world.spawnCreature(coordinate[X_INDEX], coordinate[Y_INDEX], false);
					break;
				}
				// Checking if current_human_spots can be used for spawning child if partner_spots can't
				else if (!current_human_spots.isEmpty()) {
					// Selecting random spot and spawning child
					random_index = rng.nextInt(current_human_spots.size());
					int[] coordinate = partner_spots.get(random_index);
                    world.spawnCreature(coordinate[X_INDEX], coordinate[Y_INDEX], false);
					break;
				}
				// If all fails, then there is no valid space for a child
                else
					continue;
			}
			// Battling
			else if (neighbor_creature instanceof Zombie) { 
				// Enemy zombie
				Zombie enemy_zombie = (Zombie)neighbor_creature;

				// Do both want to fight?
				if (!(enemy_zombie.engage_battle() && current_human.engage_battle()))
                   continue;
				// Fight! Variables store results
                boolean human_win = current_human.battle(); 
                boolean zombie_win = enemy_zombie.battle();
                
				// Did the human lose and the zombie win? See if human either just dies or becomes zombie
				if (!human_win && zombie_win) {
					// Infection occurrence
					boolean infected = enemy_zombie.infect();
					// Did no infection occur
					if (!infected) {
						// Remove the creature
					    world.remove_creature(current_human_x, current_human_y);
						return;
					}
					else {
						// Remove and spawn zombie in place of human
						world.remove_creature(current_human_x, current_human_y);
						world.spawnCreature(current_human_x, current_human_y, true);
					}
				}
				// Did human win and zombie lose? If so, then remove zombie
				else if (human_win && !zombie_win)
					world.remove_creature(enemy_zombie.get_x(), enemy_zombie.get_y());
			    // Tie? Nothing happens
				else 
					continue;
			}
		}
		// Update current day for current_human
 
	}
    // Initialization stage
	public static void initialize() {
		try {
			// Input stream for ini file
            InputStream input = App.class.getClassLoader().getResourceAsStream(configFile);
		    if (input == null) {
				System.out.println("Config file not found");
				System.exit(-1);
			}
			// Parse ini file
		    ini = new Wini(input);
			running_days = Integer.parseInt(ini.get("Main", "running_days"));
		    world = new World(ini.get("World"), ini.get("Human"), ini.get("Zombie"), running_days);
		
        }
		catch (IOException e) {
			System.err.println("Something went wrong in parsing config! Using default values");

		}
	}
	public static void main (String[] args) {
		// Begin initialization stage
        initialize();
		// Current day
		int current_day = 0;
		// Main loop
	    
	    while (current_day < running_days) {
	        // Iterate and process any cell at (x,y)
			
			// Cleaning terminal
            System.out.print("\033[H\033[2J");
		    System.out.flush();
                    
			// Print world
		    world.printWorld();
            world.update_current_day();
			for (int i = 0; i < world.humans.size(); i++) {
		        Human current_human = world.humans.get(i);
	    		if (current_human.get_current_day() != current_day)
					continue;
				else {
			        handleHuman(current_human);
				}
				world.introduceVisitor();
			}
			for (int j = 0; j < world.zombies.size(); j++) {
				Zombie current_zombie = world.zombies.get(j);
                if (current_zombie.get_current_day() != current_day)
					continue;
				else {

					handleZombie(current_zombie);
					
				}
				world.introduceVisitor();
			}
            // Update current day and in world
            current_day++;
		
			try {TimeUnit.SECONDS.sleep(4);}
		    catch (InterruptedException e) {e.printStackTrace();}
	    }
	}
}
