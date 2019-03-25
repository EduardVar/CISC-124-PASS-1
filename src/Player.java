/**
 * Author:	Eduard Varshavsky
 * Date:	January 21, 2019
 * Desc:	This is the Player class that holds all the defining 
 * 			characteristics of a single player
 */

public class Player
{
	// *** CLASS VARIABLES ***
	
	//Stores attributes for the player's number, name, positions, skill level
	private int number;
	private String name;
	private Position position;
	private int skillLevel;
	
	// *** CONSTRUCTOR METHODS ***
	
	//Requires an integer for the number and skill level, a string for the name
	//as well as a Position enumeration for the player's position
	//This is the constructor used to initiate a player object
	public Player(int number, String name, Position position, int skillLevel)
	{
		//Assigns parameters to variables of this class object
		this.number = number;
		this.name = name;
		this.position = position;
		this.skillLevel = skillLevel;
	}
	
	// *** OBJECT-BEHAVIOUR METHODS ***
	
	// *** MUTATOR METHODS ***
	
	// *** ACCESSOR METHODS ***

	//Getter to attain the player number as an integer
	public int getNumber()
	{
		return number;
	}

	//Getter to attain the player name as a String
	public String getName()
	{
		return name;
	}

	//Getter to attain the player position as a Position enum
	public Position getPosition()
	{
		return position;
	}

	//Getter to attain the player skill level as an integer
	public int getSkillLevel()
	{
		return skillLevel;
	}
}
