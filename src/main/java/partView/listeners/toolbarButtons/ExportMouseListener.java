package partView.listeners.toolbarButtons;

import dto.util.MyBeanUtils;
import partBiology.fileWorker.FileWorker;
import partView.mainWindowComponents.WindowMain;
import partView.windows.createLists.GeneListsWindow;
import partView.windows.createLists.TranscriptListWindow;
import partView.windows.createLists.TransposonListWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;



public class ExportMouseListener extends MouseAdapter {
    private WindowMain parent;
    private JPopupMenu popupMenu;
    private boolean mouseInsideMenu = false;

    public ExportMouseListener(WindowMain parent) {
        this.parent = parent;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (popupMenu == null) {
            popupMenu = new JPopupMenu();
            JMenuItem current = new JMenuItem("Current");
            JMenuItem allGenesList = new JMenuItem("Export Genes List");
            JMenuItem exportAllPaths = new JMenuItem("Export All Paths");

            popupMenu.add(current);
            popupMenu.add(allGenesList);
            popupMenu.add(exportAllPaths);

            current.addActionListener(new ExportCurrentActionListener(parent));
            allGenesList.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JDialog dialog = new JDialog();
                        dialog.setTitle("Choose spliter");
                        dialog.setLayout(new BorderLayout());
                        JLabel label = new JLabel("Choose spliter");
                        label.setHorizontalAlignment(SwingConstants.CENTER);
                        dialog.add(label, BorderLayout.NORTH);

                        JPanel buttonPanel = new JPanel(new FlowLayout());
                        JButton tabButton = new JButton("TAB");
                        JButton commaButton = new JButton(", ");
                        JButton dotButton = new JButton(". ");
                        JButton newlineButton = new JButton("New Line");

                        tabButton.addActionListener(new ActionListener() {
                            private StringBuilder sb = new StringBuilder();
                            private java.util.List<String> list = parent.getService().getRepository().getGenesInFile()
                                    .stream().map(gene -> gene.getName()).collect(java.util.stream.Collectors.toList());
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                dialog.dispose();
                                for (String item : list) {
                                    sb.append(item);
                                    sb.append("\t");
                                }
                                JFileChooser fileChooser = new JFileChooser();
                                fileChooser.setDialogTitle("Изберете място за запис на файла");
                                int userSelection = fileChooser.showSaveDialog(parent);
                                if (userSelection == JFileChooser.APPROVE_OPTION) {
                                    File fileToSave = fileChooser.getSelectedFile();
                                    // Уверете се, че разширението на файла е .txt
                                    if (!fileToSave.getName().toLowerCase().endsWith(".txt")) {
                                        fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".txt");
                                    }

                                    FileWorker.writeFile(fileToSave,sb.toString());
                                }
                            }
                        });

                        commaButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                dialog.dispose();
                                StringBuilder sb = new StringBuilder();
                                java.util.List<String> list = parent.getService().getRepository().getGenesInFile()
                                        .stream().map(gene -> gene.getName()).collect(java.util.stream.Collectors.toList());
                                for (String item : list) {
                                    sb.append(item);
                                    sb.append(", ");
                                }
                                JFileChooser fileChooser = new JFileChooser();
                                fileChooser.setDialogTitle("Изберете място за запис на файла");
                                int userSelection = fileChooser.showSaveDialog(parent);
                                if (userSelection == JFileChooser.APPROVE_OPTION) {
                                    File fileToSave = fileChooser.getSelectedFile();
                                    // Уверете се, че разширението на файла е .txt
                                    if (!fileToSave.getName().toLowerCase().endsWith(".txt")) {
                                        fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".txt");
                                    }

                                    FileWorker.writeFile(fileToSave,sb.toString());
                                }
                            }
                        });

                        dotButton.addActionListener(new ActionListener() {
                            private StringBuilder sb = new StringBuilder();
                            private java.util.List<String> list = parent.getService().getRepository().getGenesInFile()
                                    .stream().map(gene -> gene.getName()).collect(java.util.stream.Collectors.toList());

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                dialog.dispose();
                                for (String item : list) {
                                    sb.append(item);
                                    sb.append(". ");
                                }
                                JFileChooser fileChooser = new JFileChooser();
                                fileChooser.setDialogTitle("Изберете място за запис на файла");
                                int userSelection = fileChooser.showSaveDialog(parent);
                                if (userSelection == JFileChooser.APPROVE_OPTION) {
                                    File fileToSave = fileChooser.getSelectedFile();
                                    // Уверете се, че разширението на файла е .txt
                                    if (!fileToSave.getName().toLowerCase().endsWith(".txt")) {
                                        fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".txt");
                                    }

                                    FileWorker.writeFile(fileToSave,sb.toString());
                                }
                            }
                        });

                        newlineButton.addActionListener(new ActionListener() {
                            private StringBuilder sb = new StringBuilder();
                            private java.util.List<String> list = parent.getService().getRepository().getGenesInFile()
                                    .stream().map(gene -> gene.getName()).collect(java.util.stream.Collectors.toList());
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                dialog.dispose();
                                for (String item : list) {
                                    sb.append(item);
                                    sb.append("\n");
                                }
                                JFileChooser fileChooser = new JFileChooser();
                                fileChooser.setDialogTitle("Изберете място за запис на файла");
                                int userSelection = fileChooser.showSaveDialog(parent);
                                if (userSelection == JFileChooser.APPROVE_OPTION) {
                                    File fileToSave = fileChooser.getSelectedFile();
                                    // Уверете се, че разширението на файла е .txt
                                    if (!fileToSave.getName().toLowerCase().endsWith(".txt")) {
                                        fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".txt");
                                    }

                                    FileWorker.writeFile(fileToSave,sb.toString());
                                }
                            }
                        });

                        buttonPanel.add(tabButton);
                        buttonPanel.add(commaButton);
                        buttonPanel.add(dotButton);
                        buttonPanel.add(newlineButton);
                        dialog.add(buttonPanel, BorderLayout.CENTER);

                        dialog.setSize(300, 150); // Set the size as needed
                        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        dialog.setVisible(true);
                    }


            });
            exportAllPaths.addActionListener(new ExportActionListener(parent));

            popupMenu.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    mouseInsideMenu = true;
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    mouseInsideMenu = false;
                }
            });

            popupMenu.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    mouseInsideMenu = true;
                }
            });
        }

        JButton sourceButton = (JButton) e.getSource();
        popupMenu.show(sourceButton, 0, sourceButton.getHeight());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Point mousePos = parent.getExportButton().getMousePosition();
                if (mousePos == null || !parent.getExportButton().getBounds().contains(mousePos)) {
                    mousePos = MouseInfo.getPointerInfo().getLocation();
                    SwingUtilities.convertPointFromScreen(mousePos, popupMenu);
                    if (!popupMenu.getBounds().contains(mousePos)) {
                        popupMenu.setVisible(false);
                    }
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}
