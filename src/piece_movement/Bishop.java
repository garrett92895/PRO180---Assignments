package piece_movement;

public class Bishop extends Piece{

	public Bishop(boolean isDark, Position position) 
	{
		super(isDark, position, 'b');
		
	}

	@Override
	public boolean moveIsValid(Position endPosition, char[][] chessBoard,
			Piece[] darkPieces, Piece[] lightPieces) 
	{
		boolean valid = false;
		if(Math.abs((position.getRow() - endPosition.getRow()) / (position.getColumn() - endPosition.getColumn())) < UPPER_BOUND
		&& Math.abs((position.getRow() - endPosition.getRow()) / (position.getColumn() - endPosition.getColumn())) > LOWER_BOUND)
		{
			valid = moveIsClear(endPosition, chessBoard, darkPieces, lightPieces);
		}
		
		return valid;
	}

	@Override
	public boolean captureIsValid(Position endPosition, char[][] chessBoard,
			Piece[] darkPieces, Piece[] lightPieces) 
	{
		boolean valid = false;
		if(Math.abs((position.getRow() - endPosition.getRow()) / (position.getColumn() - endPosition.getColumn())) < UPPER_BOUND
		&& Math.abs((position.getRow() - endPosition.getRow()) / (position.getColumn() - endPosition.getColumn())) > LOWER_BOUND)
		{
			valid = captureIsClear(endPosition, chessBoard, darkPieces, lightPieces);
		}
		
		return valid;
	}

}
