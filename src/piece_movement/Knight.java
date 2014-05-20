package piece_movement;

public class Knight extends Piece{

	public Knight(boolean isDark, Position position) 
	{
		super(isDark, position);
		
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
