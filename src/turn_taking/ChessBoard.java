package turn_taking;

public class ChessBoard 
{
	private final int BOARD_SIZE = 8;
	private char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
	
	public ChessBoard()
	{
		for(int i = 0; i < board.length; i++)
		{
			for(int x = 0; x < board[i].length; x++)
			{
				board[i][x] = '-';
			}
		}
	}
	
	/**Checks for a piece at a Position on the board
	 * 
	 * @param position The position that the board checks
	 * @return int - Returns the int colorModifier based off of the color of the piece on the position
	 * in question. If there is no piece, returns 0
	 */
	public int hasPieceOnPosition(Position position)
	{
		int colorModifier = 0;
		
		if(board[position.getRow()][position.getColumn()] != '-')
		{
			if(Character.isLowerCase(board[position.getRow()][position.getColumn()]))
			{
				colorModifier = 1;
			}
			else
			{
				colorModifier = -1;
			}
		}
		
		return colorModifier;
	}
}
