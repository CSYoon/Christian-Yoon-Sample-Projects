// Programmer:  Christian Yoon and Brian Nguyen
// Section:     1  
// Program:     Project 1
// Date:        September 24th, 2010
// Description: Class representing the locations or spaces on a standard Monopoly board.
//				 Meant to be subclassed

public abstract class BoardLocation
{
	protected int location;					// Which spot the location holds on the board
											//	Ex: Go = 0, BoardWalk = 39, etc.
	protected String name;					// Name of the place on the board

	public BoardLocation(String name, int location)
	// PRE:  name has to be initialized. 0 <= location <= Max board size(39)
	// POST: A BoardLocation object has been constructed with data members name
	//			set to the values passed in the corresponding parameters
	{
		this.location = location;
		this.name = name;
	}
	
	public abstract String[] GetPlayerActions(Player player);
	// PRE:  player is initialized
	// POST: FCTVAL == array of options player has upon landing on
	//			this space, to be used in a menu in a user interface

	public void SetName(String name)
	// PRE:  name has to be initialized
	// POST: sets class member name to value passed in parameter
	{
		this.name = name;
	}
	
	
	public String GetName()
	// POST: Returns class member name
	{
		return name;
	}
	
	public int GetLocation()
	// POST: Returns class member location
	{
		return location;
	}
	
	public String toString()
	// POST: Returns string representation of the location and name of this BoardLocation
	{
		return "(" + GetLocation() + ") Name: " + GetName() + " ";
	}
}
