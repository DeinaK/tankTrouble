import java.awt.geom.Ellipse2D;

/**
 * This class implements the TrollRocket bullet type.
 */
public class TrollRocket extends Bullet
{ 
  
  private Tank tank, opponent; //creates two tanks: one for the user and the other for the opponent
  private double rotation; //rotation
  private double velocity; //veloicty
  private AllBullets b; //creates an object named b from AllBullets
  
  private TankTrouble game; // game
  
  /**
     * Constructor that takes x,y coordinates, the game instance and the tank object.
     * @param xCoordinate x-coordinate
     * @param yCoordinate y-coordinate
     * @param tanktrouble The game instance.
     * @param tankNew The tank object.
     */
  public TrollRocket (double xCoordinate, double yCoordinate, TankTrouble tanktrouble,Tank tankNew, Tank anotherTank) 
  {
    super(xCoordinate,yCoordinate,tanktrouble,tankNew); // it goes to its superclass
    tank = tankNew; // assigns tankNew to tank
    opponent = anotherTank; //assigns anotherTank to opponent
    game = getGame(); 
    b=game.getB(); // game.b gets the current bullet and then assigns it to b 
    lifeSpan = 50000; // lifespan is 50000 ms

    setIcon("src/mine.png");  // icon
    
    velocity = this.getVelocity(); // gets the velocity
    rotation = this.getRotation(); // gets the rotation degree
    /*Sets the velocities (X and Y)*/
    this.setVelocities(new double[]{(-(velocity) * Math.sin(Math.toRadians(rotation))),((velocity) * Math.cos(Math.toRadians(rotation)))}); 
    
    
  }
 
  /**
  * This method updates the rotation, position and explosion of the bullet.
  */
  public void updateBullet()
  {
    /*Sets the x and y coordinates of the bullet*/
    setX(tank.getX()-5-(32*Math.sin(Math.toRadians(tank.getRotation()))));
    setY(tank.getY()-5+(32*Math.cos(Math.toRadians(tank.getRotation()))));
    
    rotation = tank.getRotation();//update rotation
    
    /*Sets the bullet's x and y velocities*/
    this.setVelocities(new double[]{(-(velocity) * Math.sin(Math.toRadians(rotation))),((velocity) * Math.cos(Math.toRadians(rotation)))});
    
    this.moveBullet(); // move bullet with the object
    
  }
  
  
}