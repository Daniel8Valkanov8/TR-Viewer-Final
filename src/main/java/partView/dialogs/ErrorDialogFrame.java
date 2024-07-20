package partView.dialogs;

import javax.swing.*;

public class ErrorDialogFrame extends JFrame {
    public static void showErrorDialog(String title,String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage, title, JOptionPane.ERROR_MESSAGE);
    }
}