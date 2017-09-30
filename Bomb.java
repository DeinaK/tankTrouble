import java.awt.geom.Ellipse2D;

/**
 * This class implements the Bomb bullet type.
 */
public class Bomb extends Bullet
{
    //private double rotation;
    private int sharN; //number of shrapnel pieces
    private boolean hasExploded;
    private boolean sparked;
    private AllBullets b;
    private TankTrouble game;

    /**
     * Constructor that takes x,y coordinates, the game instance and the tank object.
     * @param xCoordinate x-coordinate
     * @param yCoordinate y-coordinate
     * @param tanktrouble The game instance.
     * @param tankNew The tank object.
     */
    public Bomb(double xCoordinate, double yCoordinate, TankTrouble tanktrouble,Tank tankNew)
    {
        super(xCoordinate,yCoordinate,tanktrouble,tankNew);
        //rotation = tank.getRotation();
        lifeSpan = 2000;
        hasExploded = false;
        game = getGame();
        b = game.getB();
        sharN = 20;// the number of shrapnels sent off upon explosion
        sparked=false;

        setIcon("src/Bomb.png");
    }

    /**
     * This method updates the rotation, position and explosion of the bullet.
     */
    public void updateBullet()
    {
        if((System.currentTimeMillis()-timer<lifeSpan-100)&&!sparked)//-100 is so that it can fire the shrapnels before it gets erased
        {
            super.updateBullet(); //use the superclass method for initialization of velocity
        }
        else if (!hasExploded||(sparked&&!hasExploded))
        {
            double angStep = 2.*Math.PI/sharN;

            double x = getX();
            double y = getY();
            double r = 10;
            double ang=0;
            Vector2D velocity;
            for (int i=0; i<sharN; i++)
            {
                Bullet temp = new Shrapnel(x+r*Math.cos(ang),y+r*Math.sin(ang),getGame(), game.getPlayer1()); //the initial position is determined by the angle
                velocity = new Vector2D(ang, getVelocity());//Vector2D is used to determine the velocity vector with a certain magnitude and angle
                temp.setVelocities(velocity.getVector());//velocity is set
                ang+=angStep;//angle is incremented
                b.insertBullet(temp);//and inserted to the AllBullets object
            }
            hasExploded=true;//after doing this turn hasExploded true so that it doesn't explode twice
        }
    }

    /**
     * This method gets the shape of the bullet.
     * @return Shape of the bullet.
     */
    public Ellipse2D.Double getShape()
    {
        Ellipse2D.Double shape = new Ellipse2D.Double(getX(),getY(),10,10);
        return shape;
    }

    /**
     * Accessor method for the explosion condition of the bullet.
     * @return Boolean value indicating whether the bullet exploded or not.
     */
    public boolean getExplosion()
    {
        return hasExploded;
    }
    
    /**
     * Accessor method for the sparking condition of the bullet.
     * @return Boolean value indicating whether the bullet sparkedxploded or not.
     */
    public boolean getSparked()
    {
        return sparked;
    }

    /**
     * Mutator method for the sparking of the bullet.
     */
    public void spark()
    {
        sparked=true;
    }

    /**
     * Mutator method for the explosion condition of the bullet.
     * @param exploded Boolean value whether the bullet exploded or not.
     */
    public void setExplosion(boolean exploded)
    {
        hasExploded=exploded;
    }
}