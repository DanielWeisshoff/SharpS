package com.danielweisshoff.parser.nodesystem;

public enum DataType {
	BYTE, SHORT, INT, LONG, //Ganzzahlen
	FLOAT, DOUBLE, //Dezimal
	CHAR, BOOLEAN, //Sonder
	POINTER, NULL
}
/*
 *
 * Ganzzahlen
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
 *                                                  #So soll es mal sein
 *                                                  Char             1 -> reicht für standardtexte
 *                                                  wChar            2 -> bei sonderzeichen benötigt
 *
 * boolean          1
 * pointer          4/8   <-- Je nach Betriebssystem
 */