package partView.dialogs;

import partBiology.Gene;
import partBiology.service.Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class GeneDialog extends JDialog {

    private JTextField geneNameField;
    private Service service;
    private JButton showTransposonsButton;

    public JButton getShowTransposonsButton() {
        return showTransposonsButton;
    }

    public GeneDialog(JFrame parentFrame, Gene gene) {
        super(parentFrame, "Gene Data", true);
        this.service = service;
        // Инициализация на компоненти
        JPanel panel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Панел за бутоните

        JLabel label = new JLabel("Gene name: " + gene.getName());
        geneNameField = new JTextField(gene.getName());
        JButton browseButton = new JButton("Browse");
        this.showTransposonsButton = new JButton("Show Transposons");

        // Добавяне на компонентите към панела
        panel.add(label, BorderLayout.NORTH);
        panel.add(geneNameField, BorderLayout.CENTER);

        // Добавяне на бутоните към панела за бутоните
        buttonPanel.add(browseButton);
        buttonPanel.add(showTransposonsButton);

        // Добавяне на панела за бутоните към основния панел
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Добавяне на панела към диалоговия прозорец
        add(panel);

        // Настройка на размера и позицията на прозореца
        setSize(300, 150);
        setLocationRelativeTo(parentFrame);
        // Добавяне на лисънър за бутона "Browse"
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String geneName = geneNameField.getText();
                if (!geneName.isEmpty()) {
                    String geneLink = "https://www.ncbi.nlm.nih.gov/gene/?term=Homo+sapiens+" + geneName;
                    try {
                        openWebPage(geneLink);
                    } catch (IOException | URISyntaxException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(GeneDialog.this, "Error opening web page", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(GeneDialog.this, "Please enter a gene name", "Error", JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            }

        });

    }

    // Метод за отваряне на уеб страница в браузъра
    private void openWebPage(String url) throws IOException, URISyntaxException {
        Desktop.getDesktop().browse(new URI(url));
    }


}

