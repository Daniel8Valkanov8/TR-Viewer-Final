package dto.type;

import partBiology.Transposon;
import partBiology.service.Service;

import java.util.ArrayList;
import java.util.HashMap;

public class CircleDiagramDTO {
    private Service service;
    private int DNA;
    private int OTHER;
    private int LTR;
    private int SINE;
    private int LINE;
    private final HashMap<String, Integer> specificTransposon = new HashMap<>();

    public CircleDiagramDTO(Service service) {
        System.out.println(this.service);

        this.DNA = 0;
        this.OTHER = 0;
        this.LTR = 0;
        this.SINE = 0;
        this.LINE = 0;
        this.service = service;
        createData(service.getRepository().getTransposonsInputGenes());
    }
    private void createData(ArrayList<Transposon> transposons) {

        for (Transposon transposon : transposons) {
            boolean isChildrenEmpty = transposon.getAllChildren().size()!=0;
            int childrenSize = transposon.getAllChildren().size();
            if (transposon.getType().equals("DNA")&& isChildrenEmpty) {
                ;
                this.DNA = DNA + childrenSize;
            } else if (transposon.getType().equals("OTHER/UNKNOWN")&& isChildrenEmpty) {
                this.OTHER = OTHER + childrenSize;
            } else if (transposon.getType().equals("LTR") && isChildrenEmpty) {
                this.LTR = LTR + childrenSize;
            } else if (transposon.getType().equals("SINE") && isChildrenEmpty) {
                this.SINE = SINE + childrenSize;
            } else if (transposon.getType().equals("LINE") && isChildrenEmpty) {
                this.LINE = LINE + childrenSize;
            }else {
                this.OTHER = OTHER + childrenSize;
                specificTransposon.put(transposon.getType(), specificTransposon.getOrDefault(transposon.getType(), 0) + 1);
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

    public Service getService() {
        return service;
    }


}
