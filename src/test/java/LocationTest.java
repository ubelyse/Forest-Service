import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.sql.Timestamp;
import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class LocationTest {

    @Rule
    public DatabaseRule database = new DatabaseRule();

    // Instantiation
    @Test
    public void ranger_instantiatesCorrectly_true() {
        Location testLocation = new Location("Near bridge", 1.525, -2.311);
        assertTrue(testLocation instanceof Location);
    }

    @Test
    public void ranger_instantiatesWithoutId_0() {
        Location testLocation = new Location("Near bridge", 1.525, -2.311);
        assertEquals(0, testLocation.getId());
    }

    // Name
    @Test
    public void ranger_instantiatesWithName_User() {
        Location testLocation = new Location("Near bridge", 1.525, -2.311);
        assertEquals("Near bridge", testLocation.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ranger_cannotInstantiateEmptyUserName_IllegalArgumentException() {
        Location testLocation = new Location("", 1.525, -2.311);
    }

    @Test
    public void setUserName_setsANewName_NewName() {
        Location testLocation = new Location("Near bridge", 1.525, -2.311);
        testLocation.setName("NewName");
        assertEquals("NewName", testLocation.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setUserName_cannotSetEmptyName_IllegalArgumentException() {
        Location testLocation = new Location("Near bridge", 1.525, -2.311);
        testLocation.setName("");
    }

    @Test
    public void save_savesNameToDB_Nearbridge() {
        Location testLocation = new Location("Near bridge", 1.525, -2.311);
        testLocation.save();
        Location savedLocation = Location.find(testLocation.getId());
        assertEquals("Near bridge", savedLocation.getName());
    }

    @Test
    public void update_preservesOriginalName_Nearbridge() {
        Location testLocation = new Location("Near bridge", 1.525, -2.311);
        testLocation.save();
        testLocation.update();
        Location savedLocation = Location.find(testLocation.getId());
        assertEquals("Near bridge", savedLocation.getName());
    }

    @Test
    public void update_savesNewNameToDB_NewLocation() {
        Location testLocation = new Location("Near bridge", 1.525, -2.311);
        testLocation.save();
        testLocation.setName("New Location");
        testLocation.update();
        Location savedLocation = Location.find(testLocation.getId());
        assertEquals("New Location", savedLocation.getName());
    }


    // xCoord
    @Test
    public void ranger_instantiatesWithXCoord_1_525() {
        Location testLocation = new Location("Near bridge", 1.525, -2.311);
        assertEquals(1.525, testLocation.getXCoord(), 0);
    }

    @Test
    public void setXCoord_setsANewXCoord_3_885() {
        Location testLocation = new Location("Near bridge", 1.525, -2.311);
        testLocation.setXCoord(3.885);
        assertEquals(3.885, testLocation.getXCoord(), 0);
    }

    @Test
    public void save_savesXCoordToDB_1_525() {
        Location testLocation = new Location("Near bridge", 1.525, -2.311);
        testLocation.save();
        Location savedLocation = Location.find(testLocation.getId());
        assertEquals(1.525, savedLocation.getXCoord(), 0);
    }

    @Test
    public void update_preservesOriginalXCoord_1_525() {
        Location testLocation = new Location("Near bridge", 1.525, -2.311);
        testLocation.save();
        testLocation.update();
        Location savedLocation = Location.find(testLocation.getId());
        assertEquals(1.525, savedLocation.getXCoord(), 0);
    }

    @Test
    public void update_savesNewXCoordToDB_3_885() {
        Location testLocation = new Location("Near bridge", 1.525, -2.311);
        testLocation.save();
        testLocation.setXCoord(3.885);
        testLocation.update();
        Location savedLocation = Location.find(testLocation.getId());
        assertEquals(3.885, savedLocation.getXCoord(), 0);
    }

    // yCoord
    @Test
    public void ranger_instantiatesWithYCoord_2_311() {
        Location testLocation = new Location("Near bridge", 1.525, -2.311);
        assertEquals(-2.311, testLocation.getYCoord(), 0);
    }

    @Test
    public void setYCoord_setsANewYCoord_4_243() {
        Location testLocation = new Location("Near bridge", 1.525, -2.311);
        testLocation.setYCoord(4.243);
        assertEquals(4.243, testLocation.getYCoord(), 0);
    }

    @Test
    public void save_savesYCoordToDB_2_311() {
        Location testLocation = new Location("Near bridge", 1.525, -2.311);
        testLocation.save();
        Location savedLocation = Location.find(testLocation.getId());
        assertEquals(-2.311, savedLocation.getYCoord(), 0);
    }

    @Test
    public void update_preservesOriginalYCoord_2_311() {
        Location testLocation = new Location("Near bridge", 1.525, -2.311);
        testLocation.save();
        testLocation.update();
        Location savedLocation = Location.find(testLocation.getId());
        assertEquals(-2.311, savedLocation.getYCoord(), 0);
    }

    @Test
    public void update_savesNewXCoordToDB_4_243() {
        Location testLocation = new Location("Near bridge", 1.525, -2.311);
        testLocation.save();
        testLocation.setYCoord(4.243);
        testLocation.update();
        Location savedLocation = Location.find(testLocation.getId());
        assertEquals(4.243, savedLocation.getYCoord(), 0);
    }

    // Database methods
    @Test
    public void save_setsTheId_int() {
        Location testLocation = new Location("Near bridge", 1.525, -2.311);
        testLocation.save();
        assertTrue(testLocation.getId() > 0);
    }

    @Test
    public void save_insertsObjectIntoDB_true() {
        Location testLocation = new Location("Near bridge", 1.525, -2.311);
        testLocation.save();
        Location savedLocation = Location.find(testLocation.getId());
        assertTrue(testLocation.getName().equals(savedLocation.getName()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void save_cannotSaveIfNameAlreadyExists_IllegalArgumentException() {
        Location firstLocation = new Location("Near bridge", 1.525, -2.311);
        firstLocation.save();
        Location secondLocation = new Location("Near bridge", 1.525, -2.311);
        secondLocation.save();
    }

    @Test
    public void update_preservesOriginalId_true() {
        Location testLocation = new Location("Near bridge", 1.525, -2.311);
        testLocation.save();
        testLocation.update();
        Location savedLocation = Location.find(testLocation.getId());
        assertEquals(testLocation.getId(), savedLocation.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void update_cannotSaveIfNameAlreadyExists_IllegalArgumentException() {
        Location firstLocation = new Location("Near bridge", 1.525, -2.311);
        firstLocation.save();
        Location secondLocation = new Location("New location", 1.525, -2.311);
        secondLocation.save();
        secondLocation.setName("Near bridge");
        secondLocation.update();
    }

    @Test
    public void all_getsAllObjectsFromDatabase_true() {
        Location firstLocation = new Location("Near bridge", 1.525, -2.311);
        firstLocation.save();
        Location secondLocation = new Location("New Location", 1.525, -2.311);
        secondLocation.save();
        Location[] expected = { firstLocation, secondLocation };
        assertTrue(Location.all().containsAll(Arrays.asList(expected)));
    }


    @Test
    public void delete_removesObjectFromDB_null() {
        Location testLocation = new Location("Near bridge", 1.525, -2.311);
        testLocation.save();
        testLocation.delete();
        Location savedLocation = Location.find(testLocation.getId());
        assertEquals(null, savedLocation);
    }

    @Test
    public void search_returnsNothingForUnknownValue_emptyList() {
        Location firstLocation = new Location("Near bridge", 1.525, -2.311);
        firstLocation.save();
        Location secondLocation = new Location("New Location", 1.525, -2.311);
        secondLocation.save();
        List<Location> foundLocations = Location.search("blank");
        assertEquals(Collections.<Location>emptyList(), foundLocations);
    }

    @Test
    public void search_returnsAllMatchingObjects_true() {
        Location firstLocation = new Location("First Location", 1.525, -2.311);
        firstLocation.save();
        Location secondLocation = new Location("Second Location", 1.525, -2.311);
        secondLocation.save();
        Location thirdLocation = new Location("Another Place", 1.525, -2.311);
        thirdLocation.save();
        Location[] expected = { firstLocation, secondLocation };
        List<Location> foundLocations = Location.search("loc");
        assertEquals(Arrays.asList(expected), foundLocations);
        assertFalse(foundLocations.contains(thirdLocation));
    }

    //Other methods
    @Test
    public void equals_objectIsEqualIfAllPropertiesAreEqual_true() {
        Location firstLocation = new Location("Near bridge", 1.525, -2.311);
        Location secondLocation = new Location("Near bridge", 1.525, -2.311);
        assertTrue(firstLocation.equals(secondLocation));
    }

    @Test
    public void getSightings_getsSightingAssociatedWithId_Sighting() {
        RegularAnimal testAnimal = new RegularAnimal("Rabbit");
        testAnimal.save();
        Location testLocation = new Location("Near bridge", 1.525, -2.311);
        testLocation.save();
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.save();
        Sighting testSighting = new Sighting(testAnimal.getId(), testLocation.getId(), testRanger.getId(), new Timestamp(1L));
        testSighting.save();
        List<Sighting> foundSighting = testLocation.getSightings();
        Sighting[] expected = { testSighting };
        assertTrue(foundSighting.containsAll(Arrays.asList(expected)));
    }

}