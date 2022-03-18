package com.danielweisshoff.interpreter.stack;

import com.danielweisshoff.parser.nodesystem.Data;

/*
 * TODO
 *  - returnType wird durch die Funktion definiert, f�r die der StackFrame erstellt wird
 */

/*
 * StackFrame beinhaltet:
 *  - Lokale Variablen
 *  - Return Adresse
 *
 */
/*
 * Jede Methode hat einen StackFrame.
 * Dieser speichert alle lokalen variablen und pusht sie auf den Stack
 */
public class StackFrame {

	private final Data<?>[] variables;
	private long returnAdress;

	public StackFrame(Data... variables) {
		this.variables = variables;
	}

	public Data<?> get(int id) {
		return variables[id];
	}
}
