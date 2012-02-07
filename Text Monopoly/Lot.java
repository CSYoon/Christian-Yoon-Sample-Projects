// Programmer:  Christian Yoon and Brian Nguyen
// Section:     1  
// Program:     Project 1
// Date:        September 24th, 2010
// Description: Class representing the lot properties on a standard Monopoly board

public class Lot extends Property
{
	private final static int DEFAULTHOUSES = 0;	// All lots start with no houses
	private final static int DEFAULTHOTELS = 0;	// All lots start with no hotels
	private final static int DEFAULTIMPROVE = 0;// All lots start with no improvements
	
	private final int MAXIMPROVE = 6;			// Max number of different types of rent paid
												// 	(initial rent, 4 houses, 1 hotel)
	private final int MAXHOUSES = 4;			// Max amount of houses a player can have on a lot
	private final int MAXHOTELS = 1;			// Max amount of hotels a player can have on a hotel
	
	
	private int improvement;					// Number of improvements on the Lot
	private int improveCost;					// The cost to improve the Lot
	private int numHouses;
	private int numHotels;
	private int[] rent;							// An array holding the different rents a player 
												//	can pay depending on the improvements
												//	on the Lot
	private String propColor;					// Color signifying the Lot
	

	public Lot(String name, int location, int cost, int baseRent, int h1Rent, int h2Rent, int h3Rent, 
						int h4Rent, int htRent, String propColor,int improveCost)
	//PRE:	 name and propColor have to be initialized; cost, baserent, h1Rent, h2Rent, h3Rent, h4Rent, 
	// 	   htRent, and improveCost
	//		   are >=0 and are in dollars. 0 <= location <= Max board size(39)
	//POST: A Lot object has been constructed with data members name, location, cost, rent[]
	//       propColor, and improveCost set to the values passed in the corresponding parameters
	{
		super(name, location, cost, baseRent);
		
		rent = new int[MAXIMPROVE]; 		
		rent[0] = baseRent;
		rent[1] = h1Rent;
		rent[2] = h2Rent;
		rent[3] = h3Rent;
		rent[4] = h4Rent;
		rent[5] = htRent;	
		
		this.numHouses = DEFAULTHOUSES;
		this.numHotels = DEFAULTHOTELS;
		this.propColor = propColor;
		this.improvement = DEFAULTIMPROVE;
		this.improveCost = improveCost;
	}
	
	@Override
	public void SetPropOwner(Player propOwner)
	// PRE:  propOwner has to be initialized
	// POST: Sets class member propOwner to the value passed in the parameter
	//			sets propOwner's owner string to represent the owner of this Property,
	//			and increments total numer of properties and lots owned
	{
		super.propOwner = propOwner;
		super.SetOwner(propOwner.GetToken());
		propOwner.SetProperty(this, propOwner.GetNumProperties());	
		propOwner.SetNumLots(propOwner.GetNumLots() + 1);
		propOwner.SetNumProperties(propOwner.GetNumProperties() + 1);
	}

	public void SetImprovements(int improvements)
	// PRE: improvements is initialized, 0 <= improvements <= MAXIMPROVE
	// POST: Sets class member improvement to improvements
	{
		this.improvement = improvements;
	}
	
	@Override
	public void BuyProperty(Player player)
	//PRE:	player has to be initialized
	//POST:	Sets the Property being bought to player,
	//			subtracts cost of Property from money of player,
	//			increments the number of properties the player owns,
	//			and adds this property to the player's array of properties
	{
		SetPropOwner(player);
		
		player.SetMoney(player.GetMoney() - cost);
	}
	
	public void SetNumHotels(int numHotels)
	// PRE:  numHotels must be initialized, numHotels == 0 or 1
	// POST: Sets class member numHotels to numHotels
	{
		this.numHotels = numHotels;
	}
	
	public int GetNumHotels()
	// POST: Returns the number of hotels a lot has on it
	{
		return numHotels;
	}
	
	public void SetNumHouses(int numHouses)
	// PRE:  numHouses must be initialized, 0 <= numHouses <= 4
	// POST: Sets class member numHouses to numHouses
	{
		this.numHouses = numHouses;
	}
	
	public int GetNumHouses()
	// POST: Returns the number of houses a lot has on it
	{
		return numHouses;
	}
	
	public void SellImprovements(int sellAmount)
	// PRE: (MAXIMPROVE -1 ) >= sellAmount > 1
	// POST: Increases the money of the property owner (propOwner) by
	//			half the amount of improveCost and decreases the improvement
	//			of the property by the sellAmount
	{
		SetImprovements(GetImprovements() - sellAmount);
		propOwner.SetMoney(propOwner.GetMoney() + ((improveCost/2)*sellAmount));

		if(numHotels == 1)							// then checks if propOwner has a hotel
		{
			SetNumHotels(0);						// if it does, it sets it to 0,
													//  decreases the sellAmount, and
			sellAmount--;							//  subtracts the remaining sellAmount
													//  from the amount of houses on the property
			SetNumHouses(GetNumHouses()-sellAmount);
		}
		else										// if there are no hotels on the property
		{											//  just subtract sellAmount from houses
			SetNumHouses(GetNumHouses()-sellAmount);
		}
	}

	@Override
	public void CalculateRent()
	//POST:	Sets the Property rent equal to the 
	//		the respective rent depending on how many improvements
	//		the property owner (propOwner) has.
	{
		super.SetRent(rent[improvement]);
	}
	


 
	public void ImproveProp(int buyAmount)
	//POST: Increments the improvement number the Property has
	//		 	and subtracts the cost of improving the property
	//			from the property owner's money(propOwner)
	{
		int improvementCost;
		
		improvementCost = GetImproveCost()*buyAmount;
		
		buyAmount += GetImprovements();
		
		SetImprovements(buyAmount);
		
		propOwner.SetMoney(propOwner.GetMoney() - improvementCost);
	}
		
	public void SetPropColor(String propColor)
	//PRE:	  propColor has to be initialized
	//POST: Sets private class variable propColor to the value passed in the parameter
	{
		this.propColor = propColor;
	}
	
	public String GetPropColor()
	//POST:	Returns value of class member propColor (color of the Lot)
	{
		return propColor;
	}

	public int GetImprovements()
	// POST: Returns number of improvements on the lot
	{
		return improvement;
	}
	
	public int GetImproveCost()
	// POST: Returns the cost needed to improve the lot
	{
		return improveCost;
	}
	
	@Override
	public String toString()
	//POST: Returns a string representation of the description of this Lot
	//			(name,owner,cost,current rent a player has to pay, and the cost
	//		    of improving)
	{
		return super.toString() + "[" + GetPropColor() + "] " + "Improvement: " +
				GetImprovements() + "]\n" + "Rent: $" + super.GetRent() + "; Improve Cost: $" + 
				GetImproveCost();
				
	}
}
