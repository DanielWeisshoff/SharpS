// package com.danielweisshoff.interpreter.heap;

// import java.util.ArrayList;
// import java.util.HashMap;

// /* Wenn Objekte gel�scht werden, werden ihre ID's frei. Deswegen gibt es einen Tracker(emptyIds)
//  * Wenn ein neues Objekt in den Heap soll, werden zuerst die IDs genutzt, die frei geworden sind
//  */

// /**
//  * Speichert alle Dynamischen Datenstrukturen
//  */

// public class Heap {

//     private static long idCounter;
//     private final ArrayList<Long> emptyIds = new ArrayList<>();
//     public HashMap<Long, SharpSObject> heapSpace = new HashMap<>();

//     public long insert(Object o) {
//         if (emptyIds.size() != 0) {
//             heapSpace.put(emptyIds.get(0), new SharpSObject(emptyIds.get(0), null));
//             emptyIds.remove(0);
//         } else {
//             heapSpace.put(idCounter, new SharpSObject(idCounter, null));
//             idCounter++;
//         }
//         return idCounter - 1;
//     }

//     public void free(long id) {
//         heapSpace.put(id, null);
//         emptyIds.add(id);
//     }

//     public SharpSObject get(long objectID) {
//         return heapSpace.get(objectID);
//     }
// }
