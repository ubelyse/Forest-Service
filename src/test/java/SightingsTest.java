import org.junit.Test;

import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class SightingsTest {

    @Test
    public void getLocation() {
        Sightings testSightingObj= setSightingO();
        assertEquals("zone_A", testSightingObj.getLocation());
    }

    //helper method
    public Sightings setSightingO(){
        Sightings testSighting = new Sightings("zone_A", "national park");
        return testSighting;
    }

    @Test
    public void getRanger() {
        Sightings testSightingObj= setSightingO();
        assertEquals("national park", testSightingObj.getRanger());
    }

}