package zombie_apoc;

// Necessary custom instances defined in Instances directory
import zombie_apoc.Instances.Human;
import zombie_apoc.Instances.World;
import zombie_apoc.Instances.Zombie;
import zombie_apoc.Instances.Creature;
import zombie_apoc.Visualization.VisualizationManager;
// Helpers
import java.util.ArrayList;
import java.util.Random;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import org.ini4j.Wini;

class App {
	private static String configFile = "config.ini";

    private static int X_INDEX = 0;
	private static int Y_INDEX = 1;

	private static int running_days = 0;
    private static Wini ini = null;
	private static World world = null;
	// Given how different zombie and human are, I decided to divide their rolling into separate functions
	public static void handleZombie(Zombie zombie) {
		//TODO: As said above, implement method around zombie class for probabalistic handling
                                                     
        for (int[] neighbor : zombie.get_neighbors()) {
			int neighbor_x = neighbor[X_INDEX];
			int neighbor_y = neighbor[Y_INDEX];

			Creature neighbor_creature = world.getCreature(neighbor_x, neighbor_y);

			if (neighbor_creature == null && zombie.move()) {
				world.changePosition(neighbor_y, neighbor_x, zombie);
				break;
			}
			else if (neighbor_creature instanceof Zombie) {
				
			}
		}
	}
	public static void handleHuman(Human human) {
		//TODO: Just like handleZombie, implement method around human class for probabilistic handling

		for (int[] neighbor : human.get_neighbors()) {
			int neighbor_x = neighbor[X_INDEX];
			int neighbor_y = neighbor[Y_INDEX];

			Creature neighbor_creature = world.getCreature(neighbor_x, neighbor_y);

			if (neighbor_creature == null && human.move()) {
	            world.changePosition(neighbor_y, neighbor_x, human);
                break;		       
			}
			else if (neighbor_creature instanceof Human) {
		        
			}
			else if (neighbor_creature instanceof Zombie) {
                
			}
		}

	}
        
	public static void initialize() {
		try {
            InputStream input = App.class.getClassLoader().getResourceAsStream(configFile);
		    if (input == null) {
				System.out.println("Config file not found");
				System.exit(-1);
			}
		    ini = new Wini(input);
		    world = new World(ini.get("World"), ini.get("Human"), ini.get("Zombie"));
		   running_days = Integer.parseInt(ini.get("Main", "running_days"));
        }
		catch (IOException e) {
			System.err.println("Something went wrong in parsing config! Using default values");

		}
	}
	public static void main (String[] args) {
        initialize();
	    while (running_days > 0) {
		    System.out.print("\033[H\033[2J");
		    System.out.flush();

		    world.printWorld();
	         
		    int current_zombie = 0;
            int current_human = 0;

		    int zombie_count = world.zombies.size();
		    int human_count = world.humans.size();
	        while (current_human < human_count && current_zombie < zombie_count) {
			    if (current_human < human_count)
                    handleHuman(world.humans.get(current_human));
		        if (current_zombie < zombie_count)
			        handleZombie(world.zombies.get(current_zombie));
			     current_zombie = (current_zombie < zombie_count ? current_zombie + 1 : current_zombie);
			     current_human = (current_human < human_count ? current_human + 1 : current_human); 
		    }
			world.introduceVisitor();
            running_days--;
			
	        try {TimeUnit.SECONDS.sleep(10);}
		    catch (InterruptedException e) {e.printStackTrace();}

	    }
	}
}
