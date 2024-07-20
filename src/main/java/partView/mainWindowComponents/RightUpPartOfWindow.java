package partView.mainWindowComponents;

import partView.elements.ZeroComponent;

import javax.swing.*;
import java.awt.*;

public class RightUpPartOfWindow extends JPanel implements ZeroComponent {
    private JTextArea output;

    public RightUpPartOfWindow() {
        this.setLayout(new BorderLayout());
        this.output = new JTextArea();
        this.add(new JScrollPane(output), BorderLayout.CENTER);  // Add JScrollPane for scrollable text area
        this.setVisible(true);
    }

    private boolean writeOutput(String text) {
        if (this.output.getText().isEmpty()) {
            return false;
        }
        this.output.setText(text);
        return true;
    }

    public JTextArea getOutput() {
        return output;
    }

    public void setOutput(JTextArea output) {
        this.output = output;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 400);  // Default size
    }

    public void zeroComponent() {
        this.output.setText("");
    }
}

