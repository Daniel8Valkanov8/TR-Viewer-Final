package partView.windows.createLists;

import partView.elements.SearchListener;
import partView.elements.SearchToolBarImpl;
import partView.mainWindowComponents.WindowMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

public class TransposonListWindow extends JFrame implements SearchListener {
    private JPanel panel;
    private JList<String> transposonsInFile;
    private JButton execute;
    private JToolBar mainToolBar;
    private JList<String> addedTranscposons;
    private DefaultListModel<String> transposonsInFileModel;
    private DefaultListModel<String> addedTransposonsModel;
    private JSplitPane splitPane;
    private WindowMain parent;
    public TransposonListWindow(WindowMain parent) {
        this.parent = parent;
        setTitle("Create Transcsposon combination analysis");
        setSize(800, 600);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Инициализация на панела и списъците
        this.panel = new JPanel(new BorderLayout());
        this.addedTransposonsModel = new DefaultListModel<>();
        this.mainToolBar = new JToolBar();
        this.execute = new JButton("Execute");
        this.execute.setVisible(false);
        this.mainToolBar.add(execute);
        add(mainToolBar, BorderLayout.NORTH);

        // Инициализация на списъка с гените от файла
        transposonsInFileModel = new DefaultListModel<>();
        transposonsInFileModel.addElement("DNA");
        transposonsInFileModel.addElement("LTR");
        transposonsInFileModel.addElement("LINE");
        transposonsInFileModel.addElement("SINE");
        transposonsInFileModel.addElement("OTHER/UNKNOWN");
        this.transposonsInFile = new JList<>(transposonsInFileModel);
        this.addedTranscposons = new JList<>(addedTransposonsModel);

        SearchToolBarImpl lowerToolBar = new SearchToolBarImpl(transposonsInFile, this);
        // Добавяне на списъка с гените в панела за търсене
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JScrollPane(transposonsInFile), BorderLayout.CENTER);
        listPanel.add(lowerToolBar, BorderLayout.SOUTH);

        // Инициализация на JSplitPane и настройка
        this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listPanel, new JScrollPane(addedTranscposons));
        this.splitPane.setResizeWeight(0.5);

        // Добавяне на JSplitPane към панела на прозореца
        panel.add(splitPane, BorderLayout.CENTER);
        add(panel);
        this.execute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransposonListWindow.this.dispose();
                JFrame frame = new JFrame("Transposon Combinations Result");
                frame.setPreferredSize(new Dimension(200, 600));
                JList<String> result = new JList<>(parent.getService().getRepository().findGenesWithTransposons(addedTransposonsModel));
                frame.add(new JScrollPane(result));
                frame.pack(); // Adjust the frame size to fit its components
                frame.setVisible(true);
            }

        });



        transposonsInFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String selectedGene = transposonsInFile.getSelectedValue(); // Получаване на избрания ген
                    addedTransposonsModel.addElement(selectedGene); // Добавяне на гена към списъка с добавени гени

                    updateExecuteButtonVisibility(); // Актуализиране на видимостта на бутона за изпълнение
                }
            }
        });

        addedTranscposons.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String selectedGene = addedTranscposons.getSelectedValue(); // Получаване на избрания ген

                    addedTransposonsModel.removeElement(selectedGene); // Премахване на гена от списъка с добавени гени
                    updateExecuteButtonVisibility(); // Актуализиране на видимостта на бутона за изпълнение
                }
            }
        });


        setVisible(true);
    }

    private void updateExecuteButtonVisibility() {
        if (transposonsInFileModel.size() > 3) {
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
        transposonsInFile.setModel(model);
    }
}
