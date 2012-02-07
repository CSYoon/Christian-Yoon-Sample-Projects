// Programmer:  Christian Yoon and Brian Nguyen
// Section:     1  
// Program:     Project 1
// Date:        September 24th, 2010
// Description: Class representing the Buyable properties on a standard Monopoly board

public abstract class Property extends BoardLocation
{
	protected int cost;								// cost of buying the property
	protected int rent;								// amount player has to pay when landing on this 
													// 	property
	protected String owner;							// string representation of owner (token)
	protected Player propOwner;						// player class representation of owner of property
	protected static String defaultOwner = "Bank";	// default owner of all properties through all 
													// 	instances of class

	public Property(String name, int location, int cost,int rent)
	// PRE: name has to be initialized; cost and rent >= 0; cost and rent in dollars;
	//		 0 <= location <= Max board size(39)	
	// POST:A Property object has been constructed with data members name, cost, and rent
	//		 set to the values passed in the corresponding parameters
	//		 and default owner is set to 
	{
		super(name, location);
		
		this.cost = cost;
		this.rent = rent;
		this.owner = defaultOwner;
	}
	
	public abstract void BuyProperty(Player player);
	// PRE:	 player has to be initialized
	// POST: Function that increases certain variables pertaining to Property ownage
	//		  in the Player class depending on the subclass
	
	public abstract void CalculateRent();
	// POST: Different CalculateRent options pertaining to the subclass 
	//			that sets protected class variable rent to different values
	//			using various calculations
	
	public abstract void SetPropOwner(Player propOwner);
	// PRE:	 propOwner has to be initialized
	// POST: Sets protected class variable propOwner to the value passed in the parameter
	//			and sets propOwner's token string to represent the owner of this Property
 
	@Override
	public String[] GetPlayerActions(Player player)
	// PRE:	 player has to be initialized
	// POST: FCTVAL == array of options player has upon landing on
	//			this space, to be used in a menu in a user interface
	{
		String[] menu;
		
		if (player.GetToken() != owner)		// Checks if the player owns
		{									//  the property; determines actions
			if (owner == defaultOwner)		// checks to see if bank owns the property
			{
				menu = new String[2];		// declare menu of 2 choices
				menu[0] = "Buy Property";	
				menu[1] = "Continue";
				
				return menu;
			}
			else							// if bank doesn't own the property and you don't own it...
			{								//  then another player owns it.
				menu = new String[1];
				menu[0] = "Pay $" + GetRent() + " rent to " + GetOwner();
				
				return menu;
			}
		}
		else								// you own the property, can't make anymore moves
		{
			menu = new String[1];
			menu[0] = "Continue; You already own this property.";
			
			return menu;
		}
	}
	
	public void PayRent(Player player)
	// PRE:	 player has to be initialized
	// POST: Calculates the rent the player landing on the property has to pay
	//			and then subtracts it from his money and adds the money paid,
	//			to the property owner (propOwner).
	{
		CalculateRent();
	
		player.SetMoney(player.GetMoney() - rent);
		propOwner.SetMoney(propOwner.GetMoney() + rent);
	}
	
	public void SetCost(int cost)
	// PRE:	 cost >= 0, cost is in dollars
	// POST: Sets protected class variable cost to the value passed in the parameter
	{
		this.cost = cost;
	}
	
	public int GetCost()
	// POST: Returns the value in protected class variable cost
	{
		return cost;
	}
	
	public void SetRent(int rent)
	// PRE:	 rent >= 0, rent is in dollars
	// POST: Sets protected class variable rent to the value passed in the parameter
	{
		this.rent = rent;
	}
	
	public int GetRent()
	// POST: Returns the value in protected class variable rent
	{
		return rent;
	}
	
	public void SetOwner(String owner)
	// PRE:	 owner has to be initialized
	// POST: Sets protected class variable owner to the value passed in the parameter
	{
		this.owner = owner;
	}
	
	public String GetOwner()
	// POST: Returns the value in protected class variable owner
	{
		return owner;
	}
	
	@Override
	public String toString()
	// POST: Returns a string representation of the 
	//			name, owner, and cost of this Property
	{
		return super.toString() + "[Owner: " + GetOwner() + 
			"; Cost: $" + GetCost() + ". ";
	}
}
