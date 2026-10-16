package fr.univlille.iut.sae;

import fr.univlille.iut.sae.controller.MainViewController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Classe principale de l'application de classification de données.
 * Hérite de Application pour initier l'interface graphique JavaFX.
 *
 * @author Équipe I2
 * @version 1.0 (Jalon 1)
 */
public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Point d'entrée JavaFX. Initialise la fenêtre principale (Stage)
     * et délègue la gestion de l'interface au MainViewController.
     *
     * @param stage la fenêtre principale de l'application fournie par JavaFX.
     */
    @Override
    public void start(Stage stage) {
        new MainViewController(stage);
    }
}