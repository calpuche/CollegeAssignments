package tictactoe;

public class TicTacToeBoardUtils_Alpuche {
	public static int getSpotOnTicTacToeBoard(int row, int column) {
		int spotOnArray = 0;
		if (row == 0) {
			spotOnArray = row + column;
		} else if (row == 1) {
			spotOnArray = (row * 3) + column;
		} else if (row == 2) {
			spotOnArray = (row * 3) + column;
		}
		return spotOnArray;

	}
}
