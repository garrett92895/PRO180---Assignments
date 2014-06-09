package interactive_play;

import java.util.ArrayList;

public class Queen extends Piece{

	public Queen(int isDark, Position position) {
		super(isDark, position, 'q');
		
	}

	@Override
	public boolean moveIsValid(Position endPosition, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces) {
		return moveIsClear(endPosition, chessBoard, darkPieces, lightPieces);
	}

	@Override
	public boolean captureIsValid(Position endPosition, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces, boolean darkTurn) {
		return captureIsClear(endPosition, chessBoard, darkPieces, lightPieces, darkTurn);
	}

}
