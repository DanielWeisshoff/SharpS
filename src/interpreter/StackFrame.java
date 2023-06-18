package interpreter;

import java.util.ArrayList;
import java.util.HashMap;

import parser.nodesystem.data.Data;

public class StackFrame {

    public StackFrame parent;

    public HashMap<String, Data> frame = new HashMap<>();

    private ArrayList<Data> variables = new ArrayList<>();

    public void addVariable(String name, Data data) {
        variables.add(data);
        frame.put(name, data);
    }

    public Data getVariable(String name) {
        Data data = frame.get(name);
        if (data != null)
            return data;
        else
            return parent.getVariable(name);
    }

    public ArrayList<Data> getVariables() {
        return variables;
    }
}
