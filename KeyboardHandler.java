import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/**
 * This class is the keyboard handler for the program
 */
public class KeyboardHandler extends KeyAdapter
{
    /** Game instance for the keyboard handler */
    private TankTrouble game;
    
    /** Constructor that takes the game instance*/
    public KeyboardHandler(TankTrouble tanktrouble)
    {
        game = tanktrouble;
    }
    
    /** This method handles the key press using game instance's method */ 
    public void keyPressed(KeyEvent event)
    {
        game.keyPressed(event);
    }
    
    /** This method handles the key release using game instance's method */ 
    public void keyReleased(KeyEvent event)
    {
        game.keyReleased(event);
    }
}