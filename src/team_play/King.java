package team_play;

import java.util.ArrayList;

public class King extends Piece{

	private boolean isInCheck;
	
	public King(int isDark, Position position) 
	{
		super(isDark, position, 'k');
		setInCheck(false);
	}

	@Override
	public boolean moveIsValid(Position endPosition, ChessBoard chessBoard,
			ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces) 
	{
		boolean valid = false;
		
		if(Math.abs(endPosition.getRow() - position.getRow()) <= 1 
				&& Math.abs(endPosition.getColumn() - position.getColumn()) <= 1
				&& chessBoard.hasPieceOnPosition(endPosition) == 0)
		{
				valid = true;
				moveCount++;
		}
		return valid;
	}

	@Override
	public boolean captureIsValid(Position endPosition, ChessBoard chessBoard,
			ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces) 
	{
		boolean valid = false;
		
		if(Math.abs(endPosition.getRow() - position.getRow()) <= 1 					//If the desired position is 1 row or less away from the current position
				&& Math.abs(endPosition.getColumn() - position.getColumn()) <= 1	//If the desired position is 1 column or less away from the current position
				&& chessBoard.hasPieceOnPosition(endPosition) == (colorModifier * -1))
		{
			{				
					valid = true;
					moveCount++;
			}
		}
		return valid;
	}
	
	public boolean castleIsValid(Position endPosition, ChessBoard chessBoard,
			ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces)
	{
		boolean valid = false;
		int correctRow;
		
		if(colorModifier == 1)
		{
			correctRow = 0;
		}
		else
		{
			correctRow = 7;
		}
		
		if(moveCount == 0 && (endPosition.getColumn() == 6 || endPosition.getColumn() == 2)
				&& endPosition.getRow() == correctRow)
		{
			valid = true;
		}	
		else
		{
			System.err.println("INVALID: Problem castling king.");
		}

		
		return valid;
	}

	public boolean isInCheck() {
		return isInCheck;
	}

	public void setInCheck(boolean isInCheck) {
		this.isInCheck = isInCheck;
	}

}
