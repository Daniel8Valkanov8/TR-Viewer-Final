package dto.util;

import org.apache.poi.ss.formula.functions.T;
import partBiology.Gene;
import partBiology.Transposon;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyBeanUtils {
    public static <T> DefaultListModel<T> convertToDefault(List<T> list) {
        DefaultListModel<T> defaultListModel = new DefaultListModel<>();
        for (T item : list) {
            defaultListModel.addElement(item);
        }
        return defaultListModel;
    };

    public static <T> List<T> convertToList(JList<T> jList) {
        List<T> list = new ArrayList<>();

        // Обхождане на всички елементи в JList и добавяне в обикновения списък
        for (int i = 0; i < jList.getModel().getSize(); i++) {
            list.add(jList.getModel().getElementAt(i));
        }

        return list;
    }
    public static <T> JList<T> convertToJList(Set<T> set) {
        // Създаваме нов DefaultListModel и добавяме всички елементи от set в него
        DefaultListModel<T> model = new DefaultListModel<>();
        for (T element : set) {
            model.addElement(element);
        }
        // Създаваме нов JList със създадения модел и връщаме JList
        return new JList<>(model);
    }
    public static List<String> convertToList(DefaultListModel<String> genes) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < genes.size(); i++) {
            list.add(genes.getElementAt(i));
        }
        return list;
    }
    public static <T> Set<T> convertToSet(List<T> list) {
        return new HashSet<>(list);
    }
}
