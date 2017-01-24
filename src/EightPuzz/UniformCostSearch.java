package EightPuzz;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import EightPuzz.framework.Metrics;
import EightPuzz.framework.Node;
import EightPuzz.framework.Solution;

public class UniformCostSearch {

	private static final int BOARD_SIZE = 3;
	
	private Metrics metrics;
	private Hashtable<String,Node> explored;
	private PriorityQueue<Node> frontier;
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
			Solution.write(initialState, initialState, metrics, explored);
			
		} else {
			
			frontier = new PriorityQueue<Node>();
			explored = new Hashtable<String,Node>();
			frontier.add(initialState);
			metrics.set("nodesOnStack", 1);
			
			
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
						if(frontier.size() > metrics.getInt("nodesOnStack"))
							metrics.set("nodesOnStack", frontier.size());
						}
					}
				}
			}
		}
	}
	public static void main(String[] args) throws Exception {
		UniformCostSearch ucs = new UniformCostSearch();
		ucs.UCS(args);

	}

}
