package partView.listeners.listWindowListeners;

import partView.mainWindowComponents.WindowMain;
import partView.windows.createLists.GeneListsWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExecuteActionListener implements ActionListener {
    private GeneListsWindow parent;
    private WindowMain parentMain;

    public ExecuteActionListener(GeneListsWindow parent, WindowMain parentMain) {
        this.parent = parent;
        this.parentMain = parentMain;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       GeneListWindowDiagram diagram = new GeneListWindowDiagram(this.parent, this.parentMain);
    }
}
