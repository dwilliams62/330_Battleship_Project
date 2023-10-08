//Dylan Williams
//i dont like the way i have the ship status implemented
//but i couldn't think of a better way to keep the information as it's needed
//to display what ships are still good during the match as per the instructions

public class BattleshipModel {
	//private class ship to consolidate some of the code
	private class Ship {
		//the first dimension is each spot on the ship, the second dimension holds
		//the location and status
		private String[][] shipInfo;
		private int maxHealth;
		private int currentHealth;
		private Boolean placed;
		
		Ship(int max) {
			//initializes the ship based off the max health given
			maxHealth = max;
			currentHealth = max;
			shipInfo = new String[max][2];
			placed = false;
		}
		
		//places the specific ship given in the spots provided
		public void PlaceSpecificShip(String[] t) {
			for (int i = 0; i < maxHealth; i++) {
				shipInfo[i][0] = t[i];
			}
			setPlaced();
		}
		
		//updates the ship if it was shot at
		public void UpdateShip(int t) {
			for (int i = 0; i < maxHealth; i++) {
				if (myBoard[t][0].equals(shipInfo[i][0])) {
					shipInfo[i][1] = "HIT";
					currentHealth--;
				}
			}
		}
		
		//return the status of the ship
		public int GetStatus() {
			return currentHealth;
		}
		
		//set that the ship has been placed
		public void setPlaced() {
			placed = true;
		}
		
		//return the current status of the ship for use with the view
		//mainly for the auto button to check if the ship is placed before attempting to place it
		public boolean getPlacementStatus() {
			return placed;
		}
	}
	
	//first dimension is the spot on the board, ex. A1, B2, etc
	//second dimension has the name of the spot on the board ex. A1 in slot [0]
	//and the status of that spot in slot [1]. possible status include
	//"NO SHIP", "INTACT" for having a ship that has not been hit, "HIT" for having a ship
	//that has been hit, and "MISS" for a spot with no ship that was shot at
	private String[][] myBoard;
	
	//first dimension is for each section of the ship, second dimension holds spot name and status of that spot
	private Ship carrierShip;
	private Ship battleshipShip;
	private Ship cruiserShip;
	private Ship submarineShip;
	private Ship destroyerShip;
	
	//keeps track of total amount of hits to check if you lost
	private int oppTotalHits;
	
	//default constructor
	BattleshipModel() {
		myBoard = new String[100][2];
		
		//initialize all the names in the second dimension's first slot, A1 through J10, 
		//with NO SHIP as the status
		int counter = 0;
        for (char letter = 'A'; letter <= 'J'; letter++) {
            for (int number = 1; number <= 10; number++) {
            	myBoard[counter][0] = letter + Integer.toString(number);
            	myBoard[counter][1] = "NO SHIP";
            	counter++;
            }
        }
        
        //initialize all the ships
		carrierShip = new Ship(5);
		battleshipShip = new Ship(4);
		cruiserShip = new Ship(3);
		submarineShip = new Ship(3);
		destroyerShip = new Ship(2);
        
		//if this number hits 17 it means all your ships are destroyed
        oppTotalHits = 0;
	}
	
	//places the ship on the overall board
	private void PlaceShip(String[] str) {
		for (int i = 0; i < str.length; i++) {
			myBoard[TranslateBoardLocation(str[i])][1] = "INTACT"; 
		}
	}

	//will take the name of a spot such as A6 and return the index associated with it on the board
	public int TranslateBoardLocation(String location) {
		for (int i = 0; i < 100; i++) {
			if (myBoard[i][0].equals(location)) {
				return i;
			}
		}
		//if it doesnt find it, returns a negative to signify error
		return -1;
	}
	
	public String checkHit(String str) {
		//if the spot on the board has a ship still intact, updates all the ships as it doesnt
		//know which ship was hit specifically, then returns that the shot was successful
		int t = TranslateBoardLocation(str);
		if (myBoard[t][1] == "INTACT") {
			myBoard[t][1] = "HIT";
			oppTotalHits++;
			carrierShip.UpdateShip(t);
			battleshipShip.UpdateShip(t);
			cruiserShip.UpdateShip(t);
			submarineShip.UpdateShip(t);
			destroyerShip.UpdateShip(t);
			return "HIT";
		}
		//if the spot has already been shot, it will not allow the user to shoot at it again
		else if (myBoard[t][1] == "SUNK" || myBoard[t][1] == "MISS" || myBoard[t][1] == "HIT") {
			return "DUPE";
		}
		//else if theres no ship there it's just a miss
		else {
			myBoard[t][1] = "MISS";
			return "MISS";
		}
	}
	
	//update carrier to have the locations marked
	public void PlaceCarrierShip(String[] t) {
		carrierShip.PlaceSpecificShip(t);
		PlaceShip(t);
	}
	
	//update battleship to have the locations marked
	public void PlaceBattleshipShip(String[] t) {
		battleshipShip.PlaceSpecificShip(t);
		PlaceShip(t);
	}

	//update cruiser to have the locations marked
	public void PlaceCruiserShip(String[] t) {
		cruiserShip.PlaceSpecificShip(t);
		PlaceShip(t);
	}
	
	//update submarine to have the locations marked
	public void PlaceSubmarineShip(String[] t) {
		submarineShip.PlaceSpecificShip(t);
		PlaceShip(t);
	}
	
	//update destroyer to have the locations marked
	public void PlaceDestroyerShip(String[] t) {
		destroyerShip.PlaceSpecificShip(t);
		PlaceShip(t);
	}
	
	//return the status of the carrier ship
	public int GetCarrierStatus() {
		return carrierShip.GetStatus();
	}
	
	//return the status of the battleship ship
	public int GetBattleshipStatus() {
		return battleshipShip.GetStatus();
	}
	
	//return the status of the cruiser ship
	public int GetCruiserStatus() {
		return cruiserShip.GetStatus();
	}
	
	//return the status of the submarine ship
	public int GetSubmarineStatus() {
		return submarineShip.GetStatus();
	}
	
	//return the status of the destroyer ship
	public int GetDestroyerStatus() {
		return destroyerShip.GetStatus();
	}
	
	//place the ship in the spots given in the string array
	//this large if statement is more ideal than 5 functions and the others
	//will be changed to this implementation in the final version
	public boolean GetShipPlacementStatus(String str) {
		if (str.equals("Carrier")) {
			return carrierShip.getPlacementStatus();
		}
		else if (str.equals("Battleship")) {
			return battleshipShip.getPlacementStatus();
		}
		else if (str.equals("Cruiser")) {
			return cruiserShip.getPlacementStatus();
		}
		else if (str.equals("Submarine")) {
			return submarineShip.getPlacementStatus();
		}
		else {
			return destroyerShip.getPlacementStatus();
		}
	}
	
	//a function to be used in the future if the automatic placement function
	//has time to be improved
	public boolean checkSpot(String str) {
		if (myBoard[TranslateBoardLocation(str)][1] == "NO SHIP") {
			return false;
		}
		else {
			return true;
		}
	}
	
	//checks to see if there is a winner or not
    public String checkWinner()
    {
    	if (oppTotalHits >= 17) {
    		return "YOU LOST";
    	}
    	else {
    		return "";
    	}
    }
}