package interactive_play;

import java.util.ArrayList;

public class Knight extends Piece{

	public Knight(int isDark, Position position) 
	{
		super(isDark, position, 'n');
		
	}

	@Override
	public boolean moveIsValid(Position endPosition, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces) 
	{
		boolean valid = false;
		double row = (endPosition.getRow() - position.getRow());
		double column = (endPosition.getColumn() - position.getColumn());
		double slope = 0;
		
		if(Math.abs(row) <= 2 && Math.abs(column) <= 2)
		{
			slope = Math.abs(row / column);
		}
		
		if(((slope < .5001 && slope > .4999) || (slope < 2.0001 && slope > 1.9999))
				&& ChessFunctions.findPiece(endPosition, chessBoard, darkPieces, lightPieces) == null)
		{
			valid = true;
		}
		
		return valid;
	}

	@Override
	public boolean captureIsValid(Position endPosition, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces, boolean darkTurn) 
	{
		boolean valid = false;
		double row = (endPosition.getRow() - position.getRow());
		double column = (endPosition.getColumn() - position.getColumn());
		double slope = 0;
		
		if(Math.abs(row) <= 2 && Math.abs(column) <= 2)
		{
			slope = Math.abs(row / column);
		}
		
		if(((slope < .5001 && slope > .4999) || (slope < 2.0001 && slope > 1.9999))
				&& ChessFunctions.findPiece(endPosition, chessBoard, darkPieces, lightPieces) != null 
				&& ChessFunctions.findPiece(endPosition, chessBoard, darkPieces, lightPieces).getColorModifier() == (colorModifier * -1))
		{
			valid = true;
		}
		return valid;
	}

}
