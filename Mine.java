import java.awt.geom.Ellipse2D;

/**
 * This class implements the Mine bullet type.
 */
public class Mine extends Bullet
{ 
    /**
     * Constructor that takes x,y coordinates, the game instance and the tank object.
     * @param xCoordinate x-coordinate
     * @param yCoordinate y-coordinate
     * @param tanktrouble The game instance.
     * @param tankNew The tank object.
     */
    public Mine(double xCoordinate, double yCoordinate, TankTrouble tanktrouble, Tank tankNew)
    {
        super(xCoordinate,yCoordinate,tanktrouble,tankNew);
        lifeSpan = 20000;//it will be erased after 20 seconds
        setX(xCoordinate-14);//this will approximately be the center of the tank
        setY(yCoordinate-12);
        isActive = false;//it gets activated after a second
        setIcon("src/mine.png");//change the image so that it displays a mine
    }

    /**
     * This method updates the rotation, position and explosion of the bullet.
     */
    public void updateBullet()
    {
        if (((System.currentTimeMillis()-timer)>(1000))&&!isActive)//if more than a second has passed and the tank is not active...
        {
            isActive = true;//activate  (will be used in physics)
        }
    }

    /**
     * This method gets the shape of the bullet.
     * @return Shape of the bullet.
     */
    public Ellipse2D.Double getShape()//the getShape method is overrided because a mine is larger
    {
        Ellipse2D.Double shape = new Ellipse2D.Double(getX(),getY(),30,30);
        return shape;
    }
}