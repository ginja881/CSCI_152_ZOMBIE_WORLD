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
	protected boolean isSuper; //indicating if a creature is super

	protected ArrayList<int[]> neighbors;
        // Rates for moving, death, and battling
	protected double move_rate;
    protected double battle_rate;
	protected double norm_constant;
    protected double super_rate_offset;
        // Necessary world info
	protected int world_width;
	protected int world_height;
    protected Random rng;
	// Neighbors
	public abstract ArrayList<int[]> get_neighbors();
	protected abstract void update_neighbors();
	// Battling, moving, and dying
        public abstract boolean battle();

	public abstract boolean move();

	public abstract int[] get_xy();
	public abstract void update_xy(int x, int y);
}
