package partBiology;

import partBiology.abstracts.PrintPathManipulation;
import partBiology.math.Size;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Gene extends PrintPathManipulation implements Comparable<Gene> {
    private ArrayList<String[]> allGeneRoutes;
    private String name;
    private Size size;
    private ArrayList<Transcript> transcripts;
    private int numberOfCopies = 0;


    public Gene() {
        this.transcripts = new ArrayList<>();
        this.allGeneRoutes = new ArrayList<>();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Transcript> getTranscripts() {
        return transcripts;
    }



    public void setSize() {
        long smallestStart = Long.MAX_VALUE;
        long largestEnd = Long.MIN_VALUE;

        for (Transcript transcript : this.getTranscripts()) {
            for (Map.Entry<Transposon, ArrayList<MiniTransposon>> entry : transcript.getTransposons().entrySet()) {
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
        }

        if (smallestStart != Long.MAX_VALUE && largestEnd != Long.MIN_VALUE) {
            this.size = new Size(smallestStart, largestEnd);
        }
    }

    public boolean addTranscript(Transcript transcript){
        if (!isExistTranscript(transcript)){
        transcript.setGene(this);
        setSize();
        return this.transcripts.add(transcript);
        }
        this.setSize();
        return false;
    }
    public List<Transposon> getTransposons(){
        List<Transposon> transposons = new ArrayList<>();
        for (Transcript transcript : this.getTranscripts()) {
            for (Map.Entry<Transposon, ArrayList<MiniTransposon>> entry : transcript.getTransposons().entrySet()) {
                transposons.add(entry.getKey());
            }
        }
        return transposons;
    }


    public ArrayList<String[]> getAllGeneRoutes() {
        return allGeneRoutes;
    }

    public boolean addRoute(String[] route){
        return this.allGeneRoutes.add(route);
    }

    public boolean isExistTranscript(Transcript transcript){
        return this.transcripts.contains(transcript);
    }
    public HashMap<Transposon,ArrayList<MiniTransposon>> allTransposons(){
        HashMap<Transposon,ArrayList<MiniTransposon>> allTransposons = new HashMap<>();
        for (Transcript transcript : transcripts) {
            for (Map.Entry<Transposon, ArrayList<MiniTransposon>> entry : transcript.getTransposons().entrySet()) {
                ArrayList<MiniTransposon> miniTransposons = entry.getValue();
                for (MiniTransposon miniTransposon : miniTransposons) {
                    if (!allTransposons.containsKey(entry.getKey())) {
                        allTransposons.put(entry.getKey(), new ArrayList<>());
                    }
                    allTransposons.get(entry.getKey()).add(miniTransposon);
                }
            }
        }
        return allTransposons;
    }

    public void print(){
        this.setSize();
        System.out.println(this.getName());
        for (Transcript transcript : this.getTranscripts()) {
            transcript.print();
        }
    }

    @Override
    public String toString() {
        List<MiniTransposon> miniTransposons = new ArrayList<>();
        for (Transcript transcript : this.getTranscripts()) {
            for (Map.Entry<Transposon, ArrayList<MiniTransposon>> entry : transcript.getTransposons().entrySet()) {
                entry.getValue().forEach(miniTransposons::add);
            }
        }
        this.numberOfCopies = miniTransposons.size();
        StringBuilder sb = new StringBuilder();
        sb.append(this.getName() + ":   " + miniTransposons.size());
        for (Transcript transcript : this.getTranscripts()) {
            sb.append(transcript.toString());
            sb.append("\n");
        }
        return sb.toString();
    }


    @Override
    public int compareTo(Gene otherGene) {
        return this.name.compareTo(otherGene.getName());
    }
}
