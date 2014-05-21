package piece_movement;

public class Pawn extends Piece{

	public Pawn(boolean isDark, Position position) 
	{
		super(isDark, position, 'p');
	}

	@Override
	public boolean moveIsValid(Position endPosition, char[][] chessBoard,
			Piece[] darkPieces, Piece[] lightPieces) 
	{
		boolean valid = false;
		
		if(isDark)
		{
			if(moveCount == 0)
			{
				if(endPosition.getRow() == position.getRow() + 2)
				{
					if(endPosition.getRow() < chessBoard.length)
					{
						if(chessBoard[endPosition.getColumn()][endPosition.getRow()] == '-'
								&& chessBoard[endPosition.getColumn()][endPosition.getRow() + 1] == '-')
						{
							valid = true;
						}
					}
				}
			}
			if(endPosition.getRow() == position.getRow() + 1)
			{
				if(endPosition.getRow() < chessBoard.length)
				{
					if(chessBoard[endPosition.getColumn()][endPosition.getRow()] == '-')
					{
						valid = true;
					}
				}
			}
		}
		if(!isDark)
		{
			if(moveCount == 0)
			{
				if(endPosition.getRow() == position.getRow() - 2)
				{
					if(endPosition.getRow() >= 0)
					{
						if(chessBoard[endPosition.getColumn()][endPosition.getRow()] == '-'
							&& chessBoard[endPosition.getColumn()][endPosition.getRow() - 1] == '-')
						{
							valid = true;
						}
					}
				}
			}
			if(endPosition.getRow() == position.getRow() + 1)
			{
				if(endPosition.getRow() < chessBoard.length)
				{
					if(chessBoard[endPosition.getColumn()][endPosition.getRow()] == '-')
					{
						valid = true;
					}
				}
			}
		}
		
		return valid;
	}

	@Override
	public boolean captureIsValid(Position endPosition, char[][] chessBoard,
			Piece[] darkPieces, Piece[] lightPieces) 
	{
		boolean valid = false;
		
		if(isDark)
		{
			if(moveCount == 0)
			{
				if(endPosition.getRow() == position.getRow() + 2)
				{
					if(endPosition.getRow() < chessBoard.length)
					{
						if(chessBoard[endPosition.getColumn()][endPosition.getRow()] == '-'
								&& chessBoard[endPosition.getColumn()][endPosition.getRow() + 1] == '-')
						{
							valid = true;
						}
					}
				}
			}
			if(endPosition.getRow() == position.getRow() + 1)
			{
				if(endPosition.getRow() < chessBoard.length)
				{
					if(chessBoard[endPosition.getColumn()][endPosition.getRow()] == '-')
					{
						valid = true;
					}
				}
			}
		}
		if(!isDark)
		{
			if(moveCount == 0)
			{
				if(endPosition.getRow() == position.getRow() - 2)
				{
					if(endPosition.getRow() >= 0)
					{
						if(chessBoard[endPosition.getColumn()][endPosition.getRow()] == '-'
							&& chessBoard[endPosition.getColumn()][endPosition.getRow() - 1] == '-')
						{
							valid = true;
						}
					}
				}
			}
			if(endPosition.getRow() == position.getRow() + 1)
			{
				if(endPosition.getRow() < chessBoard.length)
				{
					if(chessBoard[endPosition.getColumn()][endPosition.getRow()] == '-')
					{
						valid = true;
					}
				}
			}
		}
		
		return valid;
	}

}
