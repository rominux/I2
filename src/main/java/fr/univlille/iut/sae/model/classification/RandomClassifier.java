package fr.univlille.iut.sae.model.classification;

import fr.univlille.iut.sae.model.DataPoint;
import fr.univlille.iut.sae.model.Dataset;

import java.util.List;
import java.util.Random;

public class RandomClassifier implements Classifier {
    private final Random rand;
    private List<DataPoint> trainingSet;
    private String[] categories;

    public RandomClassifier(){
        this.rand = new Random();
    }

    public void setTrainingSet(Dataset dataset) {
        this.trainingSet = dataset.getDataPoints();
        this.categories = dataset.getCategorie();
    }

    @Override
    public String classify(DataPoint toClassify) {
        if (this.trainingSet == null || this.trainingSet.isEmpty()) {
            return "Inconnue";
        }

        if (categories.length == 0) {
            return "Inconnue";
        }

        int index = rand.nextInt(categories.length);
        return categories[index];
    }
}