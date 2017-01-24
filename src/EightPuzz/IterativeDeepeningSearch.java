package EightPuzz;

import java.util.Deque;
import java.util.Hashtable;
import java.util.List;

import EightPuzz.framework.Metrics;
import EightPuzz.framework.Node;
import EightPuzz.framework.Solution;

public class IterativeDeepeningSearch {
	
	private static final int BOARD_SIZE = 3;
	private static final int INFINITY = Integer.MAX_VALUE;
    
	private Metrics metrics;
	private static Hashtable<String,Node> explored;
	private Node root;

	

	public IterativeDeepeningSearch(){
		
	}
	
	public void DLS(String[] args) throws Exception {
		int[] tiles = new int[BOARD_SIZE * BOARD_SIZE];
		
		for(int i = 0; i < args.length; i++)
			tiles[i] = Integer.parseInt(args[i]);
		
		root = new Node(tiles);
		
		metrics = new Metrics();
		metrics.set("StartTime", System.currentTimeMillis());
		explored = new Hashtable<String, Node>();
		
		for(int limit = 0; limit < INFINITY; limit++) {
			explored.clear();
			Node found = DLS(root, limit);
			if(found != null){
				Solution.write(found, root, metrics, explored);
				return;
			}
		}		
		System.out.println("Solution not found");
	}

	private Node DLS(Node node, int limit) throws Exception{
		if(!explored.containsKey(Integer.toString(node.hashCode()))) {
			explored.put(Integer.toString(node.hashCode()), node);
			
			if(Solution.check(node) && limit == 0)
				return node;
			
			if(limit > 0){
				List<Node> children = node.getChildren();
				for(Node child : children){
					Node found = DLS(child, limit - 1);
					if(found != null){
						return found;
					}
				}
			}
		} 
		Node notFound = null;
		return notFound;		
	}
	
	public static void main(String[] args) throws Exception{
		IterativeDeepeningSearch ids = new IterativeDeepeningSearch();
		ids.DLS(args);
	}
}