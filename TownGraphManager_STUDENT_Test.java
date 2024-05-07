/* @Author Abass Shikur
 *  This is my original work
 */

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import static org.junit.Assert.*;

public class TownGraphManager_STUDENT_Test {
    private TownGraphManager graphManager;

    @Before
    public void setUp() {
        graphManager = new TownGraphManager();
    }

    @Test
    public void testAddTown() {
        assertTrue(graphManager.addTown("TownA"));
        assertTrue(graphManager.containsTown("TownA"));
    }

    @Test
    public void testAddRoad() {
        graphManager.addTown("TownA");
        graphManager.addTown("TownB");
        graphManager.addRoad("TownA", "TownB", 10, "Road1");
        assertEquals("Road1", graphManager.getRoad("TownA", "TownB"));
    }

    @Test
    public void testGetPath() {
        graphManager.addTown("TownA");
        graphManager.addTown("TownB");
        graphManager.addRoad("TownA", "TownB", 10, "Road1");
        ArrayList<String> path = graphManager.getPath("TownA", "TownB");
        assertEquals(1, path.size());
        assertEquals("TownA via Road1 to TownB 10 mi", path.get(0));
    }

    @Test
    public void testDeleteRoadConnection() {
        graphManager.addTown("TownA");
        graphManager.addTown("TownB");
        graphManager.addRoad("TownA", "TownB", 10, "Road1");
        assertTrue(graphManager.deleteRoadConnection("TownA", "TownB", "Road1"));
        assertNull(graphManager.getRoad("TownA", "TownB"));
    }

    @Test
    public void testDeleteTown() {
        graphManager.addTown("TownA");
        assertTrue(graphManager.containsTown("TownA"));
        assertTrue(graphManager.deleteTown("TownA"));
        assertFalse(graphManager.containsTown("TownA"));
    }

    @Test
    public void testAllTowns() {
        graphManager.addTown("TownA");
        graphManager.addTown("TownB");
        ArrayList<String> towns = graphManager.allTowns();
        assertEquals(2, towns.size());
        assertTrue(towns.contains("TownA"));
        assertTrue(towns.contains("TownB"));
    }

    @Test
    public void testPopulateTownGraph() throws FileNotFoundException {
        File file = new File("C:\\Users\\aweso\\Downloads\\46A-Release\\46-Release\\MD Towns.txt"); // replace with your file path
        graphManager.populateTownGraph(file);
        assertTrue(graphManager.containsTown("Frederick"));
        assertTrue(graphManager.containsTown("Clarksburg"));
        assertEquals("I270-N", graphManager.getRoad("Frederick", "Clarksburg"));
    }
}
