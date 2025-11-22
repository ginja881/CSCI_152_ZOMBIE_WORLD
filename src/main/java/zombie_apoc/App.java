package zombie_apoc;
import org.ini4j.Ini;
import Instances.Human;
import Instances.World;
import Instances.Zombie;


import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.io.InputStream;

class Main {
	// Given how different zombie and human are, I decided to divide their rolling into separate functions
	public static void handleZombie(Zombie zombie) {
		//TODO: As said above, implement method around zombie class for probabalistic handling
	}
	public static void handleHuman(Human human) {
		//TODO: Just like handleZombie, implement method around human class for probabilistic handling
	}

	public static void main (String[] args) {
        World world =  new World(
			    5.0,
			    10,
			    60.0,
			    30.0,
			    70.0,
			    70.0,
			    40.0
	    );
        int days = Integer.parseInt(args[0]);    
        if (days == 0)
		    return;
	    while (days >= 1) {
		    for (int i =0; i < world.zone.length; i++) {
			    for (int j =0; j < world.zone[i].length; j++) {
				    if (world.zone[i][j] == null)
                        continue;
				    if (world.zone[i][j] instanceof Human)
					  handleHuman((Human)world.zone[i][j]);
				    else if (world.zone[i][j] instanceof Zombie)
					  handleZombie((Zombie)world.zone[i][j]);
			    } 
		    }
		    days--;
	    }
	}
}
