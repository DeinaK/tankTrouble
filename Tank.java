import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

/**
 * This class implements the tank object for the game.
 */
public class Tank{

    public String tankName = "";
    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    public int bulletType;
    private boolean shoot;
    private TankTrouble game;
    private double rotation;
    private double velocity;
    private double addRotation;
    private int w = 700; // width
    private int h = 700; // height
    private int bulletNumber = 0;

    public int screenFit = 10;  // length of the wall
    private Random rand = new Random();
    public BufferedImage icon;  

    /**
     * Constructor that takes name, color, bullet type and the game instance.
     * @param name Tank name
     * @param color String indicating the color
     * @param bt Integer value indicating the bullet type
     * @param tk The game instance.
     */
    public Tank(String name, String color,int bt, TankTrouble tk) 
    {
        tankName += name;
        game = tk;
        shoot=true;
        rotation = 0;
        velocity=0;
        x = game.getMap().originX + rand.nextInt(7)*100 + 40;
        y = game.getMap().originY + rand.nextInt(7)*100 + 40;
        bulletType=bt;
        try {
            icon = ImageIO.read(new File("src/"+color+"Tank.png"));
        } catch (IOException e) {
            System.out.println("tank icon not found");
        }
    }

    /**
     * This method updates the tanks position and rotation
     */
    public void updateTank()
    {

        //useful info:
        //tank size = 30 x 55

        rotation = (rotation+addRotation)%360;

        velocityX = (velocity) * Math.sin(Math.toRadians(rotation));
        velocityY = -(velocity) * Math.cos(Math.toRadians(rotation));

        x+=velocityX;
        y+=velocityY;
        if(game.getPhysics().tankWall(this)==true)
        {
            x-=velocityX;
            y-=velocityY;
        }
        game.getPhysics().tankBulletChanger(this);

    }

    /**
     * This method renders the tank
     * @param g Graphics object for rendering.
     */
    public void renderTank(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        g2.rotate(Math.toRadians(rotation),x,y); 
        AffineTransform t = new AffineTransform();
        t.translate(x-31/2, y-47/2); 
        t.scale(1, 1);
        g2.drawImage(icon, t, null);  
        g2.rotate(Math.toRadians(360-rotation),x,y);
    }

    /**
     * Mutator method for tank's velocity
     * @param a New velocity
     */
    public void setVelocity(double a){
        velocity = a;
    }

    /**
     * Accessor method for y-coordinate of the tank.
     * @return y-coordinate
     */
    public double getY()
    {
        return y;
    }
    
    /**
     * Accessor method for x-coordinate of the tank.
     * @return x-coordinate
     */
    public double getX()
    {
        return x;
    }

    /**
     * Mutator method for tank's y-coordinate
     * @param a New y-coordinate
     */
    public void setY(double a)
    {
        y = a;
    }

     /**
     * Mutator method for tank's x-coordinate
     * @param a New x-coordinate
     */
    public void setX(double a)
    {
        x = a;
    }

    /**
     * Mutator method for tank's rotation
     * @param a New rotation
     */
    public void setRotation(double a)
    {
        rotation = a;
    }

    /**
     * Mutator method for tank's shooting condition
     * @param New condition
     */
    public void setShoot(boolean condition)
    {
        shoot = condition;
    }

    /**
     * Accessor method for tank's shooting condition
     * @return Boolean value indicating tank's shooting condition.
     */
    public boolean hasShoot()
    {
        return shoot;
    }

    /**
     * This method increases the number of bullets of the tank.
     * @param num Number of bullets to increase
     */
    public void addShoot (int num){
        bulletNumber += num;
        //Thread.sleep(3);

        if (bulletNumber == 2){
            setShoot (false);
        }

    }

    /**
     * This method adds given vlaue to tank's rotation
     * @param a Amount degrees to increase tank's rotation.
     */
    public void addRotationDegree(double a)
    {
        addRotation = a;
    }

    /**
     * Accessor method for the rotation of the tank.
     * @return Rotation of the tank.
     */
    public double getRotation()
    {
        return rotation;
    }

    /**
     * Accessor method for the velocity of the tank.
     * @return Velocity of the tank.
     */
    public double getVelocity()
    {
        return velocity;
    }

    /**
     * Accessor method for the area of the tank.
     * @return Area of the tank.
     */
    public Area getArea()
    {
        Area rect1 = new Area(new Rectangle2D.Double(x-31/2, y-45/2,31,45));
        Area rect2 = new Area(new Rectangle2D.Double(x-5/2, y-32/2,5,32));
        rect1.add(rect2);
        return rect1;
    }

    /**
     * Accessor method for the velocity vector of the tank.
     * @return Velocity vector of the tank.
     */
    public double[] getVelVector()
    {
        return new double[]{velocityX, velocityY};
    }

    /**
     * Accessor method for the position vector of the tank.
     * @return Position vector of the tank.
     */
    public double[] getPosVector()
    {
        return new double[]{x, y};
    }

    /**
     * Mutator method to change the bullet type of the tank.
     * @param type Type of bullet.
     */
    public void changeBulletType(int type)
    {
        bulletType = type;
    }
}