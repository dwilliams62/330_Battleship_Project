import java.util.Arrays;

public class BattleshipModel {
	//first dimension is the spot on the board, ex. A1, B2, etc
	//second dimension has the name of the spot on the board ex. A1 in slot [0]
	//and the status of that spot in slot [1]. possible status include
	//"NO SHIP", "INTACT" for having a ship that has not been hit, "HIT" for having a ship
	//that has been hit, and "MISS" for a spot with no ship that was shot at
	private String[][] myBoard;
	private String[][] oppBoard;
	
	//first dimension is for each section of the ship, second dimension holds spot name and status
	private String[][] carrierShip;
	private String[][] battleshipShip;
	private String[][] cruiserShip;
	private String[][] submarineShip;
	private String[][] destroyerShip;
	
	//keeps track of total amount of hits to check for a winner
	private int myTotalHits;
	private int oppTotalHits;
	
	//keeps track of who's turn it is
	private String turn;
	
	//default constructor
	BattleshipModel() {
		myBoard = new String[100][2];
		oppBoard = new String[100][2];
		
		//initialize all the names in the second dimension's first slot, A1 through J10, 
		//with NO SHIP as the status
		int counter = 0;
        for (char letter = 'A'; letter <= 'J'; letter++) {
            for (int number = 1; number <= 10; number++) {
            	myBoard[counter][0] = letter + Integer.toString(number);
            	myBoard[counter][1] = "NO SHIP";
            	oppBoard[counter][0] = letter + Integer.toString(number);
            	oppBoard[counter][1] = "NO SHIP";
            	counter++;
            }
        }
        
		carrierShip = new String[5][2];
		battleshipShip = new String[4][2];
		cruiserShip = new String[3][2];
		submarineShip = new String[3][2];
		destroyerShip = new String[2][2];
        
        myTotalHits = 0;
        oppTotalHits = 0;
        turn = "MY";
	}
	
	//flips between turn "MY" and "OPP"
	public void changeTurn() {
		if (turn == "MY") {
			turn = "OPP";
		}
		else {
			turn = "MY";
		}
	}
	
	public void FireShot(String location) {
		int index = TranslateBoardLocation(location);
		//send it over the network
		//process data received
	}
	
	public int TranslateBoardLocation(String location) {
		for (int i = 0; i < 100; i++) {
			if (myBoard[i][0] == location) {
				return i;
			}
		}
		return -1;
	}
	
	public String checkHit(int t) {
		if (myBoard[t][1] == "INTACT") {
			myBoard[t][1] = "HIT";
			oppTotalHits++;
			return "HIT";
		}
		else if (myBoard[t][1] == "SUNK" || myBoard[t][1] == "MISS") {
			return "DUPE";
		}
		else {
			myBoard[t][1] = "MISS";
			return "MISS";
		}
	}
	
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