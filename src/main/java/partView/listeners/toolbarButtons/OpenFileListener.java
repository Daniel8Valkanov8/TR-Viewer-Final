package partView.listeners.toolbarButtons;

import partBiology.Gene;
import partView.dialogs.MyJLoadingBar;
import partView.mainWindowComponents.WindowMain;
import partBiology.service.Service;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;

public class OpenFileListener implements ActionListener {
    private WindowMain parentFrame;
    private JFileChooser fileChooser = new JFileChooser();
    private boolean isFileChoose = false;

    public OpenFileListener(WindowMain parentFrame) {
        this.parentFrame = parentFrame;
        fileChooser.setDialogTitle("Select a file");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Show loading bar
        MyJLoadingBar loadingBar = new MyJLoadingBar(this.parentFrame);
        loadingBar.pack();
        loadingBar.setLocationRelativeTo(parentFrame);
        loadingBar.setVisible(true);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                int result = fileChooser.showOpenDialog(parentFrame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    if (isFileChoose) {
                        parentFrame.zeroComponent();
                    }
                    Path selectedFile = fileChooser.getSelectedFile().toPath();
                    JOptionPane.showMessageDialog(parentFrame,
                            "Selected file: " + selectedFile.toAbsolutePath(),
                            "File Selection",
                            JOptionPane.INFORMATION_MESSAGE);
                    Service service = new Service(selectedFile);
                    parentFrame.setService(service);
                    parentFrame.setPathToLastFile(selectedFile);
                   // System.out.println(parentFrame.getPathToLastFile().toString());
                   // System.out.println(service);
                   // System.out.println("New Service: " + service);

                    DefaultListModel<String> listModel = new DefaultListModel<>();
                    for (Gene gene : parentFrame.getService().getRepository().getGenesInFile()) {
                        listModel.addElement(gene.getName());
                    }
                    parentFrame.getLeftPartOfWindow().getElements().setModel(listModel);
                   // System.out.println(parentFrame.getService());
                    parentFrame.getLeftPartOfWindow().setLastClicked("Genes");
                    parentFrame.getLeftPartOfWindow().updateMouseListeners();
                   // System.out.println(parentFrame.getLeftPartOfWindow().getLastClicked());
                    ViewStatisticActionListener viewStatisticActionListener = new ViewStatisticActionListener(parentFrame);
                    viewStatisticActionListener.actionPerformed(e);
                    isFileChoose = true;
                }
                return null;
            }

            @Override
            protected void done() {
                // Hide the loading bar when the task is done
                loadingBar.setVisible(false);
                loadingBar.dispose();
            }
        };

        worker.execute();
    }
}
