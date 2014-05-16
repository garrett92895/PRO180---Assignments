package console_board_display;

public class MoveParser {
	private static PieceMap h = new PieceMap();
	
	public static void parseMove(String[] chessMoves)
	{
		//Output depends on the number of directives
		switch(chessMoves.length)
		{
		case 1:
			String s1 = "Places a " + h.returnPiece(chessMoves[0].charAt(0))
					+ " of color " + h.returnPiece(chessMoves[0].charAt(1))
					+ " at column " + chessMoves[0].charAt(2) 
					+ " and row " + chessMoves[0].charAt(3);
			System.out.println(s1);
			break;
		case 2:
			String s2 = "";
			if(!chessMoves[1].endsWith("*"))
			{
				s2 = "Takes the piece at column " + chessMoves[0].charAt(0)
				+ " row " + chessMoves[0].charAt(1) 
				+ " and places it at column" + chessMoves[1].charAt(0)
				+ " row " + chessMoves[1].charAt(0);
			}
			else
			{
				s2 = "Takes the piece at column " + chessMoves[0].charAt(0)
				+ " row " + chessMoves[0].charAt(1) 
				+ " and captures the piece at column" + chessMoves[1].charAt(0)
				+ " row " + chessMoves[1].charAt(0);
			}
			System.out.println(s2);
			break;
		case 4:
			String s3 = "Moves the King from column " + chessMoves[0].charAt(0)
			+ " row " + chessMoves[0].charAt(1)
			+ " to column " + chessMoves[1].charAt(0)
			+ " row " + chessMoves[1].charAt(1)
			+ " and the Rook from column " + chessMoves[2].charAt(0)
			+ " row " + chessMoves[2].charAt(1)
			+ " to column " + chessMoves[3].charAt(0)
			+ " row " + chessMoves[3].charAt(1);
			System.out.println(s3);
		}
	}
}
