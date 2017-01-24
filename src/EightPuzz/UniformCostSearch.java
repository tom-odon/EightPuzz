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

public class UniformCostSearch {

	private static final int BOARD_SIZE = 3;
	
	private Metrics metrics;
	private HashSet<Node> explored;
	private Hashtable<String, Node> exploredTable;
	private HashSet<Node> frontier;
	private PriorityQueue<Node> frontierQ;
	private Node root;
	
	public UniformCostSearch(){
		
	}
	
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
	
	public void addToFrontier(Node node){
		frontier.add(node); 
		frontierQ.add(node);
	}
	
	public void addToExplored(Node node){
		explored.add(node);
		exploredTable.put((Integer.toString(node.hashCode())), node);
	}
	
	
	public static void main(String[] args) throws Exception {
		UniformCostSearch ucs = new UniformCostSearch();
		ucs.UCS(args);

	}

}

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
