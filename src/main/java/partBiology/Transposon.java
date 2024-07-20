package partBiology;

import partBiology.abstracts.PrintPathManipulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Transposon extends PrintPathManipulation implements Comparable<Transposon> {
    private String name;
    private String type;
    private ArrayList<Transcript> allParents;
    private ArrayList<MiniTransposon> allChildren;
    private ArrayList<Gene> allParentsGene;
    private int numberOfCopiesInGene = 0;

    private HashMap<Transcript, ArrayList<MiniTransposon>> minitransposonsKey; // <transcript, <minitransposon>>


    public Transposon() {
        this.allParents = new ArrayList<>();
        this.allParentsGene = new ArrayList<>();
        this.minitransposonsKey = new HashMap<>();

        this.allChildren = new ArrayList<>();
        this.allChildren = new ArrayList<>();
    }
    //todo
    public List<MiniTransposon> getAllCopiesInGene(Gene gene) {
        return gene.allTransposons().get(this);
    }

    public ArrayList<MiniTransposon> getAllChildren() {
        return allChildren;
    }
    public boolean addChild(MiniTransposon miniTransposon){
        if (!this.allChildren.contains(miniTransposon)) {
            this.allChildren.add(miniTransposon);
            return true;
        }
        return false;
    }

    public void setMinitransposonsKey(HashMap<Transcript, ArrayList<MiniTransposon>> minitransposonsKey) {
        this.minitransposonsKey = minitransposonsKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Transcript> getAllParents() {
        return allParents;
    }

    public void setAllParents(ArrayList<Transcript> allParents) {
        this.allParents = allParents;
    }

    public ArrayList<Gene> getAllParentsGene() {
        return allParentsGene;
    }

    public void setAllParentsGene(ArrayList<Gene> allParentsGene) {
        this.allParentsGene = allParentsGene;
    }

    public boolean addTranscript(Transcript transcript){
        if (!this.allParents.contains(transcript)) {
            this.allParents.add(transcript);

            // Проверяваме дали генът на транскрипта вече е добавен към allParentsGene преди да го добавим
            addGene(transcript.getGene());

            return true;
        }
        return false;
    }


    private boolean addGene(Gene gene) {
        // Обхождаме списъка с всички родителски гени
        for (Gene existingGene : this.allParentsGene) {
            // Проверяваме дали текущият ген вече съществува в списъка
            if (existingGene.equals(gene)) {
                // Ако генът вече съществува, връщаме false и не добавяме гена
                return false;
            }
        }
        // Ако генът не е намерен в списъка, добавяме го
        this.allParentsGene.add(gene);
        // Връщаме true, тъй като успешно сме добавили гена
        return true;
    }
    public boolean addMiniTransposon(Transcript transcript, MiniTransposon miniTransposon){
        if (!minitransposonsKey.containsKey(transcript)) {
            minitransposonsKey.put(transcript, new ArrayList<>());
            transcript.addTransposon(this, miniTransposon);
            return true;
        }
        return false;
    }

    public HashMap<Transcript, ArrayList<MiniTransposon>> getMinitransposonsKey() {
        return minitransposonsKey;
    }



    @Override
    public String toString() {
        return String.format("\n\t\tName: %s, Type: %s\n", this.name, this.type);
    }

    @Override
    public void print() {
        this.numberOfCopiesInGene = this.allChildren.size();
        System.out.printf("\t\tName: %s, Type: %s", this.name, this.type);
        System.out.println();
    }

    @Override
    public int compareTo(Transposon other) {
        // Сравнете обектите по тип и брой на минитранспозоните
        int typeComparison = this.type.compareTo(other.getType());
        if (typeComparison != 0) {
            return typeComparison;
        }
        return Integer.compare(this.allChildren.size(), other.getAllChildren().size());
    }

    public void setAllChildren(ArrayList<MiniTransposon> allChildren) {
        this.allChildren = allChildren;
    }

    public int getNumberOfCopiesInGene() {
        return numberOfCopiesInGene;
    }

    public void setNumberOfCopiesInGene(int numberOfCopiesInGene) {
        this.numberOfCopiesInGene = numberOfCopiesInGene;
    }
}


