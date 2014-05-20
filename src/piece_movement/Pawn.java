package piece_movement;

public class Pawn extends Piece{

	public Pawn(boolean isDark, Position position) 
	{
		super(isDark, position);
		
	}

	@Override
	public boolean moveIsValid(Position endPosition, char[] chessBoard,
			Piece[] darkPieces, Piece[] lightPieces) 
	{
		
		return false;
	}

	@Override
	public boolean captureIsValid(Position endPosition, char[] chessBoard,
			Piece[] darkPieces, Piece[] lightPieces) 
	{
		
		return false;
	}

}
