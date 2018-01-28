import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.util.Random;

class Frame extends JFrame implements KeyListener {
    private Bear bear;
    private Env env;

    private int fps = 0, frames = 0, dx, dy, f = 0;
    private int speed = 0;
    static int Q = 2;
    private long totalTime, currentTime, lastTime;

    private Canvas canvas;
    private Graphics graphics;
    private Graphics2D g2d;

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

        bear = new Bear();
        env = new Env();
        Wall W = new Wall();
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

        BufferedImage bi = gc.createCompatibleImage(dx, dy);
        Random rand = new Random();
        currentTime = System.currentTimeMillis();

        while (true) {
            try {
                fps();

                // clear back buffer
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

                env.paint(g2d, f, c, w, speed, bear.y);
                bear.paint(g2d);

                f += speed;
                if (speed == 1) f -= y;
                y = 1 - y;

                // display frames per second
                g2d.setFont(new Font("Courier New", Font.PLAIN, 12));
                g2d.setColor(Color.GREEN);
                g2d.drawString(String.format("FPS: %s", fps), 20, 20);

                // display score
                g2d.setFont(new Font("Elephant", Font.BOLD, 20));
                g2d.setColor(Color.ORANGE);
                g2d.drawString(String.format("Score: %s", env.score), 630, 40);

                // draw image and flip
                graphics = buffer.getDrawGraphics();
                graphics.drawImage(bi, 0, 0, null);

                if (!buffer.contentsLost())
                    buffer.show();

                // let the OS have a little time
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
        lastTime = currentTime;
        currentTime = System.currentTimeMillis();
        totalTime += currentTime - lastTime;
        if (totalTime > 1000) {
            totalTime -= 1000;
            fps = frames;
            frames = 0;
        }
        frames++;
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                speed = 1;
                bear.sw = 1;
                bear.vx = 2;
                break;
            case KeyEvent.VK_LEFT:
                break;
            case KeyEvent.VK_UP:
                bear.vy = -1;
                break;
            case KeyEvent.VK_DOWN:
                bear.vy = 3;
                break;
            case KeyEvent.VK_ESCAPE:

                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        int o = e.getKeyCode();
        if (o == KeyEvent.VK_UP) bear.vy = 3;
        else if (o == KeyEvent.VK_RIGHT || o == KeyEvent.VK_LEFT) {
            bear.vx = 0;
            speed = 0;
            bear.sw = 0;
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
