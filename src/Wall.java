import java.awt.*;
import javax.swing.*;
import java.lang.InterruptedException;

class Wall extends JComponent {
    private int x, y;
    private Image wall;

    public Wall() {
        x = 90;
        y = 320;
        wall = new ImageIcon("res/Wall.png").getImage();

        Thread run = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    repaint();
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
            }
        });

        run.start();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        y--;
        g2d.drawImage(wall, x, y, null);
    }
}
