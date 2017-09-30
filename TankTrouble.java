import javax.swing.JFrame;
import java.awt.*;
import java.awt.Dimension;
import java.awt.image.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.applet.AudioClip;
import java.applet.Applet;
import java.util.Random;

/**
 * Main class of the game. All the setup for the game, including tank objects, screen, physics and scoring initialized in this class.
 * It extends Canvas for GUI and implements Runnable for threads. 
 */
public class TankTrouble extends Canvas implements Runnable
{
    /** Screen width */
    public static final int WIDTH = 1300; // screen width
    /** Screen height */
    public static final int HEIGHT = 768; // screen height
    /** Screen title */
    public final String TITLE = "TankTrouble v2.0";
    /** Global velocity of the tank objects */
    public static final int VEL = 3;
    /** Global rotation degree of the tank objects*/
    private final double ROTDEG = 3;
    /** A boolean variable to check wheter or not the program is running */
    private boolean isRunning = false;
    /** Colors to choose for the tank objects*/
    private String[] colors = {"yellow","turkuaz","red","purple","grey","green","blue"};

    public Thread thread; // For more info about threads: http://docs.oracle.com/javase/tutorial/essential/concurrency/sleep.html

    /** Menu object of the program. */
    private Menu menu;

    /** Credits object of the program*/
    private Credits credits;

    /** Map object of the program */
    private Map map;

    /** Physics object of the program */
    private Physics physics;

    /** Scoring object of the program */
    private Scoring scoring;

    /** BulletChanger object of the game */
    private BulletChanger bulletChanger;

    /** BufferedImage object to render the correct image */
    private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);

    /** Tank object for player 1*/
    private Tank player1;
    /** Bomb reference for player 1*/
    private Bomb bombRef1;
    /** Machine gun reference for player 1*/
    private MachineGun machRef1;
    /** TrollRocket reference for player 1*/
    private TrollRocket rocket1;
    /** Laser reference for player 1*/
    private Laser laserRef1;

    /** Tank object for player 2*/
    private Tank player2;

    /** Bomb reference for player 2*/
    private Bomb bombRef2;

    /** Machine gun reference for player 2*/
    private MachineGun machRef2;

    /** TrollRocket reference for player 2*/
    private TrollRocket rocket2;

    /** Laser reference for player 2*/
    private Laser laserRef2;

    /** AllBullets object for the game, keeps the track of bullets for both of the players */
    private AllBullets b;

          
    /**
     * Enumerator for the state of the game, i.e whether the game's started or not
     */
    public enum STATE
    {
        MENU,
        GAME,
        CREDITS
    };

    public STATE state = STATE.MENU; // state = MENU 

    /**
     * Main method of the program. Creates the game and starts the program.
     * @param args String array of command line arguments.
     */
    public static void main(String[] args)
    {
        //create the game instance
        TankTrouble game = new TankTrouble();

        //set dimensions of the game screen
        game.setPreferredSize(new Dimension(WIDTH,HEIGHT));

        game.setMaximumSize(new Dimension(WIDTH,HEIGHT));

        game.setMinimumSize(new Dimension(WIDTH,HEIGHT)); 

        //initialize the JFrame
        JFrame frame = new JFrame(game.TITLE);

        //add game instance to JFrame
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //start the game
        game.start();

    }
    
    private synchronized void start()
    {
        //do nothing if the game is running
        if(isRunning)
        {
            return;
        }

        //set isRunning to true
        isRunning =  true;

        //initialize the thread and start
        thread = new Thread(this);
        thread.start();
        //starting the thread will look for and run the run() method, since the TankTrouble object is runnable
    }

    /**
     * This method handles the each frame in the game, rendering the scene and updating them as players input commands. It is
     * responsible for initializing the game and keep it running.
     */
    public void run()
    {
        // initialize the game
        initializeGame();

        // get the current time
        long lastTime = System.nanoTime();

        // frames per second, set to 60 fps
        final double amountPerSecond = 60.0;

        // n is nanoseconds to wait before updating the game
        double n = 1000000000 / amountPerSecond;

        // interval is used to decide whether we need to update the game or not
        double interval = 0;

        // keep the track of updates
        int updates = 0;
        // keep the track of frames
        int frames = 0;
        // keep the track of time
        long timer = System.currentTimeMillis();

        // keep updating as long as the game is running
        while(isRunning)
        {
            //get current time
            long timeNow = System.nanoTime();
            interval += (timeNow - lastTime) / n;
            lastTime=timeNow;

            //update the game if the enough time is passed.
            if(interval>=1)
            {
                //System.out.println("Working");
                updateGame();
                updates++;
                interval--;
            }

            // render the game once it's updated (if it needs to be updated)
            renderGame();

            // increase the # of frames
            frames++;

            //
            if(System.currentTimeMillis() - timer > 1000)
            {
                timer+=1000;
                //System.out.println(updates + " ticks, fps " + frames);
                updates = 0;
                frames = 0;
            }
        }

    }

    /**
     * This method initializes the game, adding key mouse and mouse motion listeners, physics and scoring objects to the game.
     * It initializes the tanks and bullet references for each tank.
     */
    private void initializeGame()
    {
        requestFocus(); // for more info: https://answers.yahoo.com/question/index?qid=20100201110043AAdu0ic

        /*Listeners*/
        addKeyListener(new KeyboardHandler(this)); 
        addMouseListener(new MouseHandler(this));
        addMouseMotionListener(new MouseHandler(this));

        // Initialize Menu, Credits, BulletChanger, Map, Physics and Scoring instances.
        menu = new Menu();
        credits = new Credits(0,0,this);
        bulletChanger = new BulletChanger();
        map = new Map(0,0,this);
        physics = new Physics(this);
        scoring = new Scoring(this);

        //pick random colors for the tanks
        int colorRandom = (int) (Math.random()*7);
        int colorRandom2 = (int) (Math.random()*7);

        //make sure tanks have different colors
        if(colorRandom==colorRandom2)
        {
            colorRandom+=1;
            colorRandom%=7;
        }

        //initialize the tanks for each player.
        player1 = new Tank("Tank 1: Player1", colors[colorRandom],0,this);
        player2 = new Tank("Tank 2: Player2", colors[colorRandom2],0,this);

        //initialize all bullet references for each player
        bombRef1 = new Bomb(0,0,this,player1);
        bombRef2 = new Bomb(0,0,this,player2);
        bombRef1.setExplosion(true);
        bombRef2.setExplosion(true);
        machRef1 = new MachineGun(0,0,this,player1);
        machRef2 = new MachineGun(0,0,this,player2);
        machRef1.isActive = false;
        machRef2.isActive = false;
        rocket1 = new TrollRocket (0, 0, this, player1, player2);
        rocket2 = new TrollRocket (0, 0, this, player2, player1);
        laserRef1 = new Laser(0,0,this,player1);
        laserRef2 = new Laser(0,0,this,player2);
        laserRef1.isReady = true;
        laserRef2.isReady = true;

        //initialize all bullets instance for the game
        b = new AllBullets(this);

    }

    /**
     * This method updates the tanks and bullets on the screen.
     */
    private void updateGame()
    {
        // MENU and CREDITS state doesn't require updates. Update only if the state is set to GAME
        if(state == STATE.GAME)
        {
            // update tanks
            player1.updateTank();
            player2.updateTank();

            // update bullets
            b.updateAllBullets();

            //System.out.println(b.bullets.size());
            //System.out.println(player1.getRotation() + " rot");
        }
    }

    /** This method renders the game and its objects to the screen */
    private void renderGame()
    {
        BufferStrategy bs = this.getBufferStrategy();

        if(bs == null)
        {
            createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        // if the state is GAME, render map, tanks, bullets, scoring
        if(state == STATE.GAME)
        {
            g.drawImage(image,0,0,getWidth(),getHeight(),this);
            map.renderMap(g);
            player1.renderTank(g);
            player2.renderTank(g);
            b.renderAllBullets(g);
            bulletChanger.renderChanger(g);
            scoring.renderScoring(g);
        }
        // if the state is MENU, render the menu
        else if(state == STATE.MENU)
        {
            g.drawImage(image,0,0,getWidth(),getHeight(),this);
            menu.renderMenu(g);
        }
        // if the state is CREDITS, render the credits
        else if (state == STATE.CREDITS)
        {
            g.drawImage(image,0,0,getWidth(),getHeight(),this);
            credits.renderCredits(g);
        }

        //dispose graphics and show the buffer strategy object
        g.dispose();
        bs.show();

    }

    /** This method stops the program. */    
    private synchronized void stop()
    {
        long lastTime = System.nanoTime();

        //if the game is not running, no need to stop, return
        if(!isRunning)
        {
            return;
        }

        // stop the game
        isRunning = false;
        try 
        {
            thread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        System.exit(1);
    }

    /**
     * This method restarts the game. 
     */
    public void restart()
    {
        Random rand = new Random();

        //restart the tank 1 and position it randomly
        player1.setRotation(0);
        player1.setX(map.originX + rand.nextInt(7)*100 + 40);
        player1.setY(map.originY + rand.nextInt(7)*100 + 40);
        player1.setShoot(true);
        player1.changeBulletType(0);

        //restart the tank 2 and position it randomly
        player2.setRotation(0);
        player2.setX(map.originX + rand.nextInt(7)*100 + 40);
        player2.setY(map.originY + rand.nextInt(7)*100 + 40);
        player2.setShoot(true);
        player2.changeBulletType(0);

        //erase all bullets
        for(int i=0;i<b.bullets.size();i++)
        {
            Bullet temp = b.bullets.get(i);
            b.eraseBullet(temp);
        }
    }

    /**
     * This method changes the state of the program to GAME
     */
    public void setGame()
    {
        state = STATE.GAME;
    }

    /**
     * This method changes the state of the program to CREDITS
     */
    public void setCredits(){
        state = STATE.CREDITS;
    }

    /**
     * This method changes teh state of the program to MENU
     */
    public void setMenu()
    {
        state = STATE.MENU;
    }

    /** This method handles the keyboard inputs (key events). */      
    public void keyPressed(KeyEvent event)
    {
        if(state == STATE.GAME)
        {
            // get the pressed key
            int pressed = event.getKeyCode();

            // if the pressed key is directional for player 1 tank, update the tank accordingly
            if(pressed == KeyEvent.VK_RIGHT)
            {
                player1.addRotationDegree(ROTDEG);
            }
            else if (pressed == KeyEvent.VK_LEFT)
            {
                player1.addRotationDegree(-ROTDEG);
            }
            else if (pressed == KeyEvent.VK_DOWN)
            {
                player1.setVelocity(VEL);
            }
            else if (pressed == KeyEvent.VK_UP)
            {
                player1.setVelocity(-VEL);
            }

            // if the pressed key is shooting key for player 1
            else if (pressed == KeyEvent.VK_M)
            {
                //if there is a bomb of player1, spark it
                if (!bombRef1.getExplosion()&&!bombRef1.getSparked()) 
                {
                    bombRef1.spark();
                }

                //if player1 has a lazer, ready it
                else if (!laserRef1.isReady)
                {
                    laserRef1.isReady = true;
                }

                //if player1 can shoot, add new bullet
                else if(player1.hasShoot())
                {
                    //declare new bullet
                    Bullet temp;
                    //add shoot to player1
                    player1.addShoot(1);

                    //create new reference according to player1's bullet type
                    switch(player1.bulletType)
                    {
                        case 0:
                        temp = new Bullet(player1.getX(),player1.getY(),this,player1);
                        break;
                        case 1:
                        bombRef1 = new Bomb(player1.getX(),player1.getY(),this,player1); 
                        temp=bombRef1;
                        break;
                        case 2: 
                        machRef1 = new MachineGun(player1.getX(),player1.getY(),this,player1); 
                        temp=machRef1;
                        break;
                        case 3: 
                        temp = new Mine(player1.getX(),player1.getY(),this,player1);
                        break;
                        case 4: 
                        rocket1 = new TrollRocket(player1.getX(),player1.getY(),this,player1, player2); 
                        temp=rocket1;
                        break;
                        case 5: 
                        laserRef1 = new Laser(player1.getX(),player1.getY(),this,player1); 
                        temp=laserRef1;
                        break;
                        default: 
                        temp = new Bullet(player1.getX(),player1.getY(),this,player1);
                        break;
                    }

                    //add new bullet to all bullets
                    b.insertBullet(temp);
                    //set player1 so that it can't shoot
                    player1.setShoot(false);
                }
            }

            // if the pressed key is directional for player 2 tank, update the tank accordingly
            else if(pressed == KeyEvent.VK_F)
            {
                player2.addRotationDegree(ROTDEG);
            }
            else if (pressed == KeyEvent.VK_S)
            {
                player2.addRotationDegree(-ROTDEG);
            }
            else if (pressed == KeyEvent.VK_D)
            {
                player2.setVelocity(VEL);
            }
            else if (pressed == KeyEvent.VK_E)
            {
                player2.setVelocity(-VEL);
            }

            // if the pressed key is shooting key for player 2
            else if (pressed == KeyEvent.VK_Q)
            {
                //if there is a bomb of player2, spark it
                if (!bombRef2.getExplosion()&&!bombRef2.getSparked())
                {
                    bombRef2.spark();
                }
                //if player2 has a lazer, ready it
                else if (!laserRef2.isReady)
                {
                    laserRef2.isReady = true;
                }
                //if player2 can shoot, add new bullet
                else if(player2.hasShoot())
                {
                    //declare new bullet
                    Bullet temp;
                    //add shoot to player 2
                    player2.addShoot (1);

                    //create new reference according to player1's bullet type
                    switch(player2.bulletType)
                    {
                        case 0: 
                        temp = new Bullet(player2.getX(),player2.getY(),this,player2);
                        break;
                        case 1: 
                        bombRef2 = new Bomb(player2.getX(),player2.getY(),this,player2);
                        temp=bombRef2;
                        break;
                        case 2: 
                        machRef2 = new MachineGun(player2.getX(),player2.getY(),this,player2);
                        temp=machRef2;
                        break;
                        case 3:
                        temp = new Mine(player2.getX(),player2.getY(),this,player2);
                        break;
                        case 4: 
                        rocket2 = new TrollRocket(player2.getX(),player2.getY(),this,player2, player1); 
                        temp=rocket2;
                        break;
                        case 5: 
                        laserRef2 = new Laser(player2.getX(),player2.getY(),this,player2); 
                        temp=laserRef2;
                        break;
                        default:
                        temp = new Bullet(player2.getX(),player2.getY(),this,player2);
                        break;
                    }

                    //add new bullet to all bullets
                    b.insertBullet(temp);
                    //set player2 so that it can't shoot
                    player2.setShoot(false);
                }
            }
        }
    }

    /** 
     * This method handles the key release events.
     */
    public void keyReleased(KeyEvent event)
    {
        //key release affects the program only if the state is GAME
        if(state==STATE.GAME)
        {
            //get released key
            int pressed = event.getKeyCode();

            //if key is directional for player 1, set rotation and velocity to 0
            if(pressed == KeyEvent.VK_RIGHT)
            {
                player1.addRotationDegree(0);
            }
            else if (pressed == KeyEvent.VK_LEFT)
            {
                player1.addRotationDegree(0);
            }
            else if (pressed == KeyEvent.VK_DOWN)
            {
                player1.setVelocity(0);
            }

            else if (pressed == KeyEvent.VK_UP)
            {
                player1.setVelocity(0);
            }

            //erase machine gun bullets for player1
            else if ((pressed == KeyEvent.VK_M)&&(machRef1.isActive))
            {
                b.eraseBullet(machRef1);
            }

            //if key is directional for player 2, set rotation and velocity to 0
            else if(pressed == KeyEvent.VK_F)
            {
                player2.addRotationDegree(0);
            }
            else if (pressed == KeyEvent.VK_S)
            {
                player2.addRotationDegree(0);
            }
            else if (pressed == KeyEvent.VK_D)
            {
                player2.setVelocity(0);
            }
            else if (pressed == KeyEvent.VK_E)
            {
                player2.setVelocity(0);
            }

            //erase machine gun bullets for player2
            else if ((pressed == KeyEvent.VK_Q)&&(machRef2.isActive))//NEW RELEASE
            {
              b.eraseBullet(machRef2);
            }
        }
    }

    /**
     * This is accessor method for the menu object.
     * @return Menu object.
     */
    public Menu getMenu()
    {
        return this.menu;
    }

    /**
     * This is accessor method for the map object.
     * @return Map object
     */
    public Map getMap()
    {
        return this.map;
    }

    /**
     * This is accessor method for the physics object.
     * @return Physics object
     */
    public Physics getPhysics()
    {
        return this.physics;
    }

    /**
     * This is accessor method for the scoring object.
     * @return Scoring object.
     */
    public Scoring getScoring()
    {
        return this.scoring;
    }

    /**
     * This is accessor method for the credits object.
     * @return Credits object.
     */
    public Credits getCredits()
    {
        return this.credits;
    }
    
    /**
     * This is accessor method for the all bullets object.
     * @return All bullets object
     */
    public AllBullets getB()
    {
        return this.b;
    }
    
    /**
     * This is accessor method for the player1 object.
     * @return Tank object
     */
    public Tank getPlayer1()
    {
        return this.player1;
    }
    
    /**
     * This is accessor method for the player2 object.
     * @return Tank object
     */
    public Tank getPlayer2()
    {
        return this.player2;
    }
    
    /**
     * This is accessor method for the bullet changer.
     * @return BulletChanger object
     */
    public BulletChanger getBulletChanger()
    {
        return this.bulletChanger;
    }
}