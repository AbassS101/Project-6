/* @Author Abass Shikur
 *  This is my original work
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Road_STUDENT_Test {
    private Road<String> road;

    @Before
    public void setUp() {
        road = new Road<>("TownA", "TownB", 10, "RoadA");
    }

    @Test
    public void testGetName() {
        assertEquals("RoadA", road.getName());
    }

    @Test
    public void testGetWeight() {
        assertEquals(10, road.getWeight());
    }

    @Test
    public void testGetSource() {
        assertEquals("TownA", road.getSource());
    }

    @Test
    public void testGetDestination() {
        assertEquals("TownB", road.getDestination());
    }

    @Test
    public void testCompareTo() {
        Road<String> sameRoad = new Road<>("TownA", "TownB", 10, "RoadA");
        Road<String> differentRoad = new Road<>("TownA", "TownB", 10, "RoadB");
        assertEquals(0, road.compareTo(sameRoad));
        assertTrue(road.compareTo(differentRoad) < 0);
    }

    @Test
    public void testEquals() {
        Road<String> sameRoad = new Road<>("TownA", "TownB", 10, "RoadA");
        Road<String> differentRoad = new Road<>("TownA", "TownB", 10, "RoadB");
        assertTrue(road.equals(sameRoad));
        assertFalse(road.equals(differentRoad));
    }

    @Test
    public void testToString() {
        assertEquals("TownA via RoadA to TownB 10 mi", road.toString());
    }
}
