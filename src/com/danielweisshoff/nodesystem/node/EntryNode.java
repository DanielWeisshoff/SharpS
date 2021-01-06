package com.danielweisshoff.nodesystem;

public class EntryNode extends Node {

    public EntryNode() {
        super(new DataType[]{DataType.ANY}, DataType.INT);
    }

    @Override
    public Data<Integer> execute() {
        for (Node n : children) {
            n.execute().print();
        }
        //Später sollte der Interpreter die gesendete 1 Abfangen und selber
        //Die Meldung ausgeben
        System.out.println("Task finished");
        return new Data<Integer>(1, DataType.INT);
    }
}
