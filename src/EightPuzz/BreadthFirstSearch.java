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
 * Class for Breadth-First-Search of Eight Puzzle problem.
 */
public class BreadthFirstSearch {

	private static final int BOARD_SIZE = 3;
	
	private Metrics metrics;
	private Hashtable<String,Node> explored;
	private Queue<Node> frontier;
	private Node root;
	
	public BreadthFirstSearch(){
		
	}
	
	public void BFS(String[] args) throws Exception{
		int[] tiles = new int[BOARD_SIZE * BOARD_SIZE];
		for(int i = 0; i < args.length; i++)
			tiles[i] = Integer.parseInt(args[i]);
			root = new Node(tiles);
		BFS(root);
	}
	
	private void BFS(Node initialState) throws Exception{
		metrics = new Metrics();
		metrics.set("StartTime", System.currentTimeMillis());
		if (Solution.check(initialState)){
			metrics.set("TotalCost", 0);
			metrics.set("nodesOnStack", 0);
			Solution.write(initialState, initialState, metrics);
			
		} else {
			
			frontier = new LinkedList<Node>();
			explored = new Hashtable<String,Node>();
			frontier.add(initialState);
			metrics.set("nodesOnStack", 1);
			
			
			while(!frontier.isEmpty()){
				Node current = frontier.remove();
				explored.put((Integer.toString(current.hashCode())),current);
				List<Node> children = current.getChildren(); 
				for(Node child : children){
					//NewNode child = new NewNode(current, action);
					
					System.out.println(child.getTileConfig() + ", " + child.getAction().getMove()
							+ ", " + child.getAction().getCost());
					
					if(!(explored.containsKey(Integer.toString(child.hashCode())) || 
					   frontier.contains(child))) {
						if(Solution.check(child)) {
							metrics.set("NodesExplored", explored.size());
							Solution.write(child, initialState, metrics);
							return;
						} else {
						frontier.add(child);
						if(frontier.size() > metrics.getInt("nodesOnStack"))
							metrics.set("nodesOnStack", frontier.size());
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
