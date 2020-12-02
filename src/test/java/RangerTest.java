import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.sql.Timestamp;
import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class RangerTest {

    @Rule
    public DatabaseRule database = new DatabaseRule();

    // Instantiation
    @Test
    public void ranger_instantiatesCorrectly_true() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        assertTrue(testRanger instanceof Ranger);
    }

    @Test
    public void ranger_instantiatesWithoutId_0() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        assertEquals(0, testRanger.getId());
    }

    // User Name
    @Test
    public void ranger_instantiatesWithName_User() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        assertEquals("User", testRanger.getUserName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ranger_cannotInstantiateEmptyUserName_IllegalArgumentException() {
        Ranger testRanger = new Ranger("", "Bob", "Smith", 1, 5035550000L);
    }

    @Test
    public void setUserName_setsANewName_NewUser() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.setUserName("NewUser");
        assertEquals("NewUser", testRanger.getUserName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setUserName_cannotSetEmptyName_IllegalArgumentException() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.setUserName("");
    }

    @Test
    public void save_savesUserNameToDB_User() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.save();
        Ranger savedRanger = Ranger.find(testRanger.getId());
        assertEquals("User", savedRanger.getUserName());
    }

    @Test
    public void update_preservesOriginalUserName_User() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.save();
        testRanger.update();
        Ranger savedRanger = Ranger.find(testRanger.getId());
        assertEquals("User", savedRanger.getUserName());
    }

    @Test
    public void update_savesNewUserNameToDB_NewUser() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.save();
        testRanger.setUserName("NewUser");
        testRanger.update();
        Ranger savedRanger = Ranger.find(testRanger.getId());
        assertEquals("NewUser", savedRanger.getUserName());
    }


    // First Name
    @Test
    public void ranger_instantiatesWithName_Bob() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        assertEquals("Bob", testRanger.getFirstName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ranger_cannotInstantiateEmptyFirstName_IllegalArgumentException() {
        Ranger testRanger = new Ranger("User", "", "Smith", 1, 5035550000L);
    }

    @Test
    public void setFirstName_setsANewName_Tom() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.setFirstName("Tom");
        assertEquals("Tom", testRanger.getFirstName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setName_cannotSetEmptyName_IllegalArgumentException() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.setFirstName("");
    }

    @Test
    public void save_savesFirstNameToDB_Bob() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.save();
        Ranger savedRanger = Ranger.find(testRanger.getId());
        assertEquals("Bob", savedRanger.getFirstName());
    }

    @Test
    public void update_preservesOriginalFirstName_Bob() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.save();
        testRanger.update();
        Ranger savedRanger = Ranger.find(testRanger.getId());
        assertEquals("Bob", savedRanger.getFirstName());
    }

    @Test
    public void update_savesNewFirstNameToDB_Tom() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.save();
        testRanger.setFirstName("Tom");
        testRanger.update();
        Ranger savedRanger = Ranger.find(testRanger.getId());
        assertEquals("Tom", savedRanger.getFirstName());
    }

    // Last Name
    @Test
    public void ranger_instantiatesWithName_Smith() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        assertEquals("Smith", testRanger.getLastName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ranger_cannotInstantiateEmptyLastName_IllegalArgumentException() {
        Ranger testRanger = new Ranger("User", "Bob", "", 1, 5035550000L);
    }

    @Test
    public void setFirstName_setsANewName_Johnson() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.setLastName("Johnson");
        assertEquals("Johnson", testRanger.getLastName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLastName_cannotSetEmptyName_IllegalArgumentException() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.setLastName("");
    }

    @Test
    public void save_savesLastNameToDB_Smith() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.save();
        Ranger savedRanger = Ranger.find(testRanger.getId());
        assertEquals("Smith", savedRanger.getLastName());
    }

    @Test
    public void update_preservesOriginalLastName_Smith() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.save();
        testRanger.update();
        Ranger savedRanger = Ranger.find(testRanger.getId());
        assertEquals("Smith", savedRanger.getLastName());
    }

    @Test
    public void update_savesNewLastNameToDB_Johnson() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.save();
        testRanger.setLastName("Johnson");
        testRanger.update();
        Ranger savedRanger = Ranger.find(testRanger.getId());
        assertEquals("Johnson", savedRanger.getLastName());
    }

    // Badge
    @Test
    public void ranger_instantiatesWithBadge_1() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        assertEquals(1, testRanger.getBadge());
    }

    @Test
    public void setBadge_setsANewBadge_2() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.setBadge(2);
        assertEquals(2, testRanger.getBadge());
    }

    @Test
    public void save_savesBadgeToDB_1() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.save();
        Ranger savedRanger = Ranger.find(testRanger.getId());
        assertEquals(1, savedRanger.getBadge());
    }

    @Test
    public void update_preservesOriginalBadge_1() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.save();
        testRanger.update();
        Ranger savedRanger = Ranger.find(testRanger.getId());
        assertEquals(1, savedRanger.getBadge());
    }

    @Test
    public void update_savesNewBadgeToDB_2() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.save();
        testRanger.setBadge(2);
        testRanger.update();
        Ranger savedRanger = Ranger.find(testRanger.getId());
        assertEquals(2, savedRanger.getBadge());
    }

    // Phone
    @Test
    public void ranger_instantiatesWithPhone_1() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        assertEquals(5035550000L, testRanger.getPhone());
    }

    @Test
    public void setBadge_setsANewPhone_3601234567() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.setPhone(3601234567L);
        assertEquals(3601234567L, testRanger.getPhone());
    }

    @Test
    public void save_savesPhoneToDB_5035550000() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.save();
        Ranger savedRanger = Ranger.find(testRanger.getId());
        assertEquals(5035550000L, savedRanger.getPhone());
    }

    @Test
    public void update_preservesOriginalPhone_5035550000() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.save();
        testRanger.update();
        Ranger savedRanger = Ranger.find(testRanger.getId());
        assertEquals(5035550000L, savedRanger.getPhone());
    }

    @Test
    public void update_savesNewPhoneToDB_3601234567() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.save();
        testRanger.setPhone(3601234567L);
        testRanger.update();
        Ranger savedRanger = Ranger.find(testRanger.getId());
        assertEquals(3601234567L, savedRanger.getPhone());
    }

    // Database methods
    @Test
    public void save_setsTheId_int() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.save();
        assertTrue(testRanger.getId() > 0);
    }

    @Test
    public void save_insertsObjectIntoDB_true() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.save();
        Ranger savedRanger = Ranger.find(testRanger.getId());
        assertTrue(testRanger.equals(savedRanger));
    }

    @Test(expected = IllegalArgumentException.class)
    public void save_cannotSaveIfNameAlreadyExists_IllegalArgumentException() {
        Ranger firstRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        firstRanger.save();
        Ranger secondRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        secondRanger.save();
    }

    @Test
    public void update_preservesOriginalId_true() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.save();
        testRanger.update();
        Ranger savedRanger = Ranger.find(testRanger.getId());
        assertEquals(testRanger.getId(), savedRanger.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void update_cannotSaveIfNameAlreadyExists_IllegalArgumentException() {
        Ranger firstRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        firstRanger.save();
        Ranger secondRanger = new Ranger("NewUser", "Bob", "Smith", 1, 5035550000L);
        secondRanger.save();
        secondRanger.setUserName("User");
        secondRanger.update();
    }

    @Test
    public void delete_removesObjectFromDB_null() {
        Ranger testRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        testRanger.save();
        testRanger.delete();
        Ranger savedRanger = Ranger.find(testRanger.getId());
        assertEquals(null, savedRanger);
    }

    @Test
    public void all_getsAllObjectsFromDatabase_true() {
        Ranger firstRanger = new Ranger("User", "Tom", "Smith", 1, 5035550000L);
        firstRanger.save();
        Ranger secondRanger = new Ranger("NewUser", "Tommy", "Smith", 1, 5035550000L);
        secondRanger.save();
        Ranger[] expected = { firstRanger, secondRanger };
        assertTrue(Ranger.all().containsAll(Arrays.asList(expected)));
    }

    @Test
    public void search_returnsNothingForUnknownValue_emptyList() {
        Ranger firstRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        firstRanger.save();
        Ranger secondRanger = new Ranger("NewUser", "Bob", "Smith", 1, 5035550000L);
        secondRanger.save();
        List<Ranger> foundRangers = Ranger.search("tom");
        assertEquals(Collections.<Ranger>emptyList(), foundRangers);
    }

    @Test
    public void search_returnsAllMatchingObjects_true() {
        Ranger firstRanger = new Ranger("User", "Tom", "Smith", 1, 5035550000L);
        firstRanger.save();
        Ranger secondRanger = new Ranger("NewUser", "Tommy", "Smith", 1, 5035550000L);
        secondRanger.save();
        Ranger thirdRanger = new Ranger("Tom", "John", "Smith", 1, 5035550000L);
        thirdRanger.save();
        Ranger fourthRanger = new Ranger("AnotherNewUser", "George", "Tompson", 1, 5035550000L);
        fourthRanger.save();
        Ranger fifthRanger = new Ranger("YetAnotherNewUser", "Mike", "Smith", 1, 5035550000L);
        fifthRanger.save();
        Ranger[] expected = { firstRanger, secondRanger, thirdRanger, fourthRanger };
        List<Ranger> foundRangers = Ranger.search("tom");
        assertEquals(Arrays.asList(expected), foundRangers);
        assertFalse(foundRangers.contains(fifthRanger));
    }


    //Other methods
    @Test
    public void equals_objectIsEqualIfAllPropertiesAreEqual_true() {
        Ranger firstRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        Ranger secondRanger = new Ranger("User", "Bob", "Smith", 1, 5035550000L);
        assertTrue(firstRanger.equals(secondRanger));
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
        List<Sighting> foundSighting = testRanger.getSightings();
        Sighting[] expected = { testSighting };
        assertTrue(foundSighting.containsAll(Arrays.asList(expected)));
    }


}