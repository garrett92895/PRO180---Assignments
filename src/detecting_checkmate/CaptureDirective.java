package detecting_checkmate;

import java.util.ArrayList;

public class CaptureDirective extends MoveDirective{

	public CaptureDirective(int column1, int row1, int column2, int row2) 
	{
		super(column1, row1, column2, row2);
	}
	
	@Override
	public boolean execute(ChessBoard chessBoard, ArrayList<Piece> darkPieces,
			ArrayList<Piece> lightPieces, boolean darkTurn) 
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
						char column = (char)(piece.getPosition().getColumn() + 'A');
						int row = (piece.getPosition().getRow()) + 1;
						piece.setPosition(new Position(row2, column2));
						ArrayList<Piece> dangerPieces = (enemyPiece.getColorModifier() == 1) ? darkPieces : lightPieces;
						
						for(int i = 0; i < dangerPieces.size(); i++)
						{
							if(dangerPieces.get(i) != null)
							{
								if(dangerPieces.get(i).equals(enemyPiece))
								{
									dangerPieces.remove(i);
								}
							}
						}
						
						if(!isInCheck(piece.getColorModifier(), chessBoard, darkPieces, lightPieces))
						{
							System.out.println(PieceMap.returnPiece(piece.getPieceChar()) + " from " +
									column + row + " capturing " + PieceMap.returnPiece(enemyPiece.getPieceChar()) + " on " +
									(char)(enemyPiece.getPosition().getColumn() + 'A') + (enemyPiece.getPosition().getRow() + 1));
							
							successfulExecution = true;
							updateBoard(chessBoard, darkPieces, lightPieces);
						}
						else
						{
							dangerPieces.add(enemyPiece);
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
		if(successfulExecution)
		{
			King king = (King) findPiece('k', piece.getColorModifier() * -1, chessBoard, darkPieces, lightPieces);
			king.setInCheck(isInCheck(king.getColorModifier(), chessBoard, darkPieces, lightPieces));
			
			char color = (king.getColorModifier() == 1) ? 'd' : 'l';
			if(king.isInCheck())
			System.out.println(PieceMap.returnPiece(color) + " King in check");
		}
		if(!errorMessage.equals(""))
			System.err.println(errorMessage);
		return successfulExecution;
	}

}
