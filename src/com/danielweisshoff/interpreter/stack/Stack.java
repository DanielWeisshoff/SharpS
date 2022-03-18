package com.danielweisshoff.interpreter.stack;

import com.danielweisshoff.parser.PError;
import com.danielweisshoff.parser.nodesystem.Data;

public class Stack {

	private static Data<?>[] frames;
	private final int stackSize;
	private int allocatedFrames;
	private int pointer;

	public Stack(int stackSize) {
		this.stackSize = stackSize;
		frames = new Data<?>[stackSize];
	}

	public Data<?> peek(int id) {
		return frames[pointer];
	}

	public Data<?> pop() {
		Data<?> frame = frames[pointer];
		pointer--;
		allocatedFrames--;

		if (pointer < 0) {
			//Programmende erreicht, sollte man aber auch an dem zur�ckgegebenen StackFrame erkennen,
			//weil es sich dann um den Entry-StackFrame handelt
		}
		return frame;
	}

	public void push(Data<?> frame) {
		if (allocatedFrames == stackSize)
			new PError("Stackoverflow, die " + stackSize + "Kapazit�ten wurden aufgebraucht");
		pointer++;
		allocatedFrames++;
		frames[pointer] = frame;
	}
}
