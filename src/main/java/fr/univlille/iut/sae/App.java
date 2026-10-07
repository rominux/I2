package fr.univlille.iut.sae;

import fr.univlille.iut.sae.controller.MainViewController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Point d'entrée de l'application JavaFX.
 * <p>
 * Cette classe est responsable du lancement de l'application et de la création
 * de la fenêtre principale, transmise au contrôleur.
 */
public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        new MainViewController(stage);
        stage.show();
    }
}
