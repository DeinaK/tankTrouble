/**
 * This class implements the GunAmmo bullet type.
 */
public class GunAmmo extends Bullet
{
    private TankTrouble game;
    private Tank tank;
    private AllBullets b;
    
    /**
     * Constructor that takes x,y coordinates, the game instance and the tank object.
     * @param xCoordinate x-coordinate
     * @param yCoordinate y-coordinate
     * @param tanktrouble The game instance.
     * @param tankNew The tank object.
     */
    public GunAmmo(double xCoordinate, double yCoordinate, TankTrouble tanktrouble, Tank tankNew)
    {
        super(xCoordinate,yCoordinate,tanktrouble,tankNew);
        lifeSpan = 5000;//the bullet can live 5 seconds at most
        setX(xCoordinate);//make the initial x the specified xcoordinate instead of adjusting to the tank
        setY(yCoordinate);//make the initial y the specified ycoordinate instead of adjusting to the tank
    }
}