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
					+ (char)(piece.getPosition().getColumn() + 'A') + (piece.getPosition().getRow() + 1));
			updateBoard();
			System.out.println(toString());
		}
		else if(move instanceof CastleDirective)
		{
			CastleDirective castleMove = (CastleDirective) move;
			boolean isDark = false;
			if(castleMove.getRow1() == 0)
			{
				isDark = true;
			}

			Piece tempPiece1 = findPiece('k', isDark);
			Piece tempPiece2 = findPiece(new Position(castleMove.getRookMove().getRow1(), castleMove.getRookMove().getColumn1()));
			King king = null;
			Rook rook = null;
			if(tempPiece1 instanceof King && tempPiece2 instanceof Rook)
			{
				king = (King) tempPiece1;
				rook = (Rook) tempPiece2;
				if(rook.getMoveCount() == 0)
				{
					if(king.castleIsValid(new Position(castleMove.getRow2(), castleMove.getColumn2()), chessBoard, darkPieces, lightPieces)
							&& rook.moveIsClear(new Position(castleMove.getRookMove().getRow2(), castleMove.getRookMove().getColumn2()),
									chessBoard, darkPieces, lightPieces))
					{
						king.setPosition(new Position(castleMove.getRow2(), castleMove.getColumn2()));
						rook.setPosition(new Position(castleMove.getRookMove().getRow2(), castleMove.getRookMove().getColumn2()));
						rook.incrementMoveCount();
						
						System.out.println("Castled King to " + (char)(king.getPosition().getColumn() + 'A') + (king.getPosition().getRow() + 1)
								+ " and Rook to " + (char)(rook.getPosition().getColumn() + 'A') + (rook.getPosition().getRow() + 1));
						updateBoard();
						System.out.println(toString());
					}
				}
			}

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
						System.out.println(PIECE_MAP.returnPiece(piece.getPieceChar()) + " from " +
								(char)(singleMove.getColumn1() + 'A') + (singleMove.getRow1() + 1)
								+ " to " +
								(char)(singleMove.getColumn2() + 'A') + (singleMove.getRow2() + 1));
						piece.setPosition(new Position(singleMove.getRow2(), singleMove.getColumn2()));
						updateBoard();
						System.out.println(toString());
					}
					
				}
			}
		}
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
	
	public Piece findPiece(char pieceType, boolean isDark)
	{
		Piece piece = null;
		
		if(isDark)
		{
			
			for(Piece p : darkPieces)
			{
				if(p != null)
				{
					if(p.getPieceChar() == pieceType)
					{
						piece = p;
					}
				}
			}
		}
		else if(!isDark)
		{
			for(Piece p : lightPieces)
			{
				if(p != null)
				{
					if(p.getPieceChar() == pieceType)
					{
						piece = p;
					}
				}
			}
		}
		
		return piece;
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
