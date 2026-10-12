package fr.univlille.iut.sae.model.classification;

import fr.univlille.iut.sae.model.DataPoint;

import java.util.List;

/**
 * Classifieur aléatoire (Jalon 1).
 * <p>
 * La catégorie est tirée au hasard parmi celles présentes dans l'ensemble
 * d'entraînement.
 */
public class RandomClassifier implements Classifier {

    @Override
    public String classify(DataPoint toClassify, List<DataPoint> trainingSet) {
        // TODO Jalon 1 : tirage aléatoire parmi les catégories de trainingSet
        return null;
    }
}
