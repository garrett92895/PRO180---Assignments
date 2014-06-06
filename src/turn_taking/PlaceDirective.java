package turn_taking;

import java.util.ArrayList;

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
	public boolean execute(ChessBoard chessBoard, ArrayList<Piece> darkPieces,
			ArrayList<Piece> lightPieces, boolean darkTurn) 
	{
		int isDark = 0;
		boolean successfulExecution = false;
		ArrayList<Piece> pieces = (pieceColor == 'l') ? lightPieces : darkPieces;
		isDark = (pieceColor == 'l') ? -1 : 1;
		
		if(pieces.size() <= 16)
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
			
			pieces.add(tempPiece);
			
//			System.out.println("Placing " + PieceMap.returnPiece(pieceType) + " on "
//					+ (char)(tempPiece.getPosition().getColumn() + 'A') + (Math.abs(tempPiece.getPosition().getRow() - 8)));
			successfulExecution = true;
		}
		
		updateBoard(chessBoard, darkPieces, lightPieces);
		return false;
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
