package fr.univlille.iut.sae.controller;

import fr.univlille.iut.sae.io.CSVLoader;
import fr.univlille.iut.sae.model.Dataset;
import fr.univlille.iut.sae.model.classification.Classifier;
import fr.univlille.iut.sae.model.classification.RandomClassifier;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainViewController {
    private final Stage stage;
    private Dataset currentDataset;
    private Classifier classifier;
    private final FlowPane milieu;

    public MainViewController(Stage stage) {
        this.stage = stage;
        this.classifier = new RandomClassifier(); //jalon 1

        VBox vb = new VBox(10);

        HBox haut = new HBox(10);
        Label nomCSV = new Label("Aucun fichier chargé");
        Button chargerCSV = new Button("Charger un CSV");
        chargerCSV.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sélectionner un fichier CSV");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Fichiers CSV (*.csv)", "*.csv")
            );
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                loadFile(file.getAbsolutePath());
                nomCSV.setText(file.getName());
            }
        });
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

        Scene sc = new Scene(vb, 800, 600);
        this.stage.setScene(sc);
        this.stage.setTitle("SAÉ 3.02 — Outil de classification");
        this.stage.show();
    }

    public void loadFile(String path) {
        this.currentDataset = CSVLoader.loadCSV(path);
        openNewScatterPlotView();
    }

    public void addPoint() {
        // Ouvrira une boîte de dialogue pour saisir les coordonnées
    }

    public void addPoint(double[] valeurs) {
        // Crée le DataPoint et l'ajoute au currentDataset
    }

    public void classifyUnknownPoints() {
        // Parcours des points inconnus et appel à classifier.classify(...)
    }

    public void openNewScatterPlotView() {
        if (currentDataset == null) {
            return;
        }
        // this.milieu.getChildren().add(view);
    }
}