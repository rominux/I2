

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import fr.univlille.iut.sae.model.DataPoint;
import fr.univlille.iut.sae.model.Dataset;
import fr.univlille.iut.sae.model.classification.RandomClassifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RandomClassifierTest {

    private RandomClassifier classifier;
    private Dataset dataset;

    @BeforeEach
    public void setup() {
        classifier = new RandomClassifier();
        List<String> columns = List.of("Attr1", "Class");
        String[] categories = new String[]{"CatA", "CatB", "CatC"};
        List<DataPoint> points = new ArrayList<>();
        points.add(new DataPoint(new double[]{1.0}, "CatA", true, true));

        dataset = new Dataset(columns, categories, points);
    }

    @Test
    public void testClassifyWithoutTrainingSet() {
        DataPoint point = new DataPoint(new double[]{2.0});
        assertEquals("Inconnue", classifier.classify(point));
    }

    @Test
    public void testClassifyWithTrainingSet() {
        classifier.setTrainingSet(dataset);
        DataPoint point = new DataPoint(new double[]{2.0});
        String result = classifier.classify(point);
        assertNotNull(result);
        boolean isValidCategory = result.equals("CatA") || result.equals("CatB") || result.equals("CatC");
        assertTrue(isValidCategory);
    }

    @Test
    public void testClassifyWithEmptyCategories() {
        Dataset emptyCatDataset = new Dataset(
                List.of("Attr1", "Class"),
                new String[]{},
                List.of(new DataPoint(new double[]{1.0}))
        );

        classifier.setTrainingSet(emptyCatDataset);
        DataPoint point = new DataPoint(new double[]{2.0});

        assertEquals("Inconnue", classifier.classify(point));
    }
}