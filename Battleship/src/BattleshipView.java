import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.text.html.ImageView;

public class BattleshipView extends JFrame{
	private JButton [][] enemyBoard; //Enemy Board at top
	private JLabel [][] userBoard; //User board at bottom
	private JLabel [] interactBoard;
	private JLabel carrierShip; // space in between to interact with player
	private JLabel battleShip;
	private JLabel cruiserShip;
	private JLabel submarineShip;
	private JLabel destroyerShip;
	private JButton auto; // automatic button to place ships
	private static int points = 17;
	private BattleshipModel model; // using model
	private BattleshipController controller; //using controller
	ImageIcon buttonIcon = new ImageIcon("Wave2.png"); // importing image
	ImageIcon carrierShipImg = new ImageIcon("CarrierFull1.png");
	ImageIcon battleShipImg = new ImageIcon("BattleshipFull1.png");
	ImageIcon cruiserShipImg = new ImageIcon("CruiserFull1.png");
	ImageIcon submarineShipImg = new ImageIcon("SubmarineFull1.png");
	ImageIcon destroyerShipImg = new ImageIcon("DestroyerFull1.png");
	
	
	private static int BOARDGRIDLAYOUTSIZE = 10; //used in grid layout size
	private static int BOARDSIZE = 10; //constant for board size
	private static int INTERACTGRIDLAYOUTROW = 2;
	private static int INTERACTGRIDLAYOUTCOLUMN = 3;
	
	
	//constructor
	BattleshipView(BattleshipModel gameModel){
		JFrame frame = new JFrame();
		frame.setTitle("Battleship Game Server");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setSize(300,700);
		frame.setLayout(new GridLayout(3,0));
		
		JPanel enemyPanel = new JPanel();
		enemyPanel.setLayout(new GridLayout(BOARDGRIDLAYOUTSIZE,BOARDGRIDLAYOUTSIZE)); // grid layout is 30 by 30
		//enemyPanel.setBounds(0,0,0,0);
		
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
				
				enemyBoard[i][j].addActionListener(enemyHandler); //attach a listener
				enemyPanel.add(enemyBoard[i][j]); //add button to the grid layout
				number++;
			}
			letter++;
			number = 1;
		}
		
		JPanel userInteractPanel = new JPanel();
		userInteractPanel.setLayout(new GridLayout(INTERACTGRIDLAYOUTROW, INTERACTGRIDLAYOUTCOLUMN)); // grid layout is 30 by 30
		//gapPanel.setBounds(1,1,1,1);
		interactBoard = new JLabel[5];
		AutoButtonHandler autoHandler = new AutoButtonHandler(); // This is for automatic placement
		ClickListener clickListener = new ClickListener();
		this.addMouseListener(clickListener);
		DragListener dragListener = new DragListener();
		this.addMouseMotionListener(dragListener);
		
		//carrierShip = new JLabel(carrierShipImg);
		//battleShip = new JLabel(battleShipImg);
		//cruiserShip = new JLabel(cruiserShipImg);
		//submarineShip = new JLabel(submarineShipImg);
		//destroyerShip = new JLabel(destroyerShipImg);
		
		for (int i = 0; i < 5; i++) {
			interactBoard[i] = new JLabel();
			if (i == 0) {
				interactBoard[i] = new JLabel(carrierShipImg);
			}
			else if (i == 1) {
				interactBoard[i] = new JLabel(battleShipImg);
			}
			else if (i == 2) {
				interactBoard[i] = new JLabel(cruiserShipImg);
			}
			else if (i == 3) {
				interactBoard[i] = new JLabel(submarineShipImg);
			}
			else{
				interactBoard[i] = new JLabel(destroyerShipImg);
			}
			
			userInteractPanel.add(interactBoard[i]);
			userInteractPanel.setBackground(Color.WHITE);
		}
		
		auto = new JButton(); //create the button
		auto.setText("Auto"); //set the text, although the button is too small and it will not display
		
		userInteractPanel.add(auto); //add to the grid layout
		auto.addActionListener(autoHandler); //add action listener to auto button
		auto.setActionCommand("automatic");
		
		JPanel userPanel = new JPanel();
		userPanel.setLayout(new GridLayout(BOARDGRIDLAYOUTSIZE,BOARDGRIDLAYOUTSIZE)); // grid layout is 30 by 30
		//userPanel.setBounds(1,1,1,1);
		userBoard = new JLabel[BOARDSIZE][BOARDSIZE]; //user board with 100 spots for the ships
		
		//loops through and sets the user's boards settings
		for(int i = 0; i < BOARDSIZE; i++) {
			for (int j = 0; j < BOARDSIZE; j++) {
				userBoard[i][j] = new JLabel(); //create a label
				userBoard[i][j].setBorder(BorderFactory.createLineBorder(Color.black)); //outline it in black
				userPanel.add(userBoard[i][j]); //add to grid layout
			}
		}

		model = gameModel; //initialize the model to the one created in the controller
		frame.add(enemyPanel);
		frame.add(userInteractPanel);
		frame.add(userPanel);
		frame.setVisible(true);
		
	}
	
	//register the controller so the view can tell controller it is ready to send data
	public void registerController(BattleshipController cont) {
		controller = cont;
	}

	// button handler for the enemy board.
	private class EnemyButtonHandler implements ActionListener{
		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent event) {
			JButton tempBtn = (JButton)event.getSource(); //grab the button that was pressed
			
        	String result = controller.SendMessage(tempBtn.getText()); //check the hit using the button's text

        	//if it's a hit, change the button to reflect so
        	if (result.equals("HIT")) {
        		tempBtn.setText("X"); //set the button to say X
        		tempBtn.setForeground(Color.red); //make the color Red
        		points--;
        	}	
        	else if (result.equals("DUPE")) {
        		return; //pressing the button repeatedly will do nothing
        	}
        	else {
        		tempBtn.setText("O"); //set the button to O
        		tempBtn.setForeground(Color.WHITE); //show the color as white (a bit hard to see, may need to fix)
        	}
        	
        	controller.WaitForMessage(); //now the program waits for the other to send data
        	//this has the consequence of not allowing ui updates to happen before the program stops
        	//at the read function. not sure why or how to fix. but it is updated before it is seen
		}
	}
	
	// button handler for the automatic button
	private class AutoButtonHandler implements ActionListener{
		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == auto) {
				auto.setEnabled(false); //dont allow to repress auto button for now
				
				for (int i = 0; i < 5; i++) {
						interactBoard[i].setIcon(null);
					if (i == 1) {
						interactBoard[i].setText("<html>Points <br/> Remaining: " + Integer.toString(points) + "</html>");
						}
				}
				
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
				
				
				if (!controller.GetIsServer()) {
					controller.WaitForMessage(); //client will go second and will wait for server to start
				}
			}//end if get source auto
		}//end action performed method
	}//end autobutton class 
	
	private class ClickListener extends MouseAdapter{
		public void mousePressed(MouseEvent event) {
			//destroyerShipImg = event.getPoint();
		}	
	}
    private class DragListener extends MouseMotionAdapter{
    	public void mouseDragged(MouseEvent event) {
    		//Point currPoint = event.getPoint();
    		//int dx = (int) (currPoint.getX() - prevPoint.getX());
    		//int dy = (int) (currPoint.getY() - prevPoint.getY());
    		
    		//imageUpperLeft.translate(dx, dy);
    		//prevPoint = currPoint;
    		//repaint();  		
    	}
    }
	
	//display the results in a new window that when closed closes the entire program
	public void DisplayResults(String resultMessage) {
		JOptionPane.showMessageDialog(this, resultMessage);
		controller.CloseConnection();
		System.exit(0);
	}
}
