package fr.univlille.iut.sae.model;

import java.util.ArrayList;
import java.util.List;

public class Dataset {
    private String name;
    private List<String> columnNames;
    public String[] categorie;
    private List<ModelListener> listener;
    private List<DataPoint> dataPoints;

    public Dataset(List<String> columnNames,String[] categorie,List<DataPoint> dataPoints){
        this.columnNames=columnNames;
        this.categorie = categorie;
        this.dataPoints=dataPoints;
        this.listener=new ArrayList<>();
    }

    public List<DataPoint> getDataPoints(){
        return this.dataPoints;
    }

    public String[] getCategorie(){
        return this.categorie;
    }

    public int getIndexOfAttribute(String attribute) {
        return columnNames.indexOf(attribute);
    }

    public void addDataPoint(DataPoint point) {
        dataPoints.add(point);
        for (ModelListener l : listener) {
            l.onPointAdded(point);
        }
    }

    public void resetAddedPoints() {
        dataPoints.removeIf(p -> !p.getIsInitialData());
        for (ModelListener l : listener) {
            l.onDataUpdated();
        }
    }

    public List<String> getColumnNames() {
        return this.columnNames;
    }

    public void addListener(ModelListener l) {
        this.listener.add(l);
    }

    public void update(){
        for (ModelListener l : listener) {
            l.onDataUpdated();
        }
    }
}
