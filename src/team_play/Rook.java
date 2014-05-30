package team_play;

public class Rook extends Piece{

	public Rook(int isDark, Position position) 
	{
		super(isDark, position, 'r');
	}

	@Override
	public boolean moveIsValid(Position endPosition, ChessBoard chessBoard, Piece[] darkPieces, Piece[] lightPieces) 
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
	public boolean captureIsValid(Position endPosition, ChessBoard chessBoard, Piece[] darkPieces, Piece[] lightPieces) 
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
