package partBiology.fileWorker;

import partBiology.Gene;
import partView.dialogs.ErrorDialogFrame;
import partBiology.service.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileWorker {

    public static ArrayList<String> readFile(Path pathFile){
        ArrayList<String> fileData = new ArrayList<>();
        try {
            BufferedReader reader = Files.newBufferedReader(pathFile);
            String line = reader.readLine();
            while(line!=null){
                fileData.add(line);
                line = reader.readLine();
            }
            reader.close();
            return fileData;
        }catch (IOException e){

            ErrorDialogFrame.showErrorDialog("Error","Грешка при прочитане на файла: " + e.getMessage());
        }
        return null;
    }
    public static void writeFile(File fileToSave, Service service) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave));
            // Тук можете да напишете логиката за писане на съдържание във файла,
            // като използвате writer.write("съдържание");
            for (Gene gene : service.getRepository().getGenesInFile()) {
                writer.write(gene.toString());
            }
            writer.close();
        } catch (IOException ex) {

            ErrorDialogFrame.showErrorDialog("Error","Грешка при записа на файла: " + ex.getMessage());
        }
    }
    public static void writeFile(File fileToSave, String text) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave));
                writer.write(text);
                writer.close();
            ErrorDialogFrame.showErrorDialog("Success","Файлът е успешно записан.");
        } catch (IOException ex) {
            ErrorDialogFrame.showErrorDialog("Error","Грешка при записа на файла: " + ex.getMessage());
        }
    }
}
