
import static org.junit.jupiter.api.Assertions.*;

import fr.univlille.iut.sae.io.CSVLoader;
import org.junit.jupiter.api.Test;

import fr.univlille.iut.sae.model.Dataset;

public class CSVLoaderTest {

    @Test
    public void testLoadCSV() throws Exception {

        String path = "res/test.csv";

        Dataset dataset = CSVLoader.loadCSV(path);

        assertNotNull(dataset);
        assertFalse(dataset.getColumnNames().isEmpty(), "Les colonnes ne doivent pas être vides");
        assertFalse(dataset.getDataPoints().isEmpty(), "Les points de données ne doivent pas être vides");
        assertNotNull(dataset.getCategorie(), "Le tableau des catégories ne doit pas être null");
    }
}