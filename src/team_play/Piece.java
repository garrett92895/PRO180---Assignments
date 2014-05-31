package team_play;

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
	
	protected boolean moveIsClear(Position endPosition, ChessBoard chessBoard)
	{
		boolean isClear = false;
		
		if(position.compareTo(endPosition) != 0)
		{
				if(position.getRow() == endPosition.getRow())
				{
					isClear = rowMoveIsClear(endPosition, chessBoard);
				}
				else if(position.getColumn() == endPosition.getColumn())
				{
					isClear = columnMoveIsClear(endPosition, chessBoard);
				}
				else if(Math.abs((position.getRow() - endPosition.getRow()) / (position.getColumn() - endPosition.getColumn())) < UPPER_BOUND
						&& Math.abs((position.getRow() - endPosition.getRow()) / (position.getColumn() - endPosition.getColumn())) > LOWER_BOUND)
				{
					isClear = diagonalMoveIsClear(endPosition, chessBoard);
				}
		}
		
		return isClear;
	}
	protected boolean captureIsClear(Position endPosition, ChessBoard chessBoard, Piece[] darkPieces, Piece[] lightPieces)
	{
		boolean isClear = false;
		Piece[] dangerPieces = (colorModifier == 1) ? lightPieces : darkPieces;
		
		if(position.compareTo(endPosition) != 0)
		{
			if(position.getRow() == endPosition.getRow())
			{
				isClear = rowCaptureIsClear(endPosition, chessBoard, dangerPieces);
			}
			else if(position.getColumn() == endPosition.getColumn())
			{
				isClear = columnCaptureIsClear(endPosition, chessBoard, dangerPieces);
			}
			else if(Math.abs((position.getRow() - endPosition.getRow()) / (position.getColumn() - endPosition.getColumn())) < UPPER_BOUND
					&& Math.abs((position.getRow() - endPosition.getRow()) / (position.getColumn() - endPosition.getColumn())) > LOWER_BOUND)
			{
				isClear = diagonalCaptureIsClear(endPosition, chessBoard, dangerPieces);
			}
		}
		return isClear;
	}
	
	protected boolean rowMoveIsClear(Position endPosition, ChessBoard chessBoard)
	{
		boolean isClear = false;
		
		boolean pathingStop = false;
		int slopeRun = (endPosition.getColumn() - position.getColumn()) / Math.abs(endPosition.getColumn() - position.getColumn());
		
		Position pathPosition = new Position(position.getRow(), position.getColumn());
		
		while(!pathingStop)
		{
				pathPosition.setColumn(pathPosition.getColumn() + slopeRun);
				
				if(chessBoard.getBoard()[pathPosition.getRow()][pathPosition.getColumn()] != '-')
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
	protected boolean rowCaptureIsClear(Position endPosition, ChessBoard chessBoard, Piece[] dangerPieces)
	{
		boolean isClear = false;
		boolean pathingStop = false;
		int slopeRun = (endPosition.getColumn() - position.getColumn()) / Math.abs(endPosition.getColumn() - position.getColumn());
		
		Position pathPosition = new Position(position.getRow(), position.getColumn());
		
		while(!pathingStop)
		{
				pathPosition.setColumn(pathPosition.getColumn() + slopeRun);
				
				if(pathPosition.compareTo(endPosition) == 0)
				{
					for(int i = 0; i < dangerPieces.length; i++)
					{
						if(dangerPieces[i] != null)
						{
							if(dangerPieces[i].getPosition().compareTo(endPosition) == 0)
							{
								isClear = true;
							}
						}
					}
					pathingStop = true;
				}
				else if(chessBoard.getBoard()[pathPosition.getRow()][pathPosition.getColumn()] != '-')
				{
					pathingStop = true;
				}
		}
		return isClear;
	}
	protected boolean columnMoveIsClear(Position endPosition, ChessBoard chessBoard)
	{
		boolean isClear = false;
		boolean pathingStop = false;
		int slopeRise = (endPosition.getRow() - position.getRow()) / Math.abs(endPosition.getRow() - position.getRow());
		
		Position pathPosition = new Position(position.getRow(), position.getColumn());
		
		while(!pathingStop)
		{
				pathPosition.setRow(pathPosition.getRow() + slopeRise);
				
				if(pathPosition.compareTo(endPosition) == 0)
				{
					isClear = true;
					pathingStop = true;
				}
				else if(chessBoard.getBoard()[pathPosition.getRow()][pathPosition.getColumn()] != '-')
				{
					pathingStop = true;
				}
		}
		return isClear;
	}
	protected boolean columnCaptureIsClear(Position endPosition, ChessBoard chessBoard, Piece[] dangerPieces)
	{
		boolean isClear = false;
		
		boolean pathingStop = false;
		int slopeRise = (endPosition.getRow() - position.getRow()) / Math.abs(endPosition.getRow() - position.getRow());
		
		Position pathPosition = new Position(position.getRow(), position.getColumn());
		
		while(!pathingStop)
		{
			pathPosition.setRow(pathPosition.getRow() + slopeRise);
				
			if(pathPosition.compareTo(endPosition) == 0)
			{
				for(int i = 0; i < dangerPieces.length; i++)
				{
					if(dangerPieces[i] != null)
					{
						if(dangerPieces[i].getPosition().compareTo(endPosition) == 0)
						{
							isClear = true;
						}
					}
				}
				pathingStop = true;
			}
			else if(chessBoard.getBoard()[pathPosition.getRow()][pathPosition.getColumn()] != '-')
			{
				pathingStop = true;
			}
		}
		
		return isClear;
	}
	protected boolean diagonalMoveIsClear(Position endPosition, ChessBoard chessBoard)
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
			
				if(pathPosition.compareTo(endPosition) == 0)
				{
					isClear = true;
					pathingStop = true;
				}
				else if(chessBoard.getBoard()[pathPosition.getRow()][pathPosition.getColumn()] != '-')
				{								
					pathingStop = true;
				}
		}
		
		return isClear;
	}
	protected boolean diagonalCaptureIsClear(Position endPosition, ChessBoard chessBoard, Piece[] dangerPieces)
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
			
				if(pathPosition.compareTo(endPosition) == 0)
				{
						for(int i = 0; i < dangerPieces.length; i++)
						{
							if(dangerPieces[i] != null)
							{
								if(dangerPieces[i].getPosition().compareTo(endPosition) == 0)
								{
									isClear = true;
								}
							}
						}
					pathingStop = true;
				}
				else if(chessBoard.getBoard()[pathPosition.getRow()][pathPosition.getColumn()] != '-')
				{								
					pathingStop = true;
				}
		}
		
		return isClear;
	}
	
	public abstract boolean moveIsValid(Position endPosition, ChessBoard chessBoard, Piece[] darkPieces, Piece[] lightPieces);
	public abstract boolean captureIsValid(Position endPosition, ChessBoard chessBoard, Piece[] darkPieces, Piece[] lightPieces);
	
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