package partView.listeners.listWindowListeners;

import dto.util.MyBeanUtils;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import partBiology.Gene;
import partBiology.fileWorker.FileWorker;
import partBiology.service.Service;
import partView.diagrams.listGeneticDiagram.dtos.ListCircleGeneDTO;
import partView.mainWindowComponents.WindowMain;
import partView.windows.createLists.GeneListsWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GeneListWindowDiagram extends JFrame {
    private Service service;
    private GeneListsWindow geneListsWindow;
    private WindowMain windowMain;
    private JSplitPane splitPane;
    private JScrollPane textScrollPane;
    private JPanel panel;
    private JTextArea textArea;
    private JToolBar mainToolBar;
    private JButton export;
    private JProgressBar progressBar;
    private JDialog progressDialog;

    public GeneListWindowDiagram(GeneListsWindow geneListsWindow, WindowMain windowMain) {
        // Create a JDialog to display the progress bar
        progressDialog = new JDialog(this, "Loading", true);
        progressDialog.setLayout(new BorderLayout());
        progressDialog.setSize(200, 100);
        progressDialog.setLocationRelativeTo(this);
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressDialog.add(new JLabel("Initializing..."), BorderLayout.NORTH);
        progressDialog.add(progressBar, BorderLayout.CENTER);

        // Start the initialization in a background thread
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                initialize(geneListsWindow, windowMain);
                return null;
            }

            @Override
            protected void done() {
                progressDialog.dispose();
            }
        };
        worker.execute();

        // Show the progress dialog while the initialization is in progress
        progressDialog.setVisible(true);
    }

    private void initialize(GeneListsWindow geneListsWindow, WindowMain windowMain) {
        this.service = new Service(windowMain.getPathToLastFile());
        setTitle("Statistics Of Types");
        setSize(800, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        this.geneListsWindow = geneListsWindow;
        this.windowMain = windowMain;
        DefaultListModel<String> model = this.geneListsWindow.getAddedGenesModel();
        ListCircleGeneDTO data = new ListCircleGeneDTO(model, this.service);
        PieChart chart = new PieChartBuilder().width(800).height(600).title("Types TE in Genes").build();
        this.mainToolBar = new JToolBar();
        this.export = new JButton("Export");
        splitPane = new JSplitPane();
        this.textArea = new JTextArea();
        this.textScrollPane = new JScrollPane(textArea);
        export.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                JDialog dialog = new JDialog();
                dialog.setTitle("Choose splitter");
                dialog.setLayout(new BorderLayout());

                JLabel label = new JLabel("Choose splitter");
                label.setHorizontalAlignment(SwingConstants.CENTER);
                dialog.add(label, BorderLayout.NORTH);

                JPanel buttonPanel = new JPanel(new FlowLayout());
                JButton tabButton = new JButton("TAB");
                JButton commaButton = new JButton(", ");
                JButton dotButton = new JButton(". ");
                JButton newlineButton = new JButton("New Line");

                tabButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                        StringBuilder sb = new StringBuilder();
                        List<String> list = MyBeanUtils.convertToList(geneListsWindow.getAddedGenesModel());
                        for (String item : list) {
                            sb.append(item);
                            sb.append("\t");
                        }
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Select location to save the file");
                        int userSelection = fileChooser.showSaveDialog(dialog);
                        if (userSelection == JFileChooser.APPROVE_OPTION) {
                            File fileToSave = fileChooser.getSelectedFile();
                            if (!fileToSave.getName().toLowerCase().endsWith(".txt")) {
                                fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".txt");
                            }

                            FileWorker.writeFile(fileToSave, sb.toString());
                        }
                    }
                });

                commaButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                        StringBuilder sb = new StringBuilder();
                        List<String> list = MyBeanUtils.convertToList(geneListsWindow.getAddedGenesModel());
                        for (String item : list) {
                            sb.append(item);
                            sb.append(", ");
                        }
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Select location to save the file");
                        int userSelection = fileChooser.showSaveDialog(dialog);
                        if (userSelection == JFileChooser.APPROVE_OPTION) {
                            File fileToSave = fileChooser.getSelectedFile();
                            if (!fileToSave.getName().toLowerCase().endsWith(".txt")) {
                                fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".txt");
                            }

                            FileWorker.writeFile(fileToSave, sb.toString());
                        }
                    }
                });

                dotButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                        StringBuilder sb = new StringBuilder();
                        List<String> list = MyBeanUtils.convertToList(geneListsWindow.getAddedGenesModel());
                        for (String item : list) {
                            sb.append(item);
                            sb.append(". ");
                        }
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Select location to save the file");
                        int userSelection = fileChooser.showSaveDialog(dialog);
                        if (userSelection == JFileChooser.APPROVE_OPTION) {
                            File fileToSave = fileChooser.getSelectedFile();
                            if (!fileToSave.getName().toLowerCase().endsWith(".txt")) {
                                fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".txt");
                            }

                            FileWorker.writeFile(fileToSave, sb.toString());
                        }
                    }
                });

                newlineButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                        StringBuilder sb = new StringBuilder();
                        List<String> list = MyBeanUtils.convertToList(geneListsWindow.getAddedGenesModel());
                        for (String item : list) {
                            sb.append(item);
                            sb.append("\n");
                        }
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Изберете място за запис на файла");

                        int userSelection = fileChooser.showSaveDialog(GeneListWindowDiagram.this);

                        if (userSelection == JFileChooser.APPROVE_OPTION) {
                            File fileToSave = fileChooser.getSelectedFile();
                            // Уверете се, че разширението на файла е .txt
                            if (!fileToSave.getName().toLowerCase().endsWith(".txt")) {
                                fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".txt");
                            }
                            FileWorker.writeFile(fileToSave, sb.toString());
                        }
                    }
                });

                buttonPanel.add(tabButton);
                buttonPanel.add(commaButton);
                buttonPanel.add(dotButton);
                buttonPanel.add(newlineButton);
                dialog.add(buttonPanel, BorderLayout.CENTER);

                dialog.setSize(300, 150);
                dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }
        });
        mainToolBar.add(export);
        add(mainToolBar, BorderLayout.NORTH);
        // Customize Chart
        chart.getStyler().setCircular(true);
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

        this.panel = new XChartPanel<>(chart);

        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setLeftComponent(panel);


        textScrollPane.setPreferredSize(new Dimension(300, 200));
        textScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        splitPane.setRightComponent(textScrollPane);
        Service service1 = new Service(windowMain.getPathToLastFile());
        List<String> genes = MyBeanUtils.convertToList(this.geneListsWindow.getAddedGenesModel());
        List<Gene> genesElements = new ArrayList<>();
        for (String gene : genes) {
            genesElements.add(service1.getRepository().findGene(gene));
        }
        StringBuilder sb = new StringBuilder();
        for (Gene gene : genesElements) {
            sb.append(gene.toString());
        }
        this.textArea.setText(sb.toString());
        // Ensure the JSplitPane divides the window equally
        this.splitPane.setResizeWeight(0.5);

        // Set the vertical scrollbar to the top
        SwingUtilities.invokeLater(() -> textScrollPane.getVerticalScrollBar().setValue(0));

        // Add transposon info
        add(splitPane, BorderLayout.CENTER);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
