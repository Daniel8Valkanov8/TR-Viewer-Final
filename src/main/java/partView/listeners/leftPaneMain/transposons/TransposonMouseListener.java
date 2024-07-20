package partView.listeners.leftPaneMain.transposons;

import partBiology.Gene;
import partBiology.Transposon;
import partView.diagrams.TransposonDiagram;
import partView.mainWindowComponents.WindowMain;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;
//mouse liseners of transposons

public class TransposonMouseListener implements MouseListener{
    private WindowMain parent;

    public TransposonMouseListener(WindowMain parent) {
        this.parent = parent;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1 && SwingUtilities.isLeftMouseButton(e)) {
            int index = parent.getLeftPartOfWindow().getElements().locationToIndex(e.getPoint());
            if (index >= 0) {
                String selectedTransposonName = parent.getLeftPartOfWindow().getElements().getModel().getElementAt(index).split(" Type: ")[0];
                Transposon transposon = parent.getService().getRepository().findTransposon(selectedTransposonName);
                displayTransposonDetails(transposon);
            }
        }
        if (SwingUtilities.isRightMouseButton(e)) {
            JPopupMenu popupMenu = new JPopupMenu();
            JMenu filterSubMenu = new JMenu("Filter By");
            String[] types = {"LTR", "DNA", "SINE", "LINE", "OTHER"};
            for (String type : types) {
                JMenuItem item = new JMenuItem(type);
                item.addActionListener(e1 -> filterAndShowTransposons(type));
                filterSubMenu.add(item);
            }
            popupMenu.add(filterSubMenu);
            popupMenu.show(parent.getLeftPartOfWindow().getElements(), e.getX(), e.getY());
        }
    }
    private void filterAndShowTransposons(String filterType) {
        DefaultListModel<String> filteredModel = this.parent.getService().filterByType(filterType);
        parent.getLeftPartOfWindow().getElements().setModel(filteredModel);
    }
    private void displayTransposonDetails(Transposon transposon) {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\tName: ").append(transposon.getName()).append(", Type: ").append(transposon.getType()).append("\n");
        sb.append("Genes Contains Counter: ").append(transposon.getAllParentsGene().size()).append("\n");

        Set<String> set = transposon.getAllParentsGene().stream().map(Gene::getName).collect(java.util.stream.Collectors.toSet());
        for (String genesName : set) {
            sb.append(genesName).append("\t");
        }
        sb.append(System.lineSeparator());
        for (Gene gene : transposon.getAllParentsGene()) {
            sb.append(gene.toString()).append("\n");
        }
        parent.getRightUpPartOfWindow().getOutput().setText(sb.toString());
        parent.getRightUpPartOfWindow().getOutput().setCaretPosition(0);
        // Transposon diagram
        TransposonDiagram transposonDiagram = new TransposonDiagram(transposon);
        transposonDiagram.setVisible(true);
        parent.getHorizontal().setRightComponent(transposonDiagram);
        parent.getHorizontal().setDividerLocation(0.5);
    }
    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
