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
		this.neighbors = new ArrayList<int[]>();

		if (this.x > 0)
			this.neighbors.add(new int[] {(this.world_width + 1) - x, this.y});
		if (this.x < (this.world_width - 1))
			this.neighbors.add(new int[]{(this.world_width -1) + x, this.y});
                if (this.y > 0)
			this.neighbors.add(new int[]{this.x, (this.world_height -1) - y});
		if (this.y < (this.world_height - 1))
			this.neighbors.add(new int[] {this.x, (this.world_height - 1) + y});
	}
	public boolean move() {
		double chance = this.rng.nextDouble() * (this.norm_constant);
		return (chance <= this.move_rate ? true : false);
	}

	public boolean  battle() { // added by gabe  
		double player_rate = this.battle_rate; 
                
	    boolean player_win = this.rng.nextDouble() * (this.norm_constant) <= player_rate;
		return  player_win;
	}

	public boolean reproduce(Human partner) { // added by gabe
                 double overall_rate = (this.reproduce_rate + partner.reproduce_rate) / 2.0;
                 return (this.rng.nextDouble() * this.norm_constant <= overall_rate ? true : false);
                 
	}

	public int[] get_xy() {
		return new int[]{this.x, this.y};
	}
	public void update_xy(int x, int y) {
               this.x = x;
	       this.y = y;
	       this.update_neighbors();
	}
	// Constructor
	public Human(Ini.Section human_info, int x, int y, boolean isSuper) {
		this.x = x;
		this.y = y;
        this.isSuper = isSuper;
        this.rng = new Random();

		this.move_rate = Double.parseDouble(human_info.get("human_move_rate"));
		this.reproduce_rate = Double.parseDouble(human_info.get("human_reproduce_rate"));
		this.battle_rate = Double.parseDouble(human_info.get("human_battle_rate"));
		this.super_rate_offset = Double.parseDouble(human_info.get("human_super_offset"));
		this.norm_constant = Double.parseDouble(human_info.get("human_norm_constant"));
		this.world_height = Integer.parseInt(human_info.get("world_height"));
		this.world_width = Integer.parseInt(human_info.get("world_width"));
                
        if (this.isSuper)
			this.battle_rate += this.super_rate_offset;

		this.neighbors = new ArrayList<int[]>();
		update_neighbors();
	}

	public Human(Human other_human) {
		this.x = other_human.x;
		this.y = other_human.y;
	        this.isSuper = other_human.isSuper;

		this.move_rate = other_human.move_rate;
	
		this.reproduce_rate = other_human.reproduce_rate;
		this.battle_rate = other_human.battle_rate;
	    this.super_rate_offset = other_human.super_rate_offset; 
        this.norm_constant = other_human.norm_constant;
		this.neighbors = new ArrayList<int[]>();
		this.rng = new Random();

		update_neighbors();
	}

}
