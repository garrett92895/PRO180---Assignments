package team_play;

public class CaptureDirective extends MoveDirective{

	public CaptureDirective(int column1, int row1, int column2, int row2) 
	{
		super(column1, row1, column2, row2);
	}
	
	@Override
	public boolean execute(ChessBoard chessBoard, Piece[] darkPieces,
			Piece[] lightPieces, boolean darkTurn) 
	{
		Piece piece = findPiece(new Position(row1, column1), chessBoard, darkPieces, lightPieces);
		String errorMessage = "";
		boolean successfulExecution = false;
		
		if(piece != null)
		{
			if(isRightTurn(darkTurn, piece))
			{
				if(piece.captureIsValid(new Position(row2, column2), chessBoard, darkPieces, lightPieces))
				{
						Piece enemyPiece = findPiece(new Position(row2, column2), chessBoard, darkPieces, lightPieces);
						piece.setPosition(new Position(row2, column2));
						Piece[] dangerPieces = (enemyPiece.getColorModifier() == 1) ? darkPieces : lightPieces;
						
						if(!isInCheck(piece.getColorModifier(), chessBoard, darkPieces, lightPieces))
						{
							System.out.println(PieceMap.returnPiece(piece.getPieceChar()) + " from " +
									(char)(piece.getPosition().getColumn() + 'A') + (piece.getPosition().getRow() + 1)
									+ " capturing " + PieceMap.returnPiece(enemyPiece.getPieceChar()) + " on " +
									(char)(enemyPiece.getPosition().getColumn() + 'A') + (enemyPiece.getPosition().getRow() + 1));
							
							successfulExecution = true;
							
							for(int i = 0; i < dangerPieces.length; i++)
							{
								if(dangerPieces[i] != null)
								{
									if(dangerPieces[i].equals(enemyPiece))
									{
										dangerPieces[i] = null;
									}
								}
							}
							updateBoard(chessBoard, darkPieces, lightPieces);
						}
						else
						{
							piece.setPosition(new Position(row1, column1));
							errorMessage += "INVALID: King in check";
						}
				}	
				else
				{
					errorMessage += "INVALID: Move is not valid for that piece";
				}
			}
			else
			{
				errorMessage += "INVALID: Not the right player's turn\n";
			}
		}
		else
		{
			errorMessage += "INVALID: No piece found on starting position\n";
		}
		System.err.println(errorMessage);
		return successfulExecution;
	}

}
