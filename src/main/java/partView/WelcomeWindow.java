package partView;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WelcomeWindow extends JFrame {
    private JProgressBar progressBar;

    public WelcomeWindow() {
        setTitle("TR-Viewer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Начертаване на снимката като фон
                Path path = Paths.get("welcome.png").toAbsolutePath().normalize();
                ImageIcon backgroundImage = new ImageIcon(path.toString());
                Image image = backgroundImage.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        addTitle("Welcome to TR-Viewer");

        // Отлагане на показването на прогрес бара с 2 секунди
        Timer delayTimer = new Timer(1100, e -> {
            addProgressBar();
            startProgressBar();
        });
        delayTimer.setRepeats(false); // Само веднъж да се изпълни
        delayTimer.start();

        setVisible(true);
    }

    private void addTitle(String titleText) {
        JLabel titleLabel = new JLabel(titleText);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.white);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 50));
        getContentPane().add(titleLabel, BorderLayout.CENTER);
    }

    private void addProgressBar() {
        progressBar = new JProgressBar();
        progressBar.setMaximum(100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        JPanel progressBarPanel = new JPanel(new BorderLayout());
        progressBarPanel.setOpaque(false);
        progressBarPanel.add(progressBar, BorderLayout.CENTER);
        getContentPane().add(progressBarPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private void startProgressBar() {
        Timer progressBarTimer = new Timer(50, e -> {
            int value = progressBar.getValue();
            if (value < 100) {
                progressBar.setValue(value + 1);
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        progressBarTimer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WelcomeWindow::new);
    }
}
