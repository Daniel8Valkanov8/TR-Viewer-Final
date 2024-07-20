package partView.mainWindowComponents;

import partView.dialogs.ErrorDialogFrame;
import partView.elements.ZeroComponent;
import partView.listeners.leftPaneMain.GeneticActionListener;
import partView.listeners.toolbarButtons.*;
import partView.windows.createLists.GeneListsWindow;
import partView.windows.createLists.TranscriptListWindow;
import partView.windows.createLists.TransposonListWindow;
import partBiology.service.Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.Path;

public class WindowMain extends JFrame implements ZeroComponent {
    private JButton openFile;
    private JButton viewTypesButton;
    private JButton exportButton;
    private JButton exportTextButton;
    private JButton createList;
    private JButton themeButton;
    private JSplitPane vertical;
    private JSplitPane horizontal;
    private LeftPartOfWindow leftPartOfWindow;
    private RightUpPartOfWindow rightUpPartOfWindow;
    private RightDownPartOfWindow rightDownPartOfWindow;
    private Service service;
    private Path pathToLastFile;

    public WindowMain() {
        initializeComponents();
    }

    public void initializeComponents() {
        this.openFile = new JButton("Open File");
        this.openFile.addActionListener(new OpenFileListener(this));
        this.viewTypesButton = new JButton("View Statistic Types");
        this.exportButton = new JButton("Export");
        this.exportTextButton = new JButton("Export Current");
        this.themeButton = new JButton("Change Light Mode");
        this.createList = new JButton("Create List");

        setTitle("TR-Viewer");
        setSize(1000, 600);
        setPreferredSize(new Dimension(1000, 600));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JToolBar mainToolBar = new JToolBar();
        this.add(mainToolBar, BorderLayout.NORTH);

        this.horizontal = new JSplitPane();
        this.horizontal.setPreferredSize(new Dimension(600, 400));
        this.horizontal.setOrientation(JSplitPane.VERTICAL_SPLIT);
        this.horizontal.setDividerLocation(400);
        this.horizontal.setRightComponent(new JPanel());


        this.vertical = new JSplitPane();
        this.vertical.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        this.vertical.setRightComponent(horizontal);
        this.add(vertical, BorderLayout.CENTER);

        mainToolBar.add(openFile);
        mainToolBar.add(viewTypesButton);
        mainToolBar.add(exportButton);
        //mainToolBar.add(exportTextButton);
        mainToolBar.add(themeButton);
        mainToolBar.add(createList);
        addCreateListListener(createList);
        addListenerLightMode(themeButton, mainToolBar);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.leftPartOfWindow = new LeftPartOfWindow(this);
        this.vertical.setLeftComponent(leftPartOfWindow);
        this.rightUpPartOfWindow = new RightUpPartOfWindow();
        this.rightDownPartOfWindow = new RightDownPartOfWindow();
        this.horizontal.setRightComponent(rightDownPartOfWindow);
        this.horizontal.setLeftComponent(rightUpPartOfWindow);

        ViewStatisticActionListener viewStatisticActionListener = new ViewStatisticActionListener(this);
        this.viewTypesButton.addActionListener(viewStatisticActionListener);
        ExportMouseListener exportMouseListener = new ExportMouseListener(this);
        this.exportButton.addMouseListener(exportMouseListener);
        ExportCurrentActionListener exportCurrentActionListener = new ExportCurrentActionListener(this);
        this.exportTextButton.addActionListener(exportCurrentActionListener);

        GeneticActionListener geneticActionListener = new GeneticActionListener(this);
        this.leftPartOfWindow.getGenes().addActionListener(geneticActionListener);
        this.leftPartOfWindow.getTranscripts().addActionListener(geneticActionListener);
        this.leftPartOfWindow.getTransposons().addActionListener(geneticActionListener);
    }


    private void addCreateListListener(JButton createList) {
        createList.addMouseListener(new MouseAdapter() {
            private JPopupMenu popupMenu;
            private boolean mouseInsideMenu = false;

            @Override
            public void mouseEntered(MouseEvent e) {
                if (popupMenu == null) {
                    popupMenu = new JPopupMenu();
                    JMenuItem geneItem = new JMenuItem("Genes");
                    JMenuItem transcriptItem = new JMenuItem("Transcripts");
                    JMenuItem transposonItem = new JMenuItem("Transposons");

                    popupMenu.add(geneItem);
                    popupMenu.add(transcriptItem);
                    popupMenu.add(transposonItem);

                    geneItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            GeneListsWindow geneListsWindow = new GeneListsWindow(WindowMain.this);
                        }
                    });
                    transcriptItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            TranscriptListWindow transcriptListWindow = new TranscriptListWindow(WindowMain.this);
                        }
                    });
                    transposonItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            TransposonListWindow transposonListWindow = new TransposonListWindow(WindowMain.this);
                        }
                    });

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
                        Point mousePos = createList.getMousePosition();
                        if (mousePos == null || !createList.getBounds().contains(mousePos)) {
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
        });
    }

    public JButton getOpenFile() {
        return openFile;
    }

    public void setOpenFile(JButton openFile) {
        this.openFile = openFile;
    }

    public JButton getViewTypesButton() {
        return viewTypesButton;
    }

    public void setViewTypesButton(JButton viewTypesButton) {
        this.viewTypesButton = viewTypesButton;
    }

    public JButton getExportButton() {
        return exportButton;
    }

    public void setExportButton(JButton exportButton) {
        this.exportButton = exportButton;
    }

    public JButton getExportTextButton() {
        return exportTextButton;
    }

    public void setExportTextButton(JButton exportTextButton) {
        this.exportTextButton = exportTextButton;
    }

    public JButton getCreateList() {
        return createList;
    }

    public void setCreateList(JButton createList) {
        this.createList = createList;
    }

    public JButton getThemeButton() {
        return themeButton;
    }

    public void setThemeButton(JButton themeButton) {
        this.themeButton = themeButton;
    }

    public Path getPathToLastFile() {
        return pathToLastFile;
    }

    public void setPathToLastFile(Path pathToLastFile) {
        this.pathToLastFile = pathToLastFile;
    }

    public JSplitPane getVertical() {
        return vertical;
    }

    public void setVertical(JSplitPane vertical) {
        this.vertical = vertical;
    }

    public JSplitPane getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(JSplitPane horizontal) {
        this.horizontal = horizontal;
    }

    private void addListenerLightMode(JButton themeButton, JToolBar mainToolBar) {
        themeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleTheme();
            }
        });
        mainToolBar.add(themeButton);
    }

    private void toggleTheme() {
        try {
            String currentLookAndFeel = UIManager.getLookAndFeel().getClass().getName();
            String lightTheme = "com.formdev.flatlaf.FlatLightLaf";
            String darkTheme = "com.formdev.flatlaf.FlatDarkLaf";

            String newLookAndFeel = currentLookAndFeel.equals(lightTheme) ? darkTheme : lightTheme;

            UIManager.setLookAndFeel(newLookAndFeel);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ex) {
            ErrorDialogFrame.showErrorDialog("Error", "SYSTEM ERROR");
            ex.printStackTrace();
        }
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Service getService() {
        return service;
    }

    public LeftPartOfWindow getLeftPartOfWindow() {
        return leftPartOfWindow;
    }

    public void setLeftPartOfWindow(LeftPartOfWindow leftPartOfWindow) {
        this.leftPartOfWindow = leftPartOfWindow;
    }

    public RightUpPartOfWindow getRightUpPartOfWindow() {
        return rightUpPartOfWindow;
    }

    public void setRightUpPartOfWindow(RightUpPartOfWindow rightUpPartOfWindow) {
        this.rightUpPartOfWindow = rightUpPartOfWindow;
    }

    public RightDownPartOfWindow getRightDownPartOfWindow() {
        return rightDownPartOfWindow;
    }

    public void setRightDownPartOfWindow(RightDownPartOfWindow rightDownPartOfWindow) {
        this.rightDownPartOfWindow = rightDownPartOfWindow;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WindowMain();
            }
        });
    }
    @Override
    public void zeroComponent() {
        this.service = null;
        this.leftPartOfWindow.zeroComponent();
        this.rightUpPartOfWindow.zeroComponent();
        this.horizontal.setRightComponent(new RightDownPartOfWindow());
    }
}
