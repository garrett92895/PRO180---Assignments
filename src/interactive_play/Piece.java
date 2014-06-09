package interactive_play;

import java.util.ArrayList;

public abstract class Piece {
	protected char pieceChar;
	protected int colorModifier;
	protected Position position;
	protected int moveCount;
	protected final double UPPER_BOUND = 1.001;
	protected final double LOWER_BOUND = .9999;
	
	public Piece(int isDark, Position position, char pieceChar)
	{
		this.colorModifier = isDark;
		this.position = position;
		this.pieceChar = pieceChar;
		moveCount = 0;
	}
	
	protected boolean moveIsClear(Position endPosition, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces)
	{
		boolean isClear = false;
		
		if(position.compareTo(endPosition) != 0)
		{
				if(position.getRow() == endPosition.getRow())
				{
					isClear = rowMoveIsClear(endPosition, chessBoard, darkPieces, lightPieces);
				}
				else if(position.getColumn() == endPosition.getColumn())
				{
					isClear = columnMoveIsClear(endPosition, chessBoard, darkPieces, lightPieces);
				}
				else if(Math.abs((position.getRow() - endPosition.getRow()) / (position.getColumn() - endPosition.getColumn())) < UPPER_BOUND
						&& Math.abs((position.getRow() - endPosition.getRow()) / (position.getColumn() - endPosition.getColumn())) > LOWER_BOUND)
				{
					isClear = diagonalMoveIsClear(endPosition, chessBoard, darkPieces, lightPieces);
				}
		}
		
		return isClear;
	}
	protected boolean captureIsClear(Position endPosition, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces, boolean darkTurn)
	{
		boolean isClear = false;
		double rise = position.getRow() - endPosition.getRow();
		double run = position.getColumn() - endPosition.getColumn();
		double slope = Math.abs(rise / run);
		if(position.compareTo(endPosition) != 0)
		{
			if(position.getRow() == endPosition.getRow())
			{
				isClear = rowCaptureIsClear(endPosition, chessBoard, darkPieces, lightPieces, darkTurn);
			}
			else if(position.getColumn() == endPosition.getColumn())
			{
				isClear = columnCaptureIsClear(endPosition, chessBoard, darkPieces, lightPieces, darkTurn);
			}
			else if(slope < UPPER_BOUND
					&& slope > LOWER_BOUND)
			{
				isClear = diagonalCaptureIsClear(endPosition, chessBoard, darkPieces, lightPieces, darkTurn);
			}
		}
		return isClear;
	}
	
	protected boolean rowMoveIsClear(Position endPosition, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces)
	{
		boolean isClear = false;
		
		boolean pathingStop = false;
		int slopeRun = (endPosition.getColumn() - position.getColumn()) / Math.abs(endPosition.getColumn() - position.getColumn());
		
		Position pathPosition = new Position(position.getRow(), position.getColumn());
		
		while(!pathingStop)
		{
				pathPosition.setColumn(pathPosition.getColumn() + slopeRun);
				
				if(ChessFunctions.findPiece(pathPosition, chessBoard, darkPieces, lightPieces) != null)
				{
					pathingStop = true;
				}
				else if(pathPosition.compareTo(endPosition) == 0)
				{
					isClear = true;
					pathingStop = true;
				}
		}
		
		return isClear;
		
	}
	protected boolean rowCaptureIsClear(Position endPosition, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece>lightPieces, boolean darkTurn)
	{
		ArrayList<Piece> dangerPieces = (darkTurn) ? lightPieces : darkPieces;
		boolean isClear = false;
		boolean pathingStop = false;
		int slopeRun = (endPosition.getColumn() - position.getColumn()) / Math.abs(endPosition.getColumn() - position.getColumn());
		
		Position pathPosition = new Position(position.getRow(), position.getColumn());
		
		while(!pathingStop)
		{
				pathPosition.setColumn(pathPosition.getColumn() + slopeRun);
				
				if(pathPosition.compareTo(endPosition) == 0)
				{
					for(int i = 0; i < dangerPieces.size(); i++)
					{
						if(dangerPieces.get(i) != null)
						{
							if(dangerPieces.get(i).getPosition().compareTo(endPosition) == 0)
							{
								isClear = true;
							}
						}
					}
					pathingStop = true;
				}
				else if(ChessFunctions.findPiece(pathPosition, chessBoard, darkPieces, lightPieces) != null)
				{
					pathingStop = true;
				}
		}
		return isClear;
	}
	protected boolean columnMoveIsClear(Position endPosition, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces)
	{
		boolean isClear = false;
		boolean pathingStop = false;
		int slopeRise = (endPosition.getRow() - position.getRow()) / Math.abs(endPosition.getRow() - position.getRow());
		
		Position pathPosition = new Position(position.getRow(), position.getColumn());
		boolean onBoard = (endPosition.getRow() >= 0 && endPosition.getRow() < chessBoard.BOARD_SIZE
				&& endPosition.getColumn() >= 0 && endPosition.getColumn() < chessBoard.BOARD_SIZE);
		
		while(!pathingStop)
		{
				pathPosition.setRow(pathPosition.getRow() + slopeRise);
				if(ChessFunctions.findPiece(pathPosition, chessBoard, darkPieces, lightPieces) != null)
				{
					pathingStop = true;
				}
				else if(pathPosition.compareTo(endPosition) == 0)
				{
					isClear = true;
					pathingStop = true;
				}

		}
		return isClear;
	}
	protected boolean columnCaptureIsClear(Position endPosition, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces, boolean darkTurn)
	{
		boolean isClear = false;
		
		boolean pathingStop = false;
		int slopeRise = (endPosition.getRow() - position.getRow()) / Math.abs(endPosition.getRow() - position.getRow());
		
		Position pathPosition = new Position(position.getRow(), position.getColumn());
		ArrayList<Piece> dangerPieces = (darkTurn) ? lightPieces : darkPieces;
		
		while(!pathingStop)
		{
			pathPosition.setRow(pathPosition.getRow() + slopeRise);
				
			if(pathPosition.compareTo(endPosition) == 0)
			{
				for(int i = 0; i < dangerPieces.size(); i++)
				{
					if(dangerPieces.get(i) != null)
					{
						if(dangerPieces.get(i).getPosition().compareTo(endPosition) == 0)
						{
							isClear = true;
						}
					}
				}
				pathingStop = true;
			}
			else if(ChessFunctions.findPiece(pathPosition, chessBoard, darkPieces, lightPieces) != null)
			{
				pathingStop = true;
			}
		}
		
		return isClear;
	}
	protected boolean diagonalMoveIsClear(Position endPosition, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces)
	{
		boolean isClear = false;
		
		boolean pathingStop = false;
		int slopeRise = (endPosition.getRow() - position.getRow()) / Math.abs(endPosition.getRow() - position.getRow());
		int slopeRun = (endPosition.getColumn() - position.getColumn()) / Math.abs(endPosition.getColumn() - position.getColumn());
		
		Position pathPosition = new Position(position.getRow(), position.getColumn());
		
		while(!pathingStop)
		{
				pathPosition.setRow(pathPosition.getRow() + slopeRise); 
				pathPosition.setColumn(pathPosition.getColumn() + slopeRun);
			
				boolean onBoard = (pathPosition.getRow() >= 0 && pathPosition.getRow() < chessBoard.BOARD_SIZE
						&& pathPosition.getColumn() >= 0 && pathPosition.getColumn() < chessBoard.BOARD_SIZE);
				
				if(!onBoard)
				{
					pathingStop = true;
				}
				if(onBoard && ChessFunctions.findPiece(new Position(pathPosition.getRow(), pathPosition.getColumn()), chessBoard, darkPieces, lightPieces) != null)
				{
					pathingStop = true;
				}
				else if(pathPosition.compareTo(endPosition) == 0)
				{
					isClear = true;
					pathingStop = true;
				}
				

		}
		
		return isClear;
	}
	protected boolean diagonalCaptureIsClear(Position endPosition, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces, boolean darkTurn)
	{
		boolean isClear = false;
		
		boolean pathingStop = false;
		
		
		int slopeRise = (endPosition.getRow() - position.getRow()) / Math.abs(endPosition.getRow() - position.getRow());
		int slopeRun = (endPosition.getColumn() - position.getColumn()) / Math.abs(endPosition.getColumn() - position.getColumn());
		
		Position pathPosition = new Position(position.getRow(), position.getColumn());
		ArrayList<Piece> dangerPieces = (darkTurn) ? lightPieces : darkPieces;
		
		while(!pathingStop)
		{
				pathPosition.setRow(pathPosition.getRow() + slopeRise); 
				pathPosition.setColumn(pathPosition.getColumn() + slopeRun);
			
				if(pathPosition.compareTo(endPosition) == 0)
				{
						for(int i = 0; i < dangerPieces.size(); i++)
						{
							if(dangerPieces.get(i) != null)
							{
								if(dangerPieces.get(i).getPosition().compareTo(endPosition) == 0)
								{
									isClear = true;
								}
							}
						}
					pathingStop = true;
				}
				else if(ChessFunctions.findPiece(pathPosition, chessBoard, darkPieces, lightPieces) != null)
				{								
					pathingStop = true;
				}
		}
		
		return isClear;
	}
	
	public abstract boolean moveIsValid(Position endPosition, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces);
	public abstract boolean captureIsValid(Position endPosition, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces, boolean darkTurn);
	
	public int getColorModifier() {
		return colorModifier;
	}

	public void setColorModifier(int isDark) {
		this.colorModifier = isDark;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public char getPieceChar()
	{
		return pieceChar;
	}
	
	public int getMoveCount() {
		return moveCount;
	}

	public void incrementMoveCount() {
		this.moveCount++;
	}
	
	@Override
	public String toString()
	{
		return "Piece: " + getClass() + "\nRow: " + position.getRow() + "\nColumn: " + position.getColumn();
	}
}
