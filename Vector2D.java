/**
 * This class used to do some vector calculations in 2D.
 */
public class Vector2D
{
    private double[] comps;

    /**
     * Constructor that takes components.
     * @param acomps Components for the object.
     */
    public Vector2D(double[] acomps)
    {
        comps = acomps;
    }

    /**
     * Constructor that takes angle and magnitude to create a vector
     * @param 
     */
    public Vector2D(double angle, double mag) //constructs a  vector from angle and magnitude(enter angle in radians)
    {
        comps = new double[]{Math.cos(angle)*mag,Math.sin(angle)*mag};
    }

    public double dotPro(Vector2D b)//returns the dot product of two vectors
    {
        return (this.getXcomp()*b.getXcomp() + this.getYcomp()*b.getYcomp());
    }

    public Vector2D add(Vector2D b)//returns the addtion of two vectors
    {
        return (new Vector2D(new double[]{(this.getXcomp()+b.getXcomp()),(this.getYcomp()+b.getYcomp())}));
    }

    public Vector2D subtract(Vector2D b)//returns the subtraction of a vector from another
    {
        return (new Vector2D(new double[]{(this.getXcomp()-b.getXcomp()),(this.getYcomp()-b.getYcomp())}));
    }

    public void mult(double cons)//multiplies the vector magnitude by a given constant
    {
        //return (new Vector2D(new double[]{(this.getXcomp()*cons),(this.getYcomp()*cons)}));
        comps[0] *= cons;
        comps[1] *= cons;
    }

    public void rotate(double angInc)//rotates a vector a certain number of radians
    {
        double angle = getAngle() + angInc;
        double radius = mag();
        comps[0]=radius*Math.cos(angle);
        comps[1]=radius*Math.sin(angle);
    }

    public double mag()//returns the vector magnituded
    {
        return Math.sqrt(comps[0]*comps[0] + comps[1]*comps[1]);
    }

    public Vector2D getUnit()//retunrs the unit vector of the given vector
    {
        double mag = mag();
        return (new Vector2D(new double[]{comps[0]/mag,comps[1]/mag}));
    }

    public double getAngle()//Radians
    {
        Vector2D unitted = getUnit();
        double angle = Math.acos(unitted.getXcomp()); //will be between 0 and PI
        if (comps[1]<0) //if the y-component is negative this won't suffice
        {
            angle = (-angle + 2*Math.PI) % (2*Math.PI); //then use the reflection of the angle on y-axis
        }
        return angle; //now can be between 0 and 2*PI
    }

    public double getXcomp() 
    {
        return comps[0];
    }

    public double getYcomp() 
    {
        return comps[1];
    }

    public double[] getVector() 
    {
        return comps;
    }
}