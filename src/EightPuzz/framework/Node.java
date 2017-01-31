/*
 * The Node class represents a data type for leaves of the search tree. Each node
 * has basic bookkeeping information, links to the parent node and any possible 
 * children nodes, and current state of the 2-D tile board.
 */
package EightPuzz.framework;

import java.util.LinkedList;
import java.util.List;

//Instance variables.
public class Node {
	private final int BOARD_SIZE = 3;
	private int[][] state;
	private int parent;                                               
	private Action action;
	private double pathCost;
	
	/*
	 * Class constructor. Parent int represents the hashCode of the parent node.
	 * Cost is the total path cost leading to this node. Action represents a 
	 * move cost and direction. State is the new-formed state after taking the Action.
	 */
	public Node(int parent, double cost, Action action, int[][] state)  {
		this.parent = parent;
		this.action = action;
		this.pathCost = cost + action.getCost();
		this.state = state;
		//this.state = generateState(parent, action);
	}
	
	/*
	 * Constructor that allows root node to be built from an input of 9 numbers,
	 * left to right, top to bottom. No other node will be created from this method
	 * except the root.
	 */
	public Node(int[] input) throws Exception{
		int[][] tiles = new int[3][3];
		int x, y, z = 0;
		for(x = 0; x < BOARD_SIZE; x++){
			for(y = 0; y < BOARD_SIZE; y++){
				tiles[x][y] = input[z];
				z++;
			}
		}
		this.state = tiles;
		this.parent = -1;
		this.action = new Action("none", 0);
		this.pathCost = 0;
	}
	
	/*
	 * Find the 0 tile, then figure out which successor moves are possible given
	 * the configuration. Create actions and return a list to be added to the stack.
	 */
	public List<Node> getChildren() throws Exception {
		
		//find '0' tile, store as x / y coordinates
		int x = 0, y = 0;
		outerloop:
		for(x = 0; x < BOARD_SIZE; x++){
			for(y = 0; y < BOARD_SIZE; y++){
				if(state[x][y] == 0)
				break outerloop;
			}
		}
		
		/*
		 * Tries to form each possible action, Left Right Up Down.
		 * If the action is possible, creates a child node representing that action.
		 */
		int val;
		List<Node> children = new LinkedList<Node>();
		if((x < BOARD_SIZE) && (x >= 0 ) && (y < BOARD_SIZE - 1) && (y >= 0)) {
			int[][] leftTiles = copyState();
				
			val = leftTiles[x][y + 1];
			leftTiles[x][y] = val;
			leftTiles[x][y + 1] = 0;
			Action leftAction = new Action("left", val);
			Node left = new Node(this.hashCode(), this.pathCost, leftAction, leftTiles);
			children.add(left);
		}
		
		if((x < BOARD_SIZE) && (x >= 0 ) && (y < BOARD_SIZE) && (y > 0)) {
			int[][] rightTiles = copyState();
			
			val = rightTiles[x][y - 1];
			rightTiles[x][y] = val;
			rightTiles[x][y - 1] = 0;
			Action rightAction = new Action("right", val);
			Node right = new Node(this.hashCode(), this.pathCost, rightAction, rightTiles);
			children.add(right);
		}
		
		if((x < BOARD_SIZE - 1) && (x >= 0) && (y < BOARD_SIZE) && (y >= 0)) { 
			int[][] upTiles = copyState();
			
			val = upTiles[x + 1][y];
			upTiles[x][y] = val;
			upTiles[x + 1][y] = 0;
			Action upAction = new Action("up", val);
			Node up = new Node(this.hashCode(), this.pathCost, upAction, upTiles);
			children.add(up);
			
		}
		if((x < BOARD_SIZE) && (x > 0) && (y < BOARD_SIZE) && (y >= 0)) {
			int[][] downTiles = copyState();
		
			val = downTiles[x - 1][y];
			downTiles[x][y] = val;
			downTiles[x - 1][y] = 0;
			Action downAction = new Action("down", val);
			Node down = new Node(this.hashCode(), this.pathCost, downAction, downTiles);
			children.add(down);
			
		}
		
		return children;
	}

	//Return the string representation of the parent node.
	public String getParent() {
		return Integer.toString(parent);
	}

	//Returns both move and move cost.
	public Action getAction() {
		return action;
	}

	//Total path cost, not child move cost.
	public double getPathCost() {
		return pathCost;
	}
	
	/*
	 * Returns a string representation of the tile configuration
	 */
	public String getTileConfig() {
		StringBuilder sb = new StringBuilder();
		int x = 0, y = 0;
		for(x = 0; x < BOARD_SIZE; x++){
			for(y = 0; y < BOARD_SIZE; y++){
				sb.append(state[x][y] + " ");
			}
		}
		return sb.toString();		
	}
		
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 * Hashcode overridden to reflect the board's goal state.
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		int exponent = 8;
		int x = 0, y = 0;
		for(x = 0; x < BOARD_SIZE; x++){
			for(y = 0; y < BOARD_SIZE; y++){
				hash += state[x][y] * Math.pow(10, exponent);
				exponent--;
			}
		}
		return hash;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * Overrides the standard equals() method by comparing hashcodes.
	 */
	@Override
	public boolean equals(Object other){
		if (other == null)
			return false;
		
		if(this.hashCode() == other.hashCode())
			return true;
		
		return false;
	}
	
	//Copies the state of the tiles for node creation.
	private int[][] copyState(){
		int [][] newState = new int[state.length][];
		for(int i = 0; i < state.length; i++)
		    newState[i] = state[i].clone();
		
		return newState;
	}

	//Bad comparison method.
	public int compareTo(Node other) {
		if(this.getPathCost() < other.getPathCost())
			return -1;
		
		if(this.getPathCost() > other.getPathCost())
			return 1;
		
		return 0;
	}
	
	/*
	 * Iterate through all tiles and increment a running total if the given tile
	 * is not in its goal state location. Return the total.
	 */
	public int outOfPlace(){
		int count = 0;
		if (this.state[0][0] != 1) count++;
		if (this.state[0][1] != 2) count++;
		if (this.state[0][2] != 3) count++;
		if (this.state[1][0] != 8) count++;
		if (this.state[1][1] != 0) count++;
		if (this.state[1][2] != 4) count++;
		if (this.state[2][0] != 7) count++;
		if (this.state[2][1] != 6) count++;
		if (this.state[2][2] != 5) count++;
		return count;
	}

	/*
	 * Iterate through all tiles, and for each one calculate the distance in
	 * tiles from its goal state place. Return the sum of all distances.
	 */
	public double manhattanSum() {
		int manhattan = 0;
		
		int[] xGoal = {1,0,0,0,1,2,2,2,1};
		int[] yGoal = {1,0,1,2,2,2,1,0,0};
				
		for(int x = 0; x < BOARD_SIZE; x++){
			for(int y = 0; y < BOARD_SIZE; y++){
				int tile = this.state[x][y];
				manhattan += Math.abs(xGoal[tile] - x);
				manhattan += Math.abs(yGoal[tile] - y);
			}
		}
		
		return manhattan;
	}
	
}
