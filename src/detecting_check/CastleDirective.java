package detecting_check;

public class CastleDirective extends MoveDirective{
	private MoveDirective rookMove;
	
	public CastleDirective(int column1, int row1, int column2, int row2, MoveDirective directive2)
	{
		super(column1, row1, column2, row2, false);
		rookMove = directive2;
	}
	
	@Override
	public boolean execute(ChessBoard chessBoard, Piece[] darkPieces, Piece[] lightPieces, boolean darkTurn)
	{
		int isDark = -1;
		String errorMessage = "";
		boolean successfulExecution = false;
		
		if(row1 == 0)
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

		Piece tempPiece1 = findPiece('k', isDark, chessBoard, lightPieces, lightPieces);
		Piece tempPiece2 = findPiece(new Position(rookMove.getRow1(), rookMove.getColumn1()), chessBoard, lightPieces, lightPieces);
		King king = null;
		Rook rook = null;
		if(tempPiece1 instanceof King && tempPiece2 instanceof Rook && rightTurn)
		{
			king = (King) tempPiece1;
			rook = (Rook) tempPiece2;
			if(rook.getMoveCount() == 0)
			{
				if(king.castleIsValid(new Position(row2, column2), chessBoard, darkPieces, lightPieces)
						&& rook.moveIsClear(new Position(rookMove.getRow2(), rookMove.getColumn2()),
								chessBoard, darkPieces, lightPieces))
				{
					king.setPosition(new Position(row2, column2));
					rook.setPosition(new Position(rookMove.getRow2(), rookMove.getColumn2()));
					rook.incrementMoveCount();
					king.incrementMoveCount();
					
					System.out.println("Castled King to " + (char)(king.getPosition().getColumn() + 'A') + (king.getPosition().getRow() + 1)
							+ " and Rook to " + (char)(rook.getPosition().getColumn() + 'A') + (rook.getPosition().getRow() + 1));
					updateBoard(chessBoard, lightPieces, lightPieces);
					successfulExecution = true;
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
		return successfulExecution; 
	}
	
	public MoveDirective getRookMove()
	{
		return rookMove;
	}
}
