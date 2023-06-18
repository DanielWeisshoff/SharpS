// package parser.nodesystem.node.data.var.shortcuts;

// import interpreter.Interpreter;
// import parser.PError.UnimplementedError;
// import parser.nodesystem.data.Data;
// import parser.nodesystem.data.numerical.Numerical;
// import parser.nodesystem.node.NodeType;
// import parser.nodesystem.node.data.var.AssignNode;

// public class PostDecrementNode extends AssignNode {

//     public PostDecrementNode(String name) {
//         super(name, NodeType.POST_DECREMENT_NODE);
//     }

//     @Override
//     public Data run() {

//         Numerical data = (Numerical) Interpreter.instance.findVariable(name);
//         if (data == null)
//             new UnimplementedError("Interpreter Error: var '" + name + "' not declared");

//     }

//     @Override
//     public void print() {
//         super.print();
//     }
// }
