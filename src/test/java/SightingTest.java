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
}