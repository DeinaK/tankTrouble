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
import javax.imageio.*;
import java.awt.geom.*;
import java.util.LinkedList;
import javax.imageio.*;
import java.awt.geom.*;
import java.util.LinkedList;

/**
 * This class is used to render credits screen.
 */
public class Credits 
{  
    /*X and Y coordinates of the origin */
    public int originX; 
    public int originY;

    private BufferedImage background;
    private BufferedImage menubutton;
    // private BufferedImage creditsbutton;
    private boolean conditionMenu=false;

    public TankTrouble game;

    /**
     * Constructor that takes x and y coordinates and the game instance
     * @param x X-coordinate
     * @param y Y-coordinate
     * @param tanktrouble The game instance
     */
    public Credits(int x, int y,TankTrouble tanktrouble) throws ArrayIndexOutOfBoundsException  
    {
        originX = x; // let the xCoordinate of the origin pointX (x)
        originY = y;  // let the yCoordinate of the origin pointY (y)

        //* Put the background image and the menu button *//
        try {
            background = ImageIO.read(new File("src/CreditsPage.png"));
            menubutton = ImageIO.read(new File("src/menu.png"));

        } catch (IOException e) {
            System.out.println("ERROR: Icon Not Found");
        }  

        game = tanktrouble;

    }

    /**
     * This method is used to go to Menu screen.
     * @param condition Boolean value indicating whether the program goes to menu screen or not
     */
    public void gotoMenu(boolean condition)
    {
        if(condition)
        {
            conditionMenu=true;
        }
        else
        {
            conditionMenu=false;
        }
    }  

    /**
     * This method is used to render the credits to the screen
     * @param g Graphics object for rendering.
     */
    public void renderCredits(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(background,0,0,null);

        //g2.setColor (Color.WHITE);

        /* If the position of the mouse is on the area of the menubutton, then display menuTank image*/ 
        if (conditionMenu){
            try {

                menubutton = ImageIO.read(new File("src/menuTank.png"));

            } catch (IOException e) {
                System.out.println("icon1 not found");
            }
        }

        /*Else don't do anything*/ 
        else{
            try {

                menubutton = ImageIO.read(new File("src/menu.png"));

            } catch (IOException e) {
                System.out.println("icon1 not found");
            }
        }

        //CREDITS

        //  g2.setColor (Color.BLACK);

        //  Font font = new Font("Serif", Font.PLAIN, 40);
        //  g2.setFont(font);
        g2.drawImage(background,0,0,null);
        // g2.drawImage(menubutton,500,600,null);

        // g2.drawString("Tank Trouble v2.01", 50, 150);

        // g2.drawImage(creditsbutton, 600, 600, null);

    }
}