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
		if(Math.abs((position.getRow() - endPosition.getRow()) / (position.getColumn() - endPosition.getColumn())) < 1.0001
		&& Math.abs((position.getRow() - endPosition.getRow()) / (position.getColumn() - endPosition.getColumn())) > .9999)
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
		if(Math.abs((position.getRow() - endPosition.getRow()) / (position.getColumn() - endPosition.getColumn())) < 1.0001
		&& Math.abs((position.getRow() - endPosition.getRow()) / (position.getColumn() - endPosition.getColumn())) > .9999)
		{
			valid = captureIsClear(endPosition, chessBoard, darkPieces, lightPieces);
		}
		
		return valid;
	}

}
