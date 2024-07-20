package partView.windows.createLists;
import partBiology.Gene;

import partView.elements.SearchListener;
import partView.elements.SearchToolBarImpl;
import partView.listeners.listWindowListeners.ExecuteActionListener;
import partView.mainWindowComponents.WindowMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

public class GeneListsWindow extends JFrame implements SearchListener {
    private JPanel panel;
    private JList<String> genesInFile;
    private JList<String> addedGenes;
    private JButton execute;
    private JToolBar mainToolBar;
    private DefaultListModel<String> genesInFileModel;
    private DefaultListModel<String> addedGenesModel;
    private JSplitPane splitPane;
    private WindowMain parent;
    private JButton upload;
    private MouseAdapter mouseAdapterGenesInFile;
    // reference to the main Window

    public GeneListsWindow(WindowMain parent) {

        setTitle("Create gene analysis");
        setSize(800, 600);

        this.parent = parent;
        // Инициализация на панела и списъците
        panel = new JPanel(new BorderLayout());
        addedGenesModel = new DefaultListModel<>();

        mainToolBar = new JToolBar();
        upload = new JButton("Upload List");
        execute = new JButton("Execute");
        execute.setVisible(false);
        mainToolBar.add(upload);
        mainToolBar.add(execute);
        add(mainToolBar, BorderLayout.NORTH);

        // Инициализация на списъка с гените от файла
        genesInFileModel = new DefaultListModel<>();
        for (Gene gene : this.parent.getService().getRepository().getGenesInFile()) {
            genesInFileModel.addElement(gene.getName());
        }
        genesInFile = new JList<>(genesInFileModel);
        addedGenes = new JList<>(addedGenesModel);

        SearchToolBarImpl lowerToolBar = new SearchToolBarImpl(genesInFile, this);
        // Добавяне на списъка с гените в панела за търсене
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JScrollPane(genesInFile), BorderLayout.CENTER);
        listPanel.add(lowerToolBar, BorderLayout.SOUTH);

        // Инициализация на JSplitPane и настройка
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listPanel, new JScrollPane(addedGenes));
        splitPane.setResizeWeight(0.5);

        // Добавяне на JSplitPane към панела на прозореца
        panel.add(splitPane, BorderLayout.CENTER);
        add(panel);

        //todo: listener with window
        ExecuteActionListener execute = new ExecuteActionListener(this, this.parent);
        this.execute.addActionListener(execute);
        upload.addActionListener(new UploadCompareListListener(this));


        this.mouseAdapterGenesInFile = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String selectedGene = genesInFile.getSelectedValue(); // Получаване на избрания ген
                    addedGenesModel.addElement(selectedGene); // Добавяне на гена към списъка с добавени гени
                    genesInFileModel.removeElement(selectedGene); // Премахване на гена от списъка с гените от файла
                    updateExecuteButtonVisibility(); // Актуализиране на видимостта на бутона за изпълнение
                }
            }
        };

        MouseAdapter mouseAdapterAddedGenes = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                String selectedGene = addedGenes.getSelectedValue(); // Получаване на избрания ген
                // Добавяне на гена към списъка с гените от файла
                addedGenesModel.removeElement(selectedGene);// Премахване на гена от списъка с добавени гени
                    genesInFileModel.addElement(selectedGene);
                    genesInFile.setModel(genesInFileModel);
                updateExecuteButtonVisibility(); // Актуализиране на видимостта на бутона за изпълнение
                }
            }
        };
        genesInFile.addMouseListener(mouseAdapterGenesInFile);
        addedGenes.addMouseListener(mouseAdapterAddedGenes);

        setVisible(true);

    }

    public JList<String> getGenesInFile() {
        return genesInFile;
    }

    public void setGenesInFile(JList<String> genesInFile) {
        this.genesInFile = genesInFile;
    }

    public JButton getExecute() {
        return execute;
    }

    public void setExecute(JButton execute) {
        this.execute = execute;
    }

    public JToolBar getMainToolBar() {
        return mainToolBar;
    }

    public void setMainToolBar(JToolBar mainToolBar) {
        this.mainToolBar = mainToolBar;
    }

    public JList<String> getAddedGenes() {
        return addedGenes;
    }

    public void setAddedGenes(JList<String> addedGenes) {
        this.addedGenes = addedGenes;
    }

    public DefaultListModel<String> getGenesInFileModel() {
        return genesInFileModel;
    }

    public void setGenesInFileModel(DefaultListModel<String> genesInFileModel) {
        this.genesInFileModel = genesInFileModel;
    }

    public void setAddedGenesModel(DefaultListModel<String> addedGenesModel) {
        this.addedGenesModel = addedGenesModel;
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }

    public void setSplitPane(JSplitPane splitPane) {
        this.splitPane = splitPane;
    }

    public JButton getUpload() {
        return upload;
    }

    public void setUpload(JButton upload) {
        this.upload = upload;
    }

    private void updateExecuteButtonVisibility() {
        if (genesInFileModel.size() > 3) {
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
        genesInFile.setModel(model);

    }

    public DefaultListModel<String> getAddedGenesModel() {
        return addedGenesModel;
    }

    @Override
    public WindowMain getParent() {
        return parent;
    }

    public void setParent(WindowMain parent) {
        this.parent = parent;
    }
}


