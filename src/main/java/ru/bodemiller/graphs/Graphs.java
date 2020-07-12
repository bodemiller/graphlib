package ru.bodemiller.graphs;

public class Graphs {

    public enum GraphType{
        DIRECTED,
        UNDIRECTED
    }

    public static <T> Graph<T> ofType(GraphType graphType){
        switch (graphType){
            case DIRECTED:
                return new DirectedGraph<>();
            case UNDIRECTED:
                return new UndirectedGraph<>();
            default:
                throw new IllegalArgumentException("Unsupported graph type: " + graphType);
        }
    }
}
