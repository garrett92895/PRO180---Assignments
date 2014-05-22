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
						if(endPosition.getColumn() == position.getColumn())
						{
							if(chessBoard[endPosition.getRow()][endPosition.getColumn()] == '-'
									&& chessBoard[endPosition.getRow() - 1][endPosition.getColumn()] == '-')
							{
								valid = true;
							}
						}
					}
				}
			}
			if(endPosition.getRow() == position.getRow() + 1)
			{
				if(endPosition.getRow() < chessBoard.length)
				{
					if(endPosition.getColumn() == position.getColumn())
					{
						if(chessBoard[endPosition.getRow()][endPosition.getColumn()] == '-')
						{
							valid = true;
						}
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
						if(endPosition.getColumn() == position.getColumn())
						{
							if(chessBoard[endPosition.getRow()][endPosition.getColumn()] == '-'
								&& chessBoard[endPosition.getRow() - 1][endPosition.getColumn()] == '-')
							{
								valid = true;
							}
						}
					}
				}
			}
			if(endPosition.getRow() == position.getRow() - 1)
			{
				if(endPosition.getRow() < chessBoard.length)
				{
					if(endPosition.getColumn() == position.getColumn())
					{
						if(chessBoard[endPosition.getRow()][endPosition.getColumn()] == '-')
						{
							valid = true;
						}
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
			if(endPosition.getRow() - 1 == position.getRow())
			{
				if(Math.abs(endPosition.getColumn() - position.getColumn()) == 1)
				{
					for(int i = 0; i < lightPieces.length; i++)
					{
						if(lightPieces[i] != null)
						{
							if(lightPieces[i].getPosition().compareTo(endPosition) == 0)
							{
								valid = true;
							}
						}
					}
				}
			}
		}
		else
		{
			if(position.getRow() - 1 == endPosition.getRow())
			{
				if(Math.abs(endPosition.getColumn() - position.getColumn()) == 1)
				{
					for(int i = 0; i < darkPieces.length; i++)
					{
						if(darkPieces[i] != null)
						{
							if(darkPieces[i].getPosition().compareTo(endPosition) == 0)
							{
								valid = true;
							}
						}
					}
				}
			}
		}
		
		return valid;
	}

}
