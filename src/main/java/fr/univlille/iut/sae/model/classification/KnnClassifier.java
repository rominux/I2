package fr.univlille.iut.sae.model.classification;

import fr.univlille.iut.sae.model.DataPoint;

import java.util.List;

/**
 * Classifieur des k plus proches voisins (squelette, Jalon 2).
 */
public class KnnClassifier implements Classifier {

    private final int k;

    /**
     * @param k le nombre de voisins à considérer
     */
    public KnnClassifier(int k) {
        this.k = k;
    }

    @Override
    public String classify(DataPoint toClassify, List<DataPoint> trainingSet) {
        // TODO Jalon 2 : implémenter l'algorithme des k plus proches voisins
        return null;
    }
}
