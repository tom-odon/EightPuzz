package EightPuzz.framework;

/*
 * String representation of the move required to achieve the current state of the
 * tile board. Permissible strings are "left", "right", "up", and "down".
 */
public class Action {
	private String move;
	private double cost;
	
	public Action(String move, int cost) throws Exception {
		this.setMove(move);
		this.setCost(cost);
	}
	
	private void setCost(int cost) {
		this.cost = cost;
	}

	public double getCost(){
		return cost;
		
	}
	public String getMove() {
		return move;
	}

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

	public double cost() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
