import java.awt.Graphics;
import java.util.LinkedList;

/**
 * This class is used to keep the list of bullets, render the bullets and add/remove them from the game.
 */
public class AllBullets 
{
    public LinkedList<Bullet> bullets = new LinkedList<Bullet>();

    private Bullet temp;
    private TankTrouble game;

    /**
     * Constructor that takes the game instance.
     * @param tanktrouble The game instance.
     */
    public AllBullets(TankTrouble tanktrouble)
    {
        game = tanktrouble;
    }
    
    /**
     * This method updates all the bullets.
     */
    public void updateAllBullets(){
        for(int i = 0 ; i < bullets.size() ; i++)
        {
            temp = bullets.get(i);
            game.getPhysics().bulletWall(temp);
            game.getPhysics().bulletTank(temp);
            if(System.currentTimeMillis()-temp.timer>=temp.lifeSpan)
            {
                temp.tank.setShoot(true);
                eraseBullet(temp);
            }
            else
                temp.updateBullet();
        }
    }

    /**
     * This method renders all the bullets to the screen.
     * @param g Graphics object for rendering.
     */
    public void renderAllBullets(Graphics g){
        for(int i = 0 ; i < bullets.size() ; i++)
        {
            temp = bullets.get(i);
            temp.renderBullet(g);
        }
    }

    /**
     * This method adds new bullet to the list of bullets.
     * @param bulletNew The bullet to add.
     */
    public void insertBullet(Bullet bulletNew)
    {
        bullets.add(bulletNew);
    }

    /**
     * This method removes given bullet from the list of bullets.
     * @param bulletOld The bullet to remove.
     */
    public void eraseBullet(Bullet bulletOld)
    {
        bullets.remove(bulletOld); 
    }

}