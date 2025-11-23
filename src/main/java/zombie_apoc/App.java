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
import java.utill.concurrent.TimeUnit;

class App {
	private static String configFile = "../resource/config.ini";

	private int running_days = 0;
        private static Wini ini = null;
	private static World world = null;
	// Given how different zombie and human are, I decided to divide their rolling into separate functions
	public static void handleZombie(Zombie zombie) {
		//TODO: As said above, implement method around zombie class for probabalistic handling
	}
	public static void handleHuman(Human human) {
		//TODO: Just like handleZombie, implement method around human class for probabilistic handling
	}
        
	public static void initialize() {
		try {
		    ini = new Wini(new File(configFile));
		    
		        
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
	         for (int i = 0; i < world.height; i++) {
		    for (int j = 0; j < world.width; j++) {
			    Creature currentCreature = world.getCreature(j, i);
			    if (currentCreature == null)
				    continue;
			    else if (currentCreature instanceof Human)
				    handleHuman(currentCreature);
			    else if (currentCreature instanceof Zombie)
				    handleZombie(currentCreature);
		    }
	        }
                
	        try {TimeUnit.SECONDS.sleep(1);}
		catch (InterruptedException e) {e.printStackTrace();}

	    }
	}
}
