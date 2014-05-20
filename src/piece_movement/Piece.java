package piece_movement;

public abstract class Piece {
	protected boolean isDark;
	protected Position position;
	protected int moveCount;
	
	public Piece(boolean isDark, Position position)
	{
		this.isDark = isDark;
		this.position = position;
	}
	
	public abstract boolean moveIsValid(Position endPosition, char[] chessBoard, Piece[] darkPieces, Piece[] lightPieces);
	public abstract boolean captureIsValid(Position endPosition, char[] chessBoard, Piece[] darkPieces, Piece[] lightPieces);
}
