import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.geom.*;

public class Bullet{
    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    private double velocity;
    private double rotation;
    public boolean isActive;
    private BufferedImage icon;
    private TankTrouble game;
    public Tank tank;
    public Rectangle2D previousWall;
    public long timer;
    public long lifeSpan;

    /**
     * Constructor that takes x,y coordinates, the game instance and the tank object.
     * @param xCoordinate x-coordinate
     * @param yCoordinate y-coordinate
     * @param tanktrouble The game instance.
     * @param tankNew The tank object.
     */
    public Bullet(double xCoordinate, double yCoordinate, TankTrouble tanktrouble,Tank tankNew)
    {
        tank = tankNew;
        game = tanktrouble;
        velocity = 4; //zaten velocityX ve velocityY oldugundan bunu pozitif tutalim dedim
        rotation = tank.getRotation();
        timer = System.currentTimeMillis();
        lifeSpan = 5000;
        isActive = true;
        x = xCoordinate-5-(32*Math.sin(Math.toRadians(rotation)));
        y = yCoordinate-5+(32*Math.cos(Math.toRadians(rotation)));

        try {
            icon = ImageIO.read(new File("src/defaultBullet.png"));
        } catch (IOException e) {
            System.out.println("bullet icon not found");
        }

    }

    /**
    * This method updates the rotation, position and explosion of the bullet.
    */
    public void updateBullet(){
        velocityX = -(velocity) * Math.sin(Math.toRadians(rotation));
        velocityY = (velocity) * Math.cos(Math.toRadians(rotation));
        moveBullet();
    }

    /**
     * This method renders the bullet.
     * @param g Graphics object for rendering.
     */
    public void renderBullet(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform t = new AffineTransform();
        t.translate(x, y);
        t.scale(1, 1); 
        g2.drawImage(icon, t, null); 
    }

    /**
     * Accessor method for the y-coordinate of the bullet.
     * @return y-coordinate
     */
    public double getY()
    {
        return y;
    }

    /**
     * Accessor method for the y-coordinate of the bullet.
     * @return y-coordinate
     */
    public double getX()
    {
        return x;
    }

    /**
     * Accessor method for the shape of the bullet.
     * @return Shape of the bullet
     */
    public Ellipse2D.Double getShape()
    {
        Ellipse2D.Double shape = new Ellipse2D.Double(x,y,5,5);
        return shape;
    }

    /**
     * Mutator method for the rotation of the bullet.
     * @param a New rotation
     */
    public void setRotation(double a)
    {
        rotation = (a+360)%360;
    }

    /**
     * Accessor method for the rotation of the bullet.
     * @return Rotation of the bullet.
     */
    public double getRotation()
    {
        return rotation;
    }

    /**
     * Mutator method for the velocity of the bullet.
     * @param a New velocity
     */
    public void setVelocity(double a)
    {
        velocity = a;
    }

    /**
     * Mutator method for the velocity of the bullet that takes vector.
     * @param a New velocity vector
     */
    public void setVelocities(double[] a)//sets both velocities from the vector
    {
        velocityX = a[0];
        velocityY = a[1];
        velocity = Math.sqrt(a[0]*a[0]+a[1]*a[1]);
    }

    /**
     * Accessor method for the velocity of the bullet.
     * @return Velocity of the bullet.
     */
    public double getVelocity()
    {
        return velocity;
    }

    /**
     * This method moves the bullet's coordinates according to its velocity
     */
    public void moveBullet()//moves the bullet in x and y by velocityX and velocityY
    {
        x+=velocityX;
        y+=velocityY;
    }

    /**
     * Mutator method for the x-coordinate of the bullet that takes vector.
     * @param a New x-coordinate
     */
    public void setX(double newX) 
    {
        x=newX;
    }

    /**
     * Mutator method for the y-coordinate of the bullet that takes vector.
     * @param a New y-coordinate
     */
    public void setY(double newY) 
    {
        y=newY;
    }

    /**
     * Accessor method for the position vector of the bullet.
     * @return Position vector of the bullet.
     */
    public double[] getPosVector()
    {
        return new double[]{x, y};
    }

    /**
     * Accessor method for the velocity vector of the bullet.
     * @return Velocity vector of the bullet.
     */
    public double[] getVelVector()
    {
        return new double[]{velocityX, velocityY};
    }

    /**
     * Mutator method for the icon of the bullet.
     * @param imgName String indicating the image name.
     */
    public void setIcon(String imgName)
    {
        try {
            icon = ImageIO.read(new File(imgName));
        } catch (IOException e) {
            System.out.println(imgName + " bullet icon not found");
        }
    }

    /**
     * Accessor method for the game instance.
     * @return Game object.
     */
    public TankTrouble getGame()
    {
        return game;
    }
}