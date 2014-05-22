package piece_movement;

public class Rook extends Piece{

	public Rook(boolean isDark, Position position) 
	{
		super(isDark, position, 'r');
	}

	@Override
	public boolean moveIsValid(Position endPosition, char[][] chessBoard,
			Piece[] darkPieces, Piece[] lightPieces) 
	{
		boolean valid = false;
		if(endPosition.getColumn() == position.getColumn()
				|| endPosition.getRow() == position.getRow())
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
		if(endPosition.getColumn() == position.getColumn()
				|| endPosition.getRow() == position.getRow())
		{
			valid = captureIsClear(endPosition, chessBoard, darkPieces, lightPieces);
		}
		return valid;
	}

}
