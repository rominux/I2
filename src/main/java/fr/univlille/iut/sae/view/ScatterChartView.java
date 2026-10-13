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

        //prépare toutes les séries
        for (String categorie : dataset.getCategorie()) {
            creerSerie(categorie);
        }
        creerSerie("Inconnue");

        //remplit les séries
        for (DataPoint dp : dataset.getDataPoints()) {
            ajouterPointALaSerie(dp);
        }

        //ajoute les séries au graphique
        for (XYChart.Series<Number, Number> serie : seriesParCategorie.values()) {
            scatterChart.getData().add(serie);
        }
    }

    private void creerSerie(String categorie) {
        if (!seriesParCategorie.containsKey(categorie)) {
            XYChart.Series<Number, Number> serie = new XYChart.Series<>();
            serie.setName(categorie);
            seriesParCategorie.put(categorie, serie);
        }
    }

    private void ajouterPointALaSerie(DataPoint dp) {
        String categorie = dp.getCategory();
        creerSerie(categorie);

        XYChart.Series<Number, Number> serie = seriesParCategorie.get(categorie);

        XYChart.Data<Number, Number> dataNode = new XYChart.Data<>(
                dp.getValues(axisXIndex),
                dp.getValues(axisYIndex)
        );

        if (!dp.getIsInitialData()) {
            // TODO: ajouter différence visuelle pour les points ajoutés manuellement
        }

        serie.getData().add(dataNode);
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