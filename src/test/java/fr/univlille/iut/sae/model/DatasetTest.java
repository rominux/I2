package fr.univlille.iut.sae.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import fr.univlille.iut.sae.model.DataPoint;
import fr.univlille.iut.sae.model.Dataset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DatasetTest {

    private Dataset dataset;
    private List<String> columnNames;
    private String[] categories;
    private List<DataPoint> dataPoints;

    @BeforeEach
    public void setup() {
        columnNames = new ArrayList<>(List.of("Attr1", "Attr2", "Class"));
        categories = new String[]{"CatA", "CatB"};

        dataPoints = new ArrayList<>();
        dataPoints.add(new DataPoint(new double[]{1.0, 2.0}, "CatA", true, true));
        dataPoints.add(new DataPoint(new double[]{3.0, 4.0}, "CatB", true, false));

        dataset = new Dataset(columnNames, categories, dataPoints);
    }

    @Test
    public void testDatasetInitialization() {
        assertEquals(columnNames, dataset.getColumnNames());
        assertArrayEquals(categories, dataset.getCategorie());
        assertEquals(2, dataset.getDataPoints().size());
    }

    @Test
    public void testGetIndexOfAttribute() {
        assertEquals(0, dataset.getIndexOfAttribute("Attr1"));
        assertEquals(1, dataset.getIndexOfAttribute("Attr2"));
        assertEquals(2, dataset.getIndexOfAttribute("Class"));
        assertEquals(-1, dataset.getIndexOfAttribute("Inexistant"));
    }

    @Test
    public void testAddDataPoint() {
        DataPoint newPoint = new DataPoint(new double[]{5.0, 6.0}, "CatA", true, false);

        dataset.addDataPoint(newPoint);

        assertEquals(3, dataset.getDataPoints().size());
        assertTrue(dataset.getDataPoints().contains(newPoint));
    }

    @Test
    public void testResetAddedPoints() {

        assertEquals(2, dataset.getDataPoints().size());
        dataset.resetAddedPoints();
        assertEquals(1, dataset.getDataPoints().size());
        assertTrue(dataset.getDataPoints().get(0).getIsInitialData());
    }
}