package fr.univlille.iut.sae.io;

import fr.univlille.iut.sae.model.DataPoint;
import fr.univlille.iut.sae.model.Dataset;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CSVLoader {
    public static Dataset loadCSV(String path){
        List<String> NomColomne = new ArrayList<>();
        List<DataPoint> dataPoints = new ArrayList<>();
        Set<String> categories = new HashSet<>();

        Path filePath =Path.of(path);
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String ligne;

            if ((ligne = br.readLine()) != null) {
                String[] headers = ligne.split(",");

                for (int i = 0; i < headers.length - 1; i++) {
                    NomColomne.add(headers[i].trim());
                }

                while ((ligne = br.readLine()) != null) {
                    String[] valeurs = ligne.split(",");
                    if (valeurs.length != headers.length) {
                        continue; //on ignore les lignes mal formatées
                    }

                    try {
                        double[] attributs = new double[valeurs.length-1];

                        for (int i = 0; i < valeurs.length - 1; i++) {
                            attributs[i] = Double.parseDouble(valeurs[i].trim());
                        }

                        String categorie = valeurs[valeurs.length - 1].trim();
                        categories.add(categorie);

                        dataPoints.add(new DataPoint(attributs, categorie, true, true));

                    } catch (NumberFormatException e) {
                        //si une valeur n'est pas un nombre, on ignore juste la ligne
                        continue;
                    }
                }
            }

        } catch (IOException e) {
            //les vraies erreurs de lecture du fichier tombent ici. Le dataset retourné sera vide.
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        }

        return new Dataset(NomColomne, categories.toArray(new String[0]), dataPoints);
    }
}