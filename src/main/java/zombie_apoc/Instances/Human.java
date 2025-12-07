/*
 * Author Name: Joseph Carter
 * Submission Date: 11/12/2025
 * Description: human class representation, for abstraction of two main entities
 */


package zombie_apoc.Instances;

import zombie_apoc.Instances.World;
import zombie_apoc.Instances.Creature;
import zombie_apoc.Instances.Zombie;

import java.util.ArrayList;
import java.util.Random;
import org.ini4j.Ini; 


public class Human extends Creature {
        // Reproduction rate
	private double reproduce_rate;


	// Neighbors
	public ArrayList<int[]> get_neighbors() { return this.neighbors;};
	protected void update_neighbors() {
		// Updating neighbors
		this.neighbors = new ArrayList<int[]>();
        // ASSUMING UP DOWN LEFT RIGHT WITH RESPECT TO BOUNDS
		if (this.x > 0)
			this.neighbors.add(new int[] {this.x-1, this.y});
		if (this.x < (this.world_width - 1))
			this.neighbors.add(new int[]{this.x+1, this.y});
        if (this.y > 0)
			this.neighbors.add(new int[]{this.x, y-1});
		if (this.y < (this.world_height - 1))
			this.neighbors.add(new int[] {this.x, y+1});
	}
	public boolean move() {
		// Move rate check
		double chance = this.rng.nextDouble() * (this.norm_constant);
		return (chance <= this.move_rate ? true : false);
	}

	public boolean  battle() { // added by gabe
		// Battle Rate check  
		double player_rate = this.battle_rate; 
                
	    boolean player_win = this.rng.nextDouble() * (this.norm_constant) <= player_rate;
		return  player_win;
	}

	public boolean reproduce(Human partner) { // added by gabe
		         // Reproduction rate check
                 double overall_rate = (this.reproduce_rate + partner.reproduce_rate) / 2.0;
                 return (this.rng.nextDouble() * this.norm_constant <= overall_rate ? true : false);
                 
	}
	public boolean engage_battle() 
	{
		// Battle engagement
		boolean engage = this.rng.nextDouble() * this.norm_constant <= this.engage_rate ? true :false;
		return engage;
	}
	// Getters for position
    public int get_x() {return this.x;}
	public int get_y() {return this.y;}
	public void update_xy(int x, int y) {
		   // Setter for position
           this.x = x;
	       this.y = y;
	       this.update_neighbors();
	}

	public int get_current_day() {return this.current_day;}
	public void update_current_day() {this.current_day++;}
	public boolean isSuper() {return this.isSuper;}
	
	// Constructor
	public Human(Ini.Section human_info, int x, int y, boolean isSuper, int current_day) {
		this.x = x;
		this.y = y;
        this.isSuper = isSuper;
        this.rng = new Random();
        this.current_day = current_day;
         
		this.move_rate = Math.abs(Double.parseDouble(human_info.get("human_move_rate")));
		this.reproduce_rate = Math.abs(Double.parseDouble(human_info.get("human_reproduce_rate")));
		this.battle_rate = Math.abs(Double.parseDouble(human_info.get("human_battle_rate")));
		this.super_rate_offset = Math.abs(Double.parseDouble(human_info.get("human_super_offset")));
		this.norm_constant = Math.abs(Double.parseDouble(human_info.get("human_norm_constant")));
		this.world_height = Math.abs(Integer.parseInt(human_info.get("world_height")));
		this.world_width = Math.abs(Integer.parseInt(human_info.get("world_width")));
		this.engage_rate = Math.abs(Double.parseDouble(human_info.get("human_engage_rate")));

        // setting battle rate for super humans     
        if (this.isSuper)
			this.battle_rate += this.super_rate_offset;

		this.neighbors = new ArrayList<int[]>();
		update_neighbors();
	}

}
