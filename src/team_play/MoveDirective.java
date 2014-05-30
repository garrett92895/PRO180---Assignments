package team_play;

public class MoveDirective extends Directive{
	protected int row2;
	protected int column2;
	
	public MoveDirective(int column1, int row1, int column2, int row2)
	{
		super(column1, row1 - 1);
		this.row2 = row2 - 1;
		this.column2 = column2;
	}
	
	@Override
	public boolean execute(ChessBoard chessBoard, Piece[] darkPieces,
			Piece[] lightPieces, boolean darkTurn) 
	{
		Piece piece = findPiece(new Position(row1, column1), chessBoard, darkPieces, lightPieces);
		String errorMessage = "";
		boolean rightTurn = false;
		boolean successfulExecution = false;
		
		if(piece != null)
		{
			int turnNum = darkTurn ? 1 : -1;
			if(turnNum == piece.getColorModifier())
			{
				rightTurn = true;
			}
			else
			{
				errorMessage += "INVALID: Not the right player's turn\n";
			}
			
			if(rightTurn)
			{
				if(piece.moveIsValid(new Position(row2, column2), chessBoard, darkPieces, lightPieces))
				{
					piece.setPosition(new Position(row2, column2));
					
					if(!isInCheck(piece.getColorModifier(), chessBoard, darkPieces, lightPieces))
					{
					System.out.println(PieceMap.returnPiece(piece.getPieceChar()) + " from " +
							(char)(column1 + 'A') + (row1 + 1)
							+ " to " +
							(char)(column2 + 'A') + (row2 + 1));

					successfulExecution = true;
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
		}
		else
		{
			errorMessage += "INVALID: No piece found on starting position\n";
		}
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
