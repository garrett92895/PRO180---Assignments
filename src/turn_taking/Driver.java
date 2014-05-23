package turn_taking;

public class Driver {

	public static void main(String[] args) 
	{
		Game game = new Game();	
		
		//Constructs a new MoveReader with the file entered through the command line argument
		MoveReader reader = new MoveReader(args[0]);
		//Calls collectDirectives() which reads the file and, using regex, parses each line into a Directive object
		//which it then returns. moves stores all of the Directive objects
		Directive[] moves = reader.collectDirectives();
		
		//Loops through the array of Directive objects and executes them
		for(Directive d : moves)
		{
			game.executeDirective(d);
		}
	}

}
