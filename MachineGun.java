/**
 * This class implements the MachineGun bullet type.
 */
public class MachineGun extends Bullet
{
    private TankTrouble game;
    private Tank tank;
    private double rotation;
    private double velocity;
    private int shootN;
    private AllBullets b;
    //public boolean isActive;//to control the release

    /**
     * Constructor that takes x,y coordinates, the game instance and the tank object.
     * @param xCoordinate x-coordinate
     * @param yCoordinate y-coordinate
     * @param tanktrouble The game instance.
     * @param tankNew The tank object.
     */
    public MachineGun(double xCoordinate, double yCoordinate, TankTrouble tanktrouble, Tank tankNew)
    {
        super(xCoordinate,yCoordinate,tanktrouble,tankNew);
        game = getGame();
        b=game.getB();
        tank = tankNew;
        lifeSpan = 5000;
        shootN = 0;//to control the firing frequency
        //isActive = true;
        velocity = this.getVelocity();
        rotation = this.getRotation();
        this.setVelocities(new double[]{(-(velocity) * Math.sin(Math.toRadians(rotation))),((velocity) * Math.cos(Math.toRadians(rotation)))});
    }
    /**
    * This method updates the rotation, position and explosion of the bullet.
    */
    public void updateBullet()
    {
        setX(tank.getX()-5-(32*Math.sin(Math.toRadians(tank.getRotation()))));
        setY(tank.getY()-5+(32*Math.cos(Math.toRadians(tank.getRotation()))));
        rotation = tank.getRotation();//update rotation
        this.setVelocities(new double[]{(-(velocity) * Math.sin(Math.toRadians(rotation))),((velocity) * Math.cos(Math.toRadians(rotation)))});

        if(((System.currentTimeMillis()-timer)>(500))&&(shootN==0))//starts firing after half-a-second and when shootN is zero
        {
            double x = getX();
            double y = getY();
            double r = 5;
            double rotAng = Math.toRadians(10*Math.random());//randomized from -10 to 10 degrees
            Vector2D velocity = new Vector2D(getVelVector());//uses Vector2D to get the velocity vector of the main bullet
            velocity.rotate(rotAng);//rotates by a certain randomized angle
            double curAng = velocity.getAngle();//gets the resultant angle

            Bullet temp = new GunAmmo(x+r*Math.cos(curAng),y+r*Math.sin(curAng),getGame(), tank);
            temp.setVelocities(velocity.getVector());//sets velocity as the vector that was rotated
            b.insertBullet(temp);//inserts to the AllBullets object
        }
        shootN = (shootN+1)%3;//shootN is incremented in modulo 3 so that the machine gun will fire every 3 instances the bullet is updated
    }
}