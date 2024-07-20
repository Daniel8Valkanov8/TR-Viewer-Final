package partView.listeners.toolbarButtons;

import org.knowm.xchart.*;
import org.knowm.xchart.style.colors.XChartSeriesColors;

import dto.type.CircleDiagramDTO;
import partView.mainWindowComponents.WindowMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//diagrams for total statistics of transpoisons in file
public class ViewStatisticActionListener implements ActionListener {

    private WindowMain window;

    public ViewStatisticActionListener(WindowMain window) {
        this.window = window;
        System.out.println(window);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if (window.getService() == null) {
           JOptionPane.showMessageDialog(window, "No file selected!", "No file", JOptionPane.ERROR_MESSAGE);
           return;
       }
        CircleDiagramDTO data = new CircleDiagramDTO(this.window.getService());

        // Create Chart
        PieChart chart = new PieChartBuilder().width(800).height(600).title("").build();

        // Customize Chart
        chart.getStyler().setCircular(true); // Променете circular на true за да направите диаграмата като пита
        chart.getStyler().setLegendVisible(true);
        chart.getStyler().setToolTipsEnabled(true);
        chart.getStyler().setPlotContentSize(0.9);
        chart.getStyler().setPlotBackgroundColor(Color.DARK_GRAY); // Set background color

        // Set custom slice colors for better visualization
        chart.getStyler().setSeriesColors(new Color[]{
                XChartSeriesColors.BLUE,
                XChartSeriesColors.RED,
                XChartSeriesColors.GREEN,
                XChartSeriesColors.YELLOW,
                XChartSeriesColors.PURPLE
        });



        chart.addSeries("DNA", data.getDNA());
        chart.addSeries("OTHER", data.getOTHER());
        chart.addSeries("LTR", data.getLTR());
        chart.addSeries("SINE", data.getSINE());
        chart.addSeries("LINE", data.getLINE());

        // Display the count of transposons and mini transposons
        String transposonInfo = "<html>Total:<br>" +
                "Gene: " + data.getService().getRepository().getGenesInFile().size() + "<br>" +
                "Transcripts: " + data.getService().getRepository().getTranscriptsInFile().size() + "<br>" +
                "Transposons: " + data.getService().getRepository().getTransposonsInputGenes().size() + "<br>" +
                "Copies: " + data.getService().getRepository().getMiniTransposonsInFile().size() + "</html>";

        // Show chart in Swing UI
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Statistics Of Types: " + window.getPathToLastFile().getFileName());
            frame.setLayout(new BorderLayout());

            // Add chart
            JPanel chartPanel = new XChartPanel<>(chart);
            frame.add(chartPanel, BorderLayout.CENTER);

            // Add transposon info
            JLabel infoLabel = new JLabel(transposonInfo);
            infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            frame.add(infoLabel, BorderLayout.NORTH);

            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}




