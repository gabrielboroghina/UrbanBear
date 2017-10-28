import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.util.Random;

class Frame extends JFrame implements KeyListener {
    bear b;
    env e;

    int fps = 0, frames = 0, dx, dy, f = 0;
    int speed = 0;
    static int Q = 2;
    long totaltime, currenttime, lasttime;

    Canvas canvas;
    Graphics graphics;
    Graphics2D g2d;

    Frame(String nume, int x, int y, int dx, int dy) {
        super(nume);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIgnoreRepaint(true);
        setLocation(x, y);
        addKeyListener(this);
        setResizable(false);

        ImageIcon icon = new ImageIcon("res/icon.png");
        setIconImage(icon.getImage());

        this.dx = dx;
        this.dy = dy;

        canvas = new Canvas();
        canvas.setIgnoreRepaint(true);
        canvas.setSize(dx, dy);
        add(canvas);

        pack();
        setVisible(true);

        b = new bear();
        e = new env();
        wall W = new wall();
        add(W);

        render(); // active render
    }

    private void render() {
        int y = 0, c, w;
        canvas.createBufferStrategy(2);
        BufferStrategy buffer = canvas.getBufferStrategy();

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();

        //Imaginea pe care o construim
        BufferedImage bi = gc.createCompatibleImage(dx, dy);
        Random rand = new Random();
        currenttime = System.currentTimeMillis();

        while (true) {
            try {
                fps();

                // clear back buffer...
                try {
                    Thread.sleep(Q);
                } catch (Exception ex) {
                }
                g2d = bi.createGraphics();
                g2d.setBackground(Color.blue);
                g2d.fillRect(0, 0, dx, dy);

                if (f > 956) f = 0;
                c = rand.nextInt(200);
                w = rand.nextInt(300);

                e.paint(g2d, f, c, w, speed, b.y);
                b.paint(g2d);

                f += speed;
                if (speed == 1) f -= y;
                y = 1 - y;

                // display frames per second...
                g2d.setFont(new Font("Courier New", Font.PLAIN, 12));
                g2d.setColor(Color.GREEN);
                g2d.drawString(String.format("FPS: %s", fps), 20, 20);

                //display score
                g2d.setFont(new Font("Elephant", Font.BOLD, 20));
                g2d.setColor(Color.ORANGE);
                g2d.drawString(String.format("Score: %s", e.score), 630, 40);

                // Blit image and flip...
                graphics = buffer.getDrawGraphics();
                graphics.drawImage(bi, 0, 0, null);

                if (!buffer.contentsLost())
                    buffer.show();

                // Let the OS have a little time...
                Thread.yield();

            } finally {

                // release resources
                if (graphics != null)
                    graphics.dispose();

                if (g2d != null)
                    g2d.dispose();
            }
        }
    }

    private void fps() {
        lasttime = currenttime;
        currenttime = System.currentTimeMillis();
        totaltime += currenttime - lasttime;
        if (totaltime > 1000) {
            totaltime -= 1000;
            fps = frames;
            frames = 0;
        }
        frames++;
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                speed = 1;
                b.sw = 1;
                b.vx = 2;
                break;
            case KeyEvent.VK_LEFT:
                break;
            case KeyEvent.VK_UP:
                b.vy = -1;
                break;
            case KeyEvent.VK_DOWN:
                b.vy = 3;
                break;
            case KeyEvent.VK_ESCAPE:

                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        int o = e.getKeyCode();
        if (o == KeyEvent.VK_UP) b.vy = 3;
        else if (o == KeyEvent.VK_RIGHT || o == KeyEvent.VK_LEFT) {
            b.vx = 0;
            speed = 0;
            b.sw = 0;
        }
    }

    public void keyTyped(KeyEvent e) {
    }
}

public class Walk {
    public static void main(String[] args) {
        new Frame("Walk", 100, 200, 750, 380);
    }
}
