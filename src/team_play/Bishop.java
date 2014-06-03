package team_play;

import java.util.ArrayList;

public class Bishop extends Piece{

	public Bishop(int isDark, Position position) 
	{
		super(isDark, position, 'b');
		
	}

	@Override
	public boolean moveIsValid(Position endPosition, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces) 
	{
		boolean valid = false;
		if(Math.abs((position.getRow() - endPosition.getRow()) / (position.getColumn() - endPosition.getColumn())) < UPPER_BOUND
		&& Math.abs((position.getRow() - endPosition.getRow()) / (position.getColumn() - endPosition.getColumn())) > LOWER_BOUND)
		{
			valid = moveIsClear(endPosition, chessBoard);
		}
		
		return valid;
	}

	@Override
	public boolean captureIsValid(Position endPosition, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces) 
	{
		boolean valid = false;
		if(position.getColumn() - endPosition.getColumn() != 0
		&& Math.abs((position.getRow() - endPosition.getRow()) / (position.getColumn() - endPosition.getColumn())) < UPPER_BOUND
		&& Math.abs((position.getRow() - endPosition.getRow()) / (position.getColumn() - endPosition.getColumn())) > LOWER_BOUND)
		{
			valid = captureIsClear(endPosition, chessBoard, darkPieces, lightPieces);
		}
		
		return valid;
	}

}
