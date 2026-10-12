package fr.univlille.iut.sae.controller;

import fr.univlille.iut.sae.io.CSVLoader;
import fr.univlille.iut.sae.model.DataPoint;
import fr.univlille.iut.sae.model.Dataset;
import fr.univlille.iut.sae.model.classification.Classifier;
import fr.univlille.iut.sae.model.classification.RandomClassifier;
import fr.univlille.iut.sae.view.ScatterPlotView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Contrôleur principal de l'application.
 * <p>
 * Il construit l'interface graphique, gère les interactions de l'utilisateur et
 * fait le lien entre la vue, le modèle ({@link Dataset}) et les services
 * ({@link CSVLoader}, {@link Classifier}).
 */
public class MainViewController {

    private final Stage stage;
    private final Label nomCSV;
    private final FlowPane milieu;

    private Dataset currentDataset;
    private Classifier classifier;

    /**
     * Construit l'interface graphique et l'attache à la fenêtre.
     *
     * @param stage la fenêtre principale fournie par l'application
     */
    public MainViewController(Stage stage) {
        this.stage = stage;
        this.classifier = new RandomClassifier(); // Jalon 1

        VBox vb = new VBox(10);

        HBox haut = new HBox(10);
        this.nomCSV = new Label("Aucun fichier chargé");
        Button chargerCSV = new Button("Charger un CSV");
        chargerCSV.setOnAction(e -> loadFile());
        haut.getChildren().addAll(chargerCSV, nomCSV);

        this.milieu = new FlowPane();

        HBox bas = new HBox(10);
        Button ajouterView = new Button("Ajouter une vue");
        ajouterView.setOnAction(e -> openNewScatterPlotView());
        Button addData = new Button("Ajouter une donnée");
        addData.setOnAction(e -> addPoint());
        Button classify = new Button("Classifier les données inconnues");
        classify.setOnAction(e -> classifyUnknownPoints());
        bas.getChildren().addAll(ajouterView, addData, classify);

        vb.getChildren().addAll(haut, milieu, bas);

        Scene scene = new Scene(vb, 800, 600);
        this.stage.setScene(scene);
        this.stage.setTitle("SAÉ 3.02 — Outil de classification");
    }

    /**
     * Demande un fichier CSV à l'utilisateur, le charge puis affiche une vue.
     */
    public void loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un fichier CSV");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichiers CSV (*.csv)", "*.csv"));
        File file = fileChooser.showOpenDialog(stage);
        if (file == null) {
            return;
        }

        this.currentDataset = CSVLoader.loadCSV(file.getAbsolutePath());
        this.milieu.getChildren().clear();
        if (currentDataset != null) {
            this.nomCSV.setText(file.getName());
            openNewScatterPlotView();
        }
    }

    /**
     * Demande les coordonnées d'un nouveau point, le classifie aléatoirement et
     * l'ajoute au jeu de données.
     */
    public void addPoint() {
        if (currentDataset == null) {
            return;
        }
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Ajouter une donnée");
        dialog.setHeaderText("Saisir les valeurs des attributs séparées par des virgules");
        dialog.showAndWait().ifPresent(saisie -> {
            try {
                String[] champs = saisie.split(",");
                double[] valeurs = new double[champs.length];
                for (int i = 0; i < champs.length; i++) {
                    valeurs[i] = Double.parseDouble(champs[i].trim());
                }
                DataPoint point = new DataPoint(valeurs, null, false);
                point.setCategory(classifier.classify(point, currentDataset.getDataPoints()));
                currentDataset.addDataPoint(point);
            } catch (NumberFormatException e) {
                System.err.println("Saisie invalide : " + saisie);
            }
        });
    }

    /**
     * Classifie tous les points non étiquetés du jeu de données courant.
     */
    public void classifyUnknownPoints() {
        if (currentDataset == null) {
            return;
        }
        List<DataPoint> trainingSet = new ArrayList<>(currentDataset.getDataPoints());
        for (DataPoint point : currentDataset.getDataPoints()) {
            if (!point.isClassified()) {
                point.setCategory(classifier.classify(point, trainingSet));
            }
        }
    }

    /**
     * Ajoute une nouvelle vue en nuage de points sur la zone centrale.
     */
    public void openNewScatterPlotView() {
        if (currentDataset == null) {
            return;
        }
        ScatterPlotView view = new ScatterPlotView(currentDataset, 0, 1);
        this.milieu.getChildren().add(view);
    }
}
