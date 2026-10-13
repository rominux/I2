package fr.univlille.iut.sae.model.classification;

import fr.univlille.iut.sae.model.DataPoint;
import fr.univlille.iut.sae.model.Dataset;

import java.util.List;

public interface Classifier {
    public String classify(DataPoint toClassify);
    void setTrainingSet(Dataset currentDataset);
}