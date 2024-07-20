package partView.listeners.toolbarButtons;

import partBiology.fileWorker.FileWorker;
import partView.mainWindowComponents.WindowMain;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ExportCurrentActionListener implements ActionListener {
    private WindowMain parent;

    public ExportCurrentActionListener(WindowMain parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Получаваме текстовото поле
        JTextArea forExport = parent.getRightUpPartOfWindow().getOutput();

        // Проверка дали текстовото поле е празно
        if (forExport.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Текстовото поле е празно. Моля, въведете текст за запазване.", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Отваряме диалог за избор на файл
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Изберете място за запазване на текстовия файл");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            // Проверка дали файлът има разширение .txt, ако няма - добавяме го
            if (!fileToSave.getAbsolutePath().endsWith(".txt")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".txt");
            }

            // Записваме файла
            FileWorker.writeFile(fileToSave, forExport.getText());
        }
    }
}
