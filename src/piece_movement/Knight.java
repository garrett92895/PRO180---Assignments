package piece_movement;

public class Knight extends Piece{

	public Knight(boolean isDark, Position position) 
	{
		super(isDark, position, 'n');
		
	}

	@Override
	public boolean moveIsValid(Position endPosition, char[][] chessBoard,
			Piece[] darkPieces, Piece[] lightPieces) 
	{
		boolean valid = false;
		double row = (endPosition.getRow() - position.getRow());
		double column = (endPosition.getColumn() - position.getColumn());
		double slope = Math.abs(row / column);
		
		if((slope < .5001 && slope > .4999) || (slope < 2.0001 && slope > 1.9999))
		{
			if(chessBoard[endPosition.getRow()][endPosition.getColumn()] == '-')
			{
				valid = true;
			}
		}
		
		return valid;
	}

	@Override
	public boolean captureIsValid(Position endPosition, char[][] chessBoard,
			Piece[] darkPieces, Piece[] lightPieces) 
	{
		boolean valid = false;
		double row = (endPosition.getRow() - position.getRow());
		double column = (endPosition.getColumn() - position.getColumn());
		double slope = Math.abs(row / column);
		
		if((slope < .5001 && slope > .4999) || (slope < 2.0001 && slope > 1.9999))
		{
			if(isDark)
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
			else
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
		return valid;
	}

}
