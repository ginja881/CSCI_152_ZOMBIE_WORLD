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
   private Random rng;
   public boolean infect() {
        boolean chance = this.rng.nextDouble() * this.norm_constant <= this.infect_rate;		
		return chance;
    }

    public ArrayList<int[]> get_neighbors() {return this.neighbors;}
    protected void update_neighbors() {
		if (this.x  > 0)
			this.neighbors.add(new int[]{this.x - 1, this.y});
		if (this.x < this.world_width)
			this.neighbors.add(new int[]{this.x+1, this.y});
		if (this.y < this.world_height)
			this.neighbors.add(new int[]{this.x, this.y+1});
		if (this.y > 0)
			this.neighbors.add(new int[]{this.x,  this.y-1});
    }

    public boolean move() {
		boolean chance = this.rng.nextDouble() * this.norm_constant <= this.move_rate;
		return chance;
    }
	
    public boolean battle() {
	    boolean chance = this.rng.nextDouble() * this.norm_constant <= this.battle_rate;
        return chance;	
	
    }
    public void update_ticker() {this.death_ticker--;};
    public int[] get_xy() {return new int[]{this.x, this.y};};
    public void update_xy(int x, int y) {
        this.x = x;
	    this.y = y;

	    this.update_neighbors();
    }
    public Zombie(int x, int y, Ini.Section zombie_info, boolean isSuper) {
		this.x = x;
		this.y = y;
        this.isSuper = isSuper;
		this.move_rate = Double.parseDouble(zombie_info.get("zombie_move_rate"));
		this.infect_rate = Double.parseDouble(zombie_info.get("zombie_infect_rate"));
		this.battle_rate = Double.parseDouble(zombie_info.get("zombie_battle_rate"));
        this.norm_constant = Double.parseDouble(zombie_info.get("zombie_norm_constant"));
		this.death_ticker = Integer.parseInt(zombie_info.get("zombie_death_ticker"));
        this.world_width = Integer.parseInt(zombie_info.get("world_width"));
		this.world_height = Integer.parseInt(zombie_info.get("world_height"));
		this.rng = new Random();

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
		this.norm_constant = other_zombie.norm_constant;
		this.death_ticker = other_zombie.death_ticker;
		this.world_width = other_zombie.world_width;
		this.world_height = other_zombie.world_height;
        this.rng = new Random();

		this.neighbors = new ArrayList<int[]>();
		update_neighbors();
	}
}
