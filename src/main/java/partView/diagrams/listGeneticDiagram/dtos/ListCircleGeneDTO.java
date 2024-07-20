package partView.diagrams.listGeneticDiagram.dtos;

import partBiology.service.Service;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class ListCircleGeneDTO implements Comparable<ListCircleGeneDTO> {

    private int DNA;
    private int OTHER;
    private int LTR;
    private int SINE;
    private int LINE;
    private HashMap<String, Integer> specificTransposon = new HashMap<>();

    public ListCircleGeneDTO(DefaultListModel<String> listModel, Service service) {

        this.DNA = 0;
        this.OTHER = 0;
        this.LTR = 0;
        this.SINE = 0;
        this.LINE = 0;

        createData(service.trTypeCount(listModel));
    }
    private void createData(Map<String, Integer> transposons) {
        for (Map.Entry<String, Integer> entry : transposons.entrySet()) {
            if (entry.getKey().equals("DNA")) {
                DNA = entry.getValue();
            } else if (entry.getKey().equals("OTHER/UNKNOWN")) {
                OTHER = entry.getValue();
            } else if (entry.getKey().equals("LTR")) {
                LTR = entry.getValue();
            } else if (entry.getKey().equals("SINE")) {
                SINE = entry.getValue();
            } else if (entry.getKey().equals("LINE")) {
                LINE = entry.getValue();
            } else {
                specificTransposon.put(entry.getKey(), entry.getValue());
            }
        }

    }

    public int getDNA() {
        return DNA;
    }

    public int getOTHER() {
        return OTHER;
    }

    public int getLTR() {
        return LTR;
    }

    public int getSINE() {
        return SINE;
    }

    public int getLINE() {
        return LINE;
    }

    @Override
    public int compareTo(ListCircleGeneDTO other) {
        // Сравнете интовете вътре в обектите и върнете резултата
        int thisSum = this.DNA + this.OTHER + this.LTR + this.SINE + this.LINE;
        int otherSum = other.DNA + other.OTHER + other.LTR + other.SINE + other.LINE;
        return Integer.compare(thisSum, otherSum);
    }
}
