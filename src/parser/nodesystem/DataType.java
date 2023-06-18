package parser.nodesystem;

public enum DataType {
    //Ganzzahlen
    BYTE, SHORT, INT, LONG,
    //Dezimal
    FLOAT, DOUBLE,
    //Sonder
    CHAR, BOOLEAN, POINTER, VOID, ARRAY
}

/* Ganzzahlen     bytes
 *      byte        1
 *      short       2
 *      int         4
 *      long        8
 *
 * Dezimalzahlen
 *      float       4
 *      double      8
 *
 * Anderes
 * char             1
 * wChar            2     Falls ueberhaupt implementiert 
 * bool             1
 * pointer          4/8   <-- Je nach Betriebssystem
 */