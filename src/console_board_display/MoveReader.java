package console_board_display;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MoveReader {
	private BufferedReader br;
	private final String PIECES = "([qkbnrp])";
	private final String COLORS = "([ld])";
	private final String RANKS = "([a-h])";
	private final String COLUMNS = "([1-8])";
	
	
	public MoveReader(String fileName)
	{
		
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
	
	public void runDirectives()
	{		
		String line;
		String[] directives;
		try 
		{
			if((line = br.readLine()) != null)
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
					parseMove(directives);
				}
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		//Closes the stream
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	



}
