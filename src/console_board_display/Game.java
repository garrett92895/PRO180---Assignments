package console_board_display;

public class Game {
	private char[][] chessBoard = new char[8][8];
	
	public Game()
	{
		for(int i = 0; i < chessBoard.length; i++)
		{
			for(int x = 0; x < chessBoard[i].length; x++)
			{
				chessBoard[i][x] = '-';
			}
		}
	}
	
	public void executeDirective(Directive move)
	{
		if(move instanceof PlaceDirective)
		{
			PlaceDirective placeMove = (PlaceDirective) move;
			
			if(placeMove.getPieceColor() == 'l')
			{
				chessBoard[placeMove.getRow1()][placeMove.getColumn1()] = placeMove.getPieceType();
			}
			else if(placeMove.getPieceColor() == 'd')
			{
				chessBoard[placeMove.getRow1()][placeMove.getColumn1()] = Character.toUpperCase(placeMove.getPieceType());
			}
		}
		else if(move instanceof CastleDirective)
		{
			CastleDirective castleMove = (CastleDirective) move;
			char king = chessBoard[castleMove.getRow1()][castleMove.getColumn1()];
			char rook = chessBoard[castleMove.getRookMove().getRow1()][castleMove.getRookMove().getColumn1()];
			
			chessBoard[castleMove.getRow1()][castleMove.getColumn1()] = '-';
			chessBoard[castleMove.getRookMove().getRow1()][castleMove.getRookMove().getColumn1()] = '-';
			chessBoard[castleMove.getRow2()][castleMove.getColumn2()] = king;
			chessBoard[castleMove.getRookMove().getRow2()][castleMove.getRookMove().getColumn2()] = rook;
		}
		else if(move instanceof MoveDirective)
		{
			MoveDirective singleMove = (MoveDirective) move;
			char piece = chessBoard[singleMove.getRow1()][singleMove.getColumn1()];
			
			chessBoard[singleMove.getRow1()][singleMove.getColumn1()] = '-';
			chessBoard[singleMove.getRow2()][singleMove.getColumn2()] = piece;
		}

		
	}
	
	@Override
	public String toString()
	{
		String s = "    A  B  C  D  E  F  G  H\n";
		s += "    ______________________\n";
		for(int i = 0; i < chessBoard.length; i++)
		{	s += (i + 1) + "|  ";
			for(int x = 0; x < chessBoard[i].length; x++)
			{
				s += chessBoard[i][x];
				s += "  ";
			}
			s += '\n';
		}
		return s;
	}
}
