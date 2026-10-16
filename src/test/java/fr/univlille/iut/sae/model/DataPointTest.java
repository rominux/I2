package fr.univlille.iut.sae.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DataPointTest {
    private DataPoint pointComplet;
    private DataPoint pointInconnu;

    @BeforeEach
    public void setUp() {
        pointComplet = new DataPoint(new double[]{1.5, 2.0}, "CatA", true, true);
        pointInconnu = new DataPoint(new double[]{3.0, 4.0});
    }

    @Test
    public void testConstructeur() {
        assertEquals("CatA", pointComplet.getCategory(), "La catégorie doit être 'CatA'");
        assertTrue(pointComplet.getIsClassified(), "Le point doit être classifier");
        assertTrue(pointComplet.getIsInitialData(), "Le point est un point initial");

        assertEquals("Inconnue", pointInconnu.getCategory(), "La catégorie par défaut doit être 'Inconnue'");
        assertFalse(pointInconnu.getIsClassified(), "Un nouveau point ne doit pas être classifié");
        assertFalse(pointInconnu.getIsInitialData(), "Un nouveau point ne doit pas être une donnée initiale");
    }

    @Test
    public void testGetValuesByIndex() {
        assertEquals(1.5, pointComplet.getValues(0), "La valeur à l'index 0 doit être 1.5");
        assertEquals(2.0, pointComplet.getValues(1), "La valeur à l'index 1 doit être 2.0");

        assertEquals(3.0, pointInconnu.getValues(0), "La valeur à l'index 1 doit être 3.0");
        assertEquals(4.0, pointInconnu.getValues(1), "La valeur à l'index 1 doit être 4.0");

    }

    @Test
    public void testSetters() {
        // Test setCategory
        pointInconnu.setCategory("CatB");
        assertEquals("CatB", pointInconnu.getCategory());

        // Test setClassified
        assertFalse(pointInconnu.getIsClassified());

        pointInconnu.setClassified(true);
        assertTrue(pointInconnu.getIsClassified());
    }
}