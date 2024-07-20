package partView.diagrams;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import partBiology.Gene;
import partBiology.MiniTransposon;
import partBiology.Transcript;
import partBiology.Transposon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TransposonDiagram extends JPanel {
    private Transposon transposon;

    public TransposonDiagram(Transposon transposon) {

        this.setPreferredSize(new Dimension(6000, 300));
        this.setLayout(new BorderLayout());
        this.setBackground(Color.LIGHT_GRAY);
        this.transposon = transposon;
        // Създаване на XChart
        CategoryChart chart = new CategoryChartBuilder()
                .width(5000)
                .height(300)
                .title("Transposon: " + this.transposon.getName() + "       " + "Genes contains count: " + this.transposon.getAllParentsGene().size())
                .xAxisTitle("Copy")
                .yAxisTitle("Size (nb)")
                .build();
        List<String> categories = generateTransposonCategories(this.transposon, chart);
        List<Double> values = generateTransposonValues(this.transposon);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Проверете дали е кликнато с десния бутон
                if (SwingUtilities.isRightMouseButton(e)) {
                    showContextMenu(e.getX(), e.getY());
                }
            }
    //todo
            private void showContextMenu(int x, int y) {
                // Създаване на контекстно меню
                JPopupMenu contextMenu = new JPopupMenu();

                // Създаване на елемент на менюто
                JMenuItem sortSizeItem = new JMenuItem("Сортирай по размер");
                sortSizeItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        List<String> sortedCategories = new ArrayList<>();
                        sortedCategories.add(generateTransposonCategories(transposon, chart).get(0));
                        List<Double> sortedValues = new ArrayList<>();
                        sortedValues.add(values.get(0));
                        chart.addSeries("Transposons", sortedCategories, sortedValues);
                    }
                });
            }
            });
        // Добавяне на данни към графиката
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setAxisTicksVisible(true);
        chart.getStyler().setXAxisTicksVisible(false);
        chart.getStyler().setToolTipsEnabled(true);

        // Задаване на цвета на стълбовете
        chart.getStyler().setSeriesColors(new Color[] {XChartSeriesColors.ORANGE});

        chart.addSeries("Transposons", categories, values);
        // Визуализиране на графиката
        JPanel chartPanel = new XChartPanel<>(chart);
        this.add(chartPanel, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
    }
    private List<String> generateTransposonCategories(Transposon transposon, CategoryChart chart) {
    List<String> categories = new ArrayList<>();
        String transposonName = transposon.getName();
        String element = "";
        for (Gene gene : transposon.getAllParentsGene()) {
            for (Transcript transcript : gene.getTranscripts()) {
                for (Map.Entry<Transposon, ArrayList<MiniTransposon>> entry : transcript.getTransposons().entrySet()) {
                    Transposon key = entry.getKey();
                    if (key.getName().equals(transposonName)) {
                        ArrayList<MiniTransposon> miniTransposons = entry.getValue();
                        for (MiniTransposon miniTransposon : miniTransposons) {
                            element ="Gene: "+ gene.getName() + ", " + key.getName() + ", " + miniTransposon.getChain();
                            categories.add(element);
                        }
                    }
                }
            }
        }

    return categories;
    }

    private List<Double> generateTransposonValues(Transposon transposon) {
        List<Double> values = new ArrayList<>();
        String transposonName = transposon.getName();
        for (Gene gene : transposon.getAllParentsGene()) {
            for (Transcript transcript : gene.getTranscripts()) {
                for (Map.Entry<Transposon, ArrayList<MiniTransposon>> entry : transcript.getTransposons().entrySet()) {
                    Transposon key = entry.getKey();
                    if (key.getName().equals(transposonName)) {
                        ArrayList<MiniTransposon> miniTransposons = entry.getValue();
                        for (MiniTransposon miniTransposon : miniTransposons) {
                            values.add((double) miniTransposon.getSize());
                        }
                    }
                }
            }
        }
        return values;
    }
}
