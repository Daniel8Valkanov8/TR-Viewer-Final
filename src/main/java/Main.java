import partView.WelcomeWindow;
import partView.mainWindowComponents.WindowMain;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        try {
            // Задаване на FlatLaf Light тема по подразбиране
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Отваряме прозореца за добре дошли
                WelcomeWindow welcomeWindow = new WelcomeWindow();
                welcomeWindow.setVisible(true);

                // Създаваме таймер, който да затваря прозореца след 3 секунди
                Timer timer = new Timer(7200, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        welcomeWindow.dispose(); // Затваряме прозореца за добре дошли
                    }
                });
                timer.setRepeats(false); // Задаваме таймерът да не се повтаря
                timer.start(); // Стартираме таймера

                // След като прозореца за добре дошли е затворен, продължаваме с изпълнението на програмата
                timer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Изпълняваме кода от основния метод main, след като welcomeWindow е затворен
                        runMainPart();
                    }
                });
            }
        });
    }

    // Метод, който съдържа останалата част от програмата
    private static void runMainPart() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WindowMain(); // Стартираме останалата част от програмата
            }
        });
    }



}