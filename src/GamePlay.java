/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;
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
import javax.swing.Timer;

/**
 *
 * @author Er Vaibhav Karnwal
 */
public class GamePlay extends JPanel implements ActionListener, KeyListener{
    private boolean play=false;
    private int score=0;
    private int totalBrick=21;
    private Timer timer;
    private int delay=8;
    private int ballposX=120;
    private int ballposY=350;
    private int ballXdir=-1;
    private int ballYdir=-2;
    private int playerX=320;
    private MapGenerator map;
    
    public GamePlay(){
        addKeyListener(this);
	setFocusable(true);
	setFocusTraversalKeysEnabled(false);
        timer=new Timer(delay,this);
        timer.start();
        
        map=new MapGenerator(3,7);
    }
    
    public void paint(Graphics g){
        
        // background
	g.setColor(Color.black);
	g.fillRect(1, 1, 692, 592);
        
	// drawing map
        map.draw((Graphics2D) g);
	
        // borders
	g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
	g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        
        // the scores 		
	g.setColor(Color.white);
	g.setFont(new Font("serif",Font.BOLD, 20));
        g.drawString("Score :"+score, 590,30);
		
	// the paddle
        g.setColor(Color.green);
	g.fillRect(playerX, 550, 100, 8);
		
        // the ball
	g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);
        
        
	// when you won the game
	if(totalBrick <= 0)
        {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD, 30));
            g.drawString("You Won", 200,300);
             
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD, 20));           
            g.drawString("Press (Enter) to Restart", 230,350);  
		}
		
	// when you lose the game
	if(ballposY > 570)
        {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD, 30));
            g.drawString("Game Over, Scores: "+score, 200,300);
             
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD, 20));           
            g.drawString("Press (Enter) to Restart", 230,350);        
        }
        
        g.dispose();
    }
   
    
    @Override
    public void keyPressed(KeyEvent e) {
        
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            if(playerX>=600)
                playerX=600;
            else
                moveRight();
        }
        
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            if(playerX<10)
                playerX=10;
            else
                moveLeft();
        }
        
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(!play){
                play=true;
                score=0;
                totalBrick=21;
                ballposX=120;
                ballposY=350;
                ballXdir=-1;
                ballYdir=-2;
                playerX=320;
                
                map=new MapGenerator(3,7);
                
                repaint();
            }
        }
    }
    public void moveRight()
    {
        play = true;
	playerX+=20;	
    }
	
    public void moveLeft()
    {
        play = true;
	playerX-=20;	 	
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        timer.start();
        if(play){
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 30, 8)))
            {
                ballYdir = -ballYdir;
		ballXdir = -2;
            }
            else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 70, 550, 30, 8)))
            {
                ballYdir = -ballYdir;
		ballXdir = ballXdir + 1;
            }
            else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 30, 550, 40, 8)))
            {
                ballYdir = -ballYdir;
            }
            A:for(int i=0;i<map.map.length;i++){
                for(int j=0;j<map.map[i].length;j++)
                {
                    if(map.map[i][j]>0){
                        int width=map.brickWidth;
                        int height=map.brickHeight;
                        int brickXpos=80+j*width;
                        int brickYpos=50+i*height;
                        
                        Rectangle rect = new Rectangle(brickXpos, brickYpos, width, height);					
			Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
			Rectangle brickRect = rect;
                                                
                        if(ballRect.intersects(brickRect)){
                            
                            map.setBrick(0,i,j);
                            totalBrick--;
                            score+=5;
                            
                            if(ballposX+19<=brickXpos || ballposX+1>=brickXpos+width){
                                ballXdir=-ballXdir;
                            }else{
                                ballYdir=-ballYdir;
                            }
                            
                            break A;
                        }
                        
                    }
            }
            }
            
            
            ballposX+=ballXdir;
            ballposY+=ballYdir;
            if(ballposX < 0)
            {
                ballXdir = -ballXdir;
            }
            if(ballposY < 0)
            {
                ballYdir = -ballYdir;
            }
            if(ballposX > 670)
            {
                ballXdir = -ballXdir;
            }
        }
        repaint(); 
    }
 
    

    @Override
    public void keyReleased(KeyEvent arg0) {
    }
     @Override
    public void keyTyped(KeyEvent arg0) {
    }
}
