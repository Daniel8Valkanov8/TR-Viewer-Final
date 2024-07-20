package partBiology;

import partBiology.abstracts.PrintPathManipulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MiniTransposon extends PrintPathManipulation implements Comparable<MiniTransposon> {

    private String chromosome;
    private long start;
    private long end;
    private char chain;
    private long size;

    public long getSize() {
        return size;
    }

    private ArrayList<Transcript>transcripts;
    private HashMap<Transposon,ArrayList<Transcript>>routs;
    public MiniTransposon() {
        this.transcripts = new ArrayList<>();
        this.routs = new HashMap<>();
    }

    public ArrayList<Transcript> getTranscripts() {
        return transcripts;
    }

    public void setTranscripts(ArrayList<Transcript> transcripts) {
        this.transcripts = transcripts;
    }



    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
        setSize();
    }

    private void setSize() {
        this.size = this.end - this.start;
    }

    public char getChain() {
        return chain;
    }

    public void setChain(char chain) {
        this.chain = chain;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MiniTransposon other = (MiniTransposon) obj;
        return Objects.equals(chromosome, other.chromosome) &&
                start == other.start &&
                end == other.end &&
                chain == other.chain;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chromosome, start, end, chain);
    }



    @Override
    public void print() {
        System.out.println("\t\t\tChromosome: " + this.chromosome + ", Start: " + this.start + ", End: " + this.end + ", Chain: " + this.chain);
    }

    @Override
    public String toString() {
        return String.format("\t\t\tChromosome: %s, Start: %s, End: %s, Size: %s, Chain: %s", this.chromosome, this.start, this.end,this.size, this.chain);
    }

    @Override
    public int compareTo(MiniTransposon o) {
        return 0;
    }
}
