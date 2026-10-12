package fr.univlille.iut.sae.view;

import fr.univlille.iut.sae.model.DataPoint;
import fr.univlille.iut.sae.model.Dataset;
import fr.univlille.iut.sae.model.ModelListener;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Vue affichant les points d'un {@link Dataset} sous forme de nuage de points
 * projeté sur deux attributs.
 * <p>
 * Cette vue est un observateur du modèle : elle se redessine automatiquement
 * lorsque le jeu de données est modifié.
 */
public class ScatterPlotView extends Canvas implements ModelListener {

    private static final Color[] PALETTE = {
            Color.web("#1f77b4"), Color.web("#ff7f0e"), Color.web("#2ca02c"),
            Color.web("#d62728"), Color.web("#9467bd"), Color.web("#8c564b"),
            Color.web("#e377c2"), Color.web("#7f7f7f")
    };

    private static final double PADDING = 30;

    private final Dataset dataset;
    private final int axisX;
    private final int axisY;

    /**
     * Construit une vue projetée sur les axes donnés et s'enregistre auprès du modèle.
     *
     * @param dataset le jeu de données à afficher
     * @param axisX   l'index de l'attribut porté par l'axe des abscisses
     * @param axisY   l'index de l'attribut porté par l'axe des ordonnées
     */
    public ScatterPlotView(Dataset dataset, int axisX, int axisY) {
        super(400, 300);
        this.dataset = dataset;
        this.axisX = axisX;
        this.axisY = axisY;
        this.dataset.addListener(this);
        render();
    }

    /**
     * Redessine l'intégralité du nuage de points.
     */
    public void render() {
        GraphicsContext gc = getGraphicsContext2D();
        double width = getWidth();
        double height = getHeight();
        gc.clearRect(0, 0, width, height);

        List<DataPoint> points = dataset.getDataPoints();
        if (points.isEmpty()) {
            return;
        }

        double minX = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = -Double.MAX_VALUE;
        for (DataPoint point : points) {
            minX = Math.min(minX, point.getValue(axisX));
            maxX = Math.max(maxX, point.getValue(axisX));
            minY = Math.min(minY, point.getValue(axisY));
            maxY = Math.max(maxY, point.getValue(axisY));
        }

        List<String> categories = new ArrayList<>();
        for (DataPoint point : points) {
            if (!categories.contains(point.getCategory())) {
                categories.add(point.getCategory());
            }
        }

        for (DataPoint point : points) {
            double x = toScreen(point.getValue(axisX), minX, maxX, PADDING, width - PADDING);
            double y = toScreen(point.getValue(axisY), minY, maxY, height - PADDING, PADDING);
            double radius = point.isInitialData() ? 3 : 6;
            gc.setFill(colorOf(point.getCategory(), categories));
            gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
            if (!point.isInitialData()) {
                gc.setStroke(Color.BLACK);
                gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
            }
        }
    }

    private double toScreen(double value, double min, double max, double start, double end) {
        if (max == min) {
            return (start + end) / 2;
        }
        return start + (value - min) / (max - min) * (end - start);
    }

    private Color colorOf(String category, List<String> categories) {
        if (category == null) {
            return Color.BLACK;
        }
        return PALETTE[Math.floorMod(categories.indexOf(category), PALETTE.length)];
    }

    @Override
    public void onDataUpdated() {
        render();
    }

    @Override
    public void onPointAdded(DataPoint point) {
        render();
    }
}
