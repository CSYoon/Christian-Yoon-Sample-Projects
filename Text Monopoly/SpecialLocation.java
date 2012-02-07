// Programmer:  Christian Yoon and Brian Nguyen
// Section:     1  
// Program:     Project 1
// Date:        September 24th, 2010
// Description: Class representing special locations on a standard Monopoly board

public class SpecialLocation extends BoardLocation
{
	private final int GAIN = 1;				// Variable to hold decision if a player gains money
	private final int LOSE = 2;				// Variable to hold decision if a player loses money
	private final int INCOMETAX = 200;		// Amount payed when landing on income tax location
	private final int LUXTAX = 75;			// Amount payed when landing on luxury tax location
	
	public SpecialLocation(String name, int location)
	// PRE:  name has to be initialized; 0 <= location <= Max board size(39)
	// POST: A SpecialLocation object has been constructed with data members name
	//			and location set to the values passed in the corresponding parameters
	{
		super(name, location);
	}
	
	public void IncoTax(Player player)
	// PRE:  player has to be initialized
	// POST: Subtracts the value of INCOMETAX from the player's money
	{
		player.SetMoney(player.GetMoney() - INCOMETAX);
	}
	
	public void LuxTax(Player player)
	// PRE:  player has to be initialized
	// POST: Subtracts the value of LUXTAX from the player's money
	{
		player.SetMoney(player.GetMoney() - LUXTAX);
	}
	
	@Override
	public String[] GetPlayerActions(Player player)
	// PRE:  player has to be initialized
	// POST: FCTVAL == array of options player has upon landing on
	//			this space, to be used in a menu in a user interface
	{
		String[] menu = new String[1];		// declaring an array to hold # of choices upon landing
		
		menu[0] = "Continue";				// only option a player has upon landing on this BoardLocation
		
		return menu;
	}
	
	public void GenerateCommChance(Player player)
	// PRE:  player has to be initialized
	// POST: Generates a random amount of money from 1-200 that a player wins or pays
	{
		int gainLose = (int)((Math.random()*2) + 1);		// determines if a player gains or loses 
															// 	money
		int money = (int)((Math.random() * 200) + 1);		// determines how much a player loses or 
															// 	gains
		
		if (gainLose == GAIN)								// Checks if player gains or loses money
		{
			player.SetMoney(player.GetMoney() + money);		// increases player's money by however much 
															// 	gained
			
			System.out.println("++++You have just gained $" + money + "++++");	// string representation 
																				// 	 of how much player 
																				// 	gained
		}
		else
		{
			player.SetMoney(player.GetMoney() - money);		// decreases player's money by however much 
															// 	 lost
			
			System.out.println("----You have just lost $" + money + "----");	// string representation of 
																				// 	 how much player 
																				// 	  lost
		}
	}
	
	public String toString()
	// POST: Returns a string representation of this SpecialLocation
	{
		return super.toString();
	}
}
