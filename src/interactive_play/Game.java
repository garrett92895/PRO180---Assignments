package interactive_play;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

/**
 * @author Garrett Holbrook
 *
 */
public class Game extends Observable{
	private final int NUM_OF_PIECES = 16;
	private final PieceMap PIECE_MAP = new PieceMap();
	private ChessBoard chessBoard = new ChessBoard();
	private ArrayList<Piece> darkPieces = new ArrayList<Piece>();
	private ArrayList<Piece> lightPieces = new ArrayList<Piece>();
	private boolean darkTurn;
	
	public Game()
	{
		darkTurn = false;
		PlaceDirective[] darkPiecePlace = new PlaceDirective[16];
		PlaceDirective[] lightPiecePlace = new PlaceDirective[16];
		
		for(int i = 0; i < (NUM_OF_PIECES / 2); i++)
		{
			darkPiecePlace[i] = new PlaceDirective('p', 'd', i, 2);
			lightPiecePlace[i] = new PlaceDirective('p', 'l', i, 7);
		}
		darkPiecePlace[8] = new PlaceDirective('r', 'd', 0, 1);
		darkPiecePlace[9] = new PlaceDirective('r', 'd', 7, 1);
		darkPiecePlace[10] = new PlaceDirective('n', 'd', 1, 1);
		darkPiecePlace[11] = new PlaceDirective('n', 'd', 6, 1);
		darkPiecePlace[12] = new PlaceDirective('b', 'd', 2, 1);
		darkPiecePlace[13] = new PlaceDirective('b', 'd', 5, 1);
		darkPiecePlace[14] = new PlaceDirective('q', 'd', 3, 1);
		darkPiecePlace[15] = new PlaceDirective('k', 'd', 4, 1);
		lightPiecePlace[8] = new PlaceDirective('r', 'l', 0, 8);
		lightPiecePlace[9] = new PlaceDirective('r', 'l', 7, 8);
		lightPiecePlace[10] = new PlaceDirective('n', 'l', 1, 8);
		lightPiecePlace[11] = new PlaceDirective('n', 'l', 6, 8);
		lightPiecePlace[12] = new PlaceDirective('b', 'l', 2, 8);
		lightPiecePlace[13] = new PlaceDirective('b', 'l', 5, 8);
		lightPiecePlace[14] = new PlaceDirective('q', 'l', 3, 8);
		lightPiecePlace[15] = new PlaceDirective('k', 'l', 4, 8);
		


		for(int i = 0; i < darkPiecePlace.length; i++)
		{
			executeDirective(darkPiecePlace[i]);
			executeDirective(lightPiecePlace[i]);
		}
		
		//Fool's mate
//		executeDirective(new MoveDirective(5, 6, 5, 5));
//		executeDirective(new MoveDirective(4, 1, 4, 2));
//		executeDirective(new MoveDirective(6, 6, 6, 4));
//		executeDirective(new MoveDirective(3, 0, 7, 4));
		
		//Test1
//		executeDirective(new CaptureDirective(7, 7, 7, 0));
//		executeDirective(new MoveDirective(4, 0, 4, 1));
//		executeDirective(new MoveDirective(7, 0, 7, 1));
	}
	
	public void run()
	{
		Scanner scan = new Scanner(System.in);
		//Prints board
		System.out.println(toString());
		
		//The game will run until checkmate or stalemate occurs
		while(!ChessFunctions.checkMate(1, chessBoard, darkPieces, lightPieces, !darkTurn)
				&& !ChessFunctions.checkMate(-1, chessBoard, darkPieces, lightPieces, !darkTurn)
				&& !ChessFunctions.staleMate(1, chessBoard, darkPieces, lightPieces, !darkTurn)
				&& !ChessFunctions.staleMate(-1, chessBoard, darkPieces, lightPieces, !darkTurn))
		{
			//colorTurn is assigned an appropriate string for Sysout purposes
			String colorTurn = (darkTurn) ? "Dark" : "Light";
			System.out.println(colorTurn + " turn" + "\n" + "These are your pieces with valid moves: ");
			
			//Stores an array of all of the pieces with valid moves
			ArrayList<Piece> piecesWithMoves = getPiecesWithMoves();
			
			//Prints to the console all the valid pieces
			for(int i = 0; i < piecesWithMoves.size(); i++)
			{
				Piece p = piecesWithMoves.get(i);
				
				System.out.println(i + ") " + PieceMap.returnPiece(p.getPieceChar()) + " on " + (char)(p.getPosition().getColumn() + 'A') +
						(Math.abs(p.getPosition().getRow() - 8)));
			}
			
			System.out.println("Enter the number of the piece you'd like to move.");
			int userChoice = -1;
			
			try
			{
				userChoice = Integer.parseInt(scan.nextLine());
			}catch(NumberFormatException e){}
			
			//If an error was caught userChoice will still be -1
			if(userChoice >= 0 && userChoice < piecesWithMoves.size())
			{
				//Gets the specific piece the user selected and gets all of its valid moves
				Piece userPiece = piecesWithMoves.get(userChoice);
				ArrayList<Position> moves = getMovesForPiece(userPiece);
				
				System.out.println("Here is a list of the moves the " + PieceMap.returnPiece(userPiece.getPieceChar()) + " can make");
				
				//Prints the list of valid moves for the selected piece
				for(int i = 0; i < moves.size(); i++)
				{
					System.out.println(i + ") " + (char)(moves.get(i).getColumn() + 'A') + Math.abs(moves.get(i).getRow() - 8));
				}
				
				System.out.println("Enter the number of the move you'd like to make, or enter a non-number to go back: ");
				userChoice = -1;
				
				try
				{
					userChoice = Integer.parseInt(scan.nextLine());
				}catch(NumberFormatException e){}
				
				if(userChoice >= 0 && userChoice < moves.size())
				{
					Directive userMove = null;
					//If the end position that the user selected from the list has a piece then a 
					//CaptureDirective object is created. Else, a MoveDirective
					if(ChessFunctions.findPiece(new Position(moves.get(userChoice).getRow(), moves.get(userChoice).getColumn()), 
							chessBoard, darkPieces, lightPieces) != null)
					{
						userMove = new CaptureDirective(userPiece.getPosition().getColumn(), userPiece.getPosition().getRow(),
								moves.get(userChoice).getColumn(), moves.get(userChoice).getRow());
					}
					else
					{
						userMove = new MoveDirective(userPiece.getPosition().getColumn(), userPiece.getPosition().getRow(),
								moves.get(userChoice).getColumn(), moves.get(userChoice).getRow());
					}
					
					//Executes directive that was just made
					executeDirective(userMove);
				}
			}
			else
			{
				System.out.println("Invalid choice. Please enter a valid piece choice.");
			}
		}
		
		if(ChessFunctions.checkMate(1, chessBoard, darkPieces, lightPieces, !darkTurn)
				|| ChessFunctions.checkMate(-1, chessBoard, darkPieces, lightPieces, !darkTurn))
		{
			System.out.println("Checkmate");
		}
		else
		{
			System.out.println("Stalemate");
		}
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
		if(move.execute(chessBoard, darkPieces, lightPieces, darkTurn))
		{
			setChanged();
			notifyObservers();
			if(ChessFunctions.staleMate(1, chessBoard, darkPieces, lightPieces, darkTurn)
					|| ChessFunctions.staleMate(-1, chessBoard, darkPieces, lightPieces, darkTurn))
			{
				System.out.println("Stalemate");
			}
			else if(ChessFunctions.checkMate(1, chessBoard, darkPieces, lightPieces, darkTurn)
					|| ChessFunctions.checkMate(-1, chessBoard, darkPieces, lightPieces, darkTurn))
			{
				System.out.println("Checkmate");
			}
			darkTurn = !darkTurn;
		}
	}
	
	public ArrayList<Piece> getPiecesWithMoves()
	{
		ArrayList<Piece> currentPieces = (darkTurn) ? darkPieces : lightPieces;
		ArrayList<Piece> piecesWithMoves = new ArrayList<Piece>();
		
		//Loops through every piece on a specific team with every spot on the board and adds any pieces with 
		//one or more moves
		for(Piece p : currentPieces)
		{
			for(int i = 0; i < chessBoard.BOARD_SIZE; i++)
			{
				for(int x = 0; x < chessBoard.BOARD_SIZE; x++)
				{
					if((p.moveIsValid(new Position(i, x), chessBoard, darkPieces, lightPieces)
							|| p.captureIsValid(new Position(i, x), chessBoard, darkPieces, lightPieces, darkTurn)
							&& !piecesWithMoves.contains(p)))
					{
						Position savedPosition = p.getPosition();
						Piece enemyPiece = ChessFunctions.findPiece(new Position(i, x), chessBoard, darkPieces, lightPieces);
						ArrayList<Piece> enemyPieces = (p.getColorModifier() == 1) ? lightPieces : darkPieces;
						enemyPieces.remove(enemyPiece);
						enemyPieces.remove(enemyPiece);
						p.setPosition(new Position(i, x));
						
						if(!ChessFunctions.isInCheck(p.getColorModifier(), chessBoard, darkPieces, lightPieces, !darkTurn))
						{
							piecesWithMoves.add(p);
						}
						if(enemyPiece != null)
						{
							enemyPieces.add(enemyPiece);
						}
						p.setPosition(savedPosition);
					}
				}
			}
		}
		
		return piecesWithMoves;
	}
	
	public ArrayList<Position> getMovesForPiece(Piece piece)
	{
		ArrayList<Position> moves = new ArrayList<Position>();
		Position savedPosition = piece.getPosition();
		
		//Loops through every spot on the board and checks to see if piece has a valid move or 
		//capture to that specific position. If so, it will add that position to an arraylist
		for(int i = 0; i < chessBoard.getBoard().length; i++)
		{
			for(int x = 0; x < chessBoard.getBoard()[i].length; x++)
			{
				if(piece.moveIsValid(new Position(i, x), chessBoard, darkPieces, lightPieces) 
						|| piece.captureIsValid(new Position(i, x), chessBoard, darkPieces, lightPieces, darkTurn))
				{
					Piece enemyPiece = ChessFunctions.findPiece(new Position(i, x), chessBoard, darkPieces, lightPieces);
					ArrayList<Piece> enemyPieces = (piece.getColorModifier() == 1) ? lightPieces : darkPieces;
					enemyPieces.remove(enemyPiece);
					enemyPieces.remove(enemyPiece);
					piece.setPosition(new Position(i, x));
					
					if(!ChessFunctions.isInCheck(piece.getColorModifier(), chessBoard, darkPieces, lightPieces, !darkTurn))
					{
						moves.add(new Position(i, x));
					}
					if(enemyPiece != null)
					{
						enemyPieces.add(enemyPiece);
					}
					piece.setPosition(savedPosition);
				}
			}
		}
		
		return moves;
	}

	public ChessBoard getChessBoard()
	{
		return chessBoard;
	}
	
	public ArrayList<Piece> getDarkPieces()
	{
		return darkPieces;
	}
	
	public ArrayList<Piece> getLightPieces()
	{
		return lightPieces;
	}
	
	public boolean isDarkTurn() {
		return darkTurn;
	}

	public void setDarkTurn(boolean darkTurn) {
		this.darkTurn = darkTurn;
	}

	@Override
	public String toString()
	{
		return chessBoard.toString();
	}
}
