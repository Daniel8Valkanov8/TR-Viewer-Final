package partView.windows.createLists;

import dto.util.MyBeanUtils;
import partBiology.fileWorker.FileWorker;
import partBiology.Transcript;
import partView.elements.SearchListener;
import partView.elements.SearchToolBarImpl;
import partView.mainWindowComponents.WindowMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.Set;

public class TranscriptListWindow extends JFrame implements SearchListener {

    private JPanel panel;
    private JList<String> transcriptsInFile;
    private JButton execute;
    private JToolBar mainToolBar;
    private JList<String> addedTranscripts;
    private DefaultListModel<String> transcriptsInFileModel;
    private DefaultListModel<String> addedTranscriptsModel;
    private JSplitPane splitPane;
    private WindowMain parent;


    public TranscriptListWindow(WindowMain parent) {
        this.parent = parent;
        setTitle("Create Transcript analysis");
        setSize(800, 600);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Инициализация на панела и списъците
        panel = new JPanel(new BorderLayout());
        addedTranscriptsModel = new DefaultListModel<>();
        mainToolBar = new JToolBar();
        execute = new JButton("Export Transcript List");
        execute.setVisible(false);
        mainToolBar.add(execute);
        add(mainToolBar, BorderLayout.NORTH);

        // Инициализация на списъка с гените от файла
        transcriptsInFileModel = new DefaultListModel<>();
        for (Transcript transcript : this.parent.getService().getRepository().getTranscriptsInFile()) {
            transcriptsInFileModel.addElement(transcript.getID());
        }
        this.transcriptsInFile = new JList<>(transcriptsInFileModel);
        this.addedTranscripts = new JList<>(addedTranscriptsModel);

        SearchToolBarImpl lowerToolBar = new SearchToolBarImpl(transcriptsInFile, this);
        // Добавяне на списъка с гените в панела за търсене
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JScrollPane(transcriptsInFile), BorderLayout.CENTER);
        listPanel.add(lowerToolBar, BorderLayout.SOUTH);

        // Инициализация на JSplitPane и настройка
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listPanel, new JScrollPane(addedTranscripts));
        splitPane.setResizeWeight(0.5);

        // Добавяне на JSplitPane към панела на прозореца
        panel.add(splitPane, BorderLayout.CENTER);
        add(panel);
        this.execute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TranscriptListWindow.this.dispose();
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
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                        StringBuilder sb = new StringBuilder();
                        List<String> list = MyBeanUtils.convertToList(addedTranscriptsModel);
                        for (String item : list) {
                            sb.append(item);
                            sb.append("\t");
                        }
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Изберете място за запис на файла");
                        int userSelection = fileChooser.showSaveDialog(TranscriptListWindow.this);
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
                        List<String> list = MyBeanUtils.convertToList(addedTranscriptsModel);
                        for (String item : list) {
                            sb.append(item);
                            sb.append(", ");
                        }
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Изберете място за запис на файла");
                        int userSelection = fileChooser.showSaveDialog(TranscriptListWindow.this);
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
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                        StringBuilder sb = new StringBuilder();
                        List<String> list = MyBeanUtils.convertToList(addedTranscriptsModel);
                        for (String item : list) {
                            sb.append(item);
                            sb.append(". ");
                        }
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Изберете място за запис на файла");
                        int userSelection = fileChooser.showSaveDialog(TranscriptListWindow.this);
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
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                        StringBuilder sb = new StringBuilder();
                        List<String> list = MyBeanUtils.convertToList(addedTranscriptsModel);
                        for (String item : list) {
                            sb.append(item);
                            sb.append("\n");
                        }
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Изберете място за запис на файла");
                        int userSelection = fileChooser.showSaveDialog(TranscriptListWindow.this);
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

        transcriptsInFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String selectedGene = transcriptsInFile.getSelectedValue(); // Получаване на избрания ген
                    addedTranscriptsModel.addElement(selectedGene); // Добавяне на гена към списъка с добавени гени
                    transcriptsInFileModel.removeElement(selectedGene); // Премахване на гена от списъка с гените от файла
                    updateExecuteButtonVisibility(); // Актуализиране на видимостта на бутона за изпълнение
                }
            }
        });

        addedTranscripts.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String selectedGene = addedTranscripts.getSelectedValue(); // Получаване на избрания ген
                    transcriptsInFileModel.addElement(selectedGene); // Добавяне на гена към списъка с гените от файла
                    addedTranscriptsModel.removeElement(selectedGene); // Премахване на гена от списъка с добавени гени
                    updateExecuteButtonVisibility(); // Актуализиране на видимостта на бутона за изпълнение
                }
            }
        });


        setVisible(true);
    }

    private void updateExecuteButtonVisibility() {
        if (transcriptsInFileModel.size() > 3) {
            execute.setVisible(true);
        } else {
            execute.setVisible(false);
        }
    }


    @Override
    public void onSearchUpdate(Set<String> searchResult) {
        // Актуализирайте визуализацията на списъка с гените
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String result : searchResult) {
            model.addElement(result);
        }
        transcriptsInFile.setModel(model);
    }

}
