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
}