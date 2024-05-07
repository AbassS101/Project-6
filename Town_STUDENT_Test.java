/* @Author Abass Shikur
 *  This is my original work
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Town_STUDENT_Test {
    private Town town;

    @Before
    public void setUp() {
        town = new Town("TownA");
    }

    @Test
    public void testGetName() {
        assertEquals("TownA", town.getName());
    }

    @Test
    public void testCompareTo() {
        Town sameTown = new Town("TownA");
        Town differentTown = new Town("TownB");
        assertEquals(0, town.compareTo(sameTown));
        assertTrue(town.compareTo(differentTown) < 0);
    }

    @Test
    public void testEquals() {
        Town sameTown = new Town("TownA");
        Town differentTown = new Town("TownB");
        assertTrue(town.equals(sameTown));
        assertFalse(town.equals(differentTown));
    }

    @Test
    public void testToString() {
        assertEquals("TownA", town.toString());
    }
}
