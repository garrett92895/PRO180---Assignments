package team_play;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Garrett Holbrook
 *
 */
public class Game {
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
		
	}
	
	public void run()
	{
		Scanner scan = new Scanner(System.in);
		System.out.println(toString());
		
		while(!ChessFunctions.checkMate(1, chessBoard, darkPieces, lightPieces)
				&& !ChessFunctions.checkMate(-1, chessBoard, darkPieces, lightPieces))
		{
			String colorTurn = (darkTurn) ? "Dark" : "Light";
			System.out.println(colorTurn + " turn" + "\n" + "These are your pieces with valid moves: ");
			
			ArrayList<Piece> piecesWithMoves = getPiecesWithMoves(darkTurn);
			
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
			
			if(userChoice >= 0 && userChoice < piecesWithMoves.size())
			{
				Piece userPiece = piecesWithMoves.get(userChoice);
				ArrayList<Position> moves = getMovesForPiece(userPiece);
				
				System.out.println("Here is a list of the moves the " + PieceMap.returnPiece(userPiece.getPieceChar()) + " can make");
				
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
					if(chessBoard.getBoard()[moves.get(userChoice).getRow()][moves.get(userChoice).getColumn()] != '-')
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
			darkTurn = !darkTurn;
			System.out.println(toString());
			
			
		}
	}
	
	private ArrayList<Piece> getPiecesWithMoves(boolean darkTurn)
	{
		ArrayList<Piece> currentPieces = (darkTurn) ? darkPieces : lightPieces;
		ArrayList<Piece> piecesWithMoves = new ArrayList<Piece>();
		
		for(Piece p : currentPieces)
		{
			for(int i = 0; i < chessBoard.BOARD_SIZE; i++)
			{
				for(int x = 0; x < chessBoard.BOARD_SIZE; x++)
				{
					if(p.moveIsValid(new Position(i, x), chessBoard, darkPieces, lightPieces) && !piecesWithMoves.contains(p))
					{
						piecesWithMoves.add(p);
					}
				}
			}
		}
		
		return piecesWithMoves;
	}
	
	private ArrayList<Position> getMovesForPiece(Piece piece)
	{
		ArrayList<Position> moves = new ArrayList<Position>();
		for(int i = 0; i < chessBoard.getBoard().length; i++)
		{
			for(int x = 0; x < chessBoard.getBoard()[i].length; x++)
			{
				if(piece.moveIsValid(new Position(i, x), chessBoard, darkPieces, lightPieces) 
						|| piece.captureIsValid(new Position(i, x), chessBoard, darkPieces, lightPieces))
				{
					moves.add(new Position(i, x));
				}
			}
		}
		
		return moves;
	}
	
	@Override
	public String toString()
	{
		return /*darkTurn + "\n" + */chessBoard.toString();
	}
}
