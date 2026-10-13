package fr.univlille.iut.sae.view;

import fr.univlille.iut.sae.model.DataPoint;
import fr.univlille.iut.sae.model.Dataset;
import fr.univlille.iut.sae.model.ModelListener;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

import java.util.LinkedHashMap;
import java.util.Map;

public class ScatterChartView extends VBox implements ModelListener {

    private final Dataset dataset;
    private int axisXIndex;
    private int axisYIndex;

    private ScatterChart<Number, Number> scatterChart;

    // Utilisation de LinkedHashMap pour conserver l'ordre et avoir des couleurs constantes
    private final Map<String, XYChart.Series<Number, Number>> seriesParCategorie;

    public ScatterChartView(Dataset dataset, int axisXIndex, int axisYIndex) {
        this.dataset = dataset;
        this.axisXIndex = axisXIndex;
        this.axisYIndex = axisYIndex;
        this.seriesParCategorie = new LinkedHashMap<>();

        this.dataset.addListener(this);

        construireGraphique();
    }

    private void construireGraphique() {
        String labelX = dataset.getColumnNames().get(axisXIndex);
        String labelY = dataset.getColumnNames().get(axisYIndex);

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel(labelX);
        xAxis.setAutoRanging(true);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(labelY);
        yAxis.setAutoRanging(true);

        scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setTitle(labelX + " / " + labelY);
        scatterChart.setPrefSize(600, 400);

        mettreAJourAffichage();

        this.getChildren().add(scatterChart);
    }

    public void mettreAJourAffichage() {
        scatterChart.getData().clear();
        seriesParCategorie.clear();

        // On boucle simplement sur les points existants pour les ajouter.
        // Les séries se créeront à la volée uniquement si elles contiennent des données.
        for (DataPoint dp : dataset.getDataPoints()) {
            ajouterPointALaSerie(dp);
        }
    }

    private void ajouterPointALaSerie(DataPoint dp) {
        String categorie = dp.getCategory();

        //si premier point "Inconnue"
        if (!seriesParCategorie.containsKey(categorie)) {
            XYChart.Series<Number, Number> nouvelleSerie = new XYChart.Series<>();
            nouvelleSerie.setName(categorie);
            seriesParCategorie.put(categorie, nouvelleSerie);


            XYChart.Data<Number, Number> dataNode = creerDataNode(dp);

            //ajoute point dans la série
            nouvelleSerie.getData().add(dataNode);

            //puis ajoute la série au graphique.
            scatterChart.getData().add(nouvelleSerie);
        }
        //si catégorie existe déjà, on y ajoute le point
        else {
            XYChart.Series<Number, Number> serieExistante = seriesParCategorie.get(categorie);
            XYChart.Data<Number, Number> dataNode = creerDataNode(dp);
            serieExistante.getData().add(dataNode);
        }
    }

    private XYChart.Data<Number, Number> creerDataNode(DataPoint dp) {
        XYChart.Data<Number, Number> dataNode = new XYChart.Data<>(
                dp.getValues(axisXIndex),
                dp.getValues(axisYIndex)
        );

        //visuel pour les points ajoutés manuellement
        if (!dp.getIsInitialData()) {
            dataNode.nodeProperty().addListener((observable, oldNode, newNode) -> {
                if (newNode != null) {
                    newNode.setStyle("-fx-effect: dropshadow(three-pass-box, black, 2, 1.0, 0, 0);");
                }
            });
        }
        return dataNode;
    }

    @Override
    public void onDataUpdated() {
        mettreAJourAffichage();
    }

    @Override
    public void onPointAdded(DataPoint d) {
        ajouterPointALaSerie(d);
    }

}