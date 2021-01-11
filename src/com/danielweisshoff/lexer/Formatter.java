//package com.danielweisshoff.lexer;
//
//import java.util.ArrayList;
//
///**
// * Helper class to simplify the conversion from Tokens into Tokenrows
// */
//public class Formatter {
//
//    private final Token[] tokens;
//
//    public Formatter(Token[] tokens) {
//        this.tokens = tokens;
//    }
//
//    /**
//     * Takes a List of Tokens and converts it into rows
//     *
//     * @return all rows separated by [Newline] in an arraylist
//     */
//    public ArrayList<Token[]> formatToRows() {
//        ArrayList<ArrayList<Token>> text = new ArrayList<>();
//        ArrayList<Token> currentRow = new ArrayList<>();
//
//        for (Token t : tokens) {
//            if (t.type() != TokenType.NEWLINE) {
//                currentRow.add(t);
//            } else {
//                text.add(currentRow);
//                currentRow = new ArrayList<>();
//            }
//        }
//        if (!currentRow.isEmpty())
//            text.add(currentRow);
//
//        //Testing
//        for (ArrayList<Token> list : text) {
//            System.out.println("Formatter: " + list.get(0).getDescription());
//        }
//
//        ArrayList<Token[]> formattedText = new ArrayList<>();
//        for (ArrayList<Token> t : text) {
//            Token[] arr = new Token[t.size()];
//            arr = t.toArray(arr);
//            formattedText.add(arr);
//        }
//        return formattedText;
//    }
//}
