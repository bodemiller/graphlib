package ru.bodemiller.graphs;

import ru.bodemiller.models.Vertex;

import java.util.*;
import java.util.function.Function;

public abstract class Graph<T> {

    /**
     * List of associations: vertex to list of adjacent vertices
     *
     */
    protected final Map<Vertex<T>, List<Vertex<T>>> adjacencyList = new HashMap<>();

    /**
     * Adding the vertex to the graph
     *
     * @param vertex new graph vertex
     * @return true if successfully added, false otherwise
     */
    public boolean addVertex(Vertex<T> vertex) {
        if(adjacencyList.containsKey(vertex)){
            return false;
        }
        adjacencyList.put(vertex, new ArrayList<>());
        return adjacencyList.containsKey(vertex);
    }

    /**
     * Adding the vertex to the graph
     *
     * @param vertexId new graph vertex id
     * @return true if successfully added, false otherwise
     */
    public boolean addVertex(T vertexId) {
        return addVertex(new Vertex<>(vertexId));
    }

    /**
     * Removing the vertex from the graph
     *
     * @param vertex vertex to remove
     * @return true if successfully removed, false otherwise
     */
    public boolean removeVertex(Vertex<T> vertex) {
        if(!adjacencyList.containsKey(vertex)){
            return false;
        }
        adjacencyList.remove(vertex);
        adjacencyList.values()
                .forEach(verticesList -> verticesList.remove(vertex));
        return !adjacencyList.containsKey(vertex);
    }

    /**
     * Removing the vertex from the graph by id
     *
     * @param id unique vertex identifier
     * @return true if successfully removed, false otherwise
     */
    public boolean removeVertex(T id) {
        return removeVertex(new Vertex<>(id));
    }

    /**
     * Adding the edge to the graph
     *
     * @param sourceId id of a source vertex
     * @param destId id of a destination vertex
     * @param directed if true the edge is directed from sourceId to destId, both ways otherwise
     * @return true if successfully added, false otherwise
     */
    boolean addEdge(T sourceId, T destId, boolean directed) {
        Vertex source = new Vertex(sourceId);
        Vertex dest = new Vertex(destId);
        if(!adjacencyList.containsKey(source)){
            return false;
        }
        if(!directed && !adjacencyList.containsKey(dest)){
            return false;
        }

        adjacencyList.get(source).add(dest);
        if(!directed){
            adjacencyList.get(dest).add(source);
        }
        return true;
    }

    public abstract boolean addEdge(T vertexOneId, T vertexTwoId);


    /**
     * Removing the edge from the graph
     *
     * @param sourceId id of a source vertex
     * @param destId id of a destination vertex
     * @param directed if true then removing the edge [sourceId -> destId], both ways otherwise
     * @return true if successfully removed
     */
    synchronized boolean removeEdge(T sourceId, T destId, boolean directed) {
        Vertex source = new Vertex(sourceId);
        Vertex dest = new Vertex(destId);
        List<Vertex<T>> sourceAdjVertices = adjacencyList.get(source);

        if (sourceAdjVertices != null){
            sourceAdjVertices.remove(dest);
        }
        if(!directed){
            List<Vertex<T>> destAdjVertices = adjacencyList.get(dest);
            if (destAdjVertices != null){
                destAdjVertices.remove(source);
            }
        }
        return true;
    }

    public abstract boolean removeEdge(T vertexOneId, T vertexTwoId);

    /**
     * Finding a path between two vertices
     *
     * @param from start vertex
     * @param to end vertex
     * @return list of vertices between from and to (including from and to), null if path is undefined
     */
    public List<Vertex<T>> getPath(Vertex<T> from, Vertex<T> to){
        if(!adjacencyList.containsKey(from) || !adjacencyList.containsKey(to)){
            return null;
        }
        Map<Vertex<T>, Vertex<T>> vertexToParent = new HashMap<>();
        Set<Vertex<T>> checked = new HashSet<>();
        Queue<Vertex<T>> toCheck = new LinkedList<>();

        checked.add(from);
        toCheck.add(from);
        boolean pathFound = false;

        while (!toCheck.isEmpty() && !pathFound){
            Vertex<T> vertex = toCheck.poll();
            List<Vertex<T>> vertices = adjacencyList.get(vertex);
            for (Vertex<T> adjVertex : vertices) {
                if (!checked.contains(adjVertex)) {
                    vertexToParent.put(adjVertex, vertex);
                    if (adjVertex.equals(to)) {
                        pathFound = true;
                        break;
                    }
                    checked.add(adjVertex);
                    toCheck.add(adjVertex);
                }
            }
        }
        List<Vertex<T>> path = null;
        if(pathFound){
            path = new LinkedList<>();
            Vertex<T> vertex = to;
            while(vertex != null) {
                path.add(vertex);
                vertex = vertexToParent.get(vertex);
            }
            Collections.reverse(path);
        }
        return path;
    }

    /**
     * Finding a path between two vertices
     * Caution: path may not be optimal
     *
     * @param from start vertex id
     * @param to end vertex id
     * @return list of vertices between from and to (including from and to), null if path is undefined
     */
    public List<Vertex<T>> getPath(T from, T to){
        return getPath(new Vertex<>(from), new Vertex<>(to));
    }

    /**
     * Getting list of graph vertices
     *
     * @return list of adjacent vertices
     */
    public Set<Vertex<T>> getVertices(){
        return adjacencyList.keySet();
    }

    /**
     * Getting list of adjacent vertices
     *
     * @param vertex target vertex
     * @return list of adjacent vertices
     */
    public List<Vertex<T>> getAdjacentVertices(Vertex<T> vertex){
        return adjacencyList.get(vertex);
    }

    /**
     * Getting list of adjacent vertices
     *
     * @param vertexId target vertex id
     * @return list of adjacent vertices
     */
    public List<Vertex<T>> getAdjacentVertices(T vertexId){
        return adjacencyList.get(new Vertex<>(vertexId));
    }

    /**
     * Getting list of list of adjacent vertices
     *
     * @param vertexId target vertex id
     * @return whether vertex is presented in the graph
     */
    public boolean containsVertex(T vertexId){
        return adjacencyList.containsKey(new Vertex<>(vertexId));
    }

    /**
     * Applying function on every vertex of the graph
     *
     * @param function function to apply
     * @param <R> function return type
     */
    public <R> Map<Vertex<T>, R> traverse(Function<Vertex<T>, R> function){
        Set<Vertex<T>> vertices = adjacencyList.keySet();
        if(vertices == null){
            return null;
        }
        Map<Vertex<T>, R> resultMap = new HashMap<>();
        vertices.forEach(vertex -> resultMap.put(vertex, function.apply(vertex)));
        return resultMap;
    }
}


