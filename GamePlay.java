package com.sjavaproject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    private Timer timer;
    private int delay = -1;
    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private MapGenerator map;

    public GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
         timer = new Timer(delay, this);
         timer.start();
         map = new MapGenerator(3,7);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); // Always call super.paint to ensure proper painting

        // here we have created a BackgroundColor
        g.setColor(Color.black);
        g.fillRect(1, 1, getWidth()-2, getHeight()-2);
        
//        Here we are drawing the map
        map.draw((Graphics2D) g);
        
        // Here We are creating the Borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, getHeight()); // Left border
        g.fillRect(0, 0, getWidth(), 3); // Top border
        g.fillRect(getWidth() - 3, 0, 3, getHeight()); // Right border
//        g.fillRect(0, getHeight() - 3, getWidth(), 3); // Bottom border

//        Here we are Writing the code for the Scores
        g.setColor(Color.white);
        g.setFont(new Font("serf", Font.BOLD,25));
        g.drawString(""+score, 590, 30);
        
        // The Paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        // The Ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);
        
//        Here we are adding the conditon for if the total Bricks are 0 then its Game Over
        	if(totalBricks<=0)
        	{
        		play = false;
        		ballXdir = 0;
        		ballYdir = 0;
        		g.setColor(Color.yellow);
        		g.setFont(new Font ("serif", Font.BOLD , 30));
        		g.drawString("You Won Bastered", 190, 300);
        		g.drawString("Wait For New Level", 190, 350);
        		
                g.setFont(new Font("serif", Font.BOLD,25));
                g.drawString("Enter to Restart" , 230, 400);
        	}
//        Here we are adding the conditions for the Game Over and the Restart Game
//        We also need to add program to locate if the user has entered the key
//        Inside the KeyPressed method
        if(ballposY > 570)
        {
        	play = false;
        	ballXdir= 0;
        	ballYdir = 0;
        	g.setColor(Color.red);
        	g.setFont(new Font("serf", Font.BOLD,35));
            g.drawString("Game Over , Score: " + score, 190, 300);
            g.setFont(new Font("serf", Font.BOLD,25));
            g.drawString("Enter to Restart" , 230, 350);
            
        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	timer.start();
    	if(play)
    	{
//    		Here is the code for the intersection of the ball and the paddle
    		if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8)))
    		{
    			ballYdir = -ballYdir;
    		}
    		
//    		here we are going to write the code for the brickes
//    		here A Act as a Label for the break condition;
    		A: for(int i = 0 ;i <map.map1.length ; i++  )
    		{
    			for(int j = 0 ; j < map.map1[0].length;j++)
    			{
    				if(map.map1[i][j] > 0)
    				{
//    					there here it will detact intersaction of ball and brick
    					int brickX = j * map.brickwidth+80;
    					int brickY = i*map.brickheight+50;
    					int brickWidth = map.brickwidth;
    					int brickHeight = map.brickheight;
    					
    					Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
    					Rectangle ballRect = new Rectangle(ballposX, ballposY , 20 , 20);
    					Rectangle brickRect = rect;
    					
    					if(ballRect.intersects(brickRect))
    					{
    						map.setBrickeValue(0,i,j);
    						totalBricks--;
    						score +=5;
    						if(ballposX + 19 <= brickRect.x || ballposX +1 >= brickRect.x + brickRect.width)
    						{
    							ballXdir = - ballXdir;
    						}
    						else {
    							ballYdir = - ballYdir;
    						}
    						break A;
    					}
    				}
    			}
    		}
//    		### This is the Part for the Moving direction of the Ball
    		ballposX += ballXdir;
    		ballposY += ballYdir;
//    			This is for the Left Border
    			if(ballposX<0)
    			{
    				ballXdir = -ballXdir;
    			}
//    			This is For the Top Border
    			if(ballposY<0)
    			{
    				ballYdir = -ballYdir;
    			}
//    			This is For the Right Border
    			if(ballposX > 670)
    			{
    				ballXdir = -ballXdir;
    			}
//    			###Till This is behaviour of the ball is defined
    	}
//    	Everytime the Certian action is performed the paint object will be created for the object
//    	And the new graphics will be loaded in the same frame
        repaint();
//        
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
//    	This is All the code for the Paddle and its position at every Action
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if(e.getKeyCode()== KeyEvent.VK_ENTER);
        if(!play)
        {
        	play = true;
        	ballposX = 120;
        	ballposY = 350;
        	ballXdir = -1;
        	ballYdir = -2;
        	playerX = 310;
        	score = 0;
        	totalBricks = 21;
        	map = new MapGenerator(3,7);
        	
        	repaint();
        }
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        GamePlay gameplay = new GamePlay();
        frame.setBounds(10, 10, 700, 600);
        frame.setTitle("Brick Breaker");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gameplay);
        frame.setVisible(true);
    }
}
