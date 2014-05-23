package turn_taking;

public class Queen extends Piece{

	public Queen(int isDark, Position position) {
		super(isDark, position, 'q');
		
	}

	@Override
	public boolean moveIsValid(Position endPosition, ChessBoard chessBoard, Piece[] darkPieces, Piece[] lightPieces) {
		return moveIsClear(endPosition, chessBoard, darkPieces, lightPieces);
	}

	@Override
	public boolean captureIsValid(Position endPosition, ChessBoard chessBoard, Piece[] darkPieces, Piece[] lightPieces) {
		return captureIsClear(endPosition, chessBoard, darkPieces, lightPieces);
	}

}
