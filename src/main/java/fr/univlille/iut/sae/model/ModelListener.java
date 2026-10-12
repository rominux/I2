package fr.univlille.iut.sae.model;

/**
 * Observateur du modèle de données.
 * <p>
 * Les vues implémentant cette interface sont notifiées par {@link Dataset}
 * lors de la modification des données.
 */
public interface ModelListener {

    /**
     * Appelée lorsque l'ensemble des données a été modifié de manière globale.
     */
    void onDataUpdated();

    /**
     * Appelée lorsqu'un nouveau point a été ajouté au jeu de données.
     *
     * @param point le point nouvellement ajouté
     */
    void onPointAdded(DataPoint point);
}
