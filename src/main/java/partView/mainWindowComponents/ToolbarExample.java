package partView.mainWindowComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ToolbarExample extends JFrame {
    public ToolbarExample() {
        initializeComponents();
    }

    private void initializeComponents() {
        setTitle("Toolbar Popup Menu Example");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JToolBar toolbar = new JToolBar();
        JButton plusButton = new JButton("+");

        toolbar.add(plusButton);
        add(toolbar, BorderLayout.NORTH);

        // Add a mouse listener to the "+" button
        plusButton.addMouseListener(new MouseAdapter() {
            private JPopupMenu popupMenu;

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
                }

                JButton sourceButton = (JButton) e.getSource();
                popupMenu.show(sourceButton, 0, sourceButton.getHeight());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ToolbarExample example = new ToolbarExample();
            example.setVisible(true);
        });
    }
}
