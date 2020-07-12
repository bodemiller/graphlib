# graphlib
Simple library to work with graphs

This lib can be used as a dependency in maven project.

```xml
    <dependency>
        <groupId>ru.bodemiller</groupId>
        <artifactId>graphlib</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
```

Graphs consists of vertices and edges.\
Vertex has mandatory field _id_ of a generic type:
```java
public class Vertex<T> {
    private T id;

    public Vertex(T id) {
        this.id = id;
    }
}

```
Vertex creation example:
```java
Vertex<Integer> vertex = new Vertex<>(1);

Vertex vertex = Vertex.builder().id(1).build();
```


**graphlib** supports two type of graphs based on edges orientation: directed and undirected.\
Graphs creation examples:

 ```java
Graph<String> graphOne = Graphs.ofType(Graphs.GraphType.UNDIRECTED);

Graph<Integer> graphTwo = Graphs.ofType(Graphs.GraphType.DIRECTED);

 ```


Graph methods:

```java
//vertices management
addVertex(Vertex<T> vertex)
addVertex(T vertexId)
removeVertex(Vertex<T> vertex)
removeVertex(T id)

//edges management
addEdge(T vertexOneId, T vertexTwoId)
removeEdge(T vertexOneId, T vertexTwoId)

//returns a list of edges between 2 vertices
getPath(Vertex<T> from, Vertex<T> to)

//Applying function on every vertex of the graph
traverse(Function<Vertex<T>, R> function)

```

Usega example:

```java

    /**
     *
     * [1] - [7] - [2] - [5] - [6]
     *  |           |
     * [0]    -    [3]
     *  |           |
     * [8]    -    [4]
     *
     */
    
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
    
    //2, 5, 6
    List<Vertex<Integer>> path = graph.getPath(2, 6);
    
    //5 -> 500
    //6 -> 600
    //...
    Map<Vertex<Integer>, Integer> traverseResult = graph.traverse((vertex) -> vertex.getId() * 100);
    

```
