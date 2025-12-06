/*
 * Author Name: Joseph Carter
 * Submission Date: 11/12/2025
 * Description: creature class representation, for abstraction
 */

package zombie_apoc.Instances;
import zombie_apoc.Instances.World;
import java.util.ArrayList;
import java.util.Random;
public abstract class Creature {
	// Important attributes
	
	protected int x;
	protected int y;
	protected int current_day;
	protected boolean isSuper; //indicating if a creature is super

	protected ArrayList<int[]> neighbors;
        // Rates for moving, death, and battling
	protected double move_rate;
    protected double battle_rate;
	protected double norm_constant;
    protected double super_rate_offset;
	protected double engage_rate;

    // Necessary world info
	protected int world_width;
	protected int world_height;
    protected Random rng;
	// Neighbors
	public abstract ArrayList<int[]> get_neighbors();
	protected abstract void update_neighbors();
	// Battling, moving, and engaging in battles
    public abstract boolean battle();
    public abstract boolean engage_battle();
	public abstract boolean move();
    
	// Getters and setters for position
	public abstract int get_x();
	public abstract int get_y();
	public abstract void update_xy(int x, int y);
    
	// Current day getters and setters
	public abstract int get_current_day();
	public abstract void update_current_day();
	public abstract void update_current_day(int day);

	// Extra getter for super
	public abstract boolean isSuper();
}
