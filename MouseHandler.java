import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.AffineTransformOp;
import java.io.*;
import sun.audio.*;

/**
 * This class is used to handle mouse events.
 */
public class MouseHandler implements MouseListener, MouseMotionListener
{
    private Rectangle2D button1 = new Rectangle(1000,450,300,100); //player1 -> MENU // player1 = PLAY
    private Rectangle2D button2 = new Rectangle(1000,150,300,100); //player2 -> MENU // player2 = EXIT
    private Rectangle2D button3 = new Rectangle(1000, 300, 300, 100); //credits -> MENU
    private Rectangle2D button4 = new Rectangle (500, 600, 300, 100); // menubutton -> CREDITS
    
    
    private TankTrouble game; 
    
    /**
    * Constructor of the MouseHandler that takes the game object
    * @param tanktrouble Game instance
    */
    public MouseHandler (TankTrouble tanktrouble)
    {
        game = tanktrouble; 
    }
    
    /**
     * This method handles the user's mouse events using game instances methods.
     * @param event Mouse event
     */
    public void mouseClicked(MouseEvent event)
    {
    
        if (game.state ==  TankTrouble.STATE.MENU){ 
            if(button2.contains(event.getPoint()))
            {
                game.setGame();
            }
            if(button1.contains(event.getPoint()))
            {
                System.exit(0);
            }
          
            if(button3.contains(event.getPoint()))
            {
                game.setCredits();
            }
        }
    
        if (game.state ==  TankTrouble.STATE.CREDITS)
        { 
            if(button4.contains(event.getPoint()))
            {
                game.setMenu();
            }
        }
    }
    
    public void mouseEntered(MouseEvent arg0)
    {
    } 
    public void mouseExited(MouseEvent arg0)
    {
    } 
    public void mousePressed(MouseEvent arg0)
    {
    } 
    public void mouseReleased(MouseEvent arg0){
    }
    public void mouseDragged(MouseEvent arg0)
    {
    } 
    
    public void mouseMoved(MouseEvent event)
    {
        if (game.state ==  TankTrouble.STATE.MENU)
        {
            if(button2.contains(event.getPoint()))
            {
              game.getMenu().changePlayer2(true);
              
            }
            if(!button2.contains(event.getPoint()))
            {
              game.getMenu().changePlayer2(false);
            }
            if(button1.contains(event.getPoint()))
            {
             game.getMenu().changePlayer1(true);
            }
            if(!button1.contains(event.getPoint()))
            {
              game.getMenu().changePlayer1(false);
            }
            
            if(button3.contains(event.getPoint()))
            {
             game.getMenu().gotoCredits(true);
            }
            if(!button3.contains(event.getPoint()))
            {
              game.getMenu().gotoCredits(false);
            }
        
        } 
        
        else if (game.state == TankTrouble.STATE.CREDITS)
        {
            if (button4.contains(event.getPoint()))
            {
                game.getCredits().gotoMenu (true);
            }
            if (!button4.contains(event.getPoint()))
            {
                game.getCredits().gotoMenu (false);
            }
        }
    }
   
}