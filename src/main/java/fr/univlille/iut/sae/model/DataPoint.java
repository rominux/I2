package fr.univlille.iut.sae.model;

/**
 * Représente une donnée ponctuelle du modèle.
 * <p>
 * Les valeurs des attributs sont stockées dans un tableau de réels indexé par
 * la position de la colonne dans le {@link Dataset}. La catégorie est
 * l'étiquette à prédire ; elle peut être inconnue tant que le point n'a pas
 * été classifié.
 */
public class DataPoint {

    private final double[] values;
    private String category;
    private boolean classified;
    private final boolean initialData;

    /**
     * Construit un point de données.
     *
     * @param values        les valeurs des attributs
     * @param category      la catégorie du point (peut être {@code null} si inconnue)
     * @param isInitialData {@code true} si le point provient du fichier chargé
     */
    public DataPoint(double[] values, String category, boolean isInitialData) {
        this.values = values;
        this.category = category;
        this.initialData = isInitialData;
        this.classified = (category != null && !category.isEmpty());
    }

    public double getValue(int index) {
        return values[index];
    }

    public double[] getValues() {
        return values;
    }

    public String getCategory() {
        return category;
    }

    /**
     * Définit la catégorie du point et le marque comme classifié.
     *
     * @param category la nouvelle catégorie
     */
    public void setCategory(String category) {
        this.category = category;
        this.classified = (category != null && !category.isEmpty());
    }

    public boolean isClassified() {
        return classified;
    }

    public boolean isInitialData() {
        return initialData;
    }
}
