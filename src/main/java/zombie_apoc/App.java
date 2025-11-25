package zombie_apoc;

// Necessary custom instances defined in Instances directory
import Instances.Human;
import Instances.World;
import Instances.Zombie;

// Helpers
import java.util.ArrayList;
import java.util.Random;

import java.io.IOException;
import java.io.File;
import java.util.concurrent.TimeUnit;

import org.ini4j.Wini;

class App {
	private static String configFile = "../resource/config.ini";

	private static int running_days = 0;
    private static Wini ini = null;
	private static World world = null;
	// Given how different zombie and human are, I decided to divide their rolling into separate functions
	public static void handleZombie(Zombie zombie) {
		//TODO: As said above, implement method around zombie class for probabalistic handling

		zombie.update_ticker();

	}
	public static void handleHuman(Human human) {
		//TODO: Just like handleZombie, implement method around human class for probabilistic handling
	}
        
	public static void initialize() {
		try {
		    ini = new Wini(new File(configFile));
		    world = new World(ini.get("world"), ini.get("human"), ini.get("zombie"));
			running_days = Integer.parseInt(ini.get("main", "running_days"));
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
	        while (true) {
			    if (current_human < human_count)
                    handleHuman(world.humans.get(current_human));
		        if (current_zombie < zombie_count)
			         handleZombie(world.humans.get(current_zombie));
                                                                     
			     if (current_zombie == zombie_count && current_human = human_count)
				     break;
			     current_zombie = (current_zombie < zombie_count ? current_zombie + 1 : current_zombie);
			     current_human = (current_human < human_count ? current_human + 1 : current_human); 
		    }
                
	        try {TimeUnit.SECONDS.sleep(1);}
		    catch (InterruptedException e) {e.printStackTrace();}

	    }
	}
}
