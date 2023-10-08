import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.html.ImageView;

//ImageIcon icon = new ImageIcon("Wave2.png");
//userBoard[0][0].setIcon(icon);
//code to retroactively change the image on a jlabel

public class BattleshipView extends JFrame{
	private JButton [][] enemyBoard; //Enemy Board at top
	private JLabel [][] userBoard; //User board at bottom
	private JPanel [][] gap; // space in between to interact with player
	private JButton auto; // automatic button to place ships
	private BattleshipModel model; // using model
	ImageIcon buttonIcon = new ImageIcon("Wave2.png"); // importing image
	
	private static int GRIDLAYOUTSIZE = 30; //used in grid layout size
	private static int BOARDSIZE = 10; //constant for board size
	
	//constructor
	BattleshipView(BattleshipModel gameModel){
		super("BattleShip Game"); // title bar
		
		setLayout(new GridLayout(GRIDLAYOUTSIZE,GRIDLAYOUTSIZE)); // grid layout is 30 by 30
		
		enemyBoard = new JButton[BOARDSIZE][BOARDSIZE]; // 100 buttons for enemy board ( 10 * 10)
		EnemyButtonHandler enemyHandler = new EnemyButtonHandler(); // button handler
		
		//two temporary variables to set the text of each button
		char letter = 'A';
		int number = 1;
		
		//loops through all the enemy board buttons, setting the settings as necessary
		for(int i = 0; i < BOARDSIZE; i++) {
			for (int j = 0; j < BOARDSIZE; j++) {
				enemyBoard[i][j] = new JButton(buttonIcon); //sets background picture as wave
				enemyBoard[i][j].setEnabled(false); //dont allow press till ships placed
				enemyBoard[i][j].setText(letter + Integer.toString(number)); //sets the text
				enemyBoard[i][j].setHorizontalTextPosition(JButton.CENTER); //centers the text
				enemyBoard[i][j].setVerticalTextPosition(JButton.CENTER); //centers the text
				enemyBoard[i][j].setMargin(new Insets(0,0,0,0)); //allows image to fill entire button
				
				add(enemyBoard[i][j]); //add button to the grid layout
				enemyBoard[i][j].addActionListener(enemyHandler); //attach a listener
				number++;
			}
			letter++;
			number = 1;
		}
		
		AutoButtonHandler autoHandler = new AutoButtonHandler(); // This is for automatic placement
		gap = new JPanel[BOARDSIZE][BOARDSIZE]; // 100 element space
		
		//loops through 100 spots to create a gap between the boards
		//eventually the ships for drag and drop will be here
		for(int i = 0; i < BOARDSIZE; i++) {
			for (int j = 0; j < BOARDSIZE; j++) {
				gap[i][j] = new JPanel(); //create a jpanel in the current spot
				
				//the last spot is reserved for the auto button
				if (i == 9 && j == 9) {
					auto = new JButton(); //create the button
					auto.setText("Auto"); //set the text, although the button is too small and it will not display
					add(auto); //add to the grid layout
					auto.addActionListener(autoHandler); //add action listener to auto button
					auto.setActionCommand("automatic"); //set the button command
				}
				else {
					add(gap[i][j]); //add the panel
				}
			}
		}
		
		userBoard = new JLabel[BOARDSIZE][BOARDSIZE]; //user board with 100 spots for the ships
		
		//loops through and sets the user's boards settings
		for(int i = 0; i < BOARDSIZE; i++) {
			for (int j = 0; j < BOARDSIZE; j++) {
				userBoard[i][j] = new JLabel(); //create a label
				userBoard[i][j].setBorder(BorderFactory.createLineBorder(Color.black)); //outline it in black
				add(userBoard[i][j]); //add to grid layout
			}
		}

		model = gameModel; //initialize the model to the one created in the controller
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setSize(300,700);
		setVisible(true);
	}
	

	// button handler for the enemy board.
	private class EnemyButtonHandler implements ActionListener{
		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent event) {
			JButton tempBtn = (JButton)event.getSource(); //grab the button that was pressed
			
			//once network code is implemented, result will be gathered by calling checkHit across the network
			//send over tempBtn.getText(), receive string result
			//this is for testing out everything in a singleplayer fashion
        	String result = model.checkHit(tempBtn.getText()); //check the hit using the button's text
        	
        	//if it's a hit, change the button to reflect so
        	if (result == "HIT") {
        		tempBtn.setText("X"); //set the button to say X
        		tempBtn.setForeground(Color.red); //make the color Red
        		result = model.checkWinner(); //check if you are out of ships
        		if (result == "YOU LOST") {
        			DisplayResults(result); //displays that you lost
        			//send something across network to tell opponent they won
        			System.exit(0); //exit the program
        		}
        	}	
        	else if (result == "DUPE") {
        		return; //pressing the button repeatedly will do nothing
        	}
        	else {
        		tempBtn.setText("O"); //set the button to O
        		tempBtn.setForeground(Color.WHITE); //show the color as white (a bit hard to see, may need to fix)
        	}
		}
	}
	
	// button handler for the automatic button
	private class AutoButtonHandler implements ActionListener{
		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == auto) {
				auto.setEnabled(false); //dont allow to repress auto button for now
				
				//the program then loops through each of the 5 ships and checks if it has been placed
				//if it has been placed, it will do nothing to it
				//if it has not been placed, it will then generate a random number 1-3
				//based off that number, the ships are then placed on the user's board
				//this does lead to randomness in a way that ensures there are no conflicts, however
				//this way of implementation makes my soul hurt, seeing so much code repeated. this is
				//high up on the list of things i want to change to make better, but unfortunately low on the
				//list of things that NEED to be changed. and as such it stays like this for now
				
				//carrier ship
				if (!model.GetShipPlacementStatus("Carrier")) {
					Random random = new Random();
				    int temp = random.nextInt(4 - 1) + 1;
				    if (temp == 1) {
				    	String[] placeMyCarrier = {"A1", "A2", "A3", "A4", "A5"};
				    	model.PlaceCarrierShip(placeMyCarrier);
				    	userBoard[0][0].setText("Ca"); userBoard[0][1].setText("Ca");
				    	userBoard[0][2].setText("Ca"); userBoard[0][3].setText("Ca");
				    	userBoard[0][4].setText("Ca");
				    }
				    else if (temp == 2) {
				    	String[] placeMyCarrier = {"B1", "B2", "B3", "B4", "B5"};
				    	model.PlaceCarrierShip(placeMyCarrier);
				    	userBoard[1][0].setText("Ca"); userBoard[1][1].setText("Ca");
				    	userBoard[1][2].setText("Ca"); userBoard[1][3].setText("Ca");
				    	userBoard[1][4].setText("Ca");
				    }
				    else {
				    	String[] placeMyCarrier = {"C1", "C2", "C3", "C4", "C5"};
				    	model.PlaceCarrierShip(placeMyCarrier);
				    	userBoard[2][0].setText("Ca"); userBoard[2][1].setText("Ca");
				    	userBoard[2][2].setText("Ca"); userBoard[2][3].setText("Ca");
				    	userBoard[2][4].setText("Ca");
				    }
				}
				
				//battleship
				if (!model.GetShipPlacementStatus("Battleship")) {
					Random random = new Random();
				    int temp = random.nextInt(4 - 1) + 1;
				    if (temp == 1) {
				    	String[] placeMyBattleship = {"E3", "F3", "G3", "H3"};
				    	model.PlaceBattleshipShip(placeMyBattleship);
				    	userBoard[4][2].setText("Ba"); userBoard[5][2].setText("Ba");
				    	userBoard[6][2].setText("Ba"); userBoard[7][2].setText("Ba");
				    }
				    else if (temp == 2) {
				    	String[] placeMyBattleship = {"E4", "F4", "G4", "H4"};
				    	model.PlaceBattleshipShip(placeMyBattleship);
				    	userBoard[4][3].setText("Ba"); userBoard[5][3].setText("Ba");
				    	userBoard[6][3].setText("Ba"); userBoard[7][3].setText("Ba");
				    }
				    else {
				    	String[] placeMyBattleship = {"E5", "F5", "G5", "H5"};
				    	model.PlaceBattleshipShip(placeMyBattleship);
				    	userBoard[4][4].setText("Ba"); userBoard[5][4].setText("Ba");
				    	userBoard[6][4].setText("Ba"); userBoard[7][4].setText("Ba");
				    }
				}
				
				//cruiser
				if (!model.GetShipPlacementStatus("Cruiser")) {
					Random random = new Random();
				    int temp = random.nextInt(4 - 1) + 1;
				    if (temp == 1) {
				    	String[] placeMyCruiser = {"A7", "B7", "C7"};
				    	model.PlaceCruiserShip(placeMyCruiser);
				    	userBoard[0][6].setText("Cr"); userBoard[1][6].setText("Cr");
				    	userBoard[2][6].setText("Cr");
				    }
				    else if (temp == 2) {
				    	String[] placeMyCruiser = {"A9", "B9", "C9"};
				    	model.PlaceCruiserShip(placeMyCruiser);
				    	userBoard[0][8].setText("Cr"); userBoard[1][8].setText("Cr");
				    	userBoard[2][8].setText("Cr");
				    }
				    else {
				    	String[] placeMyCruiser = {"B7", "B8", "B9"};
				    	model.PlaceCruiserShip(placeMyCruiser);
				    	userBoard[1][6].setText("Cr"); userBoard[1][7].setText("Cr");
				    	userBoard[1][8].setText("Cr"); 
				    }
				}
				
				//submarine
				if (!model.GetShipPlacementStatus("Submarine")) {
					Random random = new Random();
				    int temp = random.nextInt(4 - 1) + 1;
				    if (temp == 1) {
				    	String[] placeMySubmarine = {"E7", "F7", "G7"};
				    	model.PlaceSubmarineShip(placeMySubmarine);
				    	userBoard[4][6].setText("Su"); userBoard[5][6].setText("Su");
				    	userBoard[6][6].setText("Su");
				    }
				    else if (temp == 2) {
				    	String[] placeMySubmarine = {"E9", "F9", "G9"};
				    	model.PlaceSubmarineShip(placeMySubmarine);
				    	userBoard[4][8].setText("Su"); userBoard[5][8].setText("Su");
				    	userBoard[6][8].setText("Su");
				    }
				    else {
				    	String[] placeMySubmarine = {"G7", "G8", "G9"};
				    	model.PlaceSubmarineShip(placeMySubmarine);
				    	userBoard[6][6].setText("Su"); userBoard[6][7].setText("Su");
				    	userBoard[6][8].setText("Su"); 
				    }
				}
				
				//destroyer
				if (!model.GetShipPlacementStatus("Destroyer")) {
					Random random = new Random();
				    int temp = random.nextInt(4 - 1) + 1;
				    if (temp == 1) {
				    	String[] placeMyDestroyer = {"I9", "I10"};
				    	model.PlaceDestroyerShip(placeMyDestroyer);
				    	userBoard[8][8].setText("De"); userBoard[8][9].setText("De");
				    }
				    else if (temp == 2) {
				    	String[] placeMyDestroyer = {"J9", "J10"};
				    	model.PlaceDestroyerShip(placeMyDestroyer);
				    	userBoard[9][8].setText("De"); userBoard[9][9].setText("De");
				    }
				    else {
				    	String[] placeMyDestroyer = {"I10", "J10"};
				    	model.PlaceDestroyerShip(placeMyDestroyer);
				    	userBoard[8][9].setText("De"); userBoard[9][9].setText("De"); 
				    }//end else
				}//end if destroyer
				
				//loop through the enemy board and set enabled to true so they can start trying to hit ships
				for (int i = 0; i < BOARDSIZE; i++) {
					for (int j = 0; j < BOARDSIZE; j++) {
						enemyBoard[i][j].setEnabled(true);
					}
				}
			}//end if get source auto
		}//end action performed method
	}//end autobutton class 
	
	//display the results in a new window that when closed closes the entire program
	public void DisplayResults(String resultMessage) {
		JOptionPane.showMessageDialog(this, resultMessage);
	}
}
