package com.danielweisshoff.parser.nodesystem.node;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;

/**
 * The power of creation lies at our feet.
 * The only thing holding you back is <i>imagination</i>
 */
public abstract class Node {

    /*//Spezifiziert, welche/r Datentyp angenommen / ausgegeben wird
    private final DataType[] inputType;
    private final DataType outputType;*/

    public NodeType nodeType;

    //for print()
    protected static int printSpacing = 2;
    public static boolean advancedInfo = false;

    public Node(DataType[] inputType, DataType outputType, NodeType nodeType) {
        // this.inputType = inputType;
        // this.outputType = outputType;
        this.nodeType = nodeType;
    }

    public abstract Data run();

    public abstract void print(int depth);

    public String offset(int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth * Node.printSpacing; i++)
            sb.append(" ");
        return sb.toString();
    }

    public void printAdvanced(String msg, int depth) {
        if (advancedInfo)
            System.out.println(offset(depth) + msg);
    }

    public void printAdvanced(Node node, int depth) {
        if (advancedInfo)
            node.print(depth);
    }
}
