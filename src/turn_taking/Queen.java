package turn_taking;

import java.util.ArrayList;

public class Queen extends Piece{

	public Queen(int isDark, Position position) {
		super(isDark, position, 'q');
		
	}

	@Override
	public boolean moveIsValid(Position endPosition, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces) {
		return moveIsClear(endPosition, chessBoard);
	}

	@Override
	public boolean captureIsValid(Position endPosition, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces) {
		return captureIsClear(endPosition, chessBoard, darkPieces, lightPieces);
	}

}
