/*
 * Author Names: Joseph Carter, Bennett, Gabe, and Aaron Raycove
 * Submission Date: 12/08/2025
 * Description: representation of zombies
*/

package zombie_apoc.Instances;
import java.util.ArrayList;

import zombie_apoc.Instances.Creature;

import java.util.Random;
import org.ini4j.Ini;

public class Zombie extends Creature {
   //
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
		this.neighbors = new ArrayList<>();
		if (this.x  > 0)
			this.neighbors.add(new int[]{this.x -1, this.y});
		if (this.x < this.world_width-1)
			this.neighbors.add(new int[]{this.x+1, this.y});
		if (this.y < this.world_height-1)
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
	public boolean engage_battle() 
	{
		boolean engage = this.rng.nextDouble() * this.norm_constant <= this.engage_rate ? true :false;
		return engage;
	}
    public void update_ticker() {this.death_ticker--;};
	public int get_ticker() {return this.death_ticker;}
    public int get_x() {return this.x;}
	public int get_y() {return this.y;}
	

    public void update_xy(int x, int y) {
        this.x = x;
	    this.y = y;

	    this.update_neighbors();
    }

    public int get_current_day() {return this.current_day;}
	public void update_current_day() {this.current_day++;}
    public void update_current_day(int day) {this.current_day = day;}
	public boolean isSuper() {return this.isSuper;}
	
    public Zombie(int x, int y, Ini.Section zombie_info, boolean isSuper, int running_day) {
        
		// Position and day
		this.x = x;
		this.y = y;
        this.current_day = running_day;
		// 
        this.isSuper = isSuper;
        
		// Rates
		this.move_rate = Math.abs(Double.parseDouble(zombie_info.get("zombie_move_rate")));
		this.infect_rate = Math.abs(Double.parseDouble(zombie_info.get("zombie_infect_rate")));
		this.battle_rate = Math.abs(Double.parseDouble(zombie_info.get("zombie_battle_rate")));
        this.norm_constant = Math.abs(Double.parseDouble(zombie_info.get("zombie_norm_constant")));
		this.death_ticker = Math.abs(Integer.parseInt(zombie_info.get("zombie_death_ticker")));
        this.world_width = Math.abs(Integer.parseInt(zombie_info.get("world_width")));
		this.world_height = Math.abs(Integer.parseInt(zombie_info.get("world_height")));
		this.engage_rate = Math.abs(Double.parseDouble(zombie_info.get("zombie_engage_rate")));

		// Random object for handling chance
		this.rng = new Random();
        
		// Initializing neighbors
		this.neighbors = new ArrayList<int[]>();
		update_neighbors();
	}
	
}
