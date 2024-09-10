package Tests;

import Models.Pluto;
import View.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MapTest {

    @Test
    void testSetObjectsCoordinates() throws IOException {
        Pluto mockPluto = Mockito.mock(Pluto.class);
        Map map = new Map();

        map.setObjectsCoordinates();

        assertEquals(400, map.plutoX); // 20 * 20 = 400
        assertEquals(640, map.plutoY);


    }
}
