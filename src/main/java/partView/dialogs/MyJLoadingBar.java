package partView.dialogs;

import partView.mainWindowComponents.WindowMain;

import javax.swing.*;

public class MyJLoadingBar extends JDialog {
    private JProgressBar progressBar;

    public MyJLoadingBar(WindowMain parent) {
        super(parent, "Loading...", false);
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setIndeterminate(true);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(progressBar);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
}
