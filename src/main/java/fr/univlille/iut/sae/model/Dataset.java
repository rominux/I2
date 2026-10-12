package fr.univlille.iut.sae.model;

import java.util.ArrayList;
import java.util.List;

public class Dataset {
    private String name;
    private List<String> columnNames;
    private List<ModelListener> listener;
    private List<DataPoint> dataPoints;

    public Dataset(List<String> a){
        this.columnNames=a;
        this.listener=new ArrayList<>();
        this.dataPoints=new ArrayList<>();
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
}
