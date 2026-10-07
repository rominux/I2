package fr.univlille.iut.sae.controller;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Contrôleur principal de l'application.
 * <p>
 * Il construit l'interface graphique (barre de chargement, zone centrale
 * accueillant les vues et barre d'actions) et attache la scène à la fenêtre
 * fournie par {@link fr.univlille.iut.sae.App}.
 */
public class MainViewController {

    private final Stage stage;
    private final FlowPane milieu;

    /**
     * Construit l'interface graphique et l'attache à la fenêtre.
     *
     * @param stage la fenêtre principale fournie par l'application
     */
    public MainViewController(Stage stage) {
        this.stage = stage;

        VBox vb = new VBox(10);

        HBox haut = new HBox(10);
        Label nomCSV = new Label("Aucun fichier chargé");
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

    public void loadFile() {
    }

    public void addPoint() {
    }

    public void classifyUnknownPoints() {
    }

    public void openNewScatterPlotView() {
    }
}
