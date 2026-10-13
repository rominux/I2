package fr.univlille.iut.sae.model;

public class DataPoint {
    private double [] values;
    private String category;
    private boolean isClassified;
    private boolean isInitialData;

    public DataPoint(double[] values,String category,boolean isClassified, boolean isInitialData){
        this.values = values;
        this.category = category;
        this.isClassified = isClassified;
        this.isInitialData = isInitialData;
    }

    public DataPoint(double[] values){
        this(values,"Inconnue",false,false);
    }

    public String getCategory() {
        return this.category;
    };

    public boolean getIsClassified() {
        return this.isClassified;
    };

    public void setCategory(String category) {
        this.category = category;
    };

    public double[] getValues(){
        return this.values;
    }

    public double getValues(int pos){
        return this.values[pos];
    }

    public boolean getIsInitialData(){
        return this.isInitialData;
    }
}
