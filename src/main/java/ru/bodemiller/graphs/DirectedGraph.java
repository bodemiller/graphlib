package ru.bodemiller.graphs;

public class DirectedGraph<T> extends Graph<T> {

    @Override
    public boolean addEdge(T sourceId, T destId) {
        return addEdge(sourceId, destId, true);
    }

    @Override
    public boolean removeEdge(T vertexOneId, T vertexTwoId) {
        return removeEdge(vertexOneId, vertexTwoId, true);
    }
}
