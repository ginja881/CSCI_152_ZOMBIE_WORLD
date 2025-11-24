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
     
     private Section human_settings;
     private Section zombie_settings;

     private int max_creatures;
     private int current_creatures;
     private double human_visitor_rate;
     private double zombie_vistor_rate;

     public final int world_width;
     public final int world_height; 


     private void generateWorld() {
	     // TODO: Implement method that generates zone
     }
     public void introduceVisitor(int x, int y) {
	     // TODO: Implement method meant for spawning zombies or humans based off of visitor rates

	     if (current_creatures == max_creatures)
		     return;	
     }

     private class CreatureFactory {
	     public Human spawnHuman(int x, int y) {
		     Human human = new Human(human_info, int x, int y);
		     return human;
	     }
	     public Zombie spawnZombie() {
		     Zombie zombie = new Zombie(zombie_info, int x, int y);
		     return zombie;
	     }
     }
     public void printWorld() {
         for (int i = 0; i <  this.zone.length; i++) {
		     for (int j = 0; j < this.zone[i].length; j++) {
			 if (zone[i][j] instanceof Human)
			         System.out.print("\u263A");
		         else if (zone[i][j] instanceof Zombie)
			         System.out.print("\u2620");    
		         if (j < (zone.length - 1))
			          System.out.print(",");
		         else
			         System.out.println();	  
		    }
	     }
     }
     public Creature getCreature(int x, int y) {
	     return this.zone[y][x];
     }
     public World(
		     Ini.Section human_info,
		     Ini.Section world_info,
		     Ini.section zombie_info
     ) {
	     this.human_settings = human_info;
	     this.zombie_settings = zombie_info;

	     this.max_creatures = Integer.parseInt(world_info.get("max_creatures"));
	     this.current_creatures = 0;
	     this.world_height = Integer.parseInt(world_info.get("world_height"));
	     this.world_width = Integer.parseInt(world_info.get("world_width"));
	    
	     this.random = new Random();
             this.zone = new Creature[this.world_height][this.world_width];
	     this.humans = new ArrayList<>();
	     this.zombies = new ArrayList<>();
	     

	     generateWorld();
     }       
	 
} 
