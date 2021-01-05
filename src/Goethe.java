import java.io.File; // Import the File class
import java.io.FileWriter;
import java.io.IOException; // Import the IOException class to handle errors
import java.util.List;

import com.danielweisshoff.lexer.Token;

public class Goethe {

    public static void writeToFile(String fileName,
                                   String filePath, String text) {
        try {
            new File(filePath + "/" + fileName + ".txt");

            FileWriter myWriter = new FileWriter(
                    filePath + "/" + fileName + ".txt");
            myWriter.write(text);
            myWriter.close();

        } catch (IOException e) {
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
}
