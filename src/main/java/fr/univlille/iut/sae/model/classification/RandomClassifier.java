package fr.univlille.iut.sae.model.classification;

import fr.univlille.iut.sae.model.DataPoint;

import java.util.List;
import java.util.Random;

public class RandomClassifier implements Classifier{
    private Random random ;
    private List<DataPoint> dataPoints;
    private DataPoint point;

    public RandomClassifier(DataPoint point , List<DataPoint> listPoints) {
        this.point = point;
        this.dataPoints = listPoints;
    }



    @Override
    public String classify(DataPoint d, List<DataPoint> data) {
        return "";
    }
}
