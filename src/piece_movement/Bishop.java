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
		
		return false;
	}

	@Override
	public boolean captureIsValid(Position endPosition, char[][] chessBoard,
			Piece[] darkPieces, Piece[] lightPieces) 
	{
		
		return false;
	}

}
