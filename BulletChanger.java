import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

/**
 * This class is used to initialize and render bullet changers for the tanks.
 */
public class BulletChanger 
{ 
    public TankTrouble game;
    public int bulletType;
    public Rectangle2D.Double rect;
    public int x,y;
    public BufferedImage icon;  
    public Random rand = new Random();;
    public long timer;
    public String[] bulletIcons = {"bombRect","machineRect","mine","trollRect","lazerRect"};
    
    /**
     * Default constructor for the object. Chooses x and y coordinates at random and initializes its type at random.
     */
    public BulletChanger()
    {
        x = rand.nextInt(7)*100 + 40;
        y = rand.nextInt(7)*100 + 40;
        bulletType = rand.nextInt(5)+1;
        rect = new Rectangle2D.Double(x,y,30,30);
        try {
            icon = ImageIO.read(new File("src/" + bulletIcons[bulletType-1]+".png"));
        } catch (IOException e) {
            System.out.println("icon not found");
        }
    }

    /**
     * This method is used to render bullet changers
     * @param g Graphics object for rendering.
     */
    public void renderChanger(Graphics g)
    {
        g.drawImage(icon,x,y,null);  

    }

    /**
     * This method is used to update bullet changers.
     */
    public void updateChanger()
    {
        x = rand.nextInt(7)*100 + 40;
        y = rand.nextInt(7)*100 + 40;
        bulletType = rand.nextInt(5)+1;
        rect = new Rectangle2D.Double(x,y,30,30);
        try {
            icon = ImageIO.read(new File("src/" + bulletIcons[bulletType-1]+".png"));
        } catch (IOException e) {
            System.out.println("bullet changer icon not found");
        }
    }

  
}