package org.example;

import java.util.ArrayList;

public class GraphList {
    ArrayList<Graph> graphs = new ArrayList<>();

    public void print() {
        for (Graph graph : graphs) {
            graph.printGraph();
        }
    }

    public void printGraphsNames() {
        System.out.println("Graphs: ");
        for (Graph graph : graphs) {
            System.out.println(graph.getName());
        }
    }
}
