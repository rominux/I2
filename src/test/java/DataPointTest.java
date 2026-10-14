import static org.junit.jupiter.api.Assertions.*;

import fr.univlille.iut.sae.model.DataPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataPointTest {

    private DataPoint point1;
    private DataPoint point2;
    private double[] values;

    @BeforeEach
    public void setup() {
        values = new double[]{1.5, 2.5, 3.5};
        point1 = new DataPoint(values, "CatA", true, true);
        point2 = new DataPoint(values);
    }

    @Test
    public void testFullConstructorAndGetters() {
        assertArrayEquals(values, point1.getValues());
        assertEquals("CatA", point1.getCategory());
        assertTrue(point1.getIsClassified());
        assertTrue(point1.getIsInitialData());
    }

    @Test
    public void testDefaultConstructor() {
        assertArrayEquals(values, point2.getValues());
        assertEquals("Inconnue", point2.getCategory());
        assertFalse(point2.getIsClassified());
        assertFalse(point2.getIsInitialData());
    }

    @Test
    public void testGetValuesByIndex() {
        assertEquals(1.5, point1.getValues(0));
        assertEquals(2.5, point1.getValues(1));
        assertEquals(3.5, point1.getValues(2));
    }

    @Test
    public void testSetters() {
        // Test setCategory
        point1.setCategory("CatB");
        assertEquals("CatB", point1.getCategory());

        // Test setClassified
        point1.setClassified(false);
        assertFalse(point1.getIsClassified());

        point1.setClassified(true);
        assertTrue(point1.getIsClassified());
    }
}