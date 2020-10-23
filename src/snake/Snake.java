package snake;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Snake implements ActionListener,KeyListener {
	
	
    
	public static Snake snake;
    
	public JFrame jframe;
	
	public RenderPanel renderPanel;
	
	
	
	public Timer timer = new Timer(40, this);
	
	public ArrayList<Point> snakeParts = new ArrayList<Point>();
	
	public static final int UP = 0, DOWN = 1, LEFT=2, RIGHT = 3, SCALE = 10;
	
	public int tick = 0, dir= DOWN, score, tailLen=10,time;
	
	public Point head, cherry;
	
	public Random random;
	
	public boolean over=false, paused;
	
	public Dimension dim;
	
	public Snake() {
		jframe = new JFrame("Snake");
		jframe.setVisible(true);
		jframe.setSize(800, 700);
		jframe.setResizable(false);
		jframe.setLocation(150,100);
		jframe.add(renderPanel = new RenderPanel());
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.addKeyListener(this);
		startGame();
		
	}
	public void startGame()
	{
		over = false;
		paused = false;
		time = 0;
		score = 0;
		tailLen=10;
		tick=0;
		dir = DOWN;
		head = new Point(0, -1);
		random = new Random();
		snakeParts.clear();
		cherry = new Point(random.nextInt(79), random.nextInt(66));
		timer.start();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		renderPanel.repaint();
		tick++;
		if (tick % 2 == 0 && head != null && !over && !paused)
		{
			time++;

			snakeParts.add(new Point(head.x, head.y));

			if (dir == UP)
			{
				if (head.y - 1 >= 0 && noTailAt(head.x, head.y - 1))
				{
					head = new Point(head.x, head.y - 1);
				}
				else
				{
					over = true;

				}
			}

			if (dir == DOWN)
			{
				if (head.y + 1 < 67 && noTailAt(head.x, head.y + 1))
				{
					head = new Point(head.x, head.y + 1);
				}
				else
				{
					over = true;
				}
			}

			if (dir == LEFT)
			{
				if (head.x - 1 >= 0 && noTailAt(head.x - 1, head.y))
				{
					head = new Point(head.x - 1, head.y);
				}
				else
				{
					over = true;
				}
			}

			if (dir == RIGHT)
			{
				if (head.x + 1 < 80 && noTailAt(head.x + 1, head.y))
				{
					head = new Point(head.x + 1, head.y);
				}
				else
				{
					over = true;
				}
			}

			if (snakeParts.size() > tailLen)
			{
				snakeParts.remove(0);

			}
			
			if(cherry != null) {
				if(head.equals(cherry)) {
					score += 10;
					tailLen++;
					cherry.setLocation(random.nextInt(79), random.nextInt(66));
				}
			}
			
		}	
	}
	public boolean noTailAt(int x, int y)
	{
		for (Point point : snakeParts)
		{
			if (point.equals(new Point(x, y)))
			{
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
	snake= new Snake();	
		
	}
	public void keyPressed(KeyEvent e)
	{
		int i = e.getKeyCode();

		if ((i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT) && dir != RIGHT)
		{
			dir = LEFT;
		}

		if ((i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT) && dir != LEFT)
		{
			dir = RIGHT;
		}

		if ((i == KeyEvent.VK_W || i == KeyEvent.VK_UP) && dir != DOWN)
		{
			dir = UP;
		}

		if ((i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN) && dir != UP)
		{
			dir = DOWN;
		}

		if (i == KeyEvent.VK_SPACE)
		{
			if (over)
			{
				startGame();
			}
			else
			{
				paused = !paused;
			}
		}
	}

	public void keyReleased(KeyEvent e)
	{
	}

	public void keyTyped(KeyEvent e)
	{
	}

}
