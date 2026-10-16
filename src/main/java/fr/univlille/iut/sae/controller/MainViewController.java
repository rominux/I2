package fr.univlille.iut.sae.controller;

import fr.univlille.iut.sae.io.CSVLoader;
import fr.univlille.iut.sae.model.DataPoint;
import fr.univlille.iut.sae.model.Dataset;
import fr.univlille.iut.sae.model.classification.Classifier;
import fr.univlille.iut.sae.model.classification.RandomClassifier;
import fr.univlille.iut.sae.view.ScatterChartView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
        Dataset nouveauDataset = CSVLoader.loadCSV(path);
        if (nouveauDataset.getColumnNames().isEmpty() || nouveauDataset.getDataPoints().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de chargement");
            alert.setHeaderText("Fichier non valide");
            alert.setContentText("Le fichier sélectionné est vide, corrompu, ou n'est pas un fichier CSV valide compatible avec l'application.");
            alert.showAndWait();
            return;
        }

        this.currentDataset = nouveauDataset;
        this.milieu.getChildren().clear();
        openNewScatterPlotView();
        this.classifier.setTrainingSet(currentDataset);
    }

    public void addPoint() {
        if (currentDataset == null || currentDataset.getColumnNames().isEmpty()) {
            return;
        }

        //boîte de dialogue
        Dialog<double[]> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un point");
        dialog.setHeaderText("Saisissez les valeurs pour le nouveau point (Inconnu)");

        //boutons de la boîte
        ButtonType validerButtonType = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(validerButtonType, ButtonType.CANCEL);

        //bouton de validation est désactivé par défaut
        javafx.scene.Node validerButton = dialog.getDialogPane().lookupButton(validerButtonType);
        validerButton.setDisable(true);

        //conteneur principal option
        VBox conteneurPrincipal = new VBox(15);
        conteneurPrincipal.setPadding(new Insets(20, 20, 20, 20));

        //liste pour stocker tous les champs de texte
        java.util.List<TextField> champsSaisie = new java.util.ArrayList<>();

        //création d'une VBox pour chaque attribut
        for (String nomColonne : currentDataset.getColumnNames()) {
            VBox blocSaisie = new VBox(5); // Espacement de 5 entre les éléments
            blocSaisie.setAlignment(Pos.CENTER_LEFT);

            Label labelTitre = new Label(nomColonne + " :");
            TextField champTexte = new TextField();
            champTexte.setPromptText("Ex: 4.5");

            Label labelErreur = new Label();
            labelErreur.setTextFill(Color.RED);
            labelErreur.setStyle("-fx-font-size: 10px;");

            champsSaisie.add(champTexte);

            //listener pour valider la saisie
            champTexte.textProperty().addListener((observable, oldValue, newValue) -> {
                boolean isValid = true;
                if (newValue.trim().isEmpty()) {
                    labelErreur.setText("Ce champ ne peut pas être vide.");
                    isValid = false;
                } else {
                    try {
                        Double.parseDouble(newValue.trim());
                        labelErreur.setText(""); //efface l'erreur si c'est un nombre valide
                    } catch (NumberFormatException e) {
                        labelErreur.setText("Veuillez entrer un nombre valide (ex: 4.5).");
                        isValid = false;
                    }
                }

                //si la saisie actuelle est invalide on désactive le bouton globalement
                if (!isValid) {
                    validerButton.setDisable(true);
                } else {
                    //on vérifie si tous les autres champs sont valides
                    validerButton.setDisable(!tousLesChampsSontValides(champsSaisie));
                }
            });

            blocSaisie.getChildren().addAll(labelTitre, champTexte, labelErreur);
            conteneurPrincipal.getChildren().add(blocSaisie);
        }

        //fenêtre scrollable si le jeu de données a beaucoup de colonnes
        ScrollPane scrollPane = new ScrollPane(conteneurPrincipal);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(400);

        dialog.getDialogPane().setContent(scrollPane);

        //récupération des données lors du clic
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == validerButtonType) {
                double[] valeurs = new double[champsSaisie.size()];
                for (int i = 0; i < champsSaisie.size(); i++) {
                    valeurs[i] = Double.parseDouble(champsSaisie.get(i).getText().trim());
                }
                return valeurs;
            }
            return null;
        });

        //affichage de la boîte et ajout du point si validé
        dialog.showAndWait().ifPresent(valeursSaisies -> {
            addPoint(valeursSaisies); //crée le DataPoint
        });
    }

    //vérifie l'état global du formulaire
    private boolean tousLesChampsSontValides(java.util.List<TextField> champs) {
        for (TextField champ : champs) {
            String texte = champ.getText().trim();
            if (texte.isEmpty()) return false;
            try {
                Double.parseDouble(texte);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    public void addPoint(double[] valeurs) {
        this.currentDataset.addDataPoint(new DataPoint(valeurs));
    }

    public void classifyUnknownPoints() {
        for (DataPoint dp : currentDataset.getDataPoints()) {
            if (!dp.getIsClassified()){
                dp.setCategory(this.classifier.classify(dp));
                dp.setClassified(true);
            }
        }
        currentDataset.update();
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
        dialog.setHeaderText("S" +
                "électionnez les attributs à projeter :");

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