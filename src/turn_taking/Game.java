package turn_taking;

/**
 * @author Garrett Holbrook
 *
 */
public class Game {
	private final int NUM_OF_PIECES = 16;
	private final PieceMap PIECE_MAP = new PieceMap();
	private ChessBoard chessBoard = new ChessBoard();
	private Piece[] darkPieces = new Piece[NUM_OF_PIECES];
	private Piece[] lightPieces = new Piece[NUM_OF_PIECES];
	private boolean darkTurn;
	
	public Game()
	{
		darkTurn = false;
		
		for(int i = 0; i < (NUM_OF_PIECES / 2); i++)
		{
			darkPieces[i] = new Pawn(1, new Position(1, i));
			lightPieces[i] = new Pawn(-1, new Position(6, i));
		}
		darkPieces[8] = new Rook(1, new Position(0, 0));
		darkPieces[9] = new Rook(1, new Position(0, 7));
		darkPieces[10] = new Knight(1, new Position(0, 1));
		darkPieces[11] = new Knight(1, new Position(0, 6));
		darkPieces[12] = new Bishop(1, new Position(0, 2));
		darkPieces[13] = new Bishop(1, new Position(0, 5));
		darkPieces[14] = new Queen(1, new Position(0, 3));
		darkPieces[15] = new King(1, new Position(0, 4));
		lightPieces[8] = new Rook(-1, new Position(7, 0));
		lightPieces[9] = new Rook(-1, new Position(7, 7));
		lightPieces[10] = new Knight(-1, new Position(7, 1));
		lightPieces[11] = new Knight(-1, new Position(7, 6));
		lightPieces[12] = new Bishop(-1, new Position(7, 2));
		lightPieces[13] = new Bishop(-1, new Position(7, 5));
		lightPieces[14] = new Queen(-1, new Position(7, 3));
		lightPieces[15] = new King(-1, new Position(7, 4));
		
		updateBoard();
		System.out.println(toString());
	}
	
	/**
	 * Executes a directive by determining what sort of move it is and then
	 * following the instructions in that move
	 * 
	 * 
	 * @param  move The move that this method executes
	 */
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
			int isDark = -1;
			String errorMessage = "";
			
			if(castleMove.getRow1() == 0)
			{
				isDark = 1;
			}
			
			boolean rightTurn = false;
			if(darkTurn && isDark == 1)
			{
				rightTurn = true;
			}
			else if(!darkTurn && isDark == -1)
			{
				rightTurn = true;
			}
			else
			{
				errorMessage += "INVALID: Not the right player's turn\n";
			}

			Piece tempPiece1 = findPiece('k', isDark);
			Piece tempPiece2 = findPiece(new Position(castleMove.getRookMove().getRow1(), castleMove.getRookMove().getColumn1()));
			King king = null;
			Rook rook = null;
			if(tempPiece1 instanceof King && tempPiece2 instanceof Rook && rightTurn)
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
						king.incrementMoveCount();
						darkTurn = !darkTurn;
						
						System.out.println("Castled King to " + (char)(king.getPosition().getColumn() + 'A') + (king.getPosition().getRow() + 1)
								+ " and Rook to " + (char)(rook.getPosition().getColumn() + 'A') + (rook.getPosition().getRow() + 1));
						updateBoard();
						System.out.println(toString());
					}
					else
					{
						errorMessage += "INVALID: Path might not be clear\n";
					}
				}
				else
				{
					errorMessage += "INVALID: Rook has already moved\n";
				}
			}
			else
			{
				errorMessage += "INVALID: Might be attempting to castle pieces that aren't the rook or the king\n";
			}
			
			System.err.println("Error while castling...\n" + errorMessage);
		}
		else if(move instanceof MoveDirective)
		{
			MoveDirective singleMove = (MoveDirective) move;
			Piece piece = findPiece(new Position(singleMove.getRow1(), singleMove.getColumn1()));
			String errorMessage = "";
			boolean rightTurn = false;
			
			if(piece != null)
			{
				if(darkTurn && piece.getColorModifier() == 1)
				{
					rightTurn = true;
				}
				else if(!darkTurn && piece.getColorModifier() == -1)
				{
					rightTurn = true;
				}
				else
				{
					errorMessage += "INVALID: Not the right player's turn\n";
				}
				if(rightTurn)
				{
					if(singleMove.isCapture() && piece.captureIsValid(new Position(singleMove.getRow2(), singleMove.getColumn2()), chessBoard, darkPieces, lightPieces))
					{
							Piece enemyPiece = findPiece(new Position(singleMove.getRow2(), singleMove.getColumn2()));
							System.out.println(PIECE_MAP.returnPiece(piece.getPieceChar()) + " from " +
									(char)(piece.getPosition().getColumn() + 'A') + (piece.getPosition().getRow() + 1)
									+ " capturing " + PIECE_MAP.returnPiece(enemyPiece.getPieceChar()) + " on " +
									(char)(enemyPiece.getPosition().getColumn() + 'A') + (enemyPiece.getPosition().getRow() + 1));
							piece.setPosition(new Position(singleMove.getRow2(), singleMove.getColumn2()));
							darkTurn = !darkTurn;
							
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
					else if(piece.moveIsValid(new Position(singleMove.getRow2(), singleMove.getColumn2()), chessBoard, darkPieces, lightPieces))
					{
						System.out.println(PIECE_MAP.returnPiece(piece.getPieceChar()) + " from " +
								(char)(singleMove.getColumn1() + 'A') + (singleMove.getRow1() + 1)
								+ " to " +
								(char)(singleMove.getColumn2() + 'A') + (singleMove.getRow2() + 1));
						piece.setPosition(new Position(singleMove.getRow2(), singleMove.getColumn2()));
						darkTurn = !darkTurn;
						updateBoard();
						System.out.println(toString());
	
					}	
				}
				else
				{
					errorMessage += "INVALID: Move is not valid\n";
				}
			}
			else
			{
				errorMessage += "INVALID: No piece found on starting position\n";
			}
			System.err.println(errorMessage);
		}
	}
	
	/**
	 * 
	 */
	public void updateBoard()
	{
		for(int i = 0; i < chessBoard.BOARD_SIZE; i++)
		{
			for(int x = 0; x < chessBoard.BOARD_SIZE; x++)
			{
				Piece piece = findPiece(new Position(i, x));
				chessBoard.setPositionToChar(new Position(i, x), '-');
				if(piece != null)
				{
					if(piece.getColorModifier() == 1)
					{
						chessBoard.setPositionToChar(new Position(i, x), piece.getPieceChar());
					}
					else
					{
						chessBoard.setPositionToChar(new Position(i, x), Character.toUpperCase(piece.getPieceChar()));
					}
				}
			}
		}
	}
	
	public Piece createPiece(PlaceDirective placeMove)
	{
		int isDark = 0;
		if(placeMove.getPieceColor() == 'l')
		{
			isDark = -1;
		}
		else if(placeMove.getPieceColor() == 'd')
		{
			isDark = 1;
		}
		
		Piece tempPiece = null;
		int index = -1;
		if(isDark == 1)
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
		
		if(index >= 0 && index <= 15)
		{
			if(isDark == 1)
			{
				darkPieces[index] = tempPiece;
			}
			else
			{
				lightPieces[index] = tempPiece;
			}
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
	
	public Piece findPiece(char pieceType, int isDark)
	{
		Piece piece = null;
		
		if(isDark == 1)
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
		else if(isDark == -1)
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
		return darkTurn + "\n" + chessBoard.toString();
	}
}
