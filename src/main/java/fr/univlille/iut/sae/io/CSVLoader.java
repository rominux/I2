package fr.univlille.iut.sae.io;

import fr.univlille.iut.sae.model.DataPoint;
import fr.univlille.iut.sae.model.Dataset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service de lecture de fichiers CSV.
 * <p>
 * La première ligne contient les noms des colonnes. Les colonnes numériques
 * (toutes sauf la dernière) constituent les attributs, la dernière colonne est
 * la catégorie.
 */
public class CSVLoader {

    /**
     * Charge un fichier CSV et le traduit en {@link Dataset}.
     *
     * @param path le chemin du fichier à lire
     * @return le jeu de données correspondant, ou {@code null} en cas d'erreur
     */
    public static Dataset loadCSV(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String header = reader.readLine();
            if (header == null) {
                return null;
            }

            List<String> columnNames = new ArrayList<>(Arrays.asList(header.split(",")));
            Dataset dataset = new Dataset(columnNames);
            int categoryIndex = columnNames.size() - 1;

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] fields = line.split(",");
                double[] values = new double[categoryIndex];
                for (int i = 0; i < categoryIndex; i++) {
                    values[i] = Double.parseDouble(fields[i].trim());
                }
                String category = fields[categoryIndex].trim();
                dataset.addDataPoint(new DataPoint(values, category, true));
            }
            return dataset;
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erreur lors du chargement du CSV : " + e.getMessage());
            return null;
        }
    }
}
