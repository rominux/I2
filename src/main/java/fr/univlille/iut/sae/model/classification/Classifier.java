package fr.univlille.iut.sae.model.classification;

import fr.univlille.iut.sae.model.DataPoint;

import java.util.List;

/**
 * Stratégie de classification.
 * <p>
 * Chaque implémentation détermine la catégorie la plus probable d'un point à
 * partir d'un ensemble d'entraînement.
 */
public interface Classifier {

    /**
     * Détermine la catégorie d'un point.
     *
     * @param toClassify  le point dont la catégorie est inconnue
     * @param trainingSet les points étiquetés servant à l'apprentissage
     * @return la catégorie attribuée au point
     */
    String classify(DataPoint toClassify, List<DataPoint> trainingSet);
}
