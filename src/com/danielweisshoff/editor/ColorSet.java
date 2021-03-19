package com.danielweisshoff.editor;

public class ColorSet {

    private final String syntax;
    private final String color;

    public ColorSet(String syntax, String color) {
        this.syntax = syntax;
        this.color = color;
    }

    public String getSyntax() {
        return syntax;
    }

    public String getColor() {
        return color;
    }
}
