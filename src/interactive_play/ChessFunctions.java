package interactive_play;

import java.util.ArrayList;

public class ChessFunctions {

	public static void updateBoard(ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces)
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
	
	public static Piece findPiece(Position position, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces)
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

	public static Piece findPiece(char pieceType, int isDark, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces)
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

	public static boolean isInCheck(int isDark, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces, boolean darkTurn)
	{
		boolean valid = false;
		King king = null;
		king = (King) findPiece('k', isDark, chessBoard, darkPieces, lightPieces);
		ArrayList<Piece> dangerPieces = (isDark == 1 ? lightPieces : darkPieces);

		for(int i = 0; i < dangerPieces.size(); i++)
		{
			if(dangerPieces.get(i) != null &&
					dangerPieces.get(i).captureIsValid(new Position(king.getPosition().getRow(), king.getPosition().getColumn()), chessBoard, darkPieces, lightPieces, darkTurn))
			{
				valid = true;
			}
		}

		return valid;
	}

	public static boolean checkMate(int isDark, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces, boolean darkTurn)
	{
		King king = (King) findPiece('k', isDark, chessBoard, darkPieces, lightPieces);
		boolean checkMate = ChessFunctions.isInCheck(king.getColorModifier(), chessBoard, darkPieces, lightPieces, darkTurn);

		if(checkMate)
		{
			ArrayList<Piece> friendlyPieces = (king.getColorModifier() == 1) ? darkPieces : lightPieces;

			for(Piece p : friendlyPieces)
			{
				Position savedPosition = p.getPosition();
				for(int i = 0; i < chessBoard.getBoard().length; i++)
				{
					for(int x = 0; x < chessBoard.getBoard()[i].length; x++)
					{
						if(p.moveIsValid(new Position(i, x), chessBoard, darkPieces, lightPieces))
						{
							p.setPosition(new Position(i, x));
							updateBoard(chessBoard, darkPieces, lightPieces);
							if(!isInCheck(p.getColorModifier(), chessBoard, darkPieces, lightPieces, darkTurn))
							{
								checkMate = false;
							}
							p.setPosition(savedPosition);
							updateBoard(chessBoard, darkPieces, lightPieces);
						}

						if(p.captureIsValid(new Position(i, x), chessBoard, darkPieces, lightPieces, darkTurn))
						{
							p.setPosition(new Position(i, x));
							updateBoard(chessBoard, darkPieces, lightPieces);
							if(!isInCheck(p.getColorModifier(), chessBoard, darkPieces, lightPieces, darkTurn))
							{
								checkMate = false;
							}
							p.setPosition(savedPosition);
							updateBoard(chessBoard, darkPieces, lightPieces);
						}
					}
				}
			}
		}

		return checkMate;
	}

	public static boolean staleMate(int isDark, ChessBoard chessBoard, ArrayList<Piece> darkPieces, ArrayList<Piece> lightPieces, boolean darkTurn)
	{
		boolean stalemate = true;
		
		if(!isInCheck(isDark, chessBoard, darkPieces, lightPieces, darkTurn))
		{
			King king = (King) findPiece('k', isDark, chessBoard, darkPieces, lightPieces);
			ArrayList<Piece> friendlyPieces = (king.getColorModifier() == 1) ? darkPieces : lightPieces;
			
			for(Piece p : friendlyPieces)
			{
				Position savedPosition = p.getPosition();
				for(int i = 0; i < chessBoard.getBoard().length; i++)
				{
					for(int x = 0; x < chessBoard.getBoard()[i].length; x++)
					{
						if(p.moveIsValid(new Position(i, x), chessBoard, darkPieces, lightPieces))
						{
							p.setPosition(new Position(i, x));
							updateBoard(chessBoard, darkPieces, lightPieces);
							if(!isInCheck(p.getColorModifier(), chessBoard, darkPieces, lightPieces, darkTurn))
							{
								stalemate = false;
							}
							p.setPosition(savedPosition);
							updateBoard(chessBoard, darkPieces, lightPieces);
						}
						if(p.captureIsValid(new Position(i, x), chessBoard, darkPieces, lightPieces, darkTurn))
						{
							p.setPosition(new Position(i, x));
							updateBoard(chessBoard, darkPieces, lightPieces);
							if(!isInCheck(p.getColorModifier(), chessBoard, darkPieces, lightPieces, darkTurn))
							{
								stalemate = false;
							}
							p.setPosition(savedPosition);
							updateBoard(chessBoard, darkPieces, lightPieces);
						}
					}
				}
			}
		}
		else
		{
			stalemate = false;
		}
		
		return stalemate;
	}
	
	public static boolean isRightTurn(boolean darkTurn, Piece piece)
	{
		boolean valid = false;

		int turnNum = darkTurn ? 1 : -1;
		if(piece != null && turnNum == piece.getColorModifier())
		{
			valid = true;
		}

		return valid;
	}
}
