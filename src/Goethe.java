
import java.io.File; // Import the File class
import java.io.FileWriter;
import java.io.IOException; // Import the IOException class to handle errors
import java.util.List;

import com.danielweisshoff.lexer.Token;

public class Goethe {

<<<<<<< Updated upstream
	public static void writeToFile(String fileName,
			String filePath, String text) {
		try {
			new File(filePath + "/" + fileName + ".txt");

			FileWriter myWriter = new FileWriter(
					filePath + "/" + fileName + ".txt");
			myWriter.write(text);
			myWriter.close();
		} catch (IOException e) {
			System.out.println("Fehler beim schreiben der Outputdatei");
		}
	}

	public static void writeTokensToFile(List<Token> tokens) {

		StringBuilder converted = new StringBuilder();

		for (Token t : tokens)
			converted.append(t.getDescription() + "\n");
		converted.append("ß");
		Goethe.writeToFile("output",
				"C:\\Users\\danie\\Desktop\\",
				converted.toString());
	}
=======
    public static void writeToFile(String fileName, String filePath, String text) {
        try {
            new File(filePath + "/" + fileName + ".txt");
            FileWriter myWriter = new FileWriter(filePath + "/" + fileName + ".txt");
            myWriter.write(text);
            myWriter.close();
        } catch (IOException ignored) {
        }
    }

    public static void writeTokensToFile(List<Token> tokens) {

        StringBuilder converted = new StringBuilder();

        for (Token t : tokens)
            converted.append(t.getDescription()).append("\n");
        Goethe.writeToFile("output", "C:\\Users\\danie\\Desktop\\", converted.toString());
    }

    public static String readFile() {
        StringBuilder stringy = new StringBuilder();
        try {
            File myObj = new File("C:\\Users\\danie\\Desktop\\program.txt");
            Scanner charScanner = new Scanner(myObj).useDelimiter("(\\b|\\B)");
            while (charScanner.hasNext())
                stringy.append(charScanner.next());
        } catch (Exception pickachu) {
            System.out.println("Failer");
        }
        return stringy.toString();
    }
>>>>>>> Stashed changes
}
