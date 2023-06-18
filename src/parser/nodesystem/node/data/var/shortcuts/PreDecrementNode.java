// package parser.nodesystem.node.data.var.shortcuts;

// import interpreter.Interpreter;
// import parser.PError.UnimplementedError;
// import parser.nodesystem.data.Data;
// import parser.nodesystem.node.NodeType;
// import parser.nodesystem.node.data.var.AssignNode;

// public class PreDecrementNode extends AssignNode {

//     public PreDecrementNode(String name) {
//         super(name, NodeType.PRE_DECREMENT_NODE);

//     }

//     @Override
//     public Data run() {

//         Data data = Interpreter.instance.findVariable(name);
//         if (data == null)
//             new UnimplementedError("Interpreter Error: var '" + name + "' not declared");

//         double value = data.asDouble();
//         data.setValue(value - 1);

//         return data;
//     }

//     @Override
//     public void print() {
//         super.print();
//     }
// }