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
}