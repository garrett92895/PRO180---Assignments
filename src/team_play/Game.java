package team_play;

import java.util.ArrayList;

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
	

	@Override
	public String toString()
	{
		return /*darkTurn + "\n" + */chessBoard.toString();
	}
}
