package partView.windows.createLists;

import partBiology.fileWorker.FileWorker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

public class UploadCompareListListener implements ActionListener {
    private GeneListsWindow geneListsWindow;
    private JFileChooser jFileChooser;
    private ArrayList<String> genesToCompare;

    public UploadCompareListListener(GeneListsWindow geneListsWindow) {
        this.geneListsWindow = geneListsWindow;
        this.jFileChooser = new JFileChooser();
        this.genesToCompare = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int returnValue = jFileChooser.showOpenDialog(geneListsWindow);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            Path filePath = selectedFile.toPath();
            ArrayList<String> lines = FileWorker.readFile(filePath);
            for (String line : lines) {
                String[] gene = line.split("\t");
                String geneName = gene[0];
                genesToCompare.add(geneName);
            }
            if (genesToCompare != null) {
                for (int i = 0; i < genesToCompare.size(); i++) {
                    String[] splitLine = genesToCompare.get(i).split("\t");
                    // Процесирай splitLine по нужния начин
                    // Пример: Добавяне в някакъв списък или таблица
                    for (String gene : splitLine) {
                        System.out.println("Gene: " + gene);
                    }
                }
            }
        }
    }

}

