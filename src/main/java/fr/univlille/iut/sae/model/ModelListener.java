package fr.univlille.iut.sae.model;

public interface ModelListener {
    void onDataUpdated();
    void onPointAdded(DataPoint d);
}
