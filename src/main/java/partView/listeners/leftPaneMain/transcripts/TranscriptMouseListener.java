package partView.listeners.leftPaneMain.transcripts;

import partBiology.Transcript;
import partView.mainWindowComponents.WindowMain;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//mouse listeners of transcripts

public class TranscriptMouseListener implements MouseListener{
    private WindowMain parent;

    public TranscriptMouseListener(WindowMain parent) {
        this.parent = parent;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1 && SwingUtilities.isLeftMouseButton(e)) {
            int index = parent.getLeftPartOfWindow().getElements().locationToIndex(e.getPoint());
            if (index >= 0) {
                String selectedTranscriptID = parent.getLeftPartOfWindow().getElements().getModel().getElementAt(index);
                Transcript transcript = parent.getService().getRepository().findTranscript(selectedTranscriptID);
                displayTranscriptDetails(transcript);
            }
        }
    }
    private void displayTranscriptDetails(Transcript transcript) {
        StringBuilder sb = new StringBuilder();
        sb.append("Transcript:\t").append(transcript.getID()).append("\n")
                .append("In Gene:\n").append(transcript.getGene().toString());
        parent.getRightUpPartOfWindow().getOutput().setText(sb.toString());
        parent.getRightUpPartOfWindow().getOutput().setCaretPosition(0);
        System.out.println(this.parent.getService().getRepository().findTransposonWithMostCopies().getName());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
