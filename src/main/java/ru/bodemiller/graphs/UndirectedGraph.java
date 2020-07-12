package ru.bodemiller.graphs;

public class UndirectedGraph<T> extends Graph<T> {

    @Override
    public boolean addEdge(T sourceId, T destId) {
        return addEdge(sourceId, destId, false);
    }

    @Override
    public boolean removeEdge(T vertexOneId, T vertexTwoId) {
        return removeEdge(vertexOneId, vertexTwoId, false);
    }
}
