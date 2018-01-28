import java.awt.*;
import javax.swing.*;

import java.util.*;

class Env {
    private Image bg, coin, wall;
    int score;
    private int p, xw, yw, q;
    private boolean sw = false, sw1 = false;

    public Env() {
        bg = new ImageIcon("res/bg1.jpg").getImage();
        coin = new ImageIcon("res/coin.png").getImage();
        wall = new ImageIcon("res/Wall.png").getImage();
    }

    public void paint(Graphics2D g2d, int f, int c, int w, int speed, int y) {
        g2d.drawImage(bg, -f, 0, null);

        // coin
        if (c == 1 && !sw) {
            sw = true;
            p = 750;
        }
        if (speed == 1) {
            p--;
            xw--;
        }
        if (y >= 230 && p == 165) {
            sw = false;
            score++;
        }
        if (sw && p == 1) sw = false;

        // wall
        if (w == 1 && !sw1) {
            sw1 = true;
            xw = 750;
            yw = 220;
            q = -3;
        }

        yw += q;
        if (yw <= 0) q = 2;
        if (yw >= 220) q = -1;
        if (sw1 && xw == 1) sw1 = false;

        if (sw1 && xw < 170 && xw > 150 && yw > 200) {
            g2d.setFont(new Font("Elephant", Font.BOLD, 50));

            g2d.setColor(Color.BLACK);
            g2d.drawString("Game over!!!", 100, 100);
            Frame.Q = 3000;
        } else {
            if (sw) g2d.drawImage(coin, p, 280, null);
            if (sw1) g2d.drawImage(wall, xw, yw, null);
        }
    }
}
