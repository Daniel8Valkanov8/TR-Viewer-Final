package partView.mainWindowComponents;

import partView.elements.SearchListener;
import partView.elements.SearchToolBarMainImpl;
import partView.elements.ZeroComponent;
import partView.listeners.leftPaneMain.genes.GeneMouseListener;
import partView.listeners.leftPaneMain.transcripts.TranscriptMouseListener;
import partView.listeners.leftPaneMain.transposons.TransposonMouseListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


public class LeftPartOfWindow extends JPanel implements ZeroComponent {
    private WindowMain parent;
    private JToolBar upperToolBar;
    private JButton genes;
    private JButton transcripts;
    private JButton transposons;
    private JList<String> elements;
    private JScrollPane elementsScroll;
    private JToolBar downToolBar;
    private JLabel searchLabel;
    private JTextField searchField;
    private String lastClicked = "";
    public LeftPartOfWindow(WindowMain parent) {
        this.setLayout(new BorderLayout()); // Ensure BorderLayout is used for the panel

        this.upperToolBar = new JToolBar();
        this.genes = new JButton("Genes");
        this.transcripts = new JButton("Transcripts");
        this.transposons = new JButton("Transposons");

        this.elements = new JList<>();
        this.elementsScroll = new JScrollPane(elements); // Properly wrap the list in the scroll pane

        // Add buttons to the toolbar
        upperToolBar.add(genes);
        upperToolBar.add(transcripts);
        upperToolBar.add(transposons);


        // Initialize the search toolbar
        this.downToolBar = new JToolBar();
        this.searchLabel = new JLabel("Search: ");
        this.searchField = new JTextField(10);
        this.downToolBar.add(searchLabel);
        this.downToolBar.add(searchField);

        // Add components to the panel
        this.add(upperToolBar, BorderLayout.NORTH);
        this.add(elementsScroll, BorderLayout.CENTER);
        this.add(downToolBar, BorderLayout.SOUTH);
        this.parent = parent;
        updateMouseListeners();
        addSearchListenerLogic(this.searchField);
    }


    private void addSearchListenerLogic(JTextField searchField) {
        searchField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
               // System.out.println("insert");
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
                //System.out.println("remove");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }

            private void search() {
                String searchText = searchField.getText().toLowerCase();
                DefaultListModel<String> listModel = (DefaultListModel<String>) elements.getModel();
                List<String> searchResult;

                if (searchText.isEmpty()) {
                    searchResult = Collections.list(listModel.elements()); // Convert enumeration to list
                } else {
                    searchResult = Collections.list(listModel.elements()).stream()
                            .filter(item -> item.toLowerCase().contains(searchText))
                            .collect(Collectors.toList());
                }
                updateElementsList(new LinkedHashSet<>(searchResult));
            }

        });
    }

    private void updateElementsList(Set<String> searchResultSet) {
        DefaultListModel<String> sortedModel = new DefaultListModel<>();
        for (String item : searchResultSet) {
            sortedModel.addElement(item);
        }
        elements.setModel(sortedModel);
        updateMouseListeners();
    }
    public void updateMouseListeners() {
        switch (this.lastClicked){
            case "Genes":
                GeneMouseListener geneMouseListener = new GeneMouseListener(this.parent);
                this.elements.addMouseListener(geneMouseListener);
                break;
            case "Transcripts":
                TranscriptMouseListener transcriptMouseListener = new TranscriptMouseListener(this.parent);
                this.elements.addMouseListener(transcriptMouseListener);
                break;
            case "Transposons":
                TransposonMouseListener transposonMouseListener = new TransposonMouseListener(this.parent);
                this.elements.addMouseListener(transposonMouseListener);
                break;
        }

    }

    public JList<String> getElements() {
        return elements;
    }

    public void setElements(JList<String> elements) {
        this.elements = elements;
        this.elementsScroll.setViewportView(elements);

    }

    public String getLastClicked() {
        return lastClicked;
    }

    public void setLastClicked(String lastClicked) {
        this.lastClicked = lastClicked;
    }

    public JScrollPane getElementsScroll() {
        return elementsScroll;
    }

    public void setElementsScroll(JScrollPane elementsScroll) {
        this.elementsScroll = elementsScroll;
    }

    public void updateButtonsList(DefaultListModel<String> newModel) {

        this.elements.setModel(newModel);
        System.out.println("updating buttons list after"); // Актуализирайте модела на списъка
    }

    public JToolBar getUpperToolBar() {
        return upperToolBar;
    }

    public void setUpperToolBar(JToolBar upperToolBar) {
        this.upperToolBar = upperToolBar;
    }

    public JButton getGenes() {
        return genes;
    }

    public void setGenes(JButton genes) {
        this.genes = genes;
    }

    public JButton getTranscripts() {
        return transcripts;
    }

    public void setTranscripts(JButton transcripts) {
        this.transcripts = transcripts;
    }

    public JButton getTransposons() {
        return transposons;
    }

    public void setTransposons(JButton transposons) {
        this.transposons = transposons;
    }

    @Override
    public void zeroComponent() {
        this.elements.setModel(new DefaultListModel<>());
    }
}