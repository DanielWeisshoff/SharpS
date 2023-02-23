package utils.visualizer;

public class ColorTheme {

    public static ColorTheme IntelliJ = new ColorTheme(new ColorSet("error", "9"), // \u001b[4m    f�r underline
            new ColorSet("keyword", "5"), new ColorSet("identifier", "248"), // 7, wenn die variable/funktion nicht genutzt wird
            new ColorSet("string", "2"), new ColorSet("number", "6"), new ColorSet("dot", "4"),
            new ColorSet("parentheses", "248"), new ColorSet("default", "7"), new ColorSet("class", "6"),
            new ColorSet("function", "4"), new ColorSet("comment", "7"), new ColorSet("operator", "4"));
    private final ColorSet[] theme;

    public ColorTheme(ColorSet... theme) {
        this.theme = theme;
    }

    public ColorSet[] colorTheme() {
        return theme;
    }
}