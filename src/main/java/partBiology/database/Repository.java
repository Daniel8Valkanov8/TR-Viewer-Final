package partBiology.database;

import dto.util.MyBeanUtils;
import partBiology.fileWorker.FileWorker;
import partBiology.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Repository {
    private ArrayList<String[]> dataArray = new ArrayList<>();
    private ArrayList<Gene> genesInFile = new ArrayList<>();
    private ArrayList<Transcript> transcriptsInFile = new ArrayList<>();
    private ArrayList<Transposon> transposonsInFile = new ArrayList<>();
    private ArrayList<MiniTransposon> miniTransposonsInFile = new ArrayList<>();

    public Repository(Path path) {
        ArrayList<String> data = FileWorker.readFile(path);
        for (String line : data) {
            //System.out.println(line);
            String[] splitLine = line.split("\\t");
            //System.out.println(Arrays.toString(splitLine));
            this.dataArray.add(splitLine);
        }
        genesCreator();
        transcriptCreator();
        transposonCreator();
        connect();
        clearDataArray();
    }


    public ArrayList<Gene> getGenesInFile() {
        return genesInFile;
    }

    public ArrayList<Transcript> getTranscriptsInFile() {
        return transcriptsInFile;
    }

    public ArrayList<Transposon> getTransposonsInputGenes() {
        return transposonsInFile;
    }

    public ArrayList<MiniTransposon> getMiniTransposonsInFile() {
        return miniTransposonsInFile;
    }

    public ArrayList<String[]> getDataArray() {
        return dataArray;
    }

    public boolean add(Gene gene) {
        if (!this.genesInFile.contains(gene)) {
            this.genesInFile.add(gene);
            return true;
        }
        return false;
    }
    public ArrayList<Gene> filterGenesContainsNMAndNR() {
        ArrayList<Gene> genes = new ArrayList<>();
        for (Gene gene : this.genesInFile) {
            boolean hasNM = false;
            boolean hasNR = false;

            for (Transcript transcript : gene.getTranscripts()) {
                if (transcript.getID().startsWith("NM")) {
                    hasNM = true;
                } else if (transcript.getID().startsWith("NR")) {
                    hasNR = true;
                }

                // If the gene has both NM and NR transcripts, no need to check further
                if (hasNM && hasNR) {
                    genes.add(gene);
                    break;
                }
            }
        }
        return genes;
    }


    public boolean add(Transcript transcript) {
        if (!this.transcriptsInFile.contains(transcript)) {
            this.transcriptsInFile.add(transcript);
            return false;
        }
        return true;
    }
    public boolean add(Transposon transposon) {
        if (!this.transposonsInFile.contains(transposon)) {
            this.transposonsInFile.add(transposon);
            return true;
        }
        return false;
    }
    public boolean add(MiniTransposon miniTransposon) {
        if (!this.miniTransposonsInFile.contains(miniTransposon)) {
            this.miniTransposonsInFile.add(miniTransposon);
            return true;
        }
        return false;
    }
    private void clearDataArray() {
        this.dataArray = null;
        System.gc();
    }

     public Gene findGene(String name){
        for (Gene gene : this.genesInFile)
        {
            if(gene.getName().equals(name))
            {
                return gene;
            }
        }
        return null;
    }
    public Transcript findTranscript (String id){
        for (Transcript transcript : this.transcriptsInFile)
        {
            if(id.contains(transcript.getID()))
            {
                return transcript;
            }
        }
        return null;
    }
    public Transposon findTransposon(String name, String type){
        for (Transposon transposon : this.transposonsInFile)
        {
            if(transposon.getName().equals(name)&&transposon.getType().equals(type))
            {
                return transposon;
            }
        }
        return null;

    }
    public Transposon findTransposon(String name){
        for (Transposon transposon : this.transposonsInFile)
        {
            if(transposon.getName().equals(name))
            {
                return transposon;
            }
        }
        return null;

    }
    public MiniTransposon findMiniTransposon (String chromosome, String start, String end, char chain){
        for (MiniTransposon miniTransposon : this.miniTransposonsInFile)
        {
            if(miniTransposon.getChromosome().equals(chromosome)
                    &&miniTransposon.getStart()==(Long.parseLong(start))
                    &&miniTransposon.getEnd()==(Long.parseLong(end))
                    &&miniTransposon.getChain()==chain)
            {
                return miniTransposon;
            }
        }
        return null;

    }
    private static String getSubString(String line, boolean header){
        int separator = line.indexOf('_', 3);
        if(separator==-1){
            return line;
        }
        if (header) {
            return line.substring(0, separator);
        }else {
            return line.substring(separator + 1, line.length());
        }

    }
    //creators
    private void genesCreator() {
        for (String[] arrayLine : this.getDataArray()) {
            Gene existingGene = null;

            // Check if gene exists
            for (Gene gene : this.getGenesInFile()) {
                if (gene.getName().equals(arrayLine[4])) {
                    existingGene = gene;
                    break;
                }
            }

            if (existingGene != null) {
                // If gene exists, add the route to this gene
                existingGene.addRoute(arrayLine);
            } else {
                // If gene does not exist, create a new gene and add the route
                Gene newGene = new Gene();
                newGene.setName(arrayLine[4]);
                newGene.addRoute(arrayLine);
                this.add(newGene); // Assuming there is an add method to add the gene to the repository
            }
        }
    }


    private void transcriptCreator()
    {
        for (String[] arrayLine : this.getDataArray())
        {
            String transcriptID = getSubString(arrayLine[3], true);

            boolean transcriptExists = false;
            for (Transcript transcript : this.getTranscriptsInFile())
            {
                if(transcript.getID().equals(transcriptID))
                {
                    transcriptExists = true;
                    break;
                }
            }
            if(!transcriptExists)
            {
                Transcript newTranscript = new Transcript();
                newTranscript.setID(transcriptID);
                for (Gene gene : this.getGenesInFile())
                {
                    if(arrayLine[4].equals(gene.getName()))
                    {
                        gene.addTranscript(newTranscript);
                        break;// break for sizing garanted of parent genes - 1
                    }
                }
                this.getTranscriptsInFile().add(newTranscript);
            }
        }
    }



    private void transposonCreator(){
        for (String[] lineArray : this.getDataArray()){
            String name = lineArray[10];
            String type = lineArray[11];
            Transposon transposon = null;

            for(Transposon tempTransposon : this.getTransposonsInputGenes()){
                if (tempTransposon.getName().equals(name) && tempTransposon.getType().equals(type))
                {
                    transposon = tempTransposon;
                    break;
                }
            }
            if(transposon == null){
                transposon = new Transposon();
                transposon.setName(name);
                transposon.setType(type);
            }
            String chromosome = lineArray[7];
            long start = Long.parseLong(lineArray[8]);
            long end = Long.parseLong(lineArray[9]);
            char chain = lineArray[6].charAt(0);




            MiniTransposon miniTransposon = null;
            for (MiniTransposon tempMiniTransposon : this.getMiniTransposonsInFile())
            {
                if(
                        tempMiniTransposon.getChain() == chain
                                && tempMiniTransposon.getChromosome().equals(chromosome)
                                && tempMiniTransposon.getStart() == start
                                && tempMiniTransposon.getEnd() == end
                )
                {
                    miniTransposon = tempMiniTransposon;
                    break;
                }
            }
            if (miniTransposon == null)
            {
                miniTransposon = new MiniTransposon();
                miniTransposon.setChain(chain);
                miniTransposon.setChromosome(chromosome);
                miniTransposon.setStart(start);
                miniTransposon.setEnd(end);

            }

            this.add(miniTransposon);
            this.add(transposon);
        }
    }


    private void connect(){

        for (Gene gene : this.getGenesInFile()) {
            this.geneConector(gene);
        }
    }
    private void geneConector(Gene gene){
        for (String[] allGeneRoute : gene.getAllGeneRoutes()) {
            gene.addTranscript(this.findTranscript(getSubString(allGeneRoute[3],true)));

        }
        for (Transcript transcript : gene.getTranscripts()) {

            for (String[] allGeneRoute : gene.getAllGeneRoutes()) {
                if (transcript.getID().equals(getSubString(allGeneRoute[3],true))){
                    transcript.addTransposon(this.findTransposon(allGeneRoute[10], allGeneRoute[11]),
                            this.findMiniTransposon(allGeneRoute[7], allGeneRoute[8], allGeneRoute[9], allGeneRoute[6].charAt(0)));
                    transcript.addTransposonCopy(this.findTransposon(allGeneRoute[10], allGeneRoute[11]));
                }
                this.findTransposon(allGeneRoute[10], allGeneRoute[11]).addChild(this.findMiniTransposon(allGeneRoute[7], allGeneRoute[8], allGeneRoute[9], allGeneRoute[6].charAt(0)));
            }
        }
    }

    public ArrayList<Transposon> filterByType(String type) {
        ArrayList<Transposon> DNA = new ArrayList<>();
        ArrayList<Transposon> LTR = new ArrayList<>();
        ArrayList<Transposon> LINE = new ArrayList<>();
        ArrayList<Transposon> SINE = new ArrayList<>();
        ArrayList<Transposon> OTHER = new ArrayList<>();
        for (Transposon transposon : this.getTransposonsInputGenes()) {
            boolean dnaType = !transposon.getType().equals("DNA");
            boolean ltrType = !transposon.getType().equals("LTR");
            boolean lineType = !transposon.getType().equals("LINE");
            boolean sineType = !transposon.getType().equals("SINE");
            if (transposon.getType().contains(type)) {
                if (transposon.getType().equals("DNA")) {
                    DNA.add(transposon);
                } else if (transposon.getType().equals("LTR")) {
                    LTR.add(transposon);
                } else if (transposon.getType().equals("LINE")) {
                    LINE.add(transposon);
                } else if (transposon.getType().equals("SINE")) {
                    SINE.add(transposon);
                }else{
                    OTHER.add(transposon);
                }

            } else if (!transposon.getType().contains(type)&&dnaType&&ltrType&&lineType&&sineType) {
                OTHER.add(transposon);

            }
        }
        switch (type) {
            case "DNA":
                return DNA;
            case "LTR":
                return LTR;
            case "LINE":
                return LINE;
            case "SINE":
                return SINE;
            default:
                return OTHER;
        }
    }
    public ArrayList<Transposon> filterByType(List<Transposon> input,String type) {
        ArrayList<Transposon> DNA = new ArrayList<>();
        ArrayList<Transposon> LTR = new ArrayList<>();
        ArrayList<Transposon> LINE = new ArrayList<>();
        ArrayList<Transposon> SINE = new ArrayList<>();
        ArrayList<Transposon> OTHER = new ArrayList<>();
        for (Transposon transposon : input) {
            boolean dnaType = !transposon.getType().equals("DNA");
            boolean ltrType = !transposon.getType().equals("LTR");
            boolean lineType = !transposon.getType().equals("LINE");
            boolean sineType = !transposon.getType().equals("SINE");
            if (transposon.getType().contains(type)) {
                if (transposon.getType().equals("DNA")) {
                    DNA.add(transposon);
                } else if (transposon.getType().equals("LTR")) {
                    LTR.add(transposon);
                } else if (transposon.getType().equals("LINE")) {
                    LINE.add(transposon);
                } else if (transposon.getType().equals("SINE")) {
                    SINE.add(transposon);
                }else{
                    OTHER.add(transposon);
                }

            } else if (!transposon.getType().contains(type)&&dnaType&&ltrType&&lineType&&sineType) {
                OTHER.add(transposon);
            }
        }
        switch (type) {
            case "DNA":
                return DNA;
            case "LTR":
                return LTR;
            case "LINE":
                return LINE;
            case "SINE":
                return SINE;
            default:
                return OTHER;
        }
    }
    public ArrayList<Gene> filterGenesByTransposonType(List<Gene> input, String type) {
        ArrayList<Gene> genes = new ArrayList<>();
        for (Gene gene : input) {
            boolean found = false; // Flag to track if the gene matches the transposon type
            for (Transcript transcript : gene.getTranscripts()) {
                for (Transposon allCopiesOfTransposon : transcript.getAllCopiesOfTransposons()) {
                    if (allCopiesOfTransposon.getType().equals(type)) {
                        found = true;
                        break;

                    }

                }
            }
            if (found) {
                genes.add(gene);

            }
        }
        return genes;
    }

    public ArrayList<Gene> filterGenesByCountCopyes(int count) {
        ArrayList<Gene> genes = new ArrayList<>();
        for (Gene gene : this.genesInFile) {
            boolean shouldAddGene = false;
            for (Transcript transcript : gene.getTranscripts()) {
                int transposonCount = transcript.getTransposons().size();
                if (transposonCount >= count) {
                    shouldAddGene = true;
                    break;
                }
            }
            if (shouldAddGene && !genes.contains(gene)) {
                genes.add(gene);
            }
        }
        return genes;
    }

    public Gene geneWithMostTransposons(){
        Gene geneWithMostTransposons = null;
        int transposonCount = 0;
        for (Gene gene : this.genesInFile) {
            int currentTransposonCount = 0;
            for (Transcript transcript : gene.getTranscripts()) {
                for (Map.Entry<Transposon, ArrayList<MiniTransposon>> entry : transcript.getTransposons().entrySet()) {
                    ArrayList<MiniTransposon> miniTransposons = entry.getValue();
                    for (MiniTransposon miniTransposon : miniTransposons) {
                        currentTransposonCount += miniTransposon.getSize();
                    }
                }
            }
            if (currentTransposonCount > transposonCount) {
                geneWithMostTransposons = gene;
                transposonCount = currentTransposonCount;
            }
        }
        return geneWithMostTransposons;
    }

    public Transposon printTransposonInMostGenes(){
        Transposon transposonWithMostGenes = null;
        AtomicInteger geneCount = new AtomicInteger();
        int maxGeneCount = 0;
        for (Transposon transposon : this.getTransposonsInputGenes()) {
            int currentGeneCount = 0;
            for (Gene gene : transposon.getAllParentsGene()) {
                for (Transcript transcript : gene.getTranscripts()) {
                    // Итерация през транспозоните на текущия транскрипт
                    for (Map.Entry<Transposon, ArrayList<MiniTransposon>> entry : transcript.getTransposons().entrySet()) {
                        Transposon currentTransposon = entry.getKey();
                        if (currentTransposon.equals(transposon)) {
                            for (MiniTransposon miniTransposon : miniTransposonsInFile) {
                                currentGeneCount++;
                            }
                            break; // Излизаме от цикъла, тъй като вече сме намерили срещане
                        }
                    }
                }
            }
            if (currentGeneCount > maxGeneCount) {
                maxGeneCount = currentGeneCount;
                transposonWithMostGenes = transposon;
            }
        }
        return transposonWithMostGenes;
    }
    public Map<Transposon, ArrayList<MiniTransposon>> getTransposonsInputGenes(DefaultListModel<String> genes) {
        Map<Transposon, ArrayList<MiniTransposon>> transposonsInThisGenes = new HashMap<>();
        List<String> genesInput = MyBeanUtils.convertToList(genes);
        ArrayList<Gene> geneList = new ArrayList<>();
        for (String gene : genesInput) {
            geneList.add(findGene(gene));
        }
        for (Gene gene : geneList) {
            System.out.println(gene);
            for (Transcript transcript : gene.getTranscripts()) {
                for (Map.Entry<Transposon, ArrayList<MiniTransposon>> entry : transcript.getTransposons().entrySet()) {
                    Transposon transposon = entry.getKey();
                    ArrayList<MiniTransposon> miniTransposons = entry.getValue();
                    if (transposonsInThisGenes.containsKey(transposon)) {
                        // Ако ключът вече съществува, добавете новите минитранспозони към списъка
                        transposonsInThisGenes.get(transposon).addAll(miniTransposons);
                    } else {
                        // Ако ключът не съществува, просто го добавете в мапата
                        transposonsInThisGenes.put(transposon, miniTransposons);
                    }
                }
            }
        }
        return transposonsInThisGenes;
    }
    public JTree createGenesPathsTree(DefaultListModel<String> genes) {
        List<String> genesInput = MyBeanUtils.convertToList(genes);
        ArrayList<Gene> geneList = new ArrayList<>();
        for (String gene : genesInput) {
            geneList.add(findGene(gene));
        }

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Genes Path");
        for (Gene gene : geneList) {
            DefaultMutableTreeNode geneElement = new DefaultMutableTreeNode(gene.getName());
            root.add(geneElement); // Add gene node to root node
            for (Transcript transcript : gene.getTranscripts()) {
                DefaultMutableTreeNode transcriptElement = new DefaultMutableTreeNode(transcript.getID());
                geneElement.add(transcriptElement); // Add transcript node to gene node
                for (Map.Entry<Transposon, ArrayList<MiniTransposon>> entry : transcript.getTransposons().entrySet()) {
                    Transposon transposon = entry.getKey();
                    DefaultMutableTreeNode transposonElement = new DefaultMutableTreeNode(transposon.getName() + transposon.getType());
                    transcriptElement.add(transposonElement); // Add transposon node to transcript node
                    ArrayList<MiniTransposon> miniTransposons = entry.getValue();
                    for (MiniTransposon miniTransposon : miniTransposons) {
                        DefaultMutableTreeNode miniTransposonElement = new DefaultMutableTreeNode(miniTransposon.getStart() + "-" + miniTransposon.getEnd() + " " + miniTransposon.getChromosome()
                                + " " + miniTransposon.getSize()
                                + " " + miniTransposon.getChain());
                        transposonElement.add(miniTransposonElement); // Add mini-transposon node to transposon node
                    }
                    break;
                }
            }
        }

        JTree tree = new JTree(root);
        tree.setCellRenderer(new DefaultTreeCellRenderer());

        return tree;
    }

    //todo
    public DefaultListModel<String> findGenesWithTransposons(DefaultListModel<String> transposons) {
        DefaultListModel<String> genesWithTransposons = new DefaultListModel<>();
        List<String> transposonTypesInput = MyBeanUtils.convertToList(transposons);
        List<Gene> genesWithFirstType = filterGenesByTransposonType(this.genesInFile, transposonTypesInput.get(0));
        transposons.remove(0);
        List<Gene> output = new ArrayList<>();
        for (String type : transposonTypesInput) {
            output = this.filterGenesByTransposonType(genesWithFirstType,type);
        }
        for (Gene gene : output) {
            genesWithTransposons.addElement(gene.getName());
        }
        return genesWithTransposons;
    }
    public Transposon findTransposonWithMostCopies() {
        Transposon transposonWithMostCopies = null;
        int maxCopies = 0;

        for (Transposon transposon :transposonsInFile) {
            int currentCopies = transposon.getAllChildren().size();
            if (currentCopies > maxCopies) {
                maxCopies = currentCopies;
                transposonWithMostCopies = transposon;
            }
        }

        return transposonWithMostCopies;
    }

}
