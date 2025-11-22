/*
 * Author Name: Joseph Carter
 * Submission Date: 11/12/2025
 * Description: creature class representation, for abstraction
 */

package Instances;
import Instances.World;
import java.util.ArrayList;

public abstract class Creature {
	// Important attributes
	protected World world;
	public int x;
	public int y;
	
	protected ArrayList<int[]> neighbors;
    // Rates for moving, death, and battling
	public double move_rate;
	public double death_rate;
        public double battle_rate;
	
	// Neighbors
	public abstract ArrayList<int[]> get_neighbors();
	protected abstract void update_neighbors();

	// Battling, moving, and dying
    public abstract void battle(Creature enemy);

	public abstract void move();
	public abstract void die();
}
