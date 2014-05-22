package piece_movement;

public class King extends Piece{

	public King(boolean isDark, Position position) 
	{
		super(isDark, position, 'k');
	}

	@Override
	public boolean moveIsValid(Position endPosition, char[][] chessBoard,
			Piece[] darkPieces, Piece[] lightPieces) 
	{
		boolean valid = false;
		
		if((endPosition.getRow() == position.getRow() + 1		//If the desired position is 1 row or less away from the current position
				|| endPosition.getRow() == position.getRow() - 1
				|| endPosition.getRow() == position.getRow()) 
				&& endPosition.getRow() < chessBoard.length
				&& endPosition.getRow() >= 0)
		{
			if((endPosition.getColumn() == position.getColumn() + 1		//If the desired position is 1 column or less away from the current position
					|| endPosition.getColumn() == position.getColumn() - 1
					|| endPosition.getColumn() == position.getColumn()) 
					&& endPosition.getColumn() < chessBoard.length
					&& endPosition.getColumn() >= 0)
			{
				boolean hasPiece = false;
				for(int i = 0; i < darkPieces.length; i++)				//Checks to see if there are any pieces on the desired position already
				{
					if(darkPieces[i] != null) 
					{
						if(darkPieces[i].getPosition().getRow() == endPosition.getRow()
								&& darkPieces[i].getPosition().getColumn() == endPosition.getColumn())
						{
							hasPiece = true;
						}
					}
					if(lightPieces[i] != null)
					{
						if(lightPieces[i].getPosition().getRow() == endPosition.getRow()
								&& lightPieces[i].getPosition().getColumn() == endPosition.getColumn())
						{
							hasPiece = true;
						}
					}
				}
				
				if(!hasPiece)
				{
					valid = true;
					moveCount++;
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
		
		if((endPosition.getRow() == position.getRow() + 1		//If the desired position is 1 row or less away from the current position
				|| endPosition.getRow() == position.getRow() - 1
				|| endPosition.getRow() == position.getRow()) 
				&& endPosition.getRow() < chessBoard.length)
		{
			if((endPosition.getColumn() == position.getColumn() + 1		//If the desired position is 1 column or less away from the current position
					|| endPosition.getColumn() == position.getColumn() - 1
					|| endPosition.getColumn() == position.getColumn()) 
					&& endPosition.getColumn() < chessBoard.length)
			{
				boolean hasOpposingPiece = false;
				for(int i = 0; i < darkPieces.length; i++)				//Checks to see if there are any pieces on the desired position already
				{														//and if so, determines whether or not it is a piece of the enemy's color
					if(darkPieces[i] != null)
					{
						if(darkPieces[i].getPosition().getRow() == endPosition.getRow()
								&& darkPieces[i].getPosition().getColumn() == endPosition.getColumn())
						{
							if(!isDark)
							{
								hasOpposingPiece = true;
							}
						}
					}
					if(lightPieces[i] != null)
					{
						if(lightPieces[i].getPosition().getRow() == endPosition.getRow()
								&& lightPieces[i].getPosition().getColumn() == endPosition.getColumn())
						{
							if(isDark)
							{
								hasOpposingPiece = true;
							}
						}
					}
				}
				
				if(hasOpposingPiece)
				{
					valid = true;
					moveCount++;
				}
			}
		}
		return valid;
	}
	
	public boolean castleIsValid(Position endPosition, char[][] chessBoard,
			Piece[] darkPieces, Piece[] lightPieces)
	{
		boolean valid = false;
		
		if(moveCount == 0)
		{
			if(isDark)
			{
				if(endPosition.getRow() == 0)
				{
					if(endPosition.getColumn() == 6 || endPosition.getColumn() == 2)
					{
						valid = moveIsClear(endPosition, chessBoard, darkPieces, lightPieces);
					}
				}
			}
			else if(!isDark)
			{
				if(endPosition.getRow() == 7)
				{
					if(endPosition.getColumn() == 6 || endPosition.getColumn() == 2)
					{
						valid = moveIsClear(endPosition, chessBoard, darkPieces, lightPieces);
					}
				}
			}
		}	
		if(valid)
		{
			moveCount++;
		}
		
		return valid;
	}

}
