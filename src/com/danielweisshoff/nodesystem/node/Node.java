package com.danielweisshoff.nodesystem;

import java.util.ArrayList;

/**
 * abstract in normal unmwandeln
 * @author daniel
 */
public class Node {


    //Werden nur für Fehlersuche benötigt
    private Data<?>[] dataInput;
    private Data<?> dataOutput;


    protected ArrayList<Node> children;

    //Spezifiziert, welche/r Datentyp angenommen / ausgegeben wird
    private final DataType[] inputType;
    private final DataType outputType;

    public Node(DataType[] inputType, DataType outputType) {
        children = new ArrayList<Node>();
        this.inputType = inputType;
        this.outputType = outputType;
    }

    public void add(Node n) {
        children.add(n);
    }

    public Data<?> execute(){
    return new Data<>(null,null);
    }
}
