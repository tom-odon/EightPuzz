package EightPuzz.framework;

/*
 * String representation of the move required to achieve the current state of the
 * tile board. Permissible strings are "left", "right", "up", and "down".
 */
public class Action {
	private String move;
	private double cost;
	
	//Class constructor.
	public Action(String move, int cost) throws Exception {
		this.setMove(move);
		this.setCost(cost);
	}
	
	//Set the cost of the move.
	private void setCost(int cost) {
		this.cost = cost;
	}
	
	//Return the cost of the move.
	public double getCost(){
		return cost;
		
	}
	
	//Return the string representation of the move.
	public String getMove() {
		return move;
	}

	//Set the string representation of the move, checks for valid syntax.
	public void setMove(String move) throws Exception {
		if (move.equals("left") ||
			move.equals("right") ||
			move.equals("up") ||
			move.equals("down") ||
			move.equals("none")){
			this.move = move;
		} else {
			throw new Exception("Not a valid move");
		}
	}

	//Another accessor for cost.
	public double cost() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
