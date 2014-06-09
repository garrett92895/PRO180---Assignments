package interactive_play;

import java.util.ArrayList;

public class MoveDirective extends Directive{
	protected int row2;
	protected int column2;
	
	public MoveDirective(int column1, int row1, int column2, int row2)
	{
		super(column1, row1);
		this.row2 = row2;
		this.column2 = column2;
	}
	
	@Override
	public boolean execute(ChessBoard chessBoard, ArrayList<Piece> darkPieces,
			ArrayList<Piece> lightPieces, boolean darkTurn) 
	{
		Piece piece = ChessFunctions.findPiece(new Position(row1, column1), chessBoard, darkPieces, lightPieces);
		String errorMessage = "";
		boolean successfulExecution = false;
		
		if(piece != null)
		{
			if(ChessFunctions.isRightTurn(darkTurn, piece))
			{
				if(piece.moveIsValid(new Position(row2, column2), chessBoard, darkPieces, lightPieces))
				{
					piece.setPosition(new Position(row2, column2));
					
					if(!ChessFunctions.isInCheck(piece.getColorModifier(), chessBoard, darkPieces, lightPieces, darkTurn))
					{
						System.out.println("" +
								(char)(column1 + 'a') + (Math.abs(row1 - 8))
								+ " " +
								(char)(column2 + 'a') + (Math.abs(row2 - 8)));
	
						successfulExecution = true;
						piece.incrementMoveCount();
						ChessFunctions.updateBoard(chessBoard, darkPieces, lightPieces);
					}
					else
					{
						piece.setPosition(new Position(row1, column1));
						errorMessage += "INVALID: Your " + PieceMap.returnPiece(piece.getColorModifier()) + " King would be in check";
					}
				}	
				else
				{
					errorMessage += "INVALID: Move is not valid for that piece";
				}
			}
			else
			{
				errorMessage += "INVALID: Not the right player's turn";
			}
		}
		else
		{
			errorMessage += "INVALID: No piece found on starting position\n";
		}
		if(successfulExecution)
		{
			King king = (King) ChessFunctions.findPiece('k', piece.getColorModifier() * -1, chessBoard, darkPieces, lightPieces);
			king.setInCheck(ChessFunctions.isInCheck(king.getColorModifier(), chessBoard, darkPieces, lightPieces, darkTurn));
			char color = (king.getColorModifier() == 1) ? 'd' : 'l';
			
			if(king.isInCheck())
			{
//				if(ChessFunctions.checkMate(king.getColorModifier(), chessBoard, darkPieces, lightPieces, darkTurn))
//				{
//					System.out.println("Check mate");
//					ChessFunctions.updateBoard(chessBoard, darkPieces, lightPieces);
//					System.out.println(chessBoard);
//					//System.exit(0);
//				}
//				else
//				{
				System.out.println(PieceMap.returnPiece(color) + " King in check");
//				}
			}

		}
		if(!errorMessage.equals(""))
			System.err.println(errorMessage);
		return successfulExecution;
	}

	public int getRow2() {
		return row2;
	}

	public int getColumn2() {
		return column2;
	}
}
