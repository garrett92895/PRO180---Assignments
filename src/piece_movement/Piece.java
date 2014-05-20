package piece_movement;

public abstract class Piece {
	protected boolean isDark;
	protected Position position;
	protected int moveCount;
	
	public Piece(boolean isDark, Position position)
	{
		this.isDark = isDark;
		this.position = position;
		moveCount = 0;
	}
	
	public abstract boolean moveIsValid(Position endPosition, char[][] chessBoard, Piece[] darkPieces, Piece[] lightPieces);
	public abstract boolean captureIsValid(Position endPosition, char[][] chessBoard, Piece[] darkPieces, Piece[] lightPieces);

	public boolean isDark() {
		return isDark;
	}

	public void setDark(boolean isDark) {
		this.isDark = isDark;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getMoveCount() {
		return moveCount;
	}

	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}
}
