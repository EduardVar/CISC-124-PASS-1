/**
 * Author:	Eduard Varshavsky
 * NetID:	18ev
 * Date:	January 21, 2019
 * Desc:	This is the main program for the NHL Simulator. It deals with the
 * 			user interface and working with all the other classes to create
 * 			an efficient simulation
 */

//Imported classes

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NHLSimulator
{
	//Created a scanner for user input and a map for key-value pairs meant to
	//store the position enumerations equivalent of a given string
	private static Scanner userInput = new Scanner(System.in);
	private static Map<String, Position> posDic = new HashMap<>();
	
	//Requires an array of Strings. Returns an integer array
	//Converts all the elements of an array of strings to integers and returns
	//the array as an array of integers
	public static int[] getIntArray(String[] strArr)
	{
		//Creates a new integer array the same size as the String array
		int[] intArr = new int[strArr.length];
		
		//Iterates through each index of strArr
		for (int i = 0; i < strArr.length; i++)
		{
			//Sets same index in intArr to the int casted element from strArr
			intArr[i] = Integer.parseInt(strArr[i]);
		}
		
		//Returns the array of integers
		return intArr;
	}
	
	//Requires a String object for the input
	//Returns a String with the first letter capitalized
	public static String capitalize(String input)
	{
		//Cuts the first letter of input and upper cases it, adds the rest then
		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}
	
	//Requires an array of Team objects. Doesn't return anything
	//This method creates a team of players for each team in the conference
	public static void createTeams(Team[] teams)
	{	
		//Creates a file object to hold data of other teams from TeamData.txt
		File teamFile = new File("TeamData.txt");
		
		//Creates a bufferedReader object and an integer with initial value 0
		BufferedReader br;
		int i = 0;
		
		//Tries to read in from the file
		try
		{
			//Creates a new BufferedReader object using a new FileReader 
			//objects that reads from the teamFile File object
			br = new BufferedReader(new FileReader(teamFile));

			//Created a String that holds the current line
			String line = "";
			
			//Keeps looping until there are no more lines in the file. Each
			//line gives information on the team properties
			while ((line = br.readLine()) != null)
			{
				//Creates a new array of strings to hold the data of the line
				String[] data = line.split("\t");
				
				//Stores each individual piece of data in appropriate variable
				int teamNumber = Integer.parseInt(data[0]);
				String teamName = data[1];
				
				//Stores skill ranges in integer arrays of length 2 [low, high]
				int[] fSkillRange = getIntArray(data[2].split("-"));
				int[] dSkillRange = getIntArray(data[3].split("-"));
				int[] gSkillRange = getIntArray(data[4].split("-"));
				
				//Creates a new team at index i with information stored
				teams[i] = new Team(teamNumber, teamName, fSkillRange,
						dSkillRange, gSkillRange);
				
				//Increments i by 1
				i++;
			}
			
			//Closes the buffered reader to avoid problems
			br.close();

			//Reads in the Maple Leafs from a different function in the current
			//value of i (the last element)
			teams[i] = readInLeafs();
		}
		catch (IOException e)
		{
			//Prints out the error message if the file was not found
			System.err.println(e.getMessage());
		}
	}

	//Doesn't require anything, returns a Team object (of the Maple Leafs)
	//This method reads in the leafs' data and generates a team that is
	//returned with all the data implemented in the Team object
	public static Team readInLeafs()
	{
		//Creates new Player arrays for the forward, defensemen, and goalies
		//in each time (size defined by constants from Team class)
		Player[] forwards = new Player[Team.REQ_FORWARDS];
		Player[] defensemen = new Player[Team.REQ_DEFENSEMEN];
		Player[] goalies = new Player[Team.REQ_GOALIES];
		
		//File object to hold Maple Leafs player data from MapleLeafsData.txt
		File leafsFile = new File("MapleLeafsData.txt");
		
		//Creates a bufferedReader object and an integer with initial value 0
		BufferedReader br;
		int i = 0;
		
		//Tries to read in from the file
		try
		{
			//Creates a new BufferedReader object using a new FileReader 
			//objects that reads from the teamFile File object
			br = new BufferedReader(new FileReader(leafsFile));	
			
			//Created a String that holds the current line
			String line = "";
			
			//Keeps looping until there are no more lines in the file. Each
			//line in the final gives data on every player one the team
			while ((line = br.readLine()) != null)
			{
				//Creates a new array of strings to hold the data of the line
				String[] data = line.split("\t");
				
				//Stores each piece of player data in appropriate types
				int number = Integer.parseInt(data[0]);	//Convert String to int
				String name = data[1];
				Position pos = posDic.get(data[2]);	//Get value using data key
				int skill = Integer.parseInt(data[3]);	//Convert String to int
				
				//Checks if i is less than a certain index
				if (i < 13)
				{
					//Creates a new player with info provided at index i
					forwards[i] = new Player(number, name, pos, skill);
				}
				else if (i < 21)
				{
					//Creates a new player with info provided at index i 
					//relative to the beginning of defensemen (hence, the %13)
					defensemen[i % 13] = new Player(number, name, pos, skill);
				}
				else
				{
					//Creates a new player with info provided at index i 
					//relative to the beginning of goalies (hence, the %21)
					goalies[i % 21] = new Player(number, name, pos, skill);
				}
				
				//Increments index i by 1
				i++;
			}
			
			//Closes the buffered reader file
			br.close();
		}
		catch (IOException e)
		{
			//Prints out the error message if the file was not found
			System.err.println(e.getMessage());
		}
		
		//Returns a new Team object with all information already provided in
		//the parameters
		return new Team(16, "Toronto", forwards, defensemen, goalies);
	}
	
	//This method requires an array of Team objects. Doesn't return anything
	//This method is meant to simulate an NHL season and then output an end
	//of season report. (Part of option 1)
	public static void simulateNHLSeason(Team[] teams)
	{
		//Iterates through every team in teams array
		for (int i = 0; i < teams.length; i++)
		{
			//Loops every team again to simulate teams versing each other
			for (int j = 0; j < teams.length; j++)
			{
				//Checks if the team won't be playing itself
				if (teams[i] != teams[j])
				{
					//Creates a new game object with the two teams playing
					new Game(teams[i], teams[j]);
				}
			}
		}
		
		//Outputs an end of simulation report with the information from teams
		endOfSimReport(teams);
	}
	
	//Requires an array of Team objects. Doesn't return anything
	//Writes out to system the report end of simulation using each teams info
	public static void endOfSimReport(Team[] teams)
	{
		//Stores the winning and losing teams of the conference
		Team[] winnerAndLoser = bestAndWorst(teams);
		
		//Stores winners and losers in separate team objects
		Team winner = winnerAndLoser[0], loser = winnerAndLoser[1];
		
		//Prints the formatted end of simulation messages
		System.out.println("\nNHL Regular Season - Eastern Conference - "
				+ "2018/2019");
		System.out.printf("First Team: %1$-15sPoints: %2$-5d%n",
				winner.getTeamName(), winner.getPoints());
		System.out.printf("Last Team:  %1$-15sPoints: %2$-5d%n",
				loser.getTeamName(), loser.getPoints());
		System.out.println("Simulation completed!");
	}
	
	//Requires an array of Team objects.
	//Returns an array of Team objects of length 2
	//Finds out the best and worst teams and puts the pair in a Team array
	public static Team[] bestAndWorst(Team[] teams)
	{	
		//Stores integers for the index of highest/lowest scoring teams
		int highestIndex = 0, lowestIndex = 0;
		
		//Iterates through each team in array teams
		for (int i = 0; i < teams.length; i++)
		{
			//Checks if the current team being looked at has more points than
			//the team with most points or vice versa with lowest scoring team
			if (teams[i].getPoints() > teams[highestIndex].getPoints())
			{
				//Sets the highest index to the current index
				highestIndex = i;
			}
			else if (teams[i].getPoints() < teams[lowestIndex].getPoints())
			{
				//Sets lowest index to the current index
				lowestIndex = i;
			}
		}	
		
		//Returns array of size 2: team with most points, and team with least
		return new Team[] {teams[highestIndex], teams[lowestIndex]};
	}
	
	//Requires an array of Team objects. Doesn't return anything
	public static void viewTeamSkillProfile(Team[] teams)
	{
		//Checks if there are actual Team objects stored in teams array
		if (teams[0] == null)
		{
			//Lets user know they should run the simulation first
			System.out.println("\nMust run NHL Eastern Conference Simulation"
					+ " before accessing this option!");
		}
		else
		{
			//Creates a boolean that is true if program is searching for a team
			//Creates a string to store the name the user chooses
			boolean searching = true;
			String chosenName = "";
			
			//Prompts user to enter name of the team they wish to search for 
			System.out.println("\nEnter the name of the team: ");
			
			//Keeps looping until searching boolean is false
			while (searching)
			{
				//Checks if the user actually entered something
				if ((chosenName = userInput.nextLine()).equals(""))
				{
					//They pressed enter. Sets searching to false so loop ends
					searching = false;
				}
				else 
				{
					//Iterates through each Team object in teams array
					for (Team team : teams)
					{
						//Checks if name user chose matches the Team object
						if (chosenName.equals(team.getTeamName()))
						{
							//Display players in team using found Team Object
							displayPlayersInfo(team);
							
							//Sets searching to false to stop looping
							searching = false;
						}
					}
					
					//Checks if method is still searching for a team
					if (searching)
					{
						//Prompts user what to do
						System.out.println(chosenName + " is invalid! Please "
								+ "re-enter or press [Enter]");
					}
				}
			}
		}
	}
	
	//Requires a Team object to display. Doesn't return anything
	//This method displays the individual info of each play on team provided
	public static void displayPlayersInfo(Team team)
	{		
		//Printed out the formatted headers to the system
		System.out.printf("%n%1$-8s%2$-20s%3$-17s%4$-15s%n",
				"No", "Name", "Position", "Skill Level");
		System.out.printf("%1$-8s%2$-20s%3$-17s%4$-15s%n",
				"***", "***************", "************", "***********");
		
		//Created an array of players to store all players on the team
		Player[] teamPlayers = team.getAllPlayers();
		
		//Iterates through each player index on the team
		for (int i = 0; i < teamPlayers.length; i++)
		{
			//Stores all info of player in different variables (cleaner)
			int nom = teamPlayers[i].getNumber();
			String name = teamPlayers[i].getName();
			Position pos = teamPlayers[i].getPosition();
			int skill = teamPlayers[i].getSkillLevel();
			
			//Prints out the formatted string to the console. Capitalizes the
			//first letter of position (like shown in the assignment)
			System.out.printf("%1$-8d%2$-20s%3$-17s    %4$2d%n",
					nom, name, capitalize(pos.toString()), skill);
		}
	}
	
	//Requires an array of Team objects 
	public static void displayEndTable(Team[] teams)
	{
		//Checks if there are actual Team objects stored in teams array
		if (teams[0] == null)
		{
			//Lets user know they should run the simulation first
			System.out.println("\nMust run NHL Eastern Conference Simulation"
					+ " before accessing this option!");
		}
		else
		{
			//Prints out the headers for the end table
			System.out.println("\nTOTAL SCORES AND STATISTICS REPORT");
			System.out.println("**********************************");
			System.out.println("Team Name         GP    W     L     OTL  "
					+ "  Pts    GF     GA     Diff");
			System.out.println("***************  ****  ****  ****  ***** "
					+ " *****  *****  *****  ******");
			
			//Iterates through each team object in the teams array
			for (Team team : teams)
			{
				//Prints out the formated information for each stat of the team
				System.out.printf("%1$-15s   %2$2d    %3$2d    %4$2d  "
						+ "  %5$2d     %6$3d    %7$3d    %8$3d    %9$+3d %n",
						team.getTeamName(), team.getGamesPlayed(), 
						team.getWins(), team.getLosses(), team.getOverTimeLs(),
						team.getPoints(), team.getGoalsFor(), 
						team.getGoalsAgainst(), team.getDifference());
			}
		}
	}
	
	//Doesn't require anything. Returns the character user successfully entered
	public static char userInterface()
	{
		//Prints out information and prompts to the user
		System.out.println("NHL Simulator (Version 0.1). "
				+ "Author: Eduard Varshavsky");
		System.out.println("1 - Simulate NHL Season (Eastern Conference)");
		System.out.println("2 - View Team Skill Level Profile");
		System.out.println("3 - Display End of Regular Season Table");
		System.out.println("Select Option [1, 2, 3] (9 to Quit): ");
			
		//Created char to store number entered and a boolean to check if the
		//input is valid to be returned
		char num = 0;
		boolean valid = false;
		
		//Loops until valid is set to true
		while (!valid)
		{
			//Resets the content of num to a 0 character
			num = '0';
			
			//Stores user's input in a String called given
			String given = userInput.nextLine();
			
			//Checks if the length of given is 1 (means it's a character if 1)
			if (given.length() == 1)
			{
				//Converts given to a character and stores it at num
				num = given.charAt(0);
			}
			
			//Sets value on if it matches one of the options allowed to choose
			valid = (num == '1' || num == '2' || num == '3' || num == '9');
			
			//Checks if valid is still false
			if (!valid)
			{
				//Prompts user to try again
				System.out.println("\nInvalid input. Select Option [1, 2, 3]"
						+ " (9 to Quit): ");
			}
		}
		
		//Returns num out of the program
		return num;
	}
	
	//Requires the configurations of an array of String (optional)
	//Doesn't return anything
	//This is the main method of the program. Runs most of the main code
	public static void main(String[] args)
	{
		//Created a boolean to store if the program was still running
		boolean running = true;
		
		//Puts string keys for position values (used when reading from file)
		posDic.put("forward", Position.forward);
		posDic.put("defenseman", Position.defenseman);
		posDic.put("goalie", Position.goalie);
		
		//Stores array of Team objects. Stores every single one in conference
		Team[] teams = new Team[16];
		
		//Keeps looping until running is set to false
		while (running)
		{		
			//Acquires the user's choice from user interface. Stores as a char
			char choice = userInterface();
			
			//Switch statement that considers choices as cases
			switch (choice)
			{
			case '1':
				//Choice for simulating a conference: generates and sets team
				//and then simulates a season using teams array
				createTeams(teams);
				simulateNHLSeason(teams);
				break;
			case '2':
				//Choice for viewing a team skill level profile: runs method to
				//display it using the teams array
				viewTeamSkillProfile(teams);
				break;
			case '3':
				//Choice for view end of season team table: display all stats
				//for each team; requires the teams array
				displayEndTable(teams);
				break;
			case '9':
				//Case for exiting the program: notifies user and sets running
				//to false to stop main program loop
				System.out.println("EXITING...");
				running = false;
			}
			
			//Adds spacing to format each loop of the console
			System.out.println();
		}
		
		//Closes scanner since it doesn't require user input anymore
		userInput.close();
	}
}
