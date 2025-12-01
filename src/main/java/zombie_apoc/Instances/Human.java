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
    private double weapon_rate_offset;
    private boolean isSuper;
	// Neighbors
	public ArrayList<int[]> get_neighbors() { return this.neighbors;};
	protected void update_neighbors() {
		// TODO: Make updating algorithm
	}
	public void move() {
		// TODO: Implement probabilistic moving
	}

	public void battle(Creature enemy) {
		// TODO: Implement fighting mechanic for when humans are neighbored to a zombie, based off of battle_rate		
	}

	public boolean reproduce(Human partner) { // added by gabe
		Random rng = new Random();
        double chance = (this.reproduce_rate + partner.reproduce_rate) / 2.0;

        if (rng.nextDouble() <= chance)
            return true;
        return false;
	}

	public int get_x() {return this.x;}
	public int get_y() {return this.y;}
	// Constructor
	public Human(Ini.Section human_info, int x, int y, boolean isSuper) {
		this.x = x;
		this.y = y;
        this.isSuper = isSuper;

		this.move_rate = Double.parseDouble(human_info.get("human_move_rate"));
		this.reproduce_rate = Double.parseDouble(human_info.get("human_reproduce_rate"));
		this.battle_rate = Double.parseDouble(human_info.get("human_battle_rate"));
		this.weapon_rate_offset = Double.parseDouble(human_info.get("human_weapon_rate_offset"));
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
		this.weapon_rate_offset = other_human.weapon_rate_offset;

		this.neighbors = new ArrayList<int[]>();
		update_neighbors();
	}

}
