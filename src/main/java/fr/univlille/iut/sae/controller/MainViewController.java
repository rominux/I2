package fr.univlille.iut.sae.controller;

import fr.univlille.iut.sae.io.CSVLoader;
import fr.univlille.iut.sae.model.DataPoint;
import fr.univlille.iut.sae.model.Dataset;
import fr.univlille.iut.sae.model.classification.Classifier;
import fr.univlille.iut.sae.model.classification.RandomClassifier;
import fr.univlille.iut.sae.view.ScatterChartView;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
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
        this.milieu.setVgap(10);
        this.milieu.setHgap(10);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(this.milieu);
        scrollPane.setFitToWidth(true);

        //priorité au ScrollPane pour qu'il prenne tout l'espace central disponible
        VBox.setVgrow(scrollPane, javafx.scene.layout.Priority.ALWAYS);

        HBox bas = new HBox(10);
        Button ajouterView = new Button("Ajouter une vue");
        ajouterView.setOnAction(e -> openNewScatterPlotView());

        Button addData = new Button("Ajouter une donnée");
        addData.setOnAction(e -> addPoint());

        Button classify = new Button("Classifier les données inconnues");
        classify.setOnAction(e -> classifyUnknownPoints());

        bas.getChildren().addAll(ajouterView, addData, classify);

        vb.getChildren().addAll(haut, scrollPane, bas);

        Scene sc = new Scene(vb, 1000, 1000);
        this.stage.setScene(sc);
        this.stage.setTitle("SAÉ 3.02 — Outil de classification");
        this.stage.show();
    }

    public void loadFile(String path) {
        this.currentDataset = CSVLoader.loadCSV(path);
        this.milieu.getChildren().clear();
        openNewScatterPlotView();
    }

    public void addPoint() {
        // Ouvrira une boîte de dialogue pour saisir les coordonnées
    }

    public void addPoint(double[] valeurs) {
        this.currentDataset.addDataPoint(new DataPoint(valeurs));
    }

    public void classifyUnknownPoints() {
        for (DataPoint dp : currentDataset.getDataPoints()) {
            if (!dp.getIsClassified()){
                classifier.classify(dp);
            }
        }
    }

    public void openNewScatterPlotView() {
        if (currentDataset == null) {
            return;
        }

        if (this.milieu.getChildren().isEmpty() && currentDataset.getColumnNames().size() >= 2) {
            openNewScatterPlotView(0, 1);
            return;
        }

        //boîte de dialogue
        Dialog<int[]> dialog = new Dialog<>();
        dialog.setTitle("Choix des axes");
        dialog.setHeaderText("Sélectionnez les attributs à projeter :");

        //boutons OK et Annuler
        ButtonType okButtonType = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        //ComboBox avec noms des colonnes
        ComboBox<String> comboX = new ComboBox<>();
        ComboBox<String> comboY = new ComboBox<>();
        comboX.getItems().addAll(currentDataset.getColumnNames());
        comboY.getItems().addAll(currentDataset.getColumnNames());

        //par défaut
        if (!currentDataset.getColumnNames().isEmpty()) {
            comboX.getSelectionModel().select(0);
            comboY.getSelectionModel().select(1 % currentDataset.getColumnNames().size());
        }

        //mise en page
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.add(new Label("Axe X (Abscisse) :"), 0, 0);
        grid.add(comboX, 1, 0);
        grid.add(new Label("Axe Y (Ordonnée) :"), 0, 1);
        grid.add(comboY, 1, 1);
        dialog.getDialogPane().setContent(grid);

        //résultat (index choisis)
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new int[] {
                        comboX.getSelectionModel().getSelectedIndex(),
                        comboY.getSelectionModel().getSelectedIndex()
                };
            }
            return null;
        });

        //affichage et traitement du résultat
        dialog.showAndWait().ifPresent(indices -> {
            openNewScatterPlotView(indices[0], indices[1]);
        });
    }

    public void openNewScatterPlotView(int axisXindex,int axisYindex) {
        if (currentDataset == null) {
            return;
        }
        ScatterChartView nouvelleVue = new ScatterChartView(currentDataset,axisXindex,axisYindex);
        this.milieu.getChildren().add(nouvelleVue);
    }
}