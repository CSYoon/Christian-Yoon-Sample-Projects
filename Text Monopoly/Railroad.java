// Programmer:  Christian Yoon and Brian Nguyen
// Section:     1  
// Program:     Project 1
// Date:        September 24th, 2010
// Description: Class representing the railroad properties on a standard Monopoly board

public class Railroad extends Property
{
	private static final int RRBASERENT = 25;		// Base rent of railroads

	public Railroad(String name, int location, int cost)
	//PRE:	  name has to be initialized, cost >= 0, cost is in dollars
	//POST: A Railroad object has been constructed with data members name and cost
	//			set to the values passed in the corresponding parameters
	//			and rent equal to the RRBASERENT that is same throughout the instance
	//			of this class
	{
		super(name, location, cost, RRBASERENT);
	}
	
	@Override
	public void BuyProperty(Player player)
	//PRE:	  player has to be initialized
	//POST: Sets this Railroad to the player buying,
	//			subtracts the cost of this Railroad from the players money,
	//			increments the number of railroads and property the player owns by 1,
	//			and adds the Railroad property to the array of properties the player has.
	{
		SetPropOwner(player);

		player.SetMoney(player.GetMoney() - cost);
	}
	
	@Override
	public void CalculateRent()
	//POST: Sets the rent of this railroad equal to the number of railroads
	//		 	the Railroad owner (propOwner) has 
	{
		for (int x = 0; x < propOwner.GetNumRailroads() - 1; x++)		// Checks # of
		{																// RR to calculate rent		
			super.SetRent(super.GetRent()*2);
		}

		rent = RRBASERENT;
	}
	
	@Override
	public void SetPropOwner(Player propOwner)
	// PRE:	 propOwner has to be initialized
	// POST: Sets class member propOwner to the value passed in the parameter
	//			sets propOwner's owner string to represent the owner of this Property,
	//			and increments total numer of properties and railroads owned
	{
		super.propOwner = propOwner;
		super.SetOwner(propOwner.GetToken());
		propOwner.SetProperty(this, propOwner.GetNumProperties());	
		propOwner.SetNumRailroads(propOwner.GetNumRailroads() + 1);
		propOwner.SetNumProperties(propOwner.GetNumProperties() + 1);
	}
	
	@Override
	public String toString()
	//POST:	Returns a string representation of the name of the Railroad, owner of the Railroad,
	//				and the cost to buy the Railroad
	{
		return super.toString() + "]";
	}
}
