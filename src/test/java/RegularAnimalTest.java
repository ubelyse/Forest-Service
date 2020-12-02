import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.Timestamp;

public class RegularAnimalTest {

    @Rule
    public DatabaseRule database = new DatabaseRule();

    // Instantiation
    @Test
    public void animal_instantiatesCorrectly_true() {
        RegularAnimal testAnimal = new RegularAnimal("Rabbit");
        assertTrue(testAnimal instanceof RegularAnimal);
    }

    @Test
    public void animal_instantiatesWithoutId_0() {
        RegularAnimal testAnimal = new RegularAnimal("Rabbit");
        assertEquals(0, testAnimal.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void animal_cannotInstantiateEmptyName_IllegalArgumentException() {
        RegularAnimal testAnimal = new RegularAnimal("");
    }

    // Name
    @Test
    public void animal_instantiatesWithName_Rabbit() {
        RegularAnimal testAnimal = new RegularAnimal("Rabbit");
        assertEquals("Rabbit", testAnimal.getName());
    }

    @Test
    public void setName_setsANewName_Goat() {
        RegularAnimal testAnimal = new RegularAnimal("Rabbit");
        testAnimal.setName("Goat");
        assertEquals("Goat", testAnimal.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setName_cannotSetEmptyName_IllegalArgumentException() {
        RegularAnimal testAnimal = new RegularAnimal("Rabbit");
        testAnimal.setName("");
    }

    @Test
    public void save_savesNameToDB_Rabbit() {
        RegularAnimal testAnimal = new RegularAnimal("Rabbit");
        testAnimal.save();
        RegularAnimal savedRegularAnimal = RegularAnimal.find(testAnimal.getId());
        assertEquals("Rabbit", savedRegularAnimal.getName());
    }

    @Test
    public void update_preservesOriginalName_Rabbit() {
        RegularAnimal testAnimal = new RegularAnimal("Rabbit");
        testAnimal.save();
        testAnimal.update();
        RegularAnimal savedRegularAnimal = RegularAnimal.find(testAnimal.getId());
        assertEquals("Rabbit", savedRegularAnimal.getName());
    }

    @Test
    public void update_savesNewNameToDB_Goat() {
        RegularAnimal testAnimal = new RegularAnimal("Rabbit");
        testAnimal.save();
        testAnimal.setName("Goat");
        testAnimal.update();
        RegularAnimal savedRegularAnimal = RegularAnimal.find(testAnimal.getId());
        assertEquals("Goat", savedRegularAnimal.getName());
    }

    // Database methods
    @Test
    public void save_setsTheId_int() {
        RegularAnimal testAnimal = new RegularAnimal("Rabbit");
        testAnimal.save();
        assertTrue(testAnimal.getId() > 0);
    }

    @Test
    public void save_insertsObjectIntoDB_true() {
        RegularAnimal testAnimal = new RegularAnimal("Rabbit");
        testAnimal.save();
        RegularAnimal savedRegularAnimal = RegularAnimal.find(testAnimal.getId());
        assertTrue(testAnimal.equals(savedRegularAnimal));
    }

    @Test(expected = IllegalArgumentException.class)
    public void save_cannotSaveIfNameAlreadyExists_IllegalArgumentException() {
        RegularAnimal firstRegularAnimal = new RegularAnimal("Rabbit");
        firstRegularAnimal.save();
        RegularAnimal secondRegularAnimal = new RegularAnimal("Rabbit");
        secondRegularAnimal.save();
    }

    @Test
    public void delete_removesObjectFromDB_null() {
        RegularAnimal testAnimal = new RegularAnimal("Rabbit");
        testAnimal.save();
        testAnimal.delete();
        RegularAnimal savedRegularAnimal = RegularAnimal.find(testAnimal.getId());
        assertEquals(null, savedRegularAnimal);
    }

    @Test
    public void update_preservesOriginalId_true() {
        RegularAnimal testAnimal = new RegularAnimal("Rabbit");
        testAnimal.save();
        testAnimal.update();
        RegularAnimal savedRegularAnimal = RegularAnimal.find(testAnimal.getId());
        assertEquals(testAnimal.getId(), savedRegularAnimal.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void update_cannotSaveIfNameAlreadyExists_IllegalArgumentException() {
        RegularAnimal firstRegularAnimal = new RegularAnimal("Rabbit");
        firstRegularAnimal.save();
        RegularAnimal secondRegularAnimal = new RegularAnimal("Goat");
        secondRegularAnimal.save();
        secondRegularAnimal.setName("Rabbit");
        secondRegularAnimal.update();
    }

    @Test
    public void all_getsAllObjectsFromDatabase_true() {
        RegularAnimal firstRegularAnimal = new RegularAnimal("Rabbit");
        firstRegularAnimal.save();
        RegularAnimal secondRegularAnimal = new RegularAnimal("Goat");
        secondRegularAnimal.save();
        RegularAnimal[] expected = { firstRegularAnimal, secondRegularAnimal };
        assertTrue(RegularAnimal.all().containsAll(Arrays.asList(expected)));
    }

    @Test
    public void search_returnsNothingForUnknownValue_emptyList() {
        RegularAnimal firstRegularAnimal = new RegularAnimal("Rabbit");
        firstRegularAnimal.save();
        RegularAnimal secondRegularAnimal = new RegularAnimal("Goat");
        secondRegularAnimal.save();
        List<RegularAnimal> foundRegularAnimals = RegularAnimal.search("fox");
        assertEquals(Collections.<RegularAnimal>emptyList(), foundRegularAnimals);
    }

    @Test
    public void search_returnsAllMatchingObjects_true() {
        RegularAnimal firstRegularAnimal = new RegularAnimal("Bobcat");
        firstRegularAnimal.save();
        RegularAnimal secondRegularAnimal = new RegularAnimal("House Cat");
        secondRegularAnimal.save();
        RegularAnimal thirdRegularAnimal = new RegularAnimal("Rabbit");
        thirdRegularAnimal.save();
        List<RegularAnimal> foundRegularAnimals = RegularAnimal.search("cat");
        RegularAnimal[] expected = { firstRegularAnimal, secondRegularAnimal };
        assertEquals(Arrays.asList(expected), foundRegularAnimals);
    }

    //Other methods
    @Test
    public void equals_objectIsEqualIfAllPropertiesAreEqual_true() {
        RegularAnimal firstRegularAnimal = new RegularAnimal("Rabbit");
        RegularAnimal secondRegularAnimal = new RegularAnimal("Rabbit");
        assertTrue(firstRegularAnimal.equals(secondRegularAnimal));
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
        List<Sighting> foundSighting = testAnimal.getSightings();
        Sighting[] expected = { testSighting };
        assertTrue(foundSighting.containsAll(Arrays.asList(expected)));
    }

}
