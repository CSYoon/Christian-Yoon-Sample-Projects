// Programmer:  Christian Yoon and Brian Nguyen
// Section:     1  
// Program:     Project 2, Map Applet
// Date:        November 4th, 2010
// Description: Class that represents an entity on a map; each entity has a name
//				 and a set of points that define its geography

import java.awt.Graphics;		// Allows use of graphics and drawing

public abstract class MapObjects
{
    protected String name;					// The name of the object on the map
    protected ScaledPoint[] scalePoints;	// Array of percentages of the objects coordinates
											//  compared to the applet dimensions
    protected int[] xCoords;				// Array of the object's bounds x-coordinates
    protected int[] yCoords;				// Parallel array of the object's bounds y-coordinates
    protected int appHeight;				// Height of the applet
    protected int appWidth;					// Width of the applet
    protected int numPts;					// Number of points defining the bounds of the state
    protected int centerX;					// The center of the state's x-coordinate
    protected int centerY;					// The center of the state's y-coordinate
    protected ScaledPoint centerPoints;		// Scaled point of the center; to handle resizing

    public MapObjects()
	// POST: Creates an instance of the class that sets the class members to empty values,
	//			Class member numPts is set to 3, must have at least 3 points
    {
        this("", null, null, 0, 0, 3);
    }
	
    public MapObjects(String name, int[] xCoords, int[] yCoords, int appHeight, int appWidth, int numPts)
	// PRE:	 name, xCoords, and yCoords are initialized, appHeight and appWidth are positive integers and
	//			are representations of the applet dimensions, numPts is a positive integer > 2
    {
        this.name = name;
        this.xCoords = xCoords;
        this.yCoords = yCoords;
        this.appHeight = appHeight;
        this.appWidth = appWidth;
        this.numPts = numPts;
        centerX = 0;
        centerY = 0;
    }

    public void CalculateCenter()
	// POST: Calculates the center of the state or lake
    {
        for (int x = 0; x < numPts; x++)	// Calculates the coordinates using scaled values and applet
        {									//	 dimensions
            centerX += xCoords[x];
            centerY += yCoords[x];
        }

        centerX /= numPts;
        centerY /= numPts;

        // Creates a scaled point to scale the center
        centerPoints = new ScaledPoint (centerX, centerY, appHeight, appWidth);
    }

    public void CalculateScalePoints()
	// POST: Calculates the scaled coordinates of the object's bounds
    {
        scalePoints = new ScaledPoint[numPts];	// Initializes the array to the number of boundary points

        for (int ct = 0; ct < numPts; ct++)		// Populates the array of scaled points
        {
            scalePoints[ct] = new ScaledPoint(xCoords[ct], yCoords[ct], appHeight, appWidth);
        }
    }

    public int GetCenterX(int x)
	// POST: Returns the x-coordinate of the center of the object
    {
        return centerPoints.GetRealX(x);
    }

    public int GetCenterY(int y)
	// POST: Returns the y-coordinate of the center of the object
    {
        return centerPoints.GetRealY(y);
    }

    public String GetName()
	// POST: Returns the name of the object
    {
        return name;
    }

    public abstract void Draw(Graphics g, int centX, int centY);
	// PRE:	 g must be from the Paint() method, appHeight and appWidth are positive integers representing
	//			applet dimensions
	// POST: Draws the instance of the class to the GUI based on the points the user input
	
    public abstract void ShowDetails(Graphics g, int appHeight, int appWidth);
	// PRE:	 g must be from the Paint() method
	// POST: Outputs name and population to the GUI

    @Override
    public String toString()
    // POST: Returns a string representation of the object
    {
        return GetName();
    }
}
