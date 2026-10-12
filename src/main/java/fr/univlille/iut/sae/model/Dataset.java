package fr.univlille.iut.sae.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Jeu de données du modèle.
 * <p>
 * Un {@code Dataset} détient les noms des colonnes ainsi que la collection des
 * {@link DataPoint}. Il joue le rôle de « sujet » dans le patron Observateur :
 * toute modification est notifiée aux {@link ModelListener} enregistrés.
 */
public class Dataset {

    private String name;
    private final List<String> columnNames;
    private final List<DataPoint> dataPoints;
    private final List<ModelListener> listeners;

    /**
     * Construit un jeu de données à partir des noms de colonnes.
     *
     * @param columnNames les noms des colonnes (attributs puis catégorie)
     */
    public Dataset(List<String> columnNames) {
        this.columnNames = columnNames;
        this.dataPoints = new ArrayList<>();
        this.listeners = new ArrayList<>();
    }

    /**
     * Enregistre un observateur qui sera notifié des modifications du modèle.
     *
     * @param listener l'observateur à ajouter
     */
    public void addListener(ModelListener listener) {
        listeners.add(listener);
    }

    public int getIndexOfAttribute(String attribute) {
        return columnNames.indexOf(attribute);
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }

    /**
     * Ajoute un point au jeu de données et notifie les observateurs.
     *
     * @param point le point à ajouter
     */
    public void addDataPoint(DataPoint point) {
        dataPoints.add(point);
        for (ModelListener listener : listeners) {
            listener.onPointAdded(point);
        }
    }

    /**
     * Supprime les points ajoutés après le chargement (ceux qui ne proviennent
     * pas du fichier initial) et notifie les observateurs.
     */
    public void resetAddedPoints() {
        dataPoints.removeIf(point -> !point.isInitialData());
        for (ModelListener listener : listeners) {
            listener.onDataUpdated();
        }
    }
}
