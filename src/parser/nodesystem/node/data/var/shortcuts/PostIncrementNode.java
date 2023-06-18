// package parser.nodesystem.node.data.var.shortcuts;

// import interpreter.Interpreter;
// import parser.PError.UnimplementedError;
// import parser.nodesystem.data.Data;
// import parser.nodesystem.node.NodeType;
// import parser.nodesystem.node.data.var.AssignNode;
// import parser.symboltable.symbol.returntype.floatingpoint.Float64Type;

// public class PostIncrementNode extends AssignNode {

//     public PostIncrementNode(String name) {
//         super(name, NodeType.POST_INCREMENT_NODE);
//     }

//     @Override
//     public Data run() {

//         Data data = Interpreter.instance.findVariable(name);
//         if (data == null)
//             new UnimplementedError("Interpreter Error: var '" + name + "' not declared");

//         double value = data.asDouble();
//         data.setValue(value + 1);
//         return new Data(value, Float64Type.INSTANCE);
//     }

//     @Override
//     public void print() {
//         super.print();
//     }
// }
