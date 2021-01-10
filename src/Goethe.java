import com.danielweisshoff.lexer.Token;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Goethe {

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
        converted.append("ß");
        Goethe.writeToFile("output",
                "C:\\Users\\danie\\Desktop\\",
                converted.toString());
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
}
