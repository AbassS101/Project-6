/* @Author Abass Shikur
 *  This is my original work
 */

import org.junit.Before;
import org.junit.Test;
import java.util.Set;
import static org.junit.Assert.*;

public class Graph_STUDENT_Test {
    private Graph<String, Road<String>> graph;

    @Before
    public void setUp() {
        graph = new Graph<>();
    }

    @Test
    public void testAddVertex() {
        assertTrue(graph.addVertex("VertexA"));
        assertTrue(graph.containsVertex("VertexA"));
    }

    @Test
    public void testAddEdge() {
        graph.addVertex("VertexA");
        graph.addVertex("VertexB");
        Road<String> road = graph.addEdge("VertexA", "VertexB", 10, "RoadA");
        assertNotNull(road);
        assertEquals("RoadA", road.getName());
        assertTrue(graph.containsEdge("VertexA", "VertexB"));
    }

    @Test
    public void testGetEdge() {
        graph.addVertex("VertexA");
        graph.addVertex("VertexB");
        graph.addEdge("VertexA", "VertexB", 10, "RoadA");
        Road<String> road = graph.getEdge("VertexA", "VertexB");
        assertNotNull(road);
        assertEquals("RoadA", road.getName());
    }

    @Test
    public void testRemoveEdge() {
        graph.addVertex("VertexA");
        graph.addVertex("VertexB");
        graph.addEdge("VertexA", "VertexB", 10, "RoadA");
        Road<String> road = graph.removeEdge("VertexA", "VertexB", 10, "RoadA");
        assertNotNull(road);
        assertEquals("RoadA", road.getName());
        assertFalse(graph.containsEdge("VertexA", "VertexB"));
    }

    @Test
    public void testRemoveVertex() {
        graph.addVertex("VertexA");
        assertTrue(graph.containsVertex("VertexA"));
        assertTrue(graph.removeVertex("VertexA"));
        assertFalse(graph.containsVertex("VertexA"));
    }

    @Test
    public void testVertexSet() {
        graph.addVertex("VertexA");
        graph.addVertex("VertexB");
        Set<String> vertices = graph.vertexSet();
        assertEquals(2, vertices.size());
        assertTrue(vertices.contains("VertexA"));
        assertTrue(vertices.contains("VertexB"));
    }

    @Test
    public void testEdgeSet() {
        graph.addVertex("VertexA");
        graph.addVertex("VertexB");
        graph.addEdge("VertexA", "VertexB", 10, "RoadA");
        Set<Road<String>> edges = graph.edgeSet();
        assertEquals(1, edges.size());
        assertTrue(edges.contains(new Road<>("VertexA", "VertexB", 10, "RoadA")));
    }

    @Test
    public void testEdgesOf() {
        graph.addVertex("VertexA");
        graph.addVertex("VertexB");
        graph.addEdge("VertexA", "VertexB", 10, "RoadA");
        Set<Road<String>> edges = graph.edgesOf("VertexA");
        assertEquals(1, edges.size());
        assertTrue(edges.contains(new Road<>("VertexA", "VertexB", 10, "RoadA")));
    }
}
