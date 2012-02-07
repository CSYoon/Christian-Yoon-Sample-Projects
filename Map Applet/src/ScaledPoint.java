// Programmer:  Christian Yoon and Brian Nguyen
// Section:     1  
// Program:     Project 2, Map Applet
// Date:        November 4th, 2010
// Description: Class that takes a state's or lake's defined set of boundaries
//				 and scales it to the applet size, changing the map object's size
//				 to match the applet's chnaging size

public class ScaledPoint
{
    private double scaledX;		// The percent of the applet for the x-coordinates location
    private double scaledY;		// The percent of the applet for the y-coordinates location

    public ScaledPoint()
	// POST: Creates an instance of the class where class members are set to 0
	//			appHeight and appWidth are passed as 1 to avoid divide by 0
    {
        this(0, 0, 1, 1);
    }

    public ScaledPoint(int xCoord, int yCoord, int appHeight, int appWidth)
	// PRE:	 xCoord, yCoord, appHeight, and appWidth are all positive integers
	//			appHeight and appWidth represent the dimensions of the applet
	// POST: class members scaledX and scaledY are calculated based on the current x and
	//			y coordinates and the dimensions of the applet
    {
        scaledX = xCoord / ((double)appWidth);
        scaledY = yCoord / ((double)appHeight);
    }

    public double GetScaledX()
	// POST: Returns the scaled x-coordinate's percentage of the applet dimensions
    {
        return scaledX;
    }

    public double GetScaledY()
	// POST: Returns the scaled y-coordinate's percentage of the applet dimensions
    {
        return scaledY;
    }

    public int GetRealX(int appWidth)
	// PRE:	 appWidth must be a positive integer that represents the width of the applet
	// POST: Returns the new actual x-coordinate based on the percent and the new applet width
    {
        return (int) (scaledX * appWidth);
    }

    public int GetRealY(int appHeight)
	// PRE:	 appWidth must be a positive integer that represents the height of the applet
	// POST: Returns the new actual y-coordinate based on the percent and the new applet height
    {
        return (int) (scaledY * appHeight);
    }
}
