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
	
	public boolean isClear(Position startPosition, Position endPosition, char[][] chessBoard, Piece[] darkPieces, Piece[] lightPieces)
	{
		boolean isClear = false;
		
		if(startPosition.compareTo(endPosition) != 0)
		{
			if(startPosition.getRow() >= 0 && startPosition.getRow() < chessBoard.length
					&& startPosition.getColumn() >= 0 && startPosition.getColumn() < chessBoard.length
					&& endPosition.getRow() >= 0 && endPosition.getRow() < chessBoard.length
					&& endPosition.getColumn() >= 0 && endPosition.getColumn() < chessBoard.length)
			{

				if(startPosition.getRow() == endPosition.getRow())
				{
					boolean pathingStop = false;
					int slopeRun = endPosition.getColumn() - startPosition.getColumn() / Math.abs(endPosition.getColumn() - startPosition.getColumn());
					
					Position pathPosition = new Position(startPosition.getRow(), startPosition.getColumn());
					
					while(!pathingStop)
					{
							pathPosition.setColumn(pathPosition.getColumn() + slopeRun);
							
							if(pathPosition.compareTo(endPosition) == 0)
							{
								isClear = true;
								pathingStop = true;
							}
							else if(chessBoard[pathPosition.getColumn()][pathPosition.getRow()] != '-')
							{
								pathingStop = false;
							}
					}
				}
				else if(startPosition.getColumn() == endPosition.getColumn())
				{
					boolean pathingStop = false;
					int slopeRun = endPosition.getRow() - startPosition.getRow() / Math.abs(endPosition.getRow() - startPosition.getRow());
					
					Position pathPosition = new Position(startPosition.getRow(), startPosition.getColumn());
					
					while(!pathingStop)
					{
							pathPosition.setColumn(pathPosition.getColumn() + slopeRun);
							
							if(pathPosition.compareTo(endPosition) == 0)
							{
								isClear = true;
								pathingStop = true;
							}
							else if(chessBoard[pathPosition.getColumn()][pathPosition.getRow()] != '-')
							{
								pathingStop = false;
							}
					}
				}
				else if(Math.abs((startPosition.getRow() - endPosition.getRow()) / startPosition.getColumn() - endPosition.getColumn()) < 1.0001
						&& Math.abs((startPosition.getRow() - endPosition.getRow()) / startPosition.getColumn() - endPosition.getColumn()) > .9999)
				{
					boolean pathingStop = false;
					int slopeRise = endPosition.getRow() - startPosition.getRow() / Math.abs(endPosition.getRow() - startPosition.getRow());
					int slopeRun = endPosition.getColumn() - startPosition.getColumn() / Math.abs(endPosition.getColumn() - startPosition.getColumn());
					
					Position pathPosition = new Position(startPosition.getRow(), startPosition.getColumn());
					
					while(!pathingStop)
					{
							pathPosition.setRow(pathPosition.getRow() + slopeRise); 
							pathPosition.setColumn(pathPosition.getColumn() + slopeRun);
						
							if(pathPosition.compareTo(endPosition) == 0)
							{
								isClear = true;
								pathingStop = true;
							}
							else if(chessBoard[pathPosition.getColumn()][pathPosition.getRow()] != '-')
							{								
								pathingStop = false;
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

	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}
	
	@Override
	public String toString()
	{
		return "Piece: " + getClass() + "\nRow: " + position.getRow() + "\nColumn: " + position.getColumn();
	}
}
