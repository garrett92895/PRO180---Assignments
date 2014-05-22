package piece_movement;

public abstract class Piece {
	protected char pieceChar;
	protected boolean isDark;
	protected Position position;
	protected int moveCount;
	
	public Piece(boolean isDark, Position position, char pieceChar)
	{
		this.isDark = isDark;
		this.position = position;
		this.pieceChar = pieceChar;
		moveCount = 0;
	}
	
	public boolean moveIsClear(Position endPosition, char[][] chessBoard, Piece[] darkPieces, Piece[] lightPieces)
	{
		boolean isClear = false;
		
		if(position.compareTo(endPosition) != 0)
		{
			if(position.getRow() >= 0 && position.getRow() < chessBoard.length
					&& position.getColumn() >= 0 && position.getColumn() < chessBoard.length
					&& endPosition.getRow() >= 0 && endPosition.getRow() < chessBoard.length
					&& endPosition.getColumn() >= 0 && endPosition.getColumn() < chessBoard.length)
			{

				if(position.getRow() == endPosition.getRow())
				{
					boolean pathingStop = false;
					int slopeRun = (endPosition.getColumn() - position.getColumn()) / Math.abs(endPosition.getColumn() - position.getColumn());
					
					Position pathPosition = new Position(position.getRow(), position.getColumn());
					
					while(!pathingStop)
					{
							pathPosition.setColumn(pathPosition.getColumn() + slopeRun);
							
							if(chessBoard[pathPosition.getRow()][pathPosition.getColumn()] != '-')
							{
								pathingStop = true;
							}
							else if(pathPosition.compareTo(endPosition) == 0)
							{
								isClear = true;
								pathingStop = true;
							}
					}
				}
				else if(position.getColumn() == endPosition.getColumn())
				{
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
							else if(chessBoard[pathPosition.getRow()][pathPosition.getColumn()] != '-')
							{
								pathingStop = true;
							}
					}
				}
				else if(Math.abs((position.getRow() - endPosition.getRow()) / (position.getColumn() - endPosition.getColumn())) < 1.0001
						&& Math.abs((position.getRow() - endPosition.getRow()) / (position.getColumn() - endPosition.getColumn())) > .9999)
				{
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
							else if(chessBoard[pathPosition.getRow()][pathPosition.getColumn()] != '-')
							{								
								pathingStop = true;
							}
					}
				}
			}
		}
		
		return isClear;
	}
	public boolean captureIsClear(Position endPosition, char[][] chessBoard, Piece[] darkPieces, Piece[] lightPieces)
	{
		boolean isClear = false;
		
		if(position.compareTo(endPosition) != 0)
		{
			if(position.getRow() >= 0 && position.getRow() < chessBoard.length
					&& position.getColumn() >= 0 && position.getColumn() < chessBoard.length
					&& endPosition.getRow() >= 0 && endPosition.getRow() < chessBoard.length
					&& endPosition.getColumn() >= 0 && endPosition.getColumn() < chessBoard.length)
			{

				if(position.getRow() == endPosition.getRow())
				{
					boolean pathingStop = false;
					int slopeRun = (endPosition.getColumn() - position.getColumn()) / Math.abs(endPosition.getColumn() - position.getColumn());
					
					Position pathPosition = new Position(position.getRow(), position.getColumn());
					
					while(!pathingStop)
					{
							pathPosition.setColumn(pathPosition.getColumn() + slopeRun);
							
							if(pathPosition.compareTo(endPosition) == 0)
							{
								if(isDark)
								{
									for(int i = 0; i < lightPieces.length; i++)
									{
										if(lightPieces[i] != null)
										{
											if(lightPieces[i].getPosition().compareTo(endPosition) == 0)
											{
												isClear = true;
											}
										}
									}
								}
								else
								{
									for(int i = 0; i < darkPieces.length; i++)
									{
										if(darkPieces[i] != null)
										{
											if(darkPieces[i].getPosition().compareTo(endPosition) == 0)
											{
												isClear = true;
											}
										}
									}
								}
								pathingStop = true;
							}
							else if(chessBoard[pathPosition.getRow()][pathPosition.getColumn()] != '-')
							{
								pathingStop = true;
							}
					}
				}
				else if(position.getColumn() == endPosition.getColumn())
				{
					boolean pathingStop = false;
					int slopeRun = (endPosition.getRow() - position.getRow()) / Math.abs(endPosition.getRow() - position.getRow());
					
					Position pathPosition = new Position(position.getRow(), position.getColumn());
					
					while(!pathingStop)
					{
							pathPosition.setColumn(pathPosition.getColumn() + slopeRun);
							
							if(pathPosition.compareTo(endPosition) == 0)
							{
								isClear = true;
								pathingStop = true;
							}
							else if(chessBoard[pathPosition.getRow()][pathPosition.getColumn()] != '-')
							{
								pathingStop = true;
							}
					}
				}
				else if(Math.abs((position.getRow() - endPosition.getRow()) / (position.getColumn() - endPosition.getColumn())) < 1.0001
						&& Math.abs((position.getRow() - endPosition.getRow()) / (position.getColumn() - endPosition.getColumn())) > .9999)
				{
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
								if(isDark)
								{
									for(int i = 0; i < lightPieces.length; i++)
									{
										if(lightPieces[i] != null)
										{
											if(lightPieces[i].getPosition().compareTo(endPosition) == 0)
											{
												isClear = true;
											}
										}
									}
								}
								else
								{
									for(int i = 0; i < darkPieces.length; i++)
									{
										if(darkPieces[i] != null)
										{
											if(darkPieces[i].getPosition().compareTo(endPosition) == 0)
											{
												isClear = true;
											}
										}
									}
								}
								pathingStop = true;
							}
							else if(chessBoard[pathPosition.getRow()][pathPosition.getColumn()] != '-')
							{								
								pathingStop = true;
							}
					}
				}
			}
		}
		
		return isClear;
	}
	public abstract boolean moveIsValid(Position endPosition, char[][] chessBoard, Piece[] darkPieces, Piece[] lightPieces);
	public abstract boolean captureIsValid(Position endPosition, char[][] chessBoard, Piece[] darkPieces, Piece[] lightPieces);

	public boolean isDark() {
		return isDark;
	}

	public void setDark(boolean isDark) {
		this.isDark = isDark;
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
