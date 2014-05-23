package turn_taking;

public class PieceMap {
	private final char[] PIECE_HASHES = new char[] {'k', 'q', 'b', 'n', 'r', 'p', 'l', 'd'};
	private final String[] PIECES = new String[] {"King", "Queen", "Bishop", "Knight", "Rook", "Pawn", "light", "dark"};
	
	public String returnPiece(char c)
	{
		int index = 0;
		for(int i = 0; i < PIECE_HASHES.length; i++)
		{
			if(PIECE_HASHES[i] == c)
			{
				index = i;
			}
		}
		
		return PIECES[index];
	}
}

