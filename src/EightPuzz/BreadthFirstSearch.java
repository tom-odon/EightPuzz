package EightPuzz;

import java.util.ArrayDeque;
import java.util.Comparator;
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
 * Class for Breadth-First-Search of Eight Puzzle problem. All nodes at a given
 * depth are expanded before any deeper nodes are expanded. 
 */
public class BreadthFirstSearch {

	//class variables
	private static final int BOARD_SIZE = 3;
	
	private Metrics metrics;
	private Hashtable<String,Node> explored;
	private Queue<Node> frontier;
	private Node root;
	
	//empty constructor
	public BreadthFirstSearch(){	
	}
	
	//driver class that forms initial board, and calls the worker method.
	public void BFS(String[] args) throws Exception{
		System.out.println("Executing: Best First Search");
		int[] tiles = new int[BOARD_SIZE * BOARD_SIZE];
		for(int i = 0; i < args.length; i++)
			tiles[i] = Integer.parseInt(args[i]);
			root = new Node(tiles);
		
		switch(tiles[0]){
		case 1: System.out.println("Level: EASY"); break;
		case 2: System.out.println("Level: MEDIUM"); break;
		case 5: System.out.println("Level: HARD"); break;
		}
		BFS(root);
	}
	
	/*
	 * Worker method: checks root for solution, then enters into main algorithm: 
	 * While the frontier is not empty, load it with a node's children. If a 
	 * child has not in the explored set or on the frontier, check for a solution
	 * and return. Otherwise, place it on the frontier for expansion.
	 */
	private void BFS(Node initialState) throws Exception{
		metrics = new Metrics();
		metrics.set("StartTime", System.currentTimeMillis());
		if (Solution.check(initialState)){
			metrics.set("TotalCost", 0);
			metrics.set("nodesOnFrontier", 0);
			Solution.write(initialState, initialState, metrics, explored);
			
		} else {
			
			frontier = new LinkedList<Node>();
			explored = new Hashtable<String,Node>();
			frontier.add(initialState);
			metrics.set("nodesOnFrontier", 1);
			
			
			while(!frontier.isEmpty()){
				Node current = frontier.remove();
				explored.put((Integer.toString(current.hashCode())),current);
				List<Node> children = current.getChildren(); 
				for(Node child : children){
					
					if(!(explored.containsKey(Integer.toString(child.hashCode())) || 
					   frontier.contains(child))) {
							if(Solution.check(child)) {
								metrics.set("NodesExplored", explored.size());
								Solution.write(child, initialState, metrics, explored);
								return;
								
						} else {
						frontier.add(child);
						if(frontier.size() > metrics.getInt("nodesOnFrontier"))
							metrics.set("nodesOnFrontier", frontier.size());
						}
					}
				}
			}
		}
	}
	
	
	
	/*
	 * Main entry point of application, takes in an array of 9 integers representing
	 * the 8 tiles, from left to right, top to bottom. The array is configured into
	 * a node for the problem and then the BFS worker method is called.
	 */
	public static void main(String[] args) throws Exception {
		BreadthFirstSearch bfs = new BreadthFirstSearch();
		bfs.BFS(args);
	}
	
}

	
