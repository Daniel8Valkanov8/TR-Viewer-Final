package partView.listeners.leftPaneMain;

import partView.dialogs.ErrorDialogFrame;
import partView.mainWindowComponents.WindowMain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlusActionListener implements ActionListener {
    private WindowMain windowMain;
    public PlusActionListener(WindowMain windowMain) {
        this.windowMain = windowMain;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (windowMain.getService() == null) {
            ErrorDialogFrame.showErrorDialog("Error","Грешка при зареждането на данните от файла: ");
            return;
        }

    }
}
