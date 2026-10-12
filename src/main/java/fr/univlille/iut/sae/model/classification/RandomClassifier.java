package fr.univlille.iut.sae.model.classification;

import fr.univlille.iut.sae.model.DataPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Classifieur aléatoire (Jalon 1).
 * <p>
 * La catégorie est tirée au hasard parmi celles présentes dans l'ensemble
 * d'entraînement. Les catégories disponibles sont extraites dynamiquement des
 * points fournis : le classifieur ne détient aucune référence au modèle.
 */
public class RandomClassifier implements Classifier {

    private final Random random;

    public RandomClassifier() {
        this.random = new Random();
    }

    /**
     * Extrait les catégories distinctes des points étiquetés.
     *
     * @param trainingSet l'ensemble d'entraînement
     * @return la liste des catégories rencontrées
     */
    private List<String> extractCategories(List<DataPoint> trainingSet) {
        List<String> categories = new ArrayList<>();
        for (DataPoint point : trainingSet) {
            String category = point.getCategory();
            if (category != null && !category.isEmpty() && !categories.contains(category)) {
                categories.add(category);
            }
        }
        return categories;
    }

    @Override
    public String classify(DataPoint toClassify, List<DataPoint> trainingSet) {
        List<String> categories = extractCategories(trainingSet);
        if (categories.isEmpty()) {
            return null;
        }
        return categories.get(random.nextInt(categories.size()));
    }
}
