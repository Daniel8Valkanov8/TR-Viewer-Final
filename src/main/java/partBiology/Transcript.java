package partBiology;

import partBiology.abstracts.PrintPathManipulation;
import partBiology.math.Size;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Transcript extends PrintPathManipulation {
    private String ID;
    private long start;
    private long end;
    private Gene gene;
    private Size size;
    private long countCopiesOfTransposons = 0;
    private HashMap<Transposon, ArrayList<MiniTransposon>> transposons;
    private ArrayList<Transposon> allCopiesOfTransposons;

    private void setCoutCopiesOfTransposons() {

        for (Map.Entry<Transposon, ArrayList<MiniTransposon>> entry : this.getTransposons().entrySet()) {
            ArrayList<MiniTransposon> miniTransposons = entry.getValue();
            countCopiesOfTransposons += miniTransposons.size();

        }
    }
    public Transcript() {
        this.transposons = new HashMap<>();
        this.allCopiesOfTransposons = new ArrayList<>();
    }
    public boolean addTransposonCopy(Transposon transposon){
        return allCopiesOfTransposons.add(transposon);
    }

    public ArrayList<Transposon> getAllCopiesOfTransposons() {
        return allCopiesOfTransposons;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Gene getGene() {
        return gene;
    }

    public void setGene(Gene gene) {
        this.gene = gene;
    }

    public HashMap<Transposon, ArrayList<MiniTransposon>> getTransposons() {
        return transposons;
    }

    public void setTransposons(HashMap<Transposon, ArrayList<MiniTransposon>> transposons) {
        this.transposons = transposons;
    }

    public long getCountCopiesOfTransposons() {
        return countCopiesOfTransposons;
    }

    public boolean addTransposon(Transposon transposon, MiniTransposon miniTransposon){
    if (!isExistPath(transposon, miniTransposon)) {
        if (!transposons.containsKey(transposon)) {
            transposons.put(transposon, new ArrayList<>());
            transposon.addTranscript(this);
            this.setStartAndEnd();
        }
        this.setStartAndEnd();
        transposons.get(transposon).add(miniTransposon);
        transposon.addMiniTransposon(this, miniTransposon);
        return true;
    }
        this.setStartAndEnd();
        setCoutCopiesOfTransposons();
        return false;
    }

    public long getStart() {
        return start;
    }

    private void setStartAndEnd() {
        long smallestStart = Long.MAX_VALUE;
        long largestEnd = Long.MIN_VALUE;

        // Обхождане на всички транспозони и мини транспозони асоциирани с транскрипта
        for (Map.Entry<Transposon, ArrayList<MiniTransposon>> entry : this.getTransposons().entrySet()) {
            ArrayList<MiniTransposon> miniTransposons = entry.getValue();
            for (MiniTransposon miniTransposon : miniTransposons) {
                if (miniTransposon.getStart() < smallestStart) {
                    smallestStart = miniTransposon.getStart();
                }
                if (miniTransposon.getEnd() > largestEnd) {
                    largestEnd = miniTransposon.getEnd();
                }
            }
        }

        // Добавяне на най-малкия старт и най-големия край в списъка, ако има намерени стойности
        if (smallestStart != Long.MAX_VALUE && largestEnd != Long.MIN_VALUE) {
            this.size = new Size(smallestStart, largestEnd);
        }
    }



    public boolean isExistPath(Transposon transposon, MiniTransposon miniTransposon){

        if (this.transposons.containsKey(transposon)) {
            if (this.transposons.get(transposon).contains(miniTransposon)) {
                return true;
            }
            return false;
        }
        return false;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Transcript other = (Transcript) obj;
        return Objects.equals(ID, other.ID);
    }


    @Override
    public void print() {
        System.out.println("\t" + this.getID());
        this.getTransposons().forEach((transposon, miniTransposons) -> {
                transposon.print();
                miniTransposons.forEach(miniTransposon -> { miniTransposon.print();});
        });

    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\t" + this.getID());
        this.getTransposons().forEach((transposon, miniTransposons) -> {
            sb.append(transposon.toString());
            miniTransposons.forEach(miniTransposon -> { sb.append(miniTransposon.toString());});
        });
        return sb.toString();
    }
}