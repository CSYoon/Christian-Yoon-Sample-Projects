// Programmer:  Christian Yoon and Brian Nguyen
// Section:     1  
// Program:     Project 2, Map Applet
// Date:        November 4th, 2010
// Description: Lake class; entity on the map representing an area where there is a
//				 body of water; has a name and a defined set of bound points

import java.awt.Color;		// Allows use of colors
import java.awt.Graphics;	// Allows use of graphics and drawing

public class Lake extends MapObjects
{
    public Lake()
	// POST: Creates an instance of the class that sets the class members to empty values,
	//			Class member numPts is set to 3, must have at least 3 points
    {
        this("", null, null, 0, 0, 0);
    }

    public Lake(String name, int[] xCoords, int[] yCoords, int appHeight, int appWidth, int numPts)
	// PRE:	 name, xCoords, yCoords are initialized; appHeight and appWidth > 0, and numPts > 2
	// POST: Sets class members name, xCoords, yCoords, appHeight, appWidth, and numPts to their
	//			corresponding parameters
    {
        super(name, xCoords, yCoords, appHeight, appWidth, numPts);
    }

    public void Draw(Graphics g, int appHeight, int appWidth)
	// PRE:	 g must be from the Paint() method, appHeight and appWidth are positive integers representing
	//			applet dimensions
	// POST: Draws the instance of the class to the GUI based on the points the user input
    {
        int[] realX = new int[numPts];		// Parallel scaled arrays that are calculated by the
        int[] realY = new int[numPts];		//	ScaledPoints class

        for (int ct = 0; ct < numPts; ct++)	// Calculates the arrays of scaled points to
        {									//	handle window resizing
            realX[ct] = scalePoints[ct].GetRealX(appWidth);
            realY[ct] = scalePoints[ct].GetRealY(appHeight);
        }

        g.setColor(Color.BLUE);					// Draw the lake in blue
        g.fillPolygon(realX, realY, numPts);

        g.setColor(Color.BLACK);				// Outline the lake in black
        g.drawPolygon(realX, realY, numPts);
    }

    public void ShowDetails(Graphics g, int appHeight, int appWidth)
	// PRE:	 g must be from the Paint() method
	// POST: Outputs name and population to the GUI
    {
        g.setColor(Color.WHITE);
        g.drawString(GetName(), GetCenterX(appWidth), GetCenterY(appHeight));
    }
}
