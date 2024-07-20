

import partView.WelcomeWindow;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainRunners {

    public static void run3() {
        SwingUtilities.invokeLater(() -> {
            WelcomeWindow welcomeWindow = new WelcomeWindow();
            welcomeWindow.setVisible(true);

            // Създаваме таймер, който да затваря прозореца след 2 секунди
            Timer timer = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    welcomeWindow.dispose(); // Затваряме текущия прозорец
                }
            });
            timer.setRepeats(false); // Задаваме таймерът да не се повтаря

            timer.start(); // Стартираме таймера
        });
    }

}


