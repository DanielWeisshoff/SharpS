package parser.nodesystem.node;

import parser.nodesystem.Data;
import parser.nodesystem.DataType;

/**Foundation of all other nodes*/
public abstract class Node {

    /*//Spezifiziert, welche/r Datentyp angenommen / ausgegeben wird
    private final DataType[] inputType;
    private final DataType outputType;*/

    public NodeType nodeType;

    //for print()
    protected static int printSpacing = 2;
    public static boolean advancedInfo = false;
    public static int depth = 0;

    public Node(DataType[] inputType, DataType outputType, NodeType nodeType) {
        // this.inputType = inputType;
        // this.outputType = outputType;
        this.nodeType = nodeType;
    }

    public abstract Data run();

    //TODO export
    public void print() {
        System.out.println(offset() + nodeType);
    }

    public void print(String msg) {
        System.out.println(offset() + msg);
    }

    public String offset() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Node.depth * Node.printSpacing; i++)
            sb.append(" ");
        return sb.toString();
    }
}
