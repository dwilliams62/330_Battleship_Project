//Dylan Williams
//i dont like the way i have the update ships and ship status implemented
//but i couldn't think of a better way to keep the information as it's needed
//to display what ships are still good during the match as per the instructions

public class BattleshipModel {
	//first dimension is the spot on the board, ex. A1, B2, etc
	//second dimension has the name of the spot on the board ex. A1 in slot [0]
	//and the status of that spot in slot [1]. possible status include
	//"NO SHIP", "INTACT" for having a ship that has not been hit, "HIT" for having a ship
	//that has been hit, and "MISS" for a spot with no ship that was shot at
	private String[][] myBoard;
	
	//first dimension is for each section of the ship, second dimension holds spot name and status of that spot
	private String[][] carrierShip;
	private String[][] battleshipShip;
	private String[][] cruiserShip;
	private String[][] submarineShip;
	private String[][] destroyerShip;
	
	//keeps track of if the entire ship has sunk or not
	private int carrierStatus;
	private int battleshipStatus;
	private int cruiserStatus;
	private int submarineStatus;
	private int destroyerStatus;
	
	//keeps track of total amount of hits to check for a winner
	private int myTotalHits;
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
        
        //initialize a lot of variables
		carrierShip = new String[5][2];
		battleshipShip = new String[4][2];
		cruiserShip = new String[3][2];
		submarineShip = new String[3][2];
		destroyerShip = new String[2][2];
		
		//when the status is 0 the ship has been sunk
		carrierStatus = 5;
		battleshipStatus = 4;
		cruiserStatus = 3;
		submarineStatus = 3;
		destroyerStatus = 2;
        
        myTotalHits = 0;
        oppTotalHits = 0;
	}
	
	//places the ship on the overall board
	private void PlaceShip(String[] str) {
		for (int i = 0; i < str.length; i++) {
			myBoard[TranslateBoardLocation(str[i])][1] = "INTACT"; 
		}
	}

	//still not to sure about this method honestly
	//i dont think its even necessary but keeping it here for now. just in case.
	public void FireShot(String location) {
		int index = TranslateBoardLocation(location);
		//send it over the network
		//process data received
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
			UpdateCarrierShip(t);
			UpdateBattleshipShip(t);
			UpdateCruiserShip(t);
			UpdateSubmarineShip(t);
			UpdateDestroyerShip(t);
			return "HIT";
		}
		//if the spot has already been shot, it will not allow the user to shoot at it again
		else if (myBoard[t][1] == "SUNK" || myBoard[t][1] == "MISS") {
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
		for (int i = 0; i < 5; i++) {
			carrierShip[i][0] = t[i];
		}
		PlaceShip(t);
	}
	
	//goes through the spots of the ship, checks if the location of the shot matches any of the locations
	//of the ship, and keeps track of how many hits the ship has taken, updating the status as needed
	public void UpdateCarrierShip(int t) {
		for (int i = 0; i < 5; i++) {
			if (myBoard[t][0].equals(carrierShip[i][0])) {
				carrierShip[i][1] = "HIT";
				carrierStatus--;
			}
		}
	}
	
	//return the status of the ship
	public int GetCarrierStatus() {
		return carrierStatus;
	}
	
	//update battleship to have the locations marked
	public void PlaceBattleshipShip(String[] t) {
		for (int i = 0; i < 4; i++) {
			battleshipShip[i][0] = t[i];
		}
		PlaceShip(t);
	}
	
	//goes through the spots of the ship, checks if the location of the shot matches any of the locations
	//of the ship, and keeps track of how many hits the ship has taken, updating the status as needed
	public void UpdateBattleshipShip(int t) {
		for (int i = 0; i < 4; i++) {
			if (myBoard[t][0].equals(battleshipShip[i][0])) {
				battleshipShip[i][1] = "HIT";
				battleshipStatus--;
			}
		}
	}
	
	//return battleship status
	public int GetBattleshipStatus() {
		return battleshipStatus;
	}
	
	//update cruiser to have the locations marked
	public void PlaceCruiserShip(String[] t) {
		for (int i = 0; i < 3; i++) {
			cruiserShip[i][0] = t[i];
		}
		PlaceShip(t);
	}
	
	//goes through the spots of the ship, checks if the location of the shot matches any of the locations
	//of the ship, and keeps track of how many hits the ship has taken, updating the status as needed
	public void UpdateCruiserShip(int t) {
		for (int i = 0; i < 3; i++) {
			if (myBoard[t][0].equals(cruiserShip[i][0])) {
				cruiserShip[i][1] = "HIT";
				cruiserStatus--;
			}
		}
	}
	
	//return cruiser status
	public int GetCruiserStatus() {
		return cruiserStatus;
	}
	
	//update submarine to have the locations marked
	public void PlaceSubmarineShip(String[] t) {
		for (int i = 0; i < 3; i++) {
			submarineShip[i][0] = t[i];
		}
		PlaceShip(t);
	}
	
	//goes through the spots of the ship, checks if the location of the shot matches any of the locations
	//of the ship, and keeps track of how many hits the ship has taken, updating the status as needed
	public void UpdateSubmarineShip(int t) {
		for (int i = 0; i < 3; i++) {
			if (myBoard[t][0].equals(submarineShip[i][0])) {
				submarineShip[i][1] = "HIT";
				submarineStatus--;
			}
		}
	}
	
	//return submarine status
	public int GetSubmarineStatus() {
		return submarineStatus;
	}
	
	//update destroyer to have the locations marked
	public void PlaceDestroyerShip(String[] t) {
		for (int i = 0; i < 2; i++) {
			destroyerShip[i][0] = t[i];
		}
		PlaceShip(t);
	}
	
	//goes through the spots of the ship, checks if the location of the shot matches any of the locations
	//of the ship, and keeps track of how many hits the ship has taken, updating the status as needed
	public void UpdateDestroyerShip(int t) {
		for (int i = 0; i < 2; i++) {
			if (myBoard[t][0].equals(destroyerShip[i][0])) {
				destroyerShip[i][1] = "HIT";
				destroyerStatus--;
			}
		}
	}
	
	//return destroyer status
	public int GetDestroyerStatus() {
		return destroyerStatus;
	}
	
	//checks to see if there is a winner or not
    public String checkWinner()
    {
    	if (myTotalHits >= 17) {
    		return "YOU WIN";
    	}
    	else if (oppTotalHits >= 17) {
    		return "YOU LOST";
    	}
    	else {
    		return null;
    	}
    }
}