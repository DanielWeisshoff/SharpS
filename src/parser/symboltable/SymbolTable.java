package parser.symboltable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class SymbolTable {

    public SymbolTable parent;
    public LinkedList<SymbolTable> children = new LinkedList<>();
    private String name;

    private final HashMap<String, FunctionEntry> functionStrTable = new HashMap<>();
    private final HashMap<String, VariableEntry> variableStrTable = new HashMap<>();

    int depth;

    //for debugging
    private ArrayList<VariableEntry> variables = new ArrayList<>();

    public SymbolTable(String name, int depth) {
        this.name = name;
        this.depth = depth;
    }

    public void clear() {
        functionStrTable.clear();
        variableStrTable.clear();

        variables.clear();
    }

    public void addFunction(FunctionEntry entry) {
        functionStrTable.put(entry.getName(), entry);
    }

    public void addVariable(VariableEntry entry) {
        variableStrTable.put(entry.getName(), entry);

        variables.add(entry);
    }

    public FunctionEntry findFunction(String name) {
        FunctionEntry fe = functionStrTable.get(name);
        if (fe != null)
            return fe;
        else if (parent != null)
            return parent.findFunction(name);
        else
            return null;
    }

    public VariableEntry findVariable(String name) {
        VariableEntry fe = variableStrTable.get(name);
        if (fe != null)
            return fe;
        else if (parent != null)
            return parent.findVariable(name);
        else
            return null;
    }

    public void print(int depth) {
        String offset = "";
        String nameOffset = "";

        offset += "   ";
        for (int i = 0; i < depth; i++) {
            offset += "   ";
            nameOffset += "   ";
        }

        System.out.print(nameOffset + "" + name + ":");

        if (variables.size() == 0)
            System.out.print("\n" + offset + "| ...");
        else
            for (VariableEntry ve : variables)
                System.out.print("\n" + offset + "| " + ve.getName() + ", " + ve.getType());

        System.out.println();
        for (SymbolTable s : children) {
            s.print(depth + 1);
        }
    }

    public String getName() {
        if (parent != null)
            return parent.getName() + "/" + name;
        else
            return name;
    }
}