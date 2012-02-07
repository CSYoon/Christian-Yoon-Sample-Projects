// Programmer:  Christian Yoon and Brian Nguyen
// Section:     1  
// Program:     Project 1
// Date:        September 24th, 2010
// Description: Class representing the corner squares on a standard Monopoly board

public class CornerSquare extends BoardLocation
{

	public CornerSquare(String name, int location)
	// PRE:  name and go has to be initialized
	// POST: A CornerSquare object has been constructed with data members name and go
	//       	set to the values passed in the corresponding parameters
	{
		super(name, location);
	}
	
	@Override
	public String[] GetPlayerActions(Player player)
	// PRE:	player has to be initialized
	// POST:	FCTVAL == array of options player has upon landing on
	//				this space, to be used in a menu in a user interface
	{
		String[] menu = new String[1];
		
		menu[0] = "Continue";
		
		return menu;
	}
	
	/*
	public void PayPlayer(Player player)
	// PRE:	player has to be initialized
	// POST:	Increases the player's money by amount earned when passing Go
	{
		player.SetMoney(player.GetMoney() + GO);
	}
	*/
	
	
	public String toString()
	// POST:	Returns a string representation of the name of the CornerSquare
	{
		return super.toString();
	}
}
