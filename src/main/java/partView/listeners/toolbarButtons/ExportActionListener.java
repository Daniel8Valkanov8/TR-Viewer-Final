package partView.listeners.toolbarButtons;

import partBiology.fileWorker.FileWorker;
import partView.mainWindowComponents.WindowMain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import java.io.File;

public class ExportActionListener implements ActionListener {
    private WindowMain parent; // Преполагаме, че имате JFrame, на който е добавен бутона exportButton
    public ExportActionListener(WindowMain parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (parent.getService() == null) {
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Изберете място за запис на файла");

        int userSelection = fileChooser.showSaveDialog(parent);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            // Уверете се, че разширението на файла е .txt
            if (!fileToSave.getName().toLowerCase().endsWith(".txt")) {
                fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".txt");
            }
            FileWorker.writeFile(fileToSave,this.parent.getService());
        }
    }
}
