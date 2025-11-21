/*
 * Author Name: Joseph Carter
 * Submission Date: 11/12/2025
 * Description: World class representation, for abstraction of probability and world management
 */

package ZombieApoc; 
import java.util.ArrayList;
import java.util.Random;
import ZombieApoc.Creature;
import ZombieApoc.Zombie;



public class World {
     public Creature[][] zone;
     
     public Random random;
     
     private double visitor_rate;
     private double creature_move_rate;
     private double creature_death_rate;
     private double zombie_infect_rate;
     private double human_reproduce_rate;


     private int max_creatures;
     private int current_creatures;
     private void generateWorld() {
	     // TODO: Implement method that generates zone
     }
     public void introduceVisitor(int x, int y) {
	     // TODO: Implement method meant for spawning zombies or humans based off of visitor_rate
	
     }
     public void printWorld() {
         for (int i = 0; i <  this.zone.length; i++) {
		     for (int j = 0; j < this.zone[i].length; j++) {
			     if (zone[i][j] instanceof Human)
			         System.out.print("H");
		         else if (zone[i][j] instanceof Zombie)
			         System.out.print("Z");
		         else if (zone[i][j] == null)
			         System.out.print("#");    
		        if (j < (zone.length - 1))
			          System.out.print("|");
		         else
			         System.out.println();	  
		    }
	     }
     }
     public void removeCreature(Creature creature) {
	     // TODO: Implement method that would be called within creature classes for removal
		 return;
     }
     public World(
		     double visitor_rate,
		     int max_creatures, 
		     double creature_move_rate,
		     double creature_death_rate,
		     double zombie_infect_rate,
		     double human_reproduce_rate,
			 double creature_battle_rate
     ) {
	     this.max_creatures = max_creatures;
	     this.current_creatures = 0;
	     this.visitor_rate = visitor_rate;
	     this.random = new Random();
         this.creature_move_rate = creature_move_rate;
	     this.creature_death_rate = creature_death_rate;
		 this.creature_battle_rate = creature_battle_rate;
	     this.zombie_infect_rate = zombie_infect_rate;
	     this.human_reproduce_rate = human_reproduce_rate;
         this.zone = new Creature[9][9];
	     generateWorld();
     }       
	 
} 
