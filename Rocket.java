import java.awt.geom.Ellipse2D;

/**
 * This class was originally created for the rocket class, but since it had some problems, we did not use it in our program.
 */
public class Rocket extends Bullet
{ 

    private Tank tank, opponent;
    private double rotation;
    private double velocity;
    private AllBullets b;

    private TankTrouble game;

    public Rocket (double xCoordinate, double yCoordinate, TankTrouble tanktrouble,Tank tankNew, Tank anotherTank)
    {
        super(xCoordinate,yCoordinate,tanktrouble,tankNew);
        tank = tankNew;
        opponent = anotherTank;
        game = getGame();
        b=game.getB();
        lifeSpan = 50000;

        setIcon("src/mine.png");  

        velocity = this.getVelocity();
        rotation = this.getRotation();
        this.setVelocities(new double[]{(-(velocity) * Math.sin(Math.toRadians(rotation))),((velocity) * Math.cos(Math.toRadians(rotation)))});

    }
    public void updateBullet()
    {
        setX(tank.getX()-5-(32*Math.sin(Math.toRadians(tank.getRotation()))));
        setY(tank.getY()-5+(32*Math.cos(Math.toRadians(tank.getRotation()))));
        rotation = tank.getRotation();//update rotation
        this.setVelocities(new double[]{(-(velocity) * Math.sin(Math.toRadians(rotation))),((velocity) * Math.cos(Math.toRadians(rotation)))});

        double x = getX();
        double y = getY();
        double r = 5;
        double rotAng = Math.toRadians(10*Math.random());
        Vector2D velocity = new Vector2D(getVelVector());
        velocity.rotate(rotAng);
        double curAng = velocity.getAngle();

        Bullet temp = new GunAmmo(x+r*Math.cos(curAng),y+r*Math.sin(curAng),getGame(), tank); //This will actually be a GunAmmo object
        temp.setVelocities(velocity.getVector());
        b.insertBullet(temp);

        this.moveBullet();

    }

    public void shortestPath()
    {

    }
  
}