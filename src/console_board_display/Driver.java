package console_board_display;

public class Driver {

	public static void main(String[] args) 
	{
		Game game = new Game();
		MoveReader reader = new MoveReader(args[0]);
		Directive[] moves = reader.collectDirectives();
		
		for(int i = 0; i < moves.length; i++)
		{
			game.executeDirective(moves[i]);
		}
		System.out.println(game);
	}

}
