import java.awt.*;
import javax.swing.*;
import java.lang.InterruptedException;
class wall extends JComponent{
	int sw,x,y;
	Image wall;
	public wall()
	{
		x=90; y=320;
		wall=new ImageIcon("res/wall.png").getImage();
		Thread run=new Thread(new Runnable()
		{
			public void run()
			{
				while (true)
				{
					repaint();
					try{Thread.sleep(3);}catch (InterruptedException ie){}
				}
			}
		});
		run.start();
	}
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d=(Graphics2D) g;
		y--;
		g2d.drawImage(wall,x,y,null);
	}
}
