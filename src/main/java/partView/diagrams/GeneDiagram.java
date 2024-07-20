package partView.diagrams;

import org.knowm.xchart.XChartPanel;
import partBiology.Gene;
import partBiology.MiniTransposon;
import partBiology.Transcript;
import partBiology.Transposon;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;

import org.knowm.xchart.style.colors.XChartSeriesColors;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//vertical bar of transposons un gene
public class GeneDiagram extends JPanel {
    private Gene gene;
    public GeneDiagram() {
        this.revalidate();
        this.repaint();
    }

    public GeneDiagram(Gene gene) {
        this.setPreferredSize(new Dimension(600, 400));
        this.gene = gene;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.LIGHT_GRAY);

        List<String> categories = generateTransposonCategories(this.gene);
        List<Double> values = generateTransposonValues(this.gene);

        // Създаване на XChart
        CategoryChart chart = new CategoryChartBuilder()
                .width(10000)
                .height(300)
                .title("Gene: " + this.gene.getName())
                .xAxisTitle("TE Copies")
                .yAxisTitle("Size (nb)")
                .build();

        // Добавяне на данни към графиката
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setAxisTicksVisible(true);
        chart.getStyler().setXAxisTicksVisible(false);
        chart.getStyler().setToolTipsEnabled(true);
        // Задаване на цвета на стълбовете
        chart.getStyler().setSeriesColors(new Color[] {XChartSeriesColors.BLUE});

        chart.addSeries("Transposons", categories, values);
        // Визуализиране на графиката
        JPanel chartPanel = new XChartPanel<>(chart);
        chartPanel.setPreferredSize(new Dimension(600, 400));
        this.add(chartPanel, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
    }

    private List<String> generateTransposonCategories(Gene gene) {
        List<String> transposonInDiagram = new ArrayList<>();
        for (Transcript transcript : gene.getTranscripts()) {
            for (Map.Entry<Transposon, ArrayList<MiniTransposon>> entry : transcript.getTransposons().entrySet()) {
                Transposon key = entry.getKey();
                ArrayList<MiniTransposon> miniTransposons = entry.getValue();
                for (MiniTransposon miniTransposon : miniTransposons) {
                    String chartElement = key.getName() + ", " + miniTransposon.getChain();
                    transposonInDiagram.add(chartElement);
                }
            }

        }
        return transposonInDiagram;
    }

    private List<Double> generateTransposonValues(Gene gene) {
        List<Double> values = new ArrayList<>();
        for (Transcript transcript : gene.getTranscripts()) {
            for (Map.Entry<Transposon, ArrayList<MiniTransposon>> entry : transcript.getTransposons().entrySet()) {
                ArrayList<MiniTransposon> miniTransposons = entry.getValue();
                for (MiniTransposon miniTransposon : miniTransposons) {
                    values.add((double) miniTransposon.getSize());
                }
            }
        }
        return values;
    }
}
