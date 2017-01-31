package EightPuzz;

import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.HashSet;

import EightPuzz.framework.Metrics;
import EightPuzz.framework.Node;
import EightPuzz.framework.Solution;

/*
 * Uniform cost search expands the node with the lowest path cost next, of g(n).
 * It uses a priority queue to maintain order of the lowest path cost. It also
 * checks for a solution when a node is selected for expansion, instead of when
 *  it is first created.
 */
public class UniformCostSearch {

	//class variables
	private static final int BOARD_SIZE = 3;
	private Metrics metrics;
	private HashSet<Node> explored;
	private Hashtable<String, Node> exploredTable;
	private HashSet<Node> frontier;
	private PriorityQueue<Node> frontierQ;
	private Node root;
	
	//empty class constructor
	public UniformCostSearch(){
		
	}
	
	//driver class that forms initial board, and calls the worker method.
	public void UCS(String[] args) throws Exception{
		System.out.println("Executing: Uniform Cost Search");
		int[] tiles = new int[BOARD_SIZE * BOARD_SIZE];
		for(int i = 0; i < args.length; i++)
			tiles[i] = Integer.parseInt(args[i]);
			root = new Node(tiles);
		
		switch(tiles[0]){
		case 1: System.out.println("Level: EASY"); break;
		case 2: System.out.println("Level: MEDIUM"); break;
		case 5: System.out.println("Level: HARD"); break;
		}
		UCS(root);
	}
	
	//Main worker method. Checks goal state and then enters main loop.
	private void UCS(Node initialState) throws Exception{
		metrics = new Metrics();
		metrics.set("StartTime", System.currentTimeMillis());
		if (Solution.check(initialState)){
			metrics.set("TotalCost", 0);
			metrics.set("nodesOnStack", 0);
			Solution.write(initialState, initialState, metrics, exploredTable);
			
		} else {
			
			Comparator<Node> comparator = new UCSComparator();
			frontierQ = new PriorityQueue<Node>(10, comparator);
			frontier = new HashSet<Node>();
			explored = new HashSet<Node>();
			exploredTable = new Hashtable<String, Node>();
			
			addToFrontier(initialState);
			
			metrics.set("nodesOnFrontier", 1);
			
			//Main loop.
			while(!frontier.isEmpty()){
				Node current = frontierQ.poll();
				frontier.remove(current);
				
				if(Solution.check(current)) {
					metrics.set("NodesExplored", explored.size());
					System.out.println("SOLUTION FOUND");
					Solution.write(current, initialState, metrics, exploredTable);
					return;
				}
				
				addToExplored(current);
				
				List<Node> children = current.getChildren(); 
				for(Node child : children){
					
					if((!(explored.contains(child)) || 
						 (frontier.contains(child)))) {
						addToFrontier(child);	
						
						if(frontier.size() > metrics.getInt("nodesOnFrontier"))
							metrics.set("nodesOnFrontier", frontier.size());						
					}
				}
			}
		}
	}
	
	//Adds a node to the Frontier hash table and queue.
	public void addToFrontier(Node node){
		frontier.add(node); 
		frontierQ.add(node);
	}
	
	//Adds a node to the Explored hash table and set.
	public void addToExplored(Node node){
		explored.add(node);
		exploredTable.put((Integer.toString(node.hashCode())), node);
	}
	
	
	public static void main(String[] args) throws Exception {
		UniformCostSearch ucs = new UniformCostSearch();
		ucs.UCS(args);

	}

}

/*
 * Comparator to evaluate the number of nodes out of place, essential for the
 * implementation of the priority queue. 
 */
class UCSComparator implements Comparator<Node>{
	
	@Override
	public int compare(Node x, Node y) {
		if(x.getPathCost() < y.getPathCost())	
			return -1;
		
		if(x.getPathCost() > y.getPathCost())
			return 1;
		
		return 0;
	}
}
