package tictactoe;

//Note: By convention, the player with symbol Mark.X always goes first
public interface TicTacToeBoard 
{
	public final static int ROW_COUNT = 3;
	public final static int COLUMN_COUNT = 3;
	
	//part of pre: 0 <= row < ROW_COUNT && 0 <= column < COLUMN_COUNT
	//part of post: rv == null <==> the (row, column) spot on the board is empty
	/* EXAMPLES:
	 * EXAMPLE 1:
	 *  | | 
	 * _____
	 * X| |O
	 * -----
	 *  |X| .getMark(0,0) == null
	 *
	 * EXAMPLE 2:
	 *  | | 
	 * _____
	 * O|X|O
	 * -----
	 *  |X| .getMark(1,1) == Mark.X
	 *
	 * EXAMPLE 3:
	 *  | | 
	 * _____
	 *  |X|O
	 * -----
	 *  | |X.getMark(1,2) == Mark.O
	 */
	public Mark getMark(int row, int column);

	//part of pre: 0 <= row < ROW_COUNT && 0 <= column < COLUMN_COUNT
	//part of pre: getMark(row, column) == null
	//part of pre: !isGameOver()
	/* EXAMPLES:
	 * EXAMPLE 1:
	 * X| | 									X| | 
	 * ------									------
	 * O|X|O 							       	O|X|O
	 * -----									------
	 *  | | .setMark(2,2)  ==> this_post is 	 | |X
	 * STUDENT: Add two other examples
	 *EXAMPLE 2:
	 * X| | 									X| |X
	 * ------									------
	 * O|X|O 							       	O|X|O
	 * -----									------
	 *  | | .setMark(0,2)  ==> this_post is 	 | |
	 * EXAMPLE 1:
	 * X| |X									X| |X
	 * ------									------
	 * O|X|O 							       	O|X|O
	 * -----									------
	 *  | | .setMark(2,1)  ==> this_post is 	 |O|
	 */
	public void setMark(int row, int column);

	//part of post: rv == null <==> it is neither player's turn (i.e. game is over)
	/* EXAMPLES:
	 * EXAMPLE 1:
	 *  | | 
	 * ------
	 *  | | 
	 * ------
	 *  | | .getTurn() == Mark.X
	 *
	 * EXAMPLE 2:
	 *  | | 
	 * ------
	 *  |X|O
	 * -----
	 *  | |X.getTurn() == Mark.O
	 * STUDENT: Add an example for which getTurn() returns null
	 *  * EXAMPLE 3:
	 * X|O|X 
	 * ------
	 *  X|X|O
	 * -----
	 * O|X|O.getTurn() == null
	 * STUDENT QUESTION: Should null be a symbolic?
	 * no because null in getTurn is different then null in getWinner
	 */
	public Mark getTurn();
	
	//part of post: See Tic-tac-toe rules in order to fill this out
	/* EXAMPLES:
	 * EXAMPLE 1:
	 * X| | 
	 * ------
	 * O|X|O
	 * -----
	 *  | |X.isGameOver() == true
	 * STUDENT: Add two examples
	 * * EXAMPLE 2:
	 * X| | 
	 * ------
	 * O|X|O
	 * -----
	 *  | | .isGameOver() == false
	 *  * EXAMPLE 3:
	* X|O|X 
	* ------
	 *X|X|O
	 *-----
	 *O|X|O.isGameOver() == true
	 */ 
	public boolean isGameOver();
	
	//part of pre: isGameOver()
	//part of post: rv == null <==> neither player won (i.e. the game ended in a tie)
	/* EXAMPLES:
	 * STUDENT: Add three examples
	 * /* EXAMPLES:
	 * EXAMPLE 1:
	 * X| | 
	 * ------ 
	 * O|X|O
	 * -----
	 *  | |X .getWinner() == X
	 * EXAMPLE 2:
	 * X| | 
	 * ------
	 * O|O|O
	 * -----
	 * X|X| .getWinner() == O
	 * EXAMPLE 3:
	* X|O|X 
	* ------
	 *X|X|O
	 *-----
	 *O|X|O .getWinner() == null
	 */
	public Mark getWinner();
	
	public String toString();
}

