package interpreter.builtin.functions;

import parser.nodesystem.Data;
import parser.nodesystem.DataType;

public class helpFunction extends BuiltInFunction {
    @Override
    public Data call() {
        printHelp();
        return new Data(1, DataType.INT);
    }

    private void printHelp() {

        System.out.println("================= HELP =================");
        System.out.println("Well, this wont help you much right now,\n " + "but im sure it will in the future. \n"
                + "Anyways please notice me Senpai");
        System.out.println("=================      =================");
    }
}
