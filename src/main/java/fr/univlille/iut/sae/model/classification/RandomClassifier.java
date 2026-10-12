package fr.univlille.iut.sae.model.classification;

import fr.univlille.iut.sae.model.DataPoint;

import java.util.*;

public class RandomClassifier implements Classifier{
    private Random random ;
    private List<DataPoint> dataList;
    private DataPoint dataPoint;
    private Map<DataPoint, Integer> dataPointMap = new HashMap<DataPoint,Integer>();


    public RandomClassifier(DataPoint dataPoint, List<DataPoint> dataList) {
        this.dataPoint = dataPoint;
        this.dataList = dataList;
    }

    public void type(){

        for( int i = 0 ; i < dataList.size() ; i++){
            if(!dataPointMap.containsKey(dataList.get(i))){
                dataPointMap.put(dataList.get(i), dataPointMap.size());
            }
        }
    }

    @Override
    public String classify(DataPoint dataPoint, List<DataPoint> dataList) {
        return "";
    }
}
