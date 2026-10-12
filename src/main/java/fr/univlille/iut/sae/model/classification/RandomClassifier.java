package fr.univlille.iut.sae.model.classification;

import fr.univlille.iut.sae.model.DataPoint;
import fr.univlille.iut.sae.model.Dataset;

import java.util.*;

public class RandomClassifier implements Classifier{
    private Random rand = new Random();
    private Dataset dataset;

    public RandomClassifier(Dataset dataset) {
        this.dataset = dataset;
    }

    public String randomType(){
        int random;
        int taille = dataset.categorie.length;
        random = rand.nextInt(taille);
        return this.dataset.categorie[random];
    }

    @Override
    public String classify(List<DataPoint> datapoint) {
        for(int i = 0; i < dataset.dataPoint.size(); i++){
            if(!dataset.dataPoint.get(i).isClassified()){
                this.dataset.dataPoint.get(i).setCategory(randomType());
            }
        }

    }


}
