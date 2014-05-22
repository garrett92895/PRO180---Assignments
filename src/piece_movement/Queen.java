package piece_movement;

public class Queen extends Piece{

	public Queen(boolean isDark, Position position) {
		super(isDark, position, 'q');
		
	}

	@Override
	public boolean moveIsValid(Position endPosition, char[][] chessBoard,
			Piece[] darkPieces, Piece[] lightPieces) {
		return moveIsClear(endPosition, chessBoard, darkPieces, lightPieces);
	}

	@Override
	public boolean captureIsValid(Position endPosition, char[][] chessBoard,
			Piece[] darkPieces, Piece[] lightPieces) {
		return captureIsClear(endPosition, chessBoard, darkPieces, lightPieces);
	}

}
