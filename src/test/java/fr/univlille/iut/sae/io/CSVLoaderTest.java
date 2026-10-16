package fr.univlille.iut.sae.io;

import fr.univlille.iut.sae.model.Dataset;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CSVLoaderTest {

    @Test
    public void testLoadValidCSV() {
        // Test avec le fichier valide (test.csv)
        Dataset dataset = CSVLoader.loadCSV("src/test/resources/test.csv");

        assertNotNull(dataset, "Le dataset ne doit pas être nul");
        assertEquals(2, dataset.getColumnNames().size(), "Il doit y avoir 2 colonnes d'attributs");
        assertEquals(4, dataset.getDataPoints().size(), "Il doit y avoir 4 lignes de données valides");
    }

    @Test
    public void testLoadCorruptedMissingValues() {
        // Test avec test_corrompu2.csv (contient une ligne avec des données manquantes : 5.0,,CatA)
        Dataset dataset = CSVLoader.loadCSV("src/test/resources/test_corrompu2.csv");

        assertNotNull(dataset);
        // La ligne corrompue doit être ignorée, il ne doit donc pas y avoir 4 points
        assertTrue(dataset.getDataPoints().size() < 4, "Les lignes incomplètes doivent être ignorées");
    }

    @Test
    public void testLoadCorruptedWrongSeparator() {
        // Test avec test_corrompu.csv (les décimales utilisent des virgules, ce qui décale les colonnes)
        Dataset dataset = CSVLoader.loadCSV("src/test/resources/test_corrompu.csv");

        assertNotNull(dataset);
        // Le parseDouble va échouer ou les colonnes seront décalées, le dataset doit ignorer ces lignes
        assertTrue(dataset.getDataPoints().isEmpty(), "Le programme ne doit pas crasher avec un mauvais format de nombre");
    }

    @Test
    public void testLoadEmptyCSV() {
        // Test avec un fichier vide
        Dataset dataset = CSVLoader.loadCSV("src/test/resources/test_vide.csv");

        assertNotNull(dataset);
        assertTrue(dataset.getDataPoints().isEmpty(), "Un fichier vide doit retourner un dataset vide");
    }

    @Test
    public void testLoadBinaryOrOtherFile() {
        // Test avec le fichier non-CSV / binaire (fichier.autre)
        Dataset dataset = CSVLoader.loadCSV("src/test/resources/fichier.autre");

        assertNotNull(dataset);
        assertTrue(dataset.getDataPoints().isEmpty(), "La lecture d'un fichier binaire doit être gérée sans faire crasher l'application");
    }
}