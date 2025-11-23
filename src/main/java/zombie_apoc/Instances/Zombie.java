/*
 * Author Name: Joseph Carter
 * Submission Date: 11/12/2025
 * Description: zombie class representation, abstraction of two major entities
 */


package Instances;
import java.util.ArrayList;

import Instances.Creature;

import java.util.Random;

public class Zombie extends Creature {
    public int death_ticker;
        
	public double infect_rate;

	
	public Zombie infect(Human enemy) {
		// TODO: Implement infection, represented as a battle outcome
		return this;
	}

    public ArrayList<int[]> get_neighbors() {return this.neighbors;}
	protected void update_neighbors() {
		//TODO: Implement updating algorithm (One used in Human)
	}

        public void move() {
		// TODO: Implement probabilisitc moving
	}
	
	public void die() {
		// TODO: Implement wrapper for world.removeCreature()
	}
    public void battle(Creature enemy) {
		// TODO: Implement fighting mechanic for when zombies are neighbored to humans, based off of rates
		
	}


	public Zombie(int x, int y, double move_rate, double infect_rate) {
		this.x = x;
		this.y = y;
		this.move_rate = move_rate;
		this.infect_rate = infect_rate;
		this.death_ticker = 4;
		this.neighbors = new ArrayList<int[]>();
		update_neighbors();
	}
}
