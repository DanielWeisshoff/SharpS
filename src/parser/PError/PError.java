package parser.PError;

import lexer.Token;
import lexer.TokenType;
import logger.Channel;
import logger.Logger;
import utils.Goethe;

/**
 * Logs the error message and exits the program
 */
public abstract class PError {

    private String msg;

    private Token[] tokens;

    // public PError(String msg, int line, int pos) {
    // }

    public PError(String msg, Token... tokens) {
        this.msg = msg;
        this.tokens = tokens;

        printInfo();

        Logger.log(msg, Channel.PARSER);
        //TODO temporary
        Logger.writeBufferToFile();

        System.exit(0);
    }

    private void printInfo() {
        //TODO gibt ne Color Klasse, oder den editor nutzen
        String colorRed = "\u001b[31m";
        String colorReset = "\u001B[0m";

        //Head message
        System.out.println(colorRed + msg + ":\n" + colorReset);

        //printing the error line
        int lineNumber = tokens[0].line;
        String line = Goethe.getLine(lineNumber);
        String prefix = lineNumber + " |    ";

        System.out.println(prefix + line);

        //print offset for line number
        for (int i = 0; i < prefix.length(); i++)
            System.out.print(" ");

        //printing the markers
        int pos = 0;
        for (Token t : tokens) {
            //TODO generelle funktion fuer whitespace nutzen
            if (t.type == TokenType.TAB)
                continue;

            //offset till error token
            for (int i = pos; i <= t.end; i++) {
                if (pos < t.start)
                    System.out.print(" ");
                else
                    System.out.print(colorRed + "^");
                pos++;
            }
            System.out.print(colorReset);
        }
    }
}
