package EightPuzz;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import EightPuzz.framework.Action;
import EightPuzz.framework.Metrics;
import EightPuzz.framework.Node;
import EightPuzz.framework.Solution;

/*
 * Class for Depth-First-Search of Eight Puzzle problem. It expands the deepest node
 * in the current frontier. It utilizes a LIFO queue as opposed to Breadth First's FIFO.
 */
public class DepthFirstSearch {

	//class variables
	private static final int BOARD_SIZE = 3;
	private Metrics metrics;
	private Hashtable<String,Node> explored;
	private Deque<Node> frontier;
	private Node root;
	
	//General constructor.
	public DepthFirstSearch(){
		
	}
	
	//driver class that forms initial board, and calls the worker method.
	public void DFS(String[] args) throws Exception{
		int[] tiles = new int[BOARD_SIZE * BOARD_SIZE];
		for(int i = 0; i < args.length; i++)
			tiles[i] = Integer.parseInt(args[i]);
		
		root = new Node(tiles);

		DFS(root);
	}
	
	/*
	 * Worker method: checks root for solution, then enters into main algorithm: 
	 * While the frontier is not empty, load it with a node's children via FIFO queue. If a 
	 * child has not in the explored set or on the frontier, check for a solution
	 * and return. Otherwise, place it on the frontier for expansion.
	 */
	private void DFS(Node initialState) throws Exception {
		metrics = new Metrics();
		metrics.set("StartTime", System.currentTimeMillis());
		if (Solution.check(initialState)){
			metrics.set("TotalCost", 0);
			metrics.set("nodesOnFrontier", 0);
			Solution.write(initialState, initialState, metrics, explored);
			
		} else {
			
			frontier = new ArrayDeque<Node>();
			explored = new Hashtable<String,Node>();
			frontier.addFirst(initialState);
			metrics.set("nodesOnFrontier", 1);
			
			//Main loop
			while(frontier.peekFirst() != null) {
				Node current = frontier.removeFirst();
				
				if(!(explored.containsKey(
						Integer.toString(current.hashCode())) 
						|| frontier.contains(current))) {
					
						explored.put((Integer.toString(current.hashCode())),current);
					
						if(Solution.check(current)) {
							metrics.set("NodesExplored", explored.size());
							Solution.write(current, initialState, metrics, explored);
							return;
					
						} else {
							
							List<Node> children = current.getChildren();
							int i = 0;
							while(i < children.size()) {
									frontier.addFirst(children.get(i));
								if(frontier.size() > metrics.getInt("nodesOnFrontier"))
									metrics.set("nodesOnFrontier", frontier.size());
								i++;
							}					
						}
				}
			}
			System.out.println("FRONTIER SIZE: " +  frontier.size());
			System.out.println("FAILED TO FIND SOLUTION");
			return;
		}
	}
						
							
							
						
	
	
	/*
	 * Main entry point of application, takes in an array of 9 integers representing
	 * the 8 tiles, from left to right, top to bottom. The array is configured into
	 * a node for the problem and then the DFS worker method is called.
	 */
	public static void main(String[] args) throws Exception {
		DepthFirstSearch dfs = new DepthFirstSearch();
		dfs.DFS(args);
	}

}

