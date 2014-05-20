package piece_movement;

public class Rook extends Piece{

	public Rook(boolean isDark, Position position) 
	{
		super(isDark, position);
		// TODO Auto-generated constructor stub
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
