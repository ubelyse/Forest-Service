import org.junit.Test;

import static org.junit.Assert.*;

public class AnimalsTest {

    @Test
    public void animal_instantiatesCorrectly_true() {
        Animals testAnimalObj = testAll();
        assertEquals(true, testAnimalObj instanceof Animals);
    }

    private Animals testAll() {
       return new Animals("monkey","newborn","false","true");
    }

    @Test
    public void getName_fromAnimal_String() {
        Animals testAnimalObj = testAll();
        assertEquals("monkey", testAnimalObj.getName());
    }

    @Test
    public void getAge_fromAnimal_String() {
        Animals testAnimalObj =testAll();
        assertEquals("newborn",testAnimalObj.getAge());
    }

    @Test
    public void getEndangered() {
    }

    @Test
    public void getHealthy() {
    }

    @Test
    public void testEquals() {
    }

}