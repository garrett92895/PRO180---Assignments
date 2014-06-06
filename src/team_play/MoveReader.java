package team_play;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MoveReader {
	private BufferedReader br;
	private MoveParser parser;
	private final String PIECES = "([qkbnrp])";
	private final String COLORS = "([ld])";
	private final String RANKS = "([a-h])";
	private final String COLUMNS = "([1-8])";
	
	
	public MoveReader(String fileName)
	{
		parser = new MoveParser();
		File file = new File(fileName);	
		try
		{
			br = new BufferedReader(new FileReader(fileName));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found");
			System.exit(0);
		}
	}
	
	public MoveReader()
	{
		parser = new MoveParser();
	}
	
	public Directive[] collectDirectives()
	{		
		String line;
		String[] directives;
		ArrayList<Directive> moves = new ArrayList<Directive>();
		Directive[] returnMoves = null;
		try 
		{
			while((line = br.readLine()) != null)
			{
				directives = line.toLowerCase().split("\\s"); //Splits each line into an array of directives
				boolean matches = false;
				
				//Loops through the array of strings and checks to see if they match the requirements, if they don't then the
				//for loop is finished
				for(int i = 0; i < directives.length; i++)
				{
					if(directives[i].matches("(" + ( PIECES + "?" + COLORS + "?" + RANKS + COLUMNS) + ")" + "\\*?")) //Regex
					{
						matches = true;
					}
					else
					{
						matches = false;
						i = directives.length;
					}
				}
				
				if(matches)
				{
					moves.add(parser.parseMove(directives));
				}
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		returnMoves = new Directive[moves.size()];
		for(int i = 0; i < returnMoves.length; i++)
		{
			returnMoves[i] = moves.get(i);
		}
		//Closes the stream
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnMoves;
	}
}
