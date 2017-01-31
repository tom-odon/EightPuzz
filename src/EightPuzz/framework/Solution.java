package EightPuzz.framework;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Hashtable;

/*
 * Class for evaluation and printing of solutions. 
 */
public final class Solution {
	
	/*
	 * Checks for a valid solution by evaluating the hashcode of the goal state 
	 * against a given node.
	 */
	public static boolean check(Node current){
			if(current.hashCode() == 123804765)
				return true;
			else 
				return false;
		
	}

	/*
	 * Writes a solution to command-line output by taking in a given node, its root,
	 * the metrics of the algorithm run, and the explored hashtable from the run.
	 */
	public static void write(Node solution, Node root, Metrics metrics, Hashtable<String, Node> explored) {
		
		/*
		 * Gather the solution into a "stack" by moving up from goal node to parent,
		 * then printing the solution and stats.
		 */
			Deque<Node> solutionStack = new ArrayDeque<Node>();
			solutionStack.addFirst(solution);
			Node currentNode = explored.get(solution.getParent());
			
			while(currentNode.hashCode() != root.hashCode()){
				solutionStack.addFirst(currentNode);
				currentNode = explored.get(currentNode.getParent());
			}
			solutionStack.addFirst(root);
			
			metrics.set("PathLength", solutionStack.size());
			metrics.set("TotalCost", solution.getPathCost());
			
			//print out the solution
			while(!solutionStack.isEmpty()){
				Node top = solutionStack.removeFirst();
				System.out.print("[" + top.getTileConfig() + "] ");
				System.out.println(top.getAction().getMove() + ", " + 
								   top.getAction().getCost() + ", " + 
								   top.getPathCost());			
			}
			
			//stats
			metrics.set("EndTime", System.currentTimeMillis());
						
			System.out.println("Path length: " + metrics.get("PathLength"));
			System.out.println("Path cost: " + metrics.get("TotalCost"));
			System.out.println("Time in ms: " + 
					(Long.parseLong(metrics.get("EndTime"))
					- Long.parseLong(metrics.get("StartTime"))));
			System.out.println("Nodes explored: " + explored.size());
			System.out.println("Space: " + metrics.get("nodesOnFrontier"));
		
	}


		
}
