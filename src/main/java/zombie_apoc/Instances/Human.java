/*
 * Author Name: Joseph Carter
 * Submission Date: 11/12/2025
 * Description: human class representation, for abstraction of two main entities
 */


package Instances;

import Instances.World;
import Instances.Creature;
import Instances.Zombie;

import java.util.ArrayList;
import java.util.Random;
import org.ini4j.Ini; 


public class Human extends Creature {
    // Reproduction rate
	private double reproduce_rate;
        private double weapon_rate_offset;
	// Neighbors
	public ArrayList<int[]> get_neighbors() { return this.neighbors;};
	protected void update_neighbors() {
		// TODO: Make updating algorithm
	}
	public void move() {
		// TODO: Implement probabilistic moving
	}
	public void die() {
		// TODO: Wrapper for World.removeCreature()
	}

	public void battle(Creature enemy) {
		// TODO: Implement fighting mechanic for when humans are neighbored to a zombie, based off of battle_rate
		
	}

	public Human reproduce(Human partner) {
		// TODO: Implement probability of reproduction
		return this;
	}
	// Constructor
	public Human(Ini.Section human_info, int x, int y) {
		this.x = x;
		this.y = y;
		this.move_rate = Double.parseDouble(human_info.get("human_move_rate"));
		this.reproduce_rate = Double.parseDouble(human_info.get("human_reproduce_rate"));
		this.battle_rate = Double.parseDouble(human_info.get("human_battle_rate"));
		this.weapon_rate_offset = Double.parseDouble(human_info.get("human_weapon_rate_offset"));
		this.neighbors = new ArrayList<int[]>();
		update_neighbors();
	}

}
