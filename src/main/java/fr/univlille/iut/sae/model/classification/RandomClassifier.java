package fr.univlille.iut.sae.model.classification;

import fr.univlille.iut.sae.model.DataPoint;

import java.util.*;

public class RandomClassifier implements Classifier{
    private Random rand = new Random();
    private List<DataPoint> dataList;


    public RandomClassifier(List<DataPoint> dataList) {
        this.dataList = dataList;
    }

    public String randomType(){
        int random;
        do{
            random = rand.nextInt(dataList.size());
        }while(!this.dataList.get(random).isClassified());
        return this.dataList.get(random).getCategory();

    }

    @Override
    public String classify(List<DataPoint> dataList) {
        for(int i = 0; i < dataList.size(); i++){
            if(!dataList.get(i).isClassified()){
                this.dataList.get(i).setCategory(randomType());
            }
        }
    }
}
