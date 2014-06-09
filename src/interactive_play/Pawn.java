package interactive_play;

import java.util.ArrayList;

public class Pawn extends Piece{

	public Pawn(int isDark, Position position) 
	{
		super(isDark, position, 'p');
	}

	@Override
	public boolean moveIsValid(Position endPosition, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces) 
	{
		boolean valid = false;
		if(moveCount == 0 && endPosition.getRow() == position.getRow() + (2 * colorModifier)
				&& endPosition.getColumn() == position.getColumn()
				&& ChessFunctions.findPiece(endPosition, chessBoard, darkPieces, lightPieces) == null)
		{
			valid = true;
		}
		else if(endPosition.getRow() == position.getRow() + colorModifier
				&& endPosition.getColumn() == position.getColumn()
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
		
		if(endPosition.getRow() - colorModifier == position.getRow())
		{
			if(Math.abs(endPosition.getColumn() - position.getColumn()) == 1)
			{
				if(ChessFunctions.findPiece(endPosition, chessBoard, darkPieces, lightPieces) != null
						&& ChessFunctions.findPiece(endPosition, chessBoard, darkPieces, lightPieces).getColorModifier() == (colorModifier * -1))
				{
					valid = true;
				}
			}
		}	
		return valid;
	}

}
