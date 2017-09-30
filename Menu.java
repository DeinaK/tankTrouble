import java.awt.Graphics;
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
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.*;

/**
 * This class is used to present a graphical menu to user.
 */
public class Menu
{
    private BufferedImage background;
    private BufferedImage button1;
    private BufferedImage button2;
    private BufferedImage creditsbutton;
    private BufferedImage exitbutton;
    private boolean condition1=false;
    private boolean condition2=false;
    private boolean condition3=false; //condition for credits
    private boolean condition4=false; //condition for exit

    /**
     * Default constructor for the class. Initializes image variables.
     */
    public Menu(){
        try {
            background = ImageIO.read(new File("src/background.png"));
            button1 = ImageIO.read(new File("src/exit.png")); // exit
            button2 = ImageIO.read(new File("src/play.png"));
            creditsbutton = ImageIO.read (new File ("src/credits.png"));
            // exitbutton = ImageIO.read (new File ("exit.png"));

        } catch (IOException e) {
            System.out.println("ERROR: Icon Not Found");
        }  

    }
    
    
    public void changePlayer2(boolean condition)
    {
        if(condition)
        {
            condition2=true;
        }
        else
        {
            condition2=false;
        }
    }

    public void changePlayer1(boolean condition)
    {
        if(condition)
        {
            condition1=true;
        }
        else
        {
            condition1=false;
        }
    }
    
    /**
     * This method is used to check if user goes to credits screen.
     * @param condition Boolean value indicating whether the user goes to credits screen.
     */
    public void gotoCredits(boolean condition)
    {
        if(condition)
        {
            condition3=true;
        }
        else
        {
            condition3=false;
        }
    }

    /**
     * This method renders the menu.
     * @param g Graphics object for rendering.
     */
    public void renderMenu(Graphics g)
    {  
        if(condition2)
        {
            try {
                button2 = ImageIO.read(new File("src/playTank.png"));

            } catch (IOException e) {
                System.out.println("icon2 not found");
            }
        }
        else{
            try {
                button2 = ImageIO.read(new File("src/play.png"));

            } catch (IOException e) {
                System.out.println("icon2 not found");
            }
        }

        if(condition1)
        {
            try {

                button1 = ImageIO.read(new File("src/exitTank.png"));

            } catch (IOException e) {
                System.out.println("icon1 not found");
            }
        }
        else
        {
            try {

                button1 = ImageIO.read(new File("src/exit.png"));

            } catch (IOException e) {
                System.out.println("icon1 not found");
            }
        }

        if(condition3)
        {
            try {

                creditsbutton = ImageIO.read(new File("src/creditsTank.png"));

            } catch (IOException e) {
                System.out.println("creditsTank not found");
            }
        }
        else
        {
            try {

                creditsbutton  = ImageIO.read(new File("src/credits.png"));

            } catch (IOException e) {
                System.out.println("credits not found");
            }
        }
        
        
        //draw buttons and images to graphics object
        g.drawImage(background,0,0,null);
        g.drawImage(button1,1000,450,null);
        g.drawImage(button2,1000,150,null);
        g.drawImage(creditsbutton, 1000, 300, null);
        //  g.drawImage(exitbutton, 1000, 600, null);
    }

}