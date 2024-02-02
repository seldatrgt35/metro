package metrohomework;


import java.util.Iterator;

public class DirectedGraph<T> implements GraphInterface<T>
{
    private DictionaryInterface<T, VertexInterface<T>> vertices;
    private int edgeCount;

    public DirectedGraph()
    {
        vertices = new UnsortedLinkedDictionary<>();
        edgeCount = 0;
    } // end default constructor


    public boolean addVertex(T vertexLabel) {
        VertexInterface<T> addOutcome = vertices.add(vertexLabel , new Vertex<>(vertexLabel));
        return addOutcome == null;
    }
    public boolean addEdge(T begin , T end , double edgeWeight)
    {
        boolean result = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ((beginVertex != null) && (endVertex != null))
            result = beginVertex.connect(endVertex , edgeWeight);
        if (result)
            edgeCount++;
        return result;
    }
    public boolean addEdge(T begin , T end) {
        return addEdge(begin , end , 0);
    }

    public boolean hasEdge(T begin , T end) {
        boolean found = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ((beginVertex != null) && (endVertex != null))
        {
            Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
            while (!found && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor))
                    found = true;
            }
        }
        return found;
    }
    public boolean isEmpty()
    {
        return vertices.isEmpty();
    }
    public void clear()
    {
        vertices.clear();
        edgeCount = 0;
    }
    public int getNumberOfVertices() {
        return vertices.getSize();
    }
    public int getNumberOfEdges() {
        return edgeCount;
    }
    protected void resetVertices()
    {
        Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
        while (vertexIterator.hasNext())
        {
            VertexInterface<T> nextVertex = vertexIterator.next();
            nextVertex.unvisit();
            nextVertex.setCost(0);
            nextVertex.setPredecessor(null);
        } // end while
    } // end resetVertices
    public QueueInterface<T> getBreadthFirstTraversal(T origin, T end) {
        resetVertices(); //reset all vertices before starting
        boolean isMazeExitFound = false; // to stop searching if exit spotted
        // necessary variables to store traversal order information and vertex order.
        QueueInterface<T> traversalOrder = new LinkedQueue();
        QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue();// using queue for bfs
        // converting start and end position those user gave as parameters to vertex
        VertexInterface<T> originVertex = vertices.getValue(origin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        //mark origin vertex visited
        originVertex.visit();
        traversalOrder.enqueue(origin);
        vertexQueue.enqueue(originVertex);
        //Start traversal
        while (!isMazeExitFound && !vertexQueue.isEmpty()) {
            VertexInterface<T> currentVertex = vertexQueue.dequeue();
            //creating iterator for current neighbors
            Iterator<VertexInterface<T>> neighbors = currentVertex.getNeighborIterator();
            // Walking in neighbors
            while (!isMazeExitFound && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (!nextNeighbor.isVisited()) {
                    nextNeighbor.visit();// mark the vertex visited
                    traversalOrder.enqueue(nextNeighbor.getLabel());
                    vertexQueue.enqueue(nextNeighbor);
                }
                // if exit founded
                if (nextNeighbor.equals(endVertex))
                    isMazeExitFound = true;
            }
        }
        return traversalOrder;
    }
    public QueueInterface<T> getDepthFirstTraversal(T origin, T end) {
        resetVertices(); // reset all vertices before starting
        boolean isMazeExitFound = false; // to stop searching if exit spotted
        // necessary variables to store traversal order information and vertex order.
        QueueInterface<T> traversalOrder = new LinkedQueue();
        StackInterface<VertexInterface<T>> vertexStack = new LinkedStack();// using stack for stack
        // converting start and end position those user gave as parameters to vertex
        VertexInterface<T> originVertex = vertices.getValue(origin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        //mark origin vertex visited
        originVertex.visit();
        traversalOrder.enqueue(end);
        vertexStack.push(originVertex);
        // Start traversal
        while (!isMazeExitFound && !vertexStack.isEmpty()) {
            VertexInterface<T> currentVertex = vertexStack.pop();
            //creating iterator for current neighbors
            Iterator<VertexInterface<T>> neighbors = currentVertex.getNeighborIterator();
            traversalOrder.enqueue(currentVertex.getLabel());
            // Walking in neighbors
            while (!isMazeExitFound && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (!nextNeighbor.isVisited()) {
                    nextNeighbor.visit(); // mark the vertex visited
                    vertexStack.push(nextNeighbor);
                }
                // if exit founded
                if (nextNeighbor.equals(endVertex))
                    isMazeExitFound = true;
            }
        }
        return traversalOrder;
    }

    public int getShortestPath(T begin, T end, StackInterface<T> path) {
        resetVertices(); // reset all vertices before starting
        boolean done = false; // to stop searching if exit spotted
        QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<VertexInterface<T>>();
        // converting start and end position those user gave as parameters to vertex
        VertexInterface<T> originVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        //mark origin vertex visited
        originVertex.visit();
        vertexQueue.enqueue(originVertex);
        // Start traversal
        while (!done && !vertexQueue.isEmpty()) {
            VertexInterface<T> frontVertex = vertexQueue.dequeue();
            //creating iterator for current neighbors
            Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
            // Walking in neighbors
            while (!done && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (!nextNeighbor.isVisited()) {
                    nextNeighbor.visit(); //set visited
                    nextNeighbor.setCost(1 + frontVertex.getCost());
                    nextNeighbor.setPredecessor(frontVertex);
                    vertexQueue.enqueue(nextNeighbor);
                }
                // if exit founded
                if (nextNeighbor.equals(endVertex))
                    done = true;
            }
        }
        // storing the path
        int pathLength = (int)endVertex.getCost() + 1;
        path.push(endVertex.getLabel());
        VertexInterface<T> vertex = endVertex;
        while (vertex.hasPredecessor())
        {
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        }
        printPath(path); // printing path
        return pathLength;
    }
    void printPath(StackInterface<T> path) {
        //printing path
        while (!path.isEmpty()) {
            Main maze = new Main();
            String[] pathMazeArray = new String[maze.mazeArray.length];
            //copying maze
            for (int i = 0; i < maze.mazeArray.length; i++) {
                pathMazeArray[i] = maze.mazeArray[i];
            }
            //printing dots
            while (!path.isEmpty()) {
                String currentVertex = (String) path.pop();
                String[] coordinates = currentVertex.split("-");// getting coordinates
                StringBuilder dotReplace = new StringBuilder(
                        pathMazeArray[Integer.parseInt(coordinates[0])]);
                dotReplace.setCharAt(Integer.parseInt(coordinates[1]), '.');
                pathMazeArray[Integer.parseInt(coordinates[0])] = dotReplace.toString();
            }
            for (int i = 0; i < pathMazeArray.length; i++) {
                System.out.println(pathMazeArray[i]);
            }
            System.out.print("Number of visited vertices: ");
        }
    }

    public QueueInterface<T> getBreadthFirstTraversal(T origin) {
        return null;
    }

    public QueueInterface<T> getDepthFirstTraversal(T origin) {
        return null;
    }

    public StackInterface<T> getTopologicalOrder() {
        return null;
    }



    public double getCheapestPath(T begin, T end, StackInterface<T> path) {
        return 0;
    }
}