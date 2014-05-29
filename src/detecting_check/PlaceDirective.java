package detecting_check;

public class PlaceDirective extends Directive{
	private char pieceType;
	private char pieceColor;
	
	public PlaceDirective(char pieceType, char pieceColor, int column, int row)
	{
		super(column, row - 1);
		this.pieceColor = pieceColor;
		this.pieceType = pieceType;
	}
	
	@Override
	public boolean execute(ChessBoard chessBoard, Piece[] darkPieces,
			Piece[] lightPieces, boolean darkTurn) 
	{
		int index = -1;
		int isDark = 0;
		boolean successfulExecution = false;
		
		if(pieceColor == 'l')
		{
			isDark = -1;
			for(int i = lightPieces.length - 1; i >= 0; i-- )
			{
				if(lightPieces[i] == null)
				{
					index = i;
				}
			}
		}
		else
		{
			isDark = 1;
			for(int i = darkPieces.length - 1; i >= 0; i-- )
			{
				if(darkPieces[i] == null)
				{
					index = i;
				}
			}
		}
		
		if(index >= 0)
		{
			Piece tempPiece = null;
			switch(pieceType)
			{
			case 'p':
				tempPiece = new Pawn(isDark, new Position(0, 0));
				break;
			case 'b':
				tempPiece = new Bishop(isDark, new Position(0, 0));
				break;
			case 'n':
				tempPiece = new Knight(isDark, new Position(0, 0));
				break;
			case 'r':
				tempPiece = new Rook(isDark, new Position(0, 0));
				break;
			case 'k':
				tempPiece = new King(isDark, new Position(0, 0));
				break;
			case 'q':
				tempPiece = new Queen(isDark, new Position(0, 0));
				break;
			}
			
			tempPiece.setPosition(new Position(row1, column1));
			
			if(isDark == 1)
			{
				darkPieces[index] = tempPiece;
			}
			else
			{
				lightPieces[index] = tempPiece;
			}
			
			System.out.println("Placing " + PieceMap.returnPiece(pieceType) + " on "
					+ (char)(tempPiece.getPosition().getColumn() + 'A') + (tempPiece.getPosition().getRow() + 1));
			successfulExecution = true;
		}
		
		updateBoard(chessBoard, darkPieces, lightPieces);
		return successfulExecution;
	}
	
	public char getPieceType()
	{
		return pieceType;
	}
	
	public char getPieceColor()
	{
		return pieceColor;
	}


}
