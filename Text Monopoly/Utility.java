// Programmer:  Christian Yoon and Brian Nguyen
// Section:     1  
// Program:     Project 1
// Date:        September 24th, 2010
// Description: Class representing the utility properties on a standard Monopoly board

public class Utility extends Property
{
	private int diceRoll;	// Current total of dice to calculate rent
	
	public Utility(String name, int location, int cost,int rent)
	//PRE:  name has to be initialized; cost and rent >= 0; cost and rent in dollars
	//POST: A Utility object has been constructed with data members name, cost, and rent
	//      	set to the values passed in the corresponding parameters
	{
		super(name, location, cost, rent);
	}
	
	@Override
	public void BuyProperty(Player player)
	//PRE:	  player has to be initialized
	//POST: Sets this Utility to the player buying,
	//			subtracts the cost of this Utility from the players money,
	//			increments the number of utilities and property the player owns by 1,
	//			and adds the Utility property to the array of properties the player has.
	{
		SetPropOwner(player);

		player.SetMoney(player.GetMoney() - cost);
	}
	
	@Override
	public void SetPropOwner(Player propOwner)
	// PRE:  propOwner has to be initialized
	// POST: Sets class member propOwner to the value passed in the parameter
	//			sets propOwner's owner string to represent the owner of this Property,
	//			and increments total numer of properties and utilities owned
	{
		super.propOwner = propOwner;
		super.SetOwner(propOwner.GetToken());
		propOwner.SetProperty(this, propOwner.GetNumProperties());	
		propOwner.SetNumUtilities(propOwner.GetNumUtilities() + 1);
		propOwner.SetNumProperties(propOwner.GetNumProperties() + 1);
	}
		
	@Override
	public void CalculateRent()
	//POST: Sets the rent of this property to 4 or 10 times the player's dice roll
	//			based on whether the owner owns one or both utilities respectively
	{
		if (propOwner.GetNumUtilities() == 1)		// Checks the number of
		{											// Utilities
			super.SetRent(4 * GetDiceRoll());		// rent is equal to 4x diceRoll if
		}											//  owner owns 1 utility
		else if (propOwner.GetNumUtilities() == 2)
		{
			super.SetRent(10 * GetDiceRoll());		// rent is equal to 10x diceRoll if
		}											//  owner has 2 utilities
	}
	
	public void SetDiceRoll(int diceRoll)
	//PRE:	  diceRoll >= 2 (minimum number on a dice = 1 and there are 2 dices)
	//POST: Sets the current dice roll of the player to this Utility
	//			in order to calculate the Rent the player has to pay.
	{
		this.diceRoll = diceRoll;
	}
	





	public int GetDiceRoll()
	//PRE:	  diceRoll >= 2 (minimum number on a dice = 1 and there are 2 dices)
	//POST: Sets the current dice roll of the player to this Utility
	//			in order to calculate the Rent the player has to pay.
	{
		return diceRoll;
	}

	@Override
	public String toString()
	//POST: Returns a string representation of the name, cost and owner of this Utility
	{
		return super.toString() + "]";
	}
}
