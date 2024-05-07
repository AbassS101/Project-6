/* @Author Abass Shikur
 *  This is my original work
 */

import java.util.*;

public class Graph<V, E extends Comparable<E>> implements GraphInterface<V, E> {
    private final Map<V, Map<V, E>> adjacencyMap;
    private final Map<V, Integer> distances;
    private final Map<V, V> previousVertices;

    public Graph() {
        adjacencyMap = new HashMap<>();
        distances = new HashMap<>();
        previousVertices = new HashMap<>();
    }
    
    // This method gets the edge between two vertices
    @Override
    public E getEdge(V sourceVertex, V destinationVertex) {
    	if (sourceVertex == null || destinationVertex == null) {
            return null;
        }
        E edge = adjacencyMap.get(sourceVertex).get(destinationVertex);
        if (edge != null) {
            return edge;
        }
        
        edge = adjacencyMap.get(destinationVertex).get(sourceVertex);
        if (edge != null) {
            // If the edge is found in the opposite direction, reverse it
            Road<V> reversedEdge = new Road<>(destinationVertex, sourceVertex, ((Road<V>) edge).getWeight(), ((Road<V>) edge).getName());
            return (E) reversedEdge;
        }
        
        // If the edge doesn't exist in either direction, return null
        return null;
    }

    // This method adds an edge between two vertices
    @Override
    public E addEdge(V sourceVertex, V destinationVertex, int weight, String description) {
      if (!containsVertex(sourceVertex) || !containsVertex(destinationVertex)) {
        throw new IllegalArgumentException("Source or destination vertex not found in the graph.");
      }
      E edge = createEdge(sourceVertex, destinationVertex, weight, description);
      if (!adjacencyMap.containsKey(sourceVertex)) {
    	    adjacencyMap.put(sourceVertex, new HashMap<>());
    	}
    	adjacencyMap.get(sourceVertex).put(destinationVertex, edge);

    	if (!adjacencyMap.containsKey(destinationVertex)) {
    	    adjacencyMap.put(destinationVertex, new HashMap<>());
    	}
    	adjacencyMap.get(destinationVertex).put(sourceVertex, edge);
      return edge;
    }

    public E createEdge(V sourceVertex, V destinationVertex, int weight, String description) {
        return (E) new Road<>(sourceVertex, destinationVertex, weight, description);
    }
    
    @Override
    public boolean addVertex(V v) {
        if (containsVertex(v)) {
            return false;
        }
        adjacencyMap.put(v, new HashMap<>());
        return true;
    }

    @Override
    public boolean containsEdge(V sourceVertex, V destinationVertex) {
        if (!containsVertex(sourceVertex) || !containsVertex(destinationVertex)) {
            return false;
        }
        return adjacencyMap.get(sourceVertex).containsKey(destinationVertex);
    }

    @Override
    public boolean containsVertex(V v) {
        return adjacencyMap.containsKey(v);
    }

    @Override
    public Set<E> edgeSet() {
        Set<E> edgeSet = new HashSet<>();
        for (Map<V, E> edges : adjacencyMap.values()) {
            edgeSet.addAll(edges.values());
        }
        return edgeSet;
    }

    @Override
    public Set<E> edgesOf(V vertex) {
        if (!containsVertex(vertex)) {
            throw new IllegalArgumentException("Vertex not found in the graph.");
        }
        return new HashSet<>(adjacencyMap.get(vertex).values());
    }

    @Override
    public E removeEdge(V sourceVertex, V destinationVertex, int weight, String description) {
        if (!containsVertex(sourceVertex) || !containsVertex(destinationVertex)) {
            return null;
        }

        E edge = getEdge(sourceVertex, destinationVertex);
        if (edge == null || !edge.equals(createEdge(sourceVertex, destinationVertex, weight, description))) {
            return null;
        }

        adjacencyMap.get(sourceVertex).remove(destinationVertex);
        adjacencyMap.get(destinationVertex).remove(sourceVertex);
        return edge;
    }

    @Override
    public boolean removeVertex(V v) {
        if (!containsVertex(v)) {
            return false;
        }

        for (Map<V, E> edges : adjacencyMap.values()) {
            edges.remove(v);
        }
        adjacencyMap.remove(v);
        return true;
    }

    @Override
    public Set<V> vertexSet() {
        return adjacencyMap.keySet();
    }
    
    // This method finds the shortest path between two vertices
    @Override
    public ArrayList<String> shortestPath(V sourceVertex, V destinationVertex) {
        dijkstraShortestPath(sourceVertex);
        ArrayList<String> path = new ArrayList<>();
        V current = destinationVertex;
        while (current != null && !current.equals(sourceVertex)) {
            V previous = previousVertices.get(current);
            if (previous != null) {
                E edge = getEdge(previous, current);
                if (edge != null) {
                    String edgeInfo = formatEdgeInfo(previous, current, edge);
                    path.add(0, edgeInfo); // Add to the beginning of the list to reverse the path
                }
            }
            current = previous;
        }
        System.out.println("Shortest path from " + sourceVertex + " to " + destinationVertex + ": " + path);
        return path;
    }
    public String formatEdgeInfo(V source, V destination, E edge) {
        int weight = ((Road<V>) edge).getWeight();
        String roadName = ((Road<V>) edge).getName();
        return source + " via " + roadName + " to " + destination + " " + weight + " mi";
    }

    // This method finds the shortest path from a source vertex to all other vertices
    @Override
    public void dijkstraShortestPath(V sourceVertex) {
        distances.clear();
        previousVertices.clear();

        PriorityQueue<V> priorityQueue = new PriorityQueue<>(new Comparator<V>() {
            @Override
            public int compare(V v1, V v2) {
                Integer dist1 = distances.get(v1);
                Integer dist2 = distances.get(v2);
                if (dist1 == null) {
                    dist1 = Integer.MAX_VALUE;
                }
                if (dist2 == null) {
                    dist2 = Integer.MAX_VALUE;
                }
                return dist1.compareTo(dist2);
            }
        });
        for (V vertex : vertexSet()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(sourceVertex, 0);
        priorityQueue.add(sourceVertex);

        while (!priorityQueue.isEmpty()) {
            V current = priorityQueue.poll();
            if (distances.get(current) == Integer.MAX_VALUE) {
                continue;
            }

            for (E edge : edgesOf(current)) {
                V neighbor = getDestinationVertex(current, edge);
                int distance = distances.get(current) + getEdgeWeight(edge);
                if (distance < distances.get(neighbor)) {
                    distances.put(neighbor, distance);
                    previousVertices.put(neighbor, current);
                    if (priorityQueue.contains(neighbor)) {
                        priorityQueue.remove(neighbor);
                    }
                    priorityQueue.add(neighbor);
                }
            }
        }
    }

    public int getEdgeWeight(E edge) {
        if (edge instanceof Road) {
            return ((Road) edge).getWeight();
        }
        throw new IllegalArgumentException("Unsupported edge type: " + edge.getClass().getName());
    }

    public V getDestinationVertex(V source, E edge) {
        if (edge instanceof Road) {
            Road road = (Road) edge;
            return road.getSource().equals(source) ? (V) road.getDestination() : (V) road.getSource();
        }
        throw new IllegalArgumentException("Unsupported edge type: " + edge.getClass().getName());
    }
    
}