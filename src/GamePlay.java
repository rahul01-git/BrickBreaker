import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener,  ActionListener{

    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;
    
    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private MapGenerator mapGenerator;
    

    public GamePlay(){
        mapGenerator = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();
    }

    public void paint(Graphics g){
        g.setColor(Color.white);
        g.fillRect(1, 1, 692, 592);

        mapGenerator.draw((Graphics2D)g);

        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(680, 0, 4, 592);

        g.setColor(Color.blue);
        g.fillRect(playerX, 550, 100, 8);

        g.setColor(Color.green);
        g.fillOval(ballPosX, ballPosY, 20, 20);

        g.dispose();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            if(new Rectangle(ballPosX,ballPosY,20,30).intersects(new Rectangle(playerX,550,100,8))){
                ballYdir = -ballYdir;
            }

            for(int i = 0;i<mapGenerator.map.length; i++){
                for (int j = 0; j<mapGenerator.map[0].length;j++){
                    if(mapGenerator.map[i][j] > 0){
                        int brickX = j * mapGenerator.brickWidth + 80;
                        int brickY = i * mapGenerator.brickHeight + 50;
                        int brickWidth = mapGenerator.brickWidth;
                        int brickHeight = mapGenerator.brickHeight;

                        Rectangle brickRect = new Rectangle(brickX, brickY,brickWidth,brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX,ballPosY,20,20);

                        if(ballRect.intersects(brickRect)){
                            mapGenerator.setBrickValue(0,i,j);
                            totalBricks--;
                            score++;

                            if(ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.width){
                                ballXdir*=-1;
                            }else ballYdir*=-1;
                        }
                    }
                }
            }

            ballPosX += ballXdir;
            ballPosY += ballYdir;

            if(ballPosY<0) ballYdir*=-1;
            if(ballPosX<0) ballXdir*=-1;
            if(ballPosX>670) ballXdir*=-1;
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX >= 580) playerX = 580;
            else moveRight();
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX <= 10) playerX = 10;
            else moveLeft();
        }
        
    }

    public void moveLeft(){
        play = true;
        playerX-=20;
    }
    public void moveRight(){
        play = true;
        playerX+=20;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    
}
