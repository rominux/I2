package fr.univlille.iut.sae.model.classification;

import fr.univlille.iut.sae.model.DataPoint;
import java.util.List;

public interface Classifier {
    public String classify(DataPoint toClassify);
}