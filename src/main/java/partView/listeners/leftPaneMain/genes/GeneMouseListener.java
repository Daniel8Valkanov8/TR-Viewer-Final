package partView.listeners.leftPaneMain.genes;

import partBiology.Gene;
import partView.diagrams.GeneDiagram;
import partView.mainWindowComponents.WindowMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//muse liseners of genes
public class GeneMouseListener implements MouseListener{
    private WindowMain parent;
    public GeneMouseListener(WindowMain parent) {
        this.parent = parent;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e))
        {
            this.handleLeftClick(e);
            //System.out.println("Left click");
        }
        if (SwingUtilities.isRightMouseButton(e))
        {
            this.handleRightClick(e);
        }

    }

    private void handleRightClick(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            JPopupMenu popupMenu = createPopupMenu();
            popupMenu.show(this.parent.getLeftPartOfWindow().getElements(), e.getX(), e.getY());
        }
    }
    private JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem ltrItem = new JMenuItem("Number of copies");
        ltrItem.addActionListener(this::showFilterDialog);
        JMenu filterSubMenu = new JMenu("Filter by");
        filterSubMenu.add(ltrItem);
        popupMenu.add(filterSubMenu);
        return popupMenu;
    }
    private void showFilterDialog(ActionEvent e) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Choose count of copies");
        JTextField textField = new JTextField(10);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textField, BorderLayout.CENTER);
        dialog.add(panel);
        dialog.setPreferredSize(new Dimension(300, 200));
        dialog.pack();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    DefaultListModel<String> filteredGenes = parent.getService().filterGenesByCountCopyes(Integer.parseInt(textField.getText()));
                    parent.getLeftPartOfWindow().getElements().setModel(filteredGenes);
                    dialog.dispose();
                }
            }
        });
    }
    private void handleLeftClick(MouseEvent e) {
        String selectedGene = null;
            int index = parent.getLeftPartOfWindow().getElements().locationToIndex(e.getPoint());
            if (index >= 0) {
                selectedGene= parent.getLeftPartOfWindow().getElements().getModel().getElementAt(index);
        }
        Gene gene = parent.getService().getRepository().findGene(selectedGene);
        System.out.println(gene.getName());
        this.parent.getRightUpPartOfWindow()
                .getOutput()
                .setText(gene.toString());
        this.parent.getHorizontal().setRightComponent(new GeneDiagram(gene));
        this.parent.getHorizontal().setDividerLocation(0.5);
        parent.getRightUpPartOfWindow().getOutput().setCaretPosition(0);
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
