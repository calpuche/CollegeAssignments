package tictactoe;

public class TicTacToeBoardImpl_Alpuche implements TicTacToeBoard
{
	//Symbolics:
	protected static final int NO_MOVE = -1;
	protected static final int NO_MATCH = -1;

	protected int[] movesArray;
	
	/* EXAMPLE:
	 * new TicTacToeBoardImpl_Skeleton() is
	 *  | | 
	 * _____
	 *  | | 
	 * -----
	 *  | | 
	 * and results in movesArray = [-1,-1,-1,-1,-1,-1,-1,-1,-1]
	 */
	public TicTacToeBoardImpl_Alpuche() {
		final int CELL_COUNT = ROW_COUNT * COLUMN_COUNT;
		movesArray = new int[CELL_COUNT];
		for (int i = 0; i < CELL_COUNT; i++) {
			movesArray[i] = NO_MOVE;
		}
	}

	/* EXAMPLE:
	 * EXAMPLE 1:
	 * [-1,-1,-1,-1,-1,-1,-1,-1,-1].getMark(0, 1) == null
	 * [NO_MOVE,NO_MOVE,NO_MOVE,NO_MOVE,NO_MOVE,NO_MOVE,NO_MOVE,NO_MOVE,NO_MOVE].getMark(0,1) == null
	 * EXAMPLE 2:
	 * [ 3, 5, 7,-1,-1,-1,-1,-1,-1].getMark(1,0) == Mark.X
	 * EXAMPLE 3:
	 * [ 4, 3, 7, 5,-1,-1,-1,-1,-1].getMark(1,1) == Mark.X
	 * [ 4, 3, 7, 5,-1,-1,-1,-1,-1].getMark(1,2) == Mark.O
	 */
	public Mark getMark(int row, int column) {
		assert row >= 0 : "row = " + row + "<0!!!";
		assert row < 4 : "row = " + row + "> 3!!!";
		assert column >= 0 : "column = " + column + "<0!!!";
		assert column < 4 : "column = " + column + ">3!!!";

		Mark returnMark;
		int rawTicTacValue = TicTacToeBoardUtils_Alpuche.getSpotOnTicTacToeBoard(row, column);
		int spotOnArray = 0;
		for (int i = 0; i < movesArray.length; i++) {
			if (movesArray[i] == rawTicTacValue) {
				spotOnArray = i;
				break;

			}
			if (movesArray[i] == -1) {
				spotOnArray = -1;
			}
		}

		if (spotOnArray == -1) {
			returnMark = null;
		} else if (spotOnArray % 2 == 0) {
			returnMark = Mark.X;
		} else {
			returnMark = Mark.O;
		}
		return returnMark;
	}

	/* EXAMPLE:
	 * EXAMPLE 1:
	 * [-1,-1,-1,-1,-1,-1,-1,-1,-1].getTurn() == Mark.X
	 * [NO_MOVE,NO_MOVE,NO_MOVE,NO_MOVE,NO_MOVE,NO_MOVE,NO_MOVE,NO_MOVE,NO_MOVE].getTurn() == Mark.X
	 * EXAMPLE 2:
	 * [ 1, 3, 8,-1,-1,-1,-1,-1,-1].getTurn() == Mark.O
	 * EXAMPLE 3:
	 * STUDENT: Add example for which getTurn() is null
	 * [0,3,4,2,7,-1,-1,-1,-1] == null
	 * EXAMPLE 4:
	 * STUDENT: Add a really extreme ("corner case") example
	 * [0,1,2,3,4,5,-1,-1,-1] == Mark.X
	 */
	public Mark getTurn() {
		if (isGameOver() == true) {
			return null;
		}
		Mark returnMark;

		int lastSpot = getNumMarks();
		if (lastSpot % 2 == 0) {
			returnMark = Mark.X;
		} else {
			returnMark = Mark.O;
		}
		return returnMark;
	}

	//part of post: rv == null <==> game ended in a tie
	/* EXAMPLES:
	 * STUDENT: Add three examples at the "movesArray level"
	 * EX1) 
	 * [0,1,3,2,6,-1,-1,-1] = Mark.X
	 * EX2)
	 * [0,1,2,3,4,5,7,6,8]==null
	 * EX3)
	 * [1,3,2,5,7,4,-1,-1,-1]== Mark.O
	 */
	public Mark getWinner() {
		assert isGameOver() == true : "Game is not over!";
		// left column win
		if (getMark(0, 0) == Mark.X && getMark(1, 0) == Mark.X && getMark(2, 0) == Mark.X) {
			return Mark.X;
		}

		else if (getMark(0, 0) == Mark.O && getMark(1, 0) == Mark.O && getMark(2, 0) == Mark.O) {
			return Mark.O;
		}
		// middle coloumn win
		else if (getMark(0, 1) == Mark.X && getMark(1, 1) == Mark.X && getMark(2, 1) == Mark.X) {

			return Mark.X;
		}

		else if (getMark(0, 1) == Mark.O && getMark(1, 1) == Mark.O && getMark(2, 1) == Mark.O) {
			return Mark.O;
		}
		// right column win
		else if (getMark(0, 2) == Mark.X && getMark(1, 2) == Mark.X && getMark(2, 2) == Mark.X) {

			return Mark.X;
		}

		else if (getMark(0, 2) == Mark.O && getMark(1, 2) == Mark.O && getMark(2, 2) == Mark.O) {
			return Mark.O;
		}

		// top row win
		else if (getMark(0, 0) == Mark.X && getMark(0, 1) == Mark.X && getMark(0, 2) == Mark.X) {

			return Mark.X;
		}

		else if (getMark(0, 0) == Mark.O && getMark(0, 1) == Mark.O && getMark(0, 2) == Mark.O) {
			return Mark.O;
		}
		// middle row win
		else if (getMark(1, 0) == Mark.X && getMark(1, 1) == Mark.X && getMark(1, 2) == Mark.X) {

			return Mark.X;
		} else if (getMark(1, 0) == Mark.O && getMark(1, 1) == Mark.O && getMark(1, 2) == Mark.O) {
			return Mark.O;
		}
		// bottom row win
		else if (getMark(2, 0) == Mark.X && getMark(2, 1) == Mark.X && getMark(2, 2) == Mark.X) {

			return Mark.X;
		} else if (getMark(2, 0) == Mark.O && getMark(2, 1) == Mark.O && getMark(2, 2) == Mark.O) {
			return Mark.O;
		}
		// right diagonal win
		else if (getMark(0, 0) == Mark.X && getMark(1, 1) == Mark.X && getMark(2, 2) == Mark.X) {

			return Mark.X;
		} else if (getMark(0, 0) == Mark.O && getMark(1, 1) == Mark.O && getMark(2, 2) == Mark.O) {
			return Mark.O;
		}
		// left diagonal win
		else if (getMark(0, 2) == Mark.X && getMark(1, 1) == Mark.X && getMark(2, 0) == Mark.X) {

			return Mark.X;
		} else if (getMark(0, 2) == Mark.O && getMark(1, 1) == Mark.O && getMark(2, 0) == Mark.O) {
			return Mark.O;
		}

		else {
			return null;
		}
	}

	/* EXAMPLES:
	 * EXAMPLE 1:
	 * [ 4, 3, 0, 5, 8,-1,-1,-1,-1].isGameOver() == true
	 * EXAMPLE 2:
	 * [ 1, -1, -1, -1, -1,-1,-1,-1,-1].isGameOver()== false
	 * EXAMPLE 3:
	 * [1,3,2,5,7,4,-1,-1,-1].isGameOver()==true
	 */
	public boolean isGameOver() {
		if (getMark(0, 0) == Mark.X && getMark(1, 0) == Mark.X && getMark(2, 0) == Mark.X) {
			return true;
		}

		else if (getMark(0, 0) == Mark.O && getMark(1, 0) == Mark.O && getMark(2, 0) == Mark.O) {
			return true;
		}
		// middle coloumn win
		else if (getMark(0, 1) == Mark.X && getMark(1, 1) == Mark.X && getMark(2, 1) == Mark.X) {
			return true;
		}

		else if (getMark(0, 1) == Mark.O && getMark(1, 1) == Mark.O && getMark(2, 1) == Mark.O) {
			return true;
		}
		// right column win
		else if (getMark(0, 2) == Mark.X && getMark(1, 2) == Mark.X && getMark(2, 2) == Mark.X) {
			return true;
		}

		else if (getMark(0, 2) == Mark.O && getMark(1, 2) == Mark.O && getMark(2, 2) == Mark.O) {
			return true;
		}

		// top row win
		else if (getMark(0, 0) == Mark.X && getMark(0, 1) == Mark.X && getMark(0, 2) == Mark.X) {
			return true;
		}

		else if (getMark(0, 0) == Mark.O && getMark(0, 1) == Mark.O && getMark(0, 2) == Mark.O) {
			return true;
		}
		// middle row win
		else if (getMark(1, 0) == Mark.X && getMark(1, 1) == Mark.X && getMark(1, 2) == Mark.X) {
			return true;
		} else if (getMark(1, 0) == Mark.O && getMark(1, 1) == Mark.O && getMark(1, 2) == Mark.O) {
			return true;
		}
		// bottom row win
		else if (getMark(2, 0) == Mark.X && getMark(2, 1) == Mark.X && getMark(2, 2) == Mark.X) {
			return true;
		} else if (getMark(2, 0) == Mark.O && getMark(2, 1) == Mark.O && getMark(2, 2) == Mark.O) {
			return true;
		}
		// right diagonal win
		else if (getMark(0, 0) == Mark.X && getMark(1, 1) == Mark.X && getMark(2, 2) == Mark.X) {
			return true;
		} else if (getMark(0, 0) == Mark.O && getMark(1, 1) == Mark.O && getMark(2, 2) == Mark.O) {
			return true;
		}
		// left diagonal win
		else if (getMark(0, 2) == Mark.X && getMark(1, 1) == Mark.X && getMark(2, 0) == Mark.X) {
			return true;
		} else if (getMark(0, 2) == Mark.O && getMark(1, 1) == Mark.O && getMark(2, 0) == Mark.O) {
			return true;
		}
		else if (movesArray[movesArray.length-1] != NO_MOVE){
			return true;
		}
		for (int i = 0; i <= movesArray.length; i++) {
			if (movesArray[i] == NO_MOVE) {
				return false;
			}
		}
		return false;
	}

	/* EXAMPLES:
	 * EXAMPLE 1:
	 * [-1,-1,-1,-1,-1,-1,-1,-1,-1].setMark(2,2) --> [8,-1,-1,-1,-1,-1,-1,-1,-1]
	   EXAMPLE 2:
	   [8,-1,-1,-1,-1,-1,-1,-1,-1].setMark(0,0) -- >[8,0,-1,-1,-1,-1,-1,-1,-1]
	   EXAMPLE 3:
	   [8,0,-1,-1,-1,-1,-1,-1,-1].setMark(1,1)-->[8,0,4,-1,-1,-1,-1,-1,-1]
	 * STUDENT: add two other examples
	 */
	public void setMark(int row, int column) {
		assert row >= 0 : "row = " + row + "<0!!!";
		assert row < 4 : "row = " + row + "> 3!!!";
		assert column >= 0 : "column = " + column + "<0!!!";
		assert column < 4 : "column = " + column + ">3!!!";

		int rawTicTacValue = TicTacToeBoardUtils_Alpuche.getSpotOnTicTacToeBoard(row, column);

		int lastSpot = getNumMarks();

		movesArray[lastSpot] = rawTicTacValue;
	}

	public String toString() {
	String returnString = "";	
	//print column

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (getMark(i, j) == null) {
					returnString = returnString + " ";

				} else {
					returnString = returnString + getMark(i, j);
				}
				if (j < 2) {
					returnString = returnString + "|";
				}
			}
			if (i < 2) {
				returnString = returnString + "\n";
				returnString = returnString + "------";
				returnString = returnString + "\n";
			}

		}
		return returnString;

	}

	public int getNumMarks() {
		int lastSpot = 0;
		int i;
		for (i = 0; movesArray[i] != NO_MOVE; i++) {
			lastSpot = i;
			lastSpot++;
		}
		return lastSpot;

	}
}