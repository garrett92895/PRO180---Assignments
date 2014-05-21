package piece_movement;

public class Game {
	private final int BOARD_SIZE = 8;
	private final int NUM_OF_PIECES = 16;
	private final PieceMap PIECE_MAP = new PieceMap();
	private char[][] chessBoard = new char[BOARD_SIZE][BOARD_SIZE];
	private Piece[] darkPieces = new Piece[NUM_OF_PIECES];
	private Piece[] lightPieces = new Piece[NUM_OF_PIECES];
	
	public Game()
	{
		for(int i = 0; i < chessBoard.length; i++)
		{
			for(int x = 0; x < chessBoard[i].length; x++)
			{
				chessBoard[i][x] = '-';
			}
		}
	}
	
	public void executeDirective(Directive move)
	{
		if(move instanceof PlaceDirective)
		{
			PlaceDirective placeMove = (PlaceDirective) move;
			
			Piece piece = createPiece(placeMove);
			piece.getPosition().setRow(placeMove.getRow1());
			piece.getPosition().setColumn(placeMove.getColumn1());
			
			System.out.println("Placing " + PIECE_MAP.returnPiece(piece.getPieceChar()) + " on "
					+ );
		}
		else if(move instanceof CastleDirective)
		{
			CastleDirective castleMove = (CastleDirective) move;
			char king = chessBoard[castleMove.getRow1()][castleMove.getColumn1()];
			char rook = chessBoard[castleMove.getRookMove().getRow1()][castleMove.getRookMove().getColumn1()];
			
			chessBoard[castleMove.getRow1()][castleMove.getColumn1()] = '-';
			chessBoard[castleMove.getRookMove().getRow1()][castleMove.getRookMove().getColumn1()] = '-';
			chessBoard[castleMove.getRow2()][castleMove.getColumn2()] = king;
			chessBoard[castleMove.getRookMove().getRow2()][castleMove.getRookMove().getColumn2()] = rook;
		}
		else if(move instanceof MoveDirective)
		{
			MoveDirective singleMove = (MoveDirective) move;
			Piece piece = findPiece(new Position(singleMove.getRow1(), singleMove.getColumn1()));
			
			if(piece != null)
			{
				if(singleMove.isCapture())
				{
					if(piece.captureIsValid(new Position(singleMove.getRow2(), singleMove.getColumn2()), chessBoard, darkPieces, lightPieces))
					{
						Piece enemyPiece = findPiece(new Position(singleMove.getRow2(), singleMove.getColumn2()));
						System.out.println(PIECE_MAP.returnPiece(piece.getPieceChar()) + " from " +
								(char)(piece.getPosition().getColumn() + 'A') + (piece.getPosition().getRow() + 1)
								+ " capturing " + PIECE_MAP.returnPiece(enemyPiece.getPieceChar()) + " on " +
								(char)(enemyPiece.getPosition().getColumn() + 'A') + (enemyPiece.getPosition().getRow() + 1));
						piece.setPosition(new Position(singleMove.getRow2(), singleMove.getColumn2()));
						
						for(int i = 0; i < NUM_OF_PIECES; i++)
						{
							if(darkPieces[i] != null)
							{
								if(darkPieces[i].equals(enemyPiece))
								{
									darkPieces[i] = null;
								}
							}
							if(lightPieces[i] != null)
							{
								if(lightPieces[i].equals(enemyPiece))
								{
									lightPieces[i] = null;
								}
							}
						}
						updateBoard();
						System.out.println(toString());
					}

				}
				else
				{
					if(piece.moveIsValid(new Position(singleMove.getRow2(), singleMove.getColumn2()), chessBoard, darkPieces, lightPieces))
					{
						piece.setPosition(new Position(singleMove.getRow2(), singleMove.getColumn2()));
					}
				}
			}
		}
		updateBoard();
	}
	
	public void updateBoard()
	{
		for(int i = 0; i < chessBoard.length; i++)
		{
			for(int x = 0; x < chessBoard.length; x++)
			{
				Piece piece = findPiece(new Position(i, x));
				chessBoard[i][x] = '-';
				if(piece != null)
				{
					if(piece.isDark())
					{
						chessBoard[i][x] = piece.getPieceChar();
					}
					else
					{
						chessBoard[i][x] = Character.toUpperCase(piece.getPieceChar());
					}
				}
			}
		}
	}
	
	public Piece createPiece(PlaceDirective placeMove)
	{
		boolean isDark = false;;
		if(placeMove.getPieceColor() == 'l')
		{
			isDark = false;
		}
		else if(placeMove.getPieceColor() == 'd')
		{
			isDark = true;
		}
		
		Piece tempPiece = null;
		int index = -1;
		if(isDark)
		{
			for(int i = darkPieces.length - 1; i >= 0; i-- )
			{
				if(darkPieces[i] == null)
				{
					index = i;
				}
			}
		}
		else
		{
			for(int i = lightPieces.length - 1; i >= 0; i-- )
			{
				if(lightPieces[i] == null)
				{
					index = i;
				}
			}
		}
		
		if(index >= 0)
		{
			switch(placeMove.getPieceType())
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
		}
		if(isDark)
		{
			darkPieces[index] = tempPiece;
		}
		else
		{
			lightPieces[index] = tempPiece;
		}
		return tempPiece;
	}
	
	public Piece findPiece(Position position)
	{
		Piece returnPiece = null;
		
		for(int i = 0; i < NUM_OF_PIECES; i++)
		{
			if(darkPieces[i] != null)
			{
				if(darkPieces[i].getPosition().compareTo(position) == 0)
				{
					returnPiece = darkPieces[i];
				}
			}
			if(lightPieces[i] != null)
			{
				if(lightPieces[i].getPosition().compareTo(position) == 0)
				{
					returnPiece = lightPieces[i];
				}
			}
		}
		
		return returnPiece;
	}
	
	@Override
	public String toString()
	{
		String s = "    A  B  C  D  E  F  G  H\n";
		s += "    ______________________\n";
		for(int i = 0; i < chessBoard.length; i++)
		{	s += (i + 1) + "|  ";
			for(int x = 0; x < chessBoard[i].length; x++)
			{
				s += chessBoard[i][x];
				s += "  ";
			}
			s += '\n';
		}
		return s;
	}
}
