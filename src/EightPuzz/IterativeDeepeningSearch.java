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
	private Hashtable<String,Node> explored;
	private Deque<Node> frontier;
	private Node root;
	private Node cutoff;
	

	public IterativeDeepeningSearch(){
		
	}
	
	public void DLS(String[] args) throws Exception{
		int[] tiles = new int[BOARD_SIZE * BOARD_SIZE];
		
		for(int i = 0; i < args.length; i++){
			tiles[i] = Integer.parseInt(args[i]);
			root = new Node(tiles);
		}
		
		for(int limit = 0; limit < INFINITY; limit++) {
			Node found = DLS(root, limit);
			if(found != null){
				Solution.write(found, root, metrics);
				return;
			}
		}		
		System.out.println("Solution not found");
	}

	private Node DLS(Node node, int limit) throws Exception{
		System.out.println(node.getTileConfig());
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
		Node notFound = null;
		return notFound;		
	}
	
	public static void main(String[] args) throws Exception{
		IterativeDeepeningSearch ids = new IterativeDeepeningSearch();
		ids.DLS(args);
	}
}