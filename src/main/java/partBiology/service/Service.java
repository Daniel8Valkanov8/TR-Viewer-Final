package partBiology.service;

import partBiology.database.Repository;

import partBiology.Gene;
import partBiology.MiniTransposon;
import partBiology.Transcript;
import partBiology.Transposon;

import javax.swing.*;
import java.nio.file.Path;
import java.util.*;


public class Service {
    private Repository repository;

    public Service(Path path) {
        this.repository = new Repository(path);

    }

    public Repository getRepository() {
        return repository;
    }

    public DefaultListModel<String> filterByType(String type) {
        DefaultListModel<String> listModelFiltered = new DefaultListModel<>();
        ArrayList<Transposon> filterByType = repository.filterByType(type);
        for (Transposon transposon : filterByType) {
            listModelFiltered.addElement(transposon.getName() + " Type: " + transposon.getType());
        }
        return listModelFiltered;
    }
    public DefaultListModel<String> filterGenesByCountCopyes(int count) {
        DefaultListModel<String> listModelFiltered = new DefaultListModel<>();
        Set<Gene> uniqueGenes = new TreeSet<>(repository.filterGenesByCountCopyes(count));
        for (Gene gene : uniqueGenes) {
            listModelFiltered.addElement(gene.getName());
        }

        return listModelFiltered;
    }
    public void printGeneWithMostTransposons() {
        int count = 0;
        Gene geneWithMostTransposons = repository.geneWithMostTransposons();
            for (Transcript transcript : geneWithMostTransposons.getTranscripts()) {
                for (Map.Entry<Transposon, ArrayList<MiniTransposon>> entry : transcript.getTransposons().entrySet()) {
                    ArrayList<MiniTransposon> miniTransposons = entry.getValue();
                    for (MiniTransposon miniTransposon : miniTransposons) {
                        count ++;
                    }
            }
        }
        System.out.printf("Gene with most transposons: %d\n", count);
        System.out.println(this.repository.geneWithMostTransposons().toString());
    }
    public void printTransposonInMostGenes() {
        Transposon transposonWithMostGenes = repository.printTransposonInMostGenes();
        System.out.println(transposonWithMostGenes.getName() + transposonWithMostGenes.getAllChildren().size());
    }
    //todo if loogic is broken
    public Map<String,Integer> trTypeCount(DefaultListModel<String> listModel) {
        Map<String, Integer> typeCount = new HashMap<>();
        Map<Transposon, ArrayList<MiniTransposon>> transposonsInputGenes = this.repository.getTransposonsInputGenes(listModel);
        for (Map.Entry<Transposon, ArrayList<MiniTransposon>> entry : transposonsInputGenes.entrySet()) {
            Transposon transposon = entry.getKey();
            ArrayList<MiniTransposon> miniTransposons = entry.getValue();
            typeCount.put(transposon.getType(), typeCount.getOrDefault(transposon.getType(), 0) + miniTransposons.size());
        }
        return typeCount;
    }

}
