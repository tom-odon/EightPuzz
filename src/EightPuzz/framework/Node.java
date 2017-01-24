/*
 * The Node class represents a data type for leaves of the search tree. Each node
 * has basic bookkeeping information, links to the parent node and any possible 
 * children nodes, and current state of the 2-D tile board.
 */
package EightPuzz.framework;

import java.util.LinkedList;
import java.util.List;


public class Node {
	private final int BOARD_SIZE = 3;
	private int[][] state;
	private int parent;                                               
	private Action action;
	private double pathCost;
	
	/*
	 * Class constructor.
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
		
		//try each possible action, L R U D
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

	public String getParent() {
		return Integer.toString(parent);
	}

	public Action getAction() {
		return action;
	}

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
	
	@Override
	public boolean equals(Object other){
		if (other == null)
			return false;
		
		if(this.hashCode() == other.hashCode())
			return true;
		
		return false;
	}
	
	private int[][] copyState(){
		int [][] newState = new int[state.length][];
		for(int i = 0; i < state.length; i++)
		    newState[i] = state[i].clone();
		
		return newState;
	}

	public int compareTo(Node other) {
		if(this.getPathCost() < other.getPathCost())
			return -1;
		
		if(this.getPathCost() > other.getPathCost())
			return 1;
		
		return 0;
	}
	
	
}
