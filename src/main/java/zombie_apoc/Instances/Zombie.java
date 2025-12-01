/*
 * Author Name: Joseph Carter
 * Submission Date: 11/12/2025
 * Description: zombie class representation, abstraction of two major entities
 */


package zombie_apoc.Instances;
import java.util.ArrayList;

import zombie_apoc.Instances.Creature;

import java.util.Random;
import org.ini4j.Ini;

public class Zombie extends Creature {
    private int death_ticker;
        
	private double infect_rate;
    private boolean isSuper;
	
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
    public void update_ticker() {this.death_ticker--;};
    public int get_x() {return this.x;}
	public int get_y() {return this.y;}


	public Zombie(int x, int y, Ini.Section zombie_info, boolean isSuper) {
		this.x = x;
		this.y = y;
        this.isSuper = isSuper;
		this.move_rate = Double.parseDouble(zombie_info.get("zombie_move_rate"));
		this.infect_rate = Double.parseDouble(zombie_info.get("zombie_infect_rate"));
		this.battle_rate = Double.parseDouble(zombie_info.get("zombie_battle_rate"));

		this.death_ticker = Integer.parseInt(zombie_info.get("zombie_death_ticker"));
		
		this.neighbors = new ArrayList<int[]>();
		update_neighbors();
	}
	
	public Zombie(Zombie other_zombie) {
		this.x = other_zombie.x;
		this.y = other_zombie.y;
        this.isSuper = other_zombie.isSuper;
		this.move_rate = other_zombie.move_rate;
		this.infect_rate = other_zombie.infect_rate;
		this.battle_rate = other_zombie.battle_rate;
		
		this.neighbors = new ArrayList<int[]>();
		update_neighbors();
	}
}
