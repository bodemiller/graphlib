package ru.bodemiller.graphs;

import org.junit.Assert;
import org.junit.Test;
import ru.bodemiller.models.Vertex;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GraphTest {

    @Test
    public void vertexBuildTest(){
        var vertexId = 1;
        Vertex vertex = Vertex.builder().id(vertexId).build();
        Assert.assertEquals(vertexId, vertex.getId());
    }

    @Test
    public void addVertexTest(){
        var vertexId = "test";
        Graph<String> graph = Graphs.ofType(Graphs.GraphType.UNDIRECTED);
        graph.addVertex(vertexId);

        Assert.assertTrue(graph.containsVertex(vertexId));
    }

    @Test
    public void removeVertexTest(){
        var vertexId = 0;
        Vertex<Integer> vertexToRemove = new Vertex<>(vertexId);
        Graph<Integer> graph = getTestUndirectedGraph();
        List<Vertex<Integer>> adjacentVertices = graph.getAdjacentVertices(vertexId);
        graph.removeVertex(vertexId);

        Assert.assertFalse(graph.containsVertex(vertexId));

        //check that adjacent vertices doesn't contains 0 vertex in they adjacent vertex
        adjacentVertices.forEach(vertex ->
            Assert.assertFalse(
                    graph.getAdjacentVertices(vertex.getId())
                            .contains(vertexToRemove)
            )
        );
    }

    @Test
    public void addUndirectedEdgeTest(){
        String vertexZero = "0";
        String vertexOne = "1";
        Graph<String> graph = Graphs.ofType(Graphs.GraphType.UNDIRECTED);
        graph.addVertex(vertexZero);
        graph.addVertex(vertexOne);

        boolean edgeToMissingVertexAdded = graph.addEdge(vertexZero, "2");
        Assert.assertFalse(edgeToMissingVertexAdded);

        boolean edgeAdded = graph.addEdge(vertexZero, vertexOne);
        Assert.assertTrue(edgeAdded);
        Assert.assertTrue(graph.getAdjacentVertices(vertexZero)
                .contains(new Vertex<>(vertexOne)));
        Assert.assertTrue(graph.getAdjacentVertices(vertexOne)
                .contains(new Vertex<>(vertexZero)));
    }

    @Test
    public void removeUndirectedEdgeTest(){
        int vertexZero = 2;
        int vertexOne = 5;
        Graph<Integer> graph = getTestUndirectedGraph();
        boolean nonexistentEdgeRemoved = graph.removeEdge(vertexZero, 6);
        //removeEdge return true on missing edges
        Assert.assertTrue(nonexistentEdgeRemoved);

        boolean existedEdgeRemoved  = graph.removeEdge(vertexZero, vertexOne);
        Assert.assertTrue(existedEdgeRemoved);

        Assert.assertFalse(graph.getAdjacentVertices(vertexZero)
                .contains(new Vertex<>(vertexOne)));
        Assert.assertFalse(graph.getAdjacentVertices(vertexOne)
                .contains(new Vertex<>(vertexZero)));
    }

    @Test
    public void findPathInUndirectedGraphTest(){
        Graph<Integer> graph = getTestUndirectedGraph();

        //path may contains 5, 6 or 7 vertices
        List<Vertex<Integer>> path = graph.getPath(0, 6);
        Assert.assertNotNull(path);

        //vertex with id 9 is not presented
        List<Vertex<Integer>> undefinedPath = graph.getPath(0, 9);
        Assert.assertNull(undefinedPath);
    }

    @Test
    public void findPathInDirectedGraphTest(){
        Graph<Integer> graph = getTestDirectedGraph();

        //path may contains 3 vertices only
        List<Vertex<Integer>> path = graph.getPath(2, 6);
        Assert.assertEquals(path.size(), 3);

        //no such path
        List<Vertex<Integer>> undefinedPath = graph.getPath(6, 2);
        Assert.assertNull(undefinedPath);
    }

    @Test
    public void testTraverse(){
        Graph<Integer> graph = getTestDirectedGraph();
        Map<Vertex<Integer>, Integer> traverseResult = graph.traverse((vertex) -> vertex.getId() * 100);
        traverseResult.forEach((vertex, result) -> Assert.assertTrue(result / 100 == vertex.getId()));
    }


    /**
     *
     * [1] - [7] - [2] - [5] - [6]
     *  |           |
     * [0]    -    [3]
     *  |           |
     * [8]    -    [4]
     *
     */
    private Graph<Integer> getTestUndirectedGraph(){
        Graph<Integer> graph = Graphs.ofType(Graphs.GraphType.UNDIRECTED);

        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addVertex(7);
        graph.addVertex(8);

        graph.addEdge(0, 1);
        graph.addEdge(0, 3);
        graph.addEdge(0, 8);

        graph.addEdge(1, 7);

        graph.addEdge(2, 3);
        graph.addEdge(2, 5);
        graph.addEdge(2, 7);

        graph.addEdge(3, 4);

        graph.addEdge(4, 8);

        graph.addEdge(5, 6);

        return graph;
    }


    /**
     *
     * [1] -> [7] <- [2] -> [5] -> [6]
     *  ^             |
     *  |             v
     * [0]    ->     [3]
     *  |             |
     *  v             v
     * [8]    <-     [4]
     *
     */
    private Graph<Integer> getTestDirectedGraph(){
        Graph<Integer> graph = Graphs.ofType(Graphs.GraphType.DIRECTED);

        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addVertex(7);
        graph.addVertex(8);

        graph.addEdge(0, 1);
        graph.addEdge(0, 3);
        graph.addEdge(0, 8);

        graph.addEdge(1, 7);

        graph.addEdge(2, 3);
        graph.addEdge(2, 5);
        graph.addEdge(2, 7);

        graph.addEdge(3, 4);

        graph.addEdge(4, 8);

        graph.addEdge(5, 6);

        return graph;
    }

}
