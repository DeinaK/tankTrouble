import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.AffineTransformOp;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.geom.*;
import java.util.LinkedList;

/**
 * This class is used to construct, initialize and render the map of the game.
 */
public class Map
{

    public final int w = 700; // width
    public final int h = 700; // height

    /*Origin Coordinates*/
    public int originX; 
    public int originY;

    /*Wall Width*/
    public int wallWidth=10;

    public int randomNumber; // Random Number

    public TankTrouble game;
    public LinkedList<Rectangle2D.Double> walls = new LinkedList<Rectangle2D.Double>();
    public LinkedList<Integer> wallType = new LinkedList<Integer>();
    private BufferedImage background;

    private final int mazeWIDTH = 7; // Maze Width 700
    private final int mazeHEIGHT = 7;  // Maze Height 700

    private final int MAXSIZE = (mazeWIDTH > mazeHEIGHT) ? mazeWIDTH : mazeHEIGHT; // array size 700*700 pixel //DIMENSION
    // private int arr[][] = new int [MAXSIZE][MAXSIZE];

    private final int MAZEBINARYSIZE = 3*MAXSIZE+2;

    public int [][] mazeBinary = new int [MAZEBINARYSIZE][MAZEBINARYSIZE]; 

    private boolean[][] northMaze;     
    private boolean[][] eastMaze;      
    private boolean[][] southMaze;
    private boolean[][] westMaze;
    private boolean[][] visited;
    private boolean doneMaze = false;

    /**
     * Constructor that uses given x,y coordinates and the game instance.
     * @param x X-coordinate
     * @param y Y-coordinate
     * @param tanktrouble The game instance.
     */
    public Map(int x, int y, TankTrouble tanktrouble) throws ArrayIndexOutOfBoundsException
    {
        originX = x;
        originY = y;
        try 
        {
            background = ImageIO.read(new File("src/gamebackground.png"));
        } catch (IOException e) {
            System.out.println("icon not found");
        }
        game = tanktrouble;

        mazeInit(); 
        generateMaze();

        for (int i = 1; i<=mazeHEIGHT; i++)
        {
            for (int j = 1; j<=mazeWIDTH; j++)
            {

                //LEFT BOUND
                if (southMaze[i][j])
                {
                    //  walls.add(new Rectangle2D.Double(originX + (j-1)*100-10, originY+(i-1)*100, wallWidth,110));
                    walls.add(new Rectangle2D.Double(originX + (j-1)*100, originY+(i-1)*100, wallWidth,110));
                    mazeBinary[2*(j-1)][2*(i-1)+2] = 1;
                    wallType.add(1);
                } 

                //RIGHT BOUND
                if (northMaze[i][j])
                { // 
                    // walls.add(new Rectangle2D.Double(originX + (j-1)*100-10, originY+(i-1)*100-wallWidth, wallWidth,110));
                    walls.add(new Rectangle2D.Double(originX + (j)*100, originY+(i-1)*100, wallWidth,110));
                    mazeBinary[2*(j-1)][2*(i-1)] = 1;
                    wallType.add(1);
                }
                if (eastMaze[i][j])
                {
                    //  walls.add(new Rectangle2D.Double(originX + (j-1)*100, originY +(i-1)*100-10, 110,wallWidth));
                    walls.add(new Rectangle2D.Double(originX + (j-1)*100, originY +i*100, 110,wallWidth));
                    mazeBinary[2*(j-1)][2*(i-1)+1] = 1;
                    wallType.add(0);
                } 
                if (westMaze[i][j])
                {
                    // walls.add(new Rectangle2D.Double(originX + j*100, originY +i*100-wallWidth-10, 110,wallWidth));
                    walls.add(new Rectangle2D.Double(originX + (j-1)*100, originY +(i-1)*100, 110,wallWidth));
                    mazeBinary[2*(j-1)+2][2*(i-1)+1] = 1;
                    wallType.add(0);
                }

       
            }
        }
    }

    /**
     * This method generates a maze to construct the map. It uses DFS to generate a maze.
     * @param xCoord X-coordinate
     * @param yCoord Y-coordinate
     */
    public void generateMaze (int xCoord, int yCoord){
        visited[xCoord][yCoord] = true;

        while (!visited[xCoord][yCoord+1] || !visited[xCoord][yCoord-1] || !visited[xCoord+1][yCoord] || !visited[xCoord-1][yCoord])
        {
            while (true)
            {
                randomNumber = (int) (Math.random()*4+1);

                if (randomNumber == 1 && !visited[xCoord][yCoord+1])
                {
                    northMaze[xCoord][yCoord] = southMaze [xCoord][yCoord+1] = false;
                    generateMaze (xCoord, yCoord+1);
                    break;
                }

                else if (randomNumber== 2 && !visited[xCoord+1][yCoord])
                {
                    eastMaze[xCoord][yCoord] = westMaze [xCoord+1][yCoord] = false;
                    generateMaze (xCoord+1, yCoord);
                    break;
                }

                else if (randomNumber== 3 && !visited[xCoord][yCoord-1])
                {
                    southMaze[xCoord][yCoord] = northMaze [xCoord][yCoord-1] = false;
                    generateMaze (xCoord, yCoord-1);
                    break;
                }
                else if (randomNumber == 4 && !visited[xCoord-1][yCoord])
                {
                    westMaze[xCoord][yCoord] = eastMaze [xCoord-1][yCoord] = false;
                    generateMaze (xCoord-1, yCoord);
                    break;
                }

            }
        }

    }

    /**
     * This method calls the utility method to generate a maze from coordinates (1,1).
     */
    public void generateMaze (){
        generateMaze (1,1);
    }

    /**
     * This method initializes the maze, outer walls and inside walls.
     */
    public void mazeInit (){
        /* initialize*/
        visited = new boolean [MAXSIZE+2][MAXSIZE+2];

        /*BORDERLINE*/
        /**Outer walls**/
        for (int i = 0; i < mazeWIDTH+2; i++)
        {
            visited[i][0] = visited[i][mazeWIDTH+1] = true;
        }
        for (int j = 0; j<mazeHEIGHT+2; j++)
        {
            visited[0][j] = visited[mazeHEIGHT+1][j] = true;
        }

        /**Inside Walls**/
        northMaze = new boolean [MAXSIZE+2][MAXSIZE+2]; //Checks whether or not there is a wall on the north side of the all the points in the maze
        southMaze = new boolean [MAXSIZE+2][MAXSIZE+2]; //Checks whether or not there is a wall on the south side of the all the points in the maze
        eastMaze = new boolean [MAXSIZE+2][MAXSIZE+2]; //Checks whether or not there is a wall on the east side of the all the points in the maze
        westMaze = new boolean [MAXSIZE+2][MAXSIZE+2]; //Checks whether or not there is a wall on the west side of the all the points in the maze

        for (int i = 0; i<mazeHEIGHT+2; i++){
            for (int j = 0; j<mazeWIDTH+2; j++)
            {
                northMaze [i][j] = southMaze [i][j] = eastMaze [i][j] = westMaze [i][j] = true;
            }
        }
    }

    /**
     * This method renders the map of the game.
     * @param g Graphics object for rendering.
     */
    public void renderMap(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(background,0,0,null);

        g2.setColor (Color.BLACK);

        for(int i = 0; i<walls.size();i++)
        {
            g2.fill(walls.get(i));
        }
    }
}