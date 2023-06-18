package parser.parser;

import java.util.HashMap;
import java.util.Map;

import parser.nodesystem.DataType;
import parser.nodesystem.data.numerical.Numerical;
import parser.nodesystem.data.numerical.floatingpoint.F32;
import parser.nodesystem.data.numerical.integer.I32;

public class Arithmetic {

    static Map<DataType, Integer> createMap() {
        Map<DataType, Integer> myMap = new HashMap<>();
        myMap.put(DataType.BOOLEAN, 0);
        myMap.put(DataType.BYTE, 1);
        myMap.put(DataType.SHORT, 2);
        myMap.put(DataType.INT, 3);
        myMap.put(DataType.LONG, 4);
        myMap.put(DataType.FLOAT, 5);
        myMap.put(DataType.DOUBLE, 6);

        return myMap;
    }

    public static Numerical add(Numerical a, Numerical b) {
        int aPrec = createMap().get(a.type);
        int bPrec = createMap().get(b.type);

        Numerical convertTo = aPrec > bPrec ? a : b;

        switch (convertTo.type) {
        case INT:
            return new I32(a.value.intValue() + b.value.intValue());
        case FLOAT:
            return new F32(a.value.floatValue() + b.value.floatValue());
        default:
            return null;
        }
    }

    public static Numerical mul(Numerical a, Numerical b) {
        int aPrec = createMap().get(a.type);
        int bPrec = createMap().get(b.type);

        Numerical convertTo = aPrec > bPrec ? a : b;

        switch (convertTo.type) {
        case INT:
            return new I32(a.value.intValue() * b.value.intValue());
        case FLOAT:
            return new F32(a.value.floatValue() * b.value.floatValue());
        default:
            return null;
        }
    }

    public static Numerical div(Numerical a, Numerical b) {
        int aPrec = createMap().get(a.type);
        int bPrec = createMap().get(b.type);

        Numerical convertTo = aPrec > bPrec ? a : b;

        switch (convertTo.type) {
        case INT:
            return new I32(a.value.intValue() / b.value.intValue());
        case FLOAT:
            return new F32(a.value.floatValue() / b.value.floatValue());
        default:
            return null;
        }
    }

    public static Numerical sub(Numerical a, Numerical b) {
        int aPrec = createMap().get(a.type);
        int bPrec = createMap().get(b.type);

        Numerical convertTo = aPrec > bPrec ? a : b;

        switch (convertTo.type) {
        case INT:
            return new I32(a.value.intValue() - b.value.intValue());
        case FLOAT:
            return new F32(a.value.floatValue() - b.value.floatValue());
        default:
            return null;
        }
    }

    public static Numerical mod(Numerical a, Numerical b) {
        int aPrec = createMap().get(a.type);
        int bPrec = createMap().get(b.type);

        Numerical convertTo = aPrec > bPrec ? a : b;

        switch (convertTo.type) {
        case INT:
            return new I32(a.value.intValue() * b.value.intValue());
        case FLOAT:
            return new F32(a.value.floatValue() * b.value.floatValue());
        default:
            return null;
        }
    }
}
