import java.awt.geom.*; 
import java.applet.AudioClip; 
import java.applet.Applet; 

/**
 * This class is used to handle the physics of the game.
 */
public class Physics 
{ 
    private TankTrouble game; 
    
    /**
     * Constructor that takes the game instance.
     * @param tk The game instance.
     */
    public Physics(TankTrouble tk) 
    { 
        game = tk; 
    } 

    /**
     * This method handles the collisions between the bullets and the walls of the map.
     * @param bullet Bullet to check whether there is a collision with the walls of the map.
     */
    public void bulletWall(Bullet bullet) 
    { 
        if (!(bullet instanceof Laser))//do not make this calculation for lasers
        {
            for(int j=0;j<game.getMap().walls.size();j++) 
            { 
                Area bulletArea = new Area(bullet.getShape()); 
                Area temp = bulletArea; 
                temp.intersect(new Area(game.getMap().walls.get(j))); 
                if(temp.isEmpty()==false) 
                { 
                    if (bullet instanceof Shrapnel) {bullet.setVelocities(new double[]{0,0});}//shrapnels do not bounce! 
                    else 
                    { 
                        if(bullet.previousWall==null) 
                        { 
                            bullet.previousWall=game.getMap().walls.get(j); 
                            if(game.getMap().wallType.get(j)==1) 
                            { 
                                bullet.setRotation(360-bullet.getRotation()); 
                                //bullet.setRotation(180-bullet.getRotation()); 
                                //bullet.setVelocity(-bullet.getVelocity()); 

                            } 
                            else 
                            { 
                                bullet.setRotation(180-bullet.getRotation()); 
                                //bullet.setRotation(-bullet.getRotation()); 
                                //bullet.setVelocity(-bullet.getVelocity()); 

                            } 
                        } 
                        else if(!bullet.previousWall.equals(game.getMap().walls.get(j))) 
                        { 
                            bullet.previousWall=game.getMap().walls.get(j); 
                            if(game.getMap().wallType.get(j)==1) 
                            { 
                                bullet.setRotation(360-bullet.getRotation()); 
                                //bullet.setRotation(180-bullet.getRotation()); 
                                //bullet.setVelocity(-bullet.getVelocity()); 

                            } 
                            else 
                            { 
                                bullet.setRotation(180-bullet.getRotation()); 
                                //bullet.setRotation(-bullet.getRotation()); 
                                //bullet.setVelocity(-bullet.getVelocity()); 

                            } 
                        } 
                    } 
                } 
            } 
        }
    } 

    /**
     * This method handles the collisions between bullets and the tanks.
     * @param bullet Bullet to check whether there is a collision with the tanks.
     */
    public void bulletTank(Bullet bullet) 
    { 
        Area player1Area = game.getPlayer1().getArea(); 
        Area player2Area = game.getPlayer2().getArea(); 
        player1Area.intersect(new Area(bullet.getShape())); 
        player2Area.intersect(new Area(bullet.getShape())); 
        if((player1Area.isEmpty()==false)&&bullet.isActive)
        { 
            try{ 
                game.thread.sleep(1000); 
            } 
            catch(InterruptedException e){} 
            game.getScoring().increaseScore(2); 
            game.restart(); 
        } 
        if((player2Area.isEmpty()==false)&&bullet.isActive)
        { 

            try{ 
                game.thread.sleep(1000); 
            } 
            catch(InterruptedException e){} 
            game.getScoring().increaseScore(1); 
            game.restart(); 
        } 
    } 

    /**
     * This method handles the collisions between the tanks and the walls of the maps.
     * @param tank The tank to check whether there is a collision with the walls of the map.
     * @return Boolean value indicating collision
     */
    public boolean tankWall(Tank tank) 
    { 
        for(int j=0;j<game.getMap().walls.size();j++) 
        { 
            Area tankArea = tank.getArea(); 
            tankArea.intersect(new Area(game.getMap().walls.get(j))); 
            if(tankArea.isEmpty()==false) 
            { 
                return true; 
            } 

        } 
        return false; 
    } 

    /**
     * This method handles the collisions between the tanks and the bullet changer objects.
     * @param tank The tank to check whether there is a collision with the bullet changers.
     */
    public void tankBulletChanger(Tank tank) 
    { 
        Area tankArea = tank.getArea(); 
        tankArea.intersect(new Area(game.getBulletChanger().rect)); 
        if(tankArea.isEmpty()==false) 
        { 
            tank.changeBulletType(game.getBulletChanger().bulletType);
            game.getBulletChanger().updateChanger();
        } 

    } 

}