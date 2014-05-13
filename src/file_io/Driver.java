package file_io;

public class Driver {

	public static void main(String[] args) {
		MoveReader reader = new MoveReader(args[0]);
		reader.run();
	}

}
