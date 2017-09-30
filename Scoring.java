import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.AffineTransformOp;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.geom.*;

/**
 * This class is used to keep track of scores of players.
 */
public class Scoring
{
    private TankTrouble game;
    private int score1;
    private int score2;
    private final int shiftX = -10;
    private final int shiftY = 60;
    
    /**
     * The constructor that takes the game instance.
     * @param tk Game instance.
     */
    public Scoring(TankTrouble tk)
    {
        game = tk;
    }
    
    /**
     * This method renders the scores for the screen.
     * @param g Graphics object for rendering.
     */
    public void renderScoring(Graphics g)
    {
        Font font = new Font("Arial", Font.PLAIN, 20);
        g.setFont(font);
        //  g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawString(game.getPlayer1().tankName,game.getMap().originX+game.getMap().w+100+shiftX,90+shiftY); 
        g.drawImage(game.getPlayer1().icon,game.getMap().originX+game.getMap().w+100+shiftX,120+shiftY,null);
        g.drawString(Integer.toString(score1),game.getMap().originX+game.getMap().w+150+shiftX,150+shiftY);
        g.drawString(game.getPlayer2().tankName,game.getMap().originX+game.getMap().w+100+shiftX,220+shiftY); 
        g.drawImage(game.getPlayer2().icon,game.getMap().originX+game.getMap().w+100+shiftX,250+shiftY,null);
        g.drawString(Integer.toString(score2),game.getMap().originX+game.getMap().w+150+shiftX,280+shiftY);
    }

    /**
     * This method increases score of given player.
     * @param a Integer value indicating the player to increase score.
     */
    public void increaseScore(int a)
    {
        if(a==1)
            score1++;
        if(a==2)
            score2++;
    }
}