/*
 * Author Name: Joseph Carter
 * Submission Date: 11/12/2025
 * Description: human class representation, for abstraction of two main entities
 */


package ZombieApoc;

import ZombieApoc.World;
import ZombieApoc.Creature;
import ZombieApoc.Zombie;

import java.util.ArrayList;
import java.util.Random;

public class Human extends Creature {
    // Reproduction rate
	public double reproduce_rate;
    
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
	public Human(World world, int x, int y, double move_rate, double reproduce_rate) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.move_rate = move_rate;
		this.reproduce_rate = reproduce_rate;
		this.neighbors = new ArrayList<int[]>();
		update_neighbors();
	}

}
