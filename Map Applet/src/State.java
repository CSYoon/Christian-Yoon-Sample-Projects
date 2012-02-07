// Programmer:  Christian Yoon and Brian Nguyen
// Section:     1  
// Program:     Project 2, Map Applet
// Date:        November 4th, 2010
// Description: State class; entity on the map representing an area where people live
//				 Has a name, defined set of bound points, and a population

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class State extends MapObjects
{
    private final static Color DEFAULT = Color.GREEN;	// Default color of states
    private final static Font DEFAULTFONT = new Font("Serif",Font.PLAIN,15);
														//  by a population threshhold
    private Color stateColor;							// The actual color of the state
    private int population;								// Number of residents in the state
														//  in thousands

    public State()
	// POST: Creates an instance of the class setting all class members to empty values;
	//			numPts is set to 3; States must have at least 3 sides.
    {
        this("", null ,null , 0, 0, 3);
    }

    public State(String name, int[] xCoords, int[] yCoords, int appHeight, int appWidth, int numPts)
	// PRE:	 name, xCoords, yCoords are initialized; appHeight and appWidth > 0, and numPts > 2
	// POST: Sets class members name, xCoords, yCoords, appHeight, appWidth, and numPts to their
	//			corresponding parameters; class member population initialized to 0, color is default
    {
        super(name,xCoords,yCoords, appHeight, appWidth, numPts);

        population = 0;

        stateColor = DEFAULT;
    }

    public void SetPop(int population)
	// PRE:	 population must be initialized and must be a positive integer
	// POST: class member population is set equal to population
    {
        this.population = population;
    }

    public void SetColor(Color myColor)
	// PRE:	 myColor must be initialized
	// POST: class member stateColor is set to myColor
    {
        stateColor = myColor;
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
            realX[ct] = (int) (appWidth * scalePoints[ct].GetScaledX());
            realY[ct] = (int) (appHeight * scalePoints[ct].GetScaledY());
        }

        g.setColor(stateColor);				// Draw the state
        g.fillPolygon(realX,realY,numPts);

        g.setColor(Color.BLACK);			// Outline the state with a black border
        g.drawPolygon(realX,realY, numPts);
    }
	
    public void ShowDetails(Graphics g, int appHeight, int appWidth)
	// PRE:	 g must be from the Paint() method
	// POST: Outputs name and population to the GUI
    {
        g.setFont(DEFAULTFONT);		// Change the font for state details
        g.setColor(Color.WHITE);	// Draw in white
        
        // Draw the strings of the name and the population
        g.drawString(GetName(), GetCenterX(appWidth), GetCenterY(appHeight));
        g.drawString("("+GetPopulation()+")", GetCenterX(appWidth), GetCenterY(appHeight)+15);
    }

    public int GetPopulation()
	// POST: Returns the population of the state
    {
        return population;
    }

    @Override
    public String toString()
	// POST: Returns a string representation of the State; just it's name
    {
       return super.toString();
    }
}
