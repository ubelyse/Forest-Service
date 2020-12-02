import org.junit.*;
import static org.junit.Assert.*;

public class SightingTest {

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void sighting_instantiatesCorrectly_true() {
        Sighting testSighting = new Sighting("zone_A", "national park");
        assertEquals(true, testSighting instanceof Sighting);
    }

    @Test
    public void getLocation_sighting_A() {
        Sighting testSightingObj = new Sighting("zone_A", "national park");
        assertEquals("zone_A", testSightingObj.getLocation());
    }

    @Test
    public void getRanger_sighting_String() {
        Sighting testSightingObj = new Sighting("zone_A", "national park");
        assertEquals("national park", testSightingObj.getRanger());
    }

    @Test
    public void equals_returnsTrueIfAreSame_true() {
        Sighting firstSighting = new Sighting("zone_A", "national park");
        Sighting anotherSighting = new Sighting("zone_A", "national park");
        assertTrue(firstSighting.equals(anotherSighting));
    }

    @Test
    public void save_insertsIntoDatabase_Sightings() {
        Sighting testSightingObj = new Sighting("zone_A", "national park");
        testSightingObj.save();
        assertTrue(Sighting.all().get(0).equals(testSightingObj));
    }

    @Test
    public void all_returnsAllInstances_true() {
        Sighting firstSighting = new Sighting("zone_A", "national park");
        firstSighting.save();
        Sighting secondSighting = new Sighting("zone_A", "national park");
        secondSighting.save();
        assertEquals(true, Sighting.all().get(0).equals(firstSighting));
        assertEquals(true, Sighting.all().get(1).equals(secondSighting));
    }

    @Test
    public void save_assignsIdToSighting() {
        Sighting testSightingObj = new Sighting("zone_A", "national park");
        testSightingObj.save();
        Sighting savedSighting = Sighting.all().get(0);
        assertEquals(testSightingObj.getId(), savedSighting.getId());
    }

    @Test
    public void find_returnsSightingWithSameId_secondSighting() {
        Sighting firstSighting = new Sighting("zone_A", "national park");
        firstSighting.save();
        Sighting secondSighting = new Sighting("zone_A", "national park");
        secondSighting.save();
        assertEquals(Sighting.find(secondSighting.getId()), secondSighting);
    }

}