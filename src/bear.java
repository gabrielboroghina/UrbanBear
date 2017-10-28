import java.awt.*;
import javax.swing.*;

class bear {
    public int x, y, vx, vy, sw = 0;
    int p = 260;
    ImageIcon bear, wbear;

    public bear() {
        x = 160;
        y = p;
        bear = new ImageIcon("res/bear.png");
        wbear = new ImageIcon("res/wbear.gif");
    }

    public void paint(Graphics2D g2d) {
        y += vy;
        if (y > p) y = p;
        if (y <= 0) y = 0;
        if (sw == 0) g2d.drawImage(bear.getImage(), x, y, null);
        else g2d.drawImage(wbear.getImage(), x, y, null);
    }
}
