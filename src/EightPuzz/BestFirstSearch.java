package EightPuzz;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import EightPuzz.framework.Metrics;
import EightPuzz.framework.Node;
import EightPuzz.framework.Solution;

/*
 * Implementation of the greedy Best First Search algorithm, which uses a heuristic
 * defined as the number of tiles that are not in the correct position. Nodes are 
 * expanded based on the lowest number of out-of-place tiles.
 */
public class BestFirstSearch {

	//class variables
	private static final int BOARD_SIZE = 3;
	
	private Metrics metrics;
	private HashSet<Node> explored;
	private Hashtable<String, Node> exploredTable;
	private HashSet<Node> frontier;
	private PriorityQueue<Node> frontierQ;
	private Node root;
	
	//empty constructor
	public BestFirstSearch(){	
	}
	
	//driver class that forms initial board, and calls the worker method.
	public void BFS(String[] args) throws Exception{
		System.out.println("Executing: Breadth First Search");
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
			Solution.write(initialState, initialState, metrics, exploredTable);
			
		} else {
			
			Comparator<Node> comparator = new BestFSComparator();
			frontierQ = new PriorityQueue<Node>(10, comparator);
			frontier = new HashSet<Node>();
			explored = new HashSet<Node>();
			exploredTable = new Hashtable<String, Node>();
			
			addToFrontier(initialState);
			
			while(!frontier.isEmpty()){
				Node current = frontierQ.poll();
				frontier.remove(current);
				
				addToExplored(current);
				System.out.println("CURRENT: " + current.getTileConfig());
				
				List<Node> children = current.getChildren(); 
				for(Node child : children){
					
					System.out.println("CHILD: " + child.getTileConfig());
					
					if(!(explored.contains(child)) || 
					   frontier.contains(child)) {
						
							if(Solution.check(child)) {
								metrics.set("NodesExplored", explored.size());
								Solution.write(child, initialState, metrics, exploredTable);
								return;
								
						} else {
						addToFrontier(child);
						if(frontier.size() > metrics.getInt("nodesOnFrontier"))
							metrics.set("nodesOnFrontier", frontier.size());
						}
					}
				}
			}
		}
	}
	
	public void addToFrontier(Node node){
		frontier.add(node); 
		frontierQ.add(node);
	}
	
	public void addToExplored(Node node){
		explored.add(node);
		exploredTable.put((Integer.toString(node.hashCode())), node);
	}
	
	/*
	 * Main entry point of application, takes in an array of 9 integers representing
	 * the 8 tiles, from left to right, top to bottom. The array is configured into
	 * a node for the problem and then the BFS worker method is called.
	 */
	public static void main(String[] args) throws Exception {
		BestFirstSearch bfs = new BestFirstSearch();
		bfs.BFS(args);
	}
}

/*
 * Comparator to evaluate the number of tiles out of place of each node, essential 
 * for the implementation of the priority queue. 
 */
class BestFSComparator implements Comparator<Node>{
	
	@Override
	public int compare(Node x, Node y) {
		if(x.outOfPlace() < y.outOfPlace())	
			return -1;
		
		if(x.outOfPlace() > y.outOfPlace())
			return 1;
		
		return 0;
	}
}
