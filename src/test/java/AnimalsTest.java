import org.junit.Test;

import static org.junit.Assert.*;

public class AnimalsTest {

    @Test
    public void animal_instantiatesCorrectly_true() {
        Animals testAnimalObj = first_test();
        assertEquals(true, testAnimalObj instanceof Animals);
    }

    private Animals first_test() {
       return new Animals("lion","young","true","true");
    }
}