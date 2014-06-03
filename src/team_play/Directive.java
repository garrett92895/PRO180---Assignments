package team_play;

import java.util.ArrayList;

public abstract class Directive {
	protected int row1;
	protected int column1;
	
	public Directive(int column1, int row1)
	{
		this.row1 = row1;
		this.column1 = column1;
	}
	
	public abstract boolean execute(ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces, boolean darkTurn);
	
	public void updateBoard(ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces)
	{
		for(int i = 0; i < chessBoard.BOARD_SIZE; i++)
		{
			for(int x = 0; x < chessBoard.BOARD_SIZE; x++)
			{
				Piece piece = findPiece(new Position(i, x), chessBoard, darkPieces, lightPieces);
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
	
	public Piece findPiece(Position position, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces)
	{
		Piece returnPiece = null;
		
		for(int i = 0; i < darkPieces.size(); i++)
		{
				if(darkPieces.get(i).getPosition().compareTo(position) == 0)
				{
					returnPiece = darkPieces.get(i);
				}
		}
		for(int i = 0; i < lightPieces.size(); i++)
		{
				if(lightPieces.get(i).getPosition().compareTo(position) == 0)
				{
					returnPiece = lightPieces.get(i);
				}
		}
		
		return returnPiece;
	}
	
	public Piece findPiece(char pieceType, int isDark, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces)
	{
		Piece piece = null;
		ArrayList<Piece> dangerPieces = (isDark == 1 ? darkPieces : lightPieces);
		
		for(Piece p : dangerPieces)
		{
			if(p != null)
			{
				if(p.getPieceChar() == pieceType)
				{
					piece = p;
				}
			}
		}
		
		return piece;
	}
	
	public boolean isInCheck(int isDark, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces)
	{
		boolean valid = false;
		King king = null;
		king = (King) findPiece('k', isDark, chessBoard, darkPieces, lightPieces);
		ArrayList<Piece> dangerPieces = (isDark == 1 ? lightPieces : darkPieces);
			
		for(int i = 0; i < dangerPieces.size(); i++)
		{
			if(dangerPieces.get(i) != null &&
					dangerPieces.get(i).captureIsValid(new Position(king.getPosition().getRow(), king.getPosition().getColumn()), chessBoard, darkPieces, lightPieces))
			{
				valid = true;
			}
		}
	
		return valid;
	}
	
	public boolean isRightTurn(boolean darkTurn, Piece piece)
	{
		boolean valid = false;
		
		int turnNum = darkTurn ? 1 : -1;
		if(piece != null && turnNum == piece.getColorModifier())
		{
			valid = true;
		}
		
		return valid;
	}
	
	public int getRow1() {
		return row1;
	}
	public void setRow1(int row1) {
		this.row1 = row1;
	}
	public int getColumn1() {
		return column1;
	}
	public void setColumn1(int column1) {
		this.column1 = column1;
	}
}
