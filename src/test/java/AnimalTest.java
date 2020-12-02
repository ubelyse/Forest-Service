import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class AnimalTest {

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void animal_instantiatesCorrectly_true() {
        Animal testAnimalObj = new Animal("monkey","newborn","false","true");
        assertEquals(true, testAnimalObj instanceof Animal);
    }

    @Test
    public void getName_fromAnimal_monkey() {
        Animal testAnimalObj = new Animal("monkey","newborn","false","true");
        assertEquals("monkey", testAnimalObj.getName());
    }

    @Test
    public void getAge_fromAnimal_newborn() {
        Animal testAnimalObj = new Animal("monkey","newborn","false","true");
        assertEquals("newborn", testAnimalObj.getAge());
    }

    @Test
    public void getEndangered_fromAnimal_true() {
        Animal testAnimalObj = new Animal("monkey","newborn","false","true");
        assertEquals("true", testAnimalObj.getEndangered());
    }

    @Test
    public void getHealthy_fromAnimal_true() {
        Animal testAnimalObj = new Animal("monkey","newborn","false","true");
        assertEquals("true", testAnimalObj.getHealthy());

    }

    @Test
    public void equals_fromAnimal_true() {
        Animal firstAnimal = new Animal("monkey","newborn","false","true");
        Animal anotherAnimal = new Animal("lion", "young","true","true");
        assertTrue(firstAnimal.equals(anotherAnimal));
    }

    @Test
    public void save_ObjectIntoDatabase_Animals() {
        Animal testAnimalObj = new Animal("monkey","newborn","false","true");
        testAnimalObj.save();
        assertTrue(Animal.all().get(0).equals(testAnimalObj));
    }

    @Test
    public void all_returnsAllInstances_true() {
        Animal firstAnimal= new Animal("monkey","newborn","false","true");
        firstAnimal.save();
        Animal secondAnimal = new Animal("monkey","newborn","false","true");
        secondAnimal.save();
        assertEquals(true, Sighting.all().get(0).equals(firstAnimal));
        assertEquals(true, Sighting.all().get(1).equals(secondAnimal));
    }

    @Test
    public void save_assignsIdToAnimal() {
        Animal testAnimal = new Animal("monkey","newborn","false","true");
        testAnimal.save();
        Animal savedAnimal = Animal.all().get(0);
        assertEquals(testAnimal.getId(), savedAnimal.getId());
    }

    @Test
    public void find_returnsAnimalWithSameId_secondAnimal() {
        Animal firstAnimal = new Animal("lion", "young","true","true");
        firstAnimal.save();
        Animal secondAnimal = new Animal("lion", "young","true","true");
        secondAnimal.save();
        assertEquals(Animal.find(secondAnimal.getId()), secondAnimal);
    }

}