package partView.listeners.leftPaneMain;

import partBiology.Gene;
import partBiology.Transcript;
import partBiology.Transposon;
import partView.dialogs.ErrorDialogFrame;
import partView.mainWindowComponents.LeftPartOfWindow;
import partView.mainWindowComponents.WindowMain;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GeneticActionListener extends MouseAdapter implements ActionListener {
    private WindowMain parent;
    private JList<String> elements;


    public GeneticActionListener(WindowMain parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (parent.getService() == null) {
            ErrorDialogFrame.showErrorDialog("No File", "No chosen file yet!");
            return;
        }
        DefaultListModel<String> listModel = new DefaultListModel<>();
        this.elements = new JList<>(listModel);//todo сортираните гени
        LeftPartOfWindow leftPartOfWindow = this.parent.getLeftPartOfWindow();
        leftPartOfWindow.getElementsScroll().setViewportView(leftPartOfWindow.getElements());
        JButton button = (JButton) e.getSource();

        switch (button.getText()) {
            case "Genes":
                if (this.parent.getLeftPartOfWindow().getElements().getModel() != null) {
                    this.parent.getLeftPartOfWindow().setElements(new JList<>());
                }
                for (Gene gene : parent.getService().getRepository().getGenesInFile()) {
                    listModel.addElement(gene.getName());
                }
                leftPartOfWindow.getElements().setModel(listModel);
                System.out.println(parent.getService());
                this.parent.getLeftPartOfWindow().setLastClicked("Genes");
                this.parent.getLeftPartOfWindow().updateMouseListeners();
                System.out.println(this.parent.getLeftPartOfWindow().getLastClicked());
                break;
            case "Transcripts":
                for (Transcript transcript : parent.getService().getRepository().getTranscriptsInFile()) {
                    listModel.addElement(transcript.getID());
                }if (this.parent.getLeftPartOfWindow().getElements().getModel() != null) {
                this.parent.getLeftPartOfWindow().setElements(new JList<>());
            }
                leftPartOfWindow.getElements().setModel(listModel);
                System.out.println(parent.getService());
                this.parent.getLeftPartOfWindow().setLastClicked("Transcripts");
                this.parent.getLeftPartOfWindow().updateMouseListeners();
                System.out.println(this.parent.getLeftPartOfWindow().getLastClicked());
                break;
            case "Transposons":
                for (Transposon transposon : parent.getService().getRepository().getTransposonsInputGenes()) {
                    String element = transposon.getName() + " Type: " + transposon.getType();
                    listModel.addElement(element);
                }
                if (this.parent.getLeftPartOfWindow().getElements().getModel() != null) {
                this.parent.getLeftPartOfWindow().setElements(new JList<>());
            }
                this.parent.getLeftPartOfWindow().setElements(elements);
                System.out.println(parent.getService());
                this.parent.getLeftPartOfWindow().setLastClicked("Transposons");
                this.parent.getLeftPartOfWindow().updateMouseListeners();
                System.out.println(this.parent.getLeftPartOfWindow().getLastClicked());
                break;
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            JList<String> list = (JList<String>) e.getSource();
            int index = list.locationToIndex(e.getPoint());
            if (index >= 0) {
                String selectedValue = list.getModel().getElementAt(index);
                // Вашата логика за десен бутон тук
                System.out.println("Right-clicked on: " + selectedValue);
                // Можете да добавите контекстно меню или друга логика тук
            }
        }
    }

    public void attachListeners(JButton genesButton, JButton transcriptsButton, JButton transposonsButton, JList<String> list) {
        genesButton.addActionListener(this);
        transcriptsButton.addActionListener(this);
        transposonsButton.addActionListener(this);
        list.addMouseListener(this);
    }
}
//todo логика за десен бутон