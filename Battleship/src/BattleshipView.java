import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
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
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.text.html.ImageView;

public class BattleshipView extends JFrame{
	private JButton [][] enemyBoard; //Enemy Board at top
	private JLabel [][] userBoard; //User board at bottom
	private JLabel [] interactBoard;
	private JButton auto; // automatic button to place ships
	private JLabel clicked;
	
	private BattleshipModel model; // using model
	private BattleshipController controller; //using controller
	
	private ImageIcon buttonIcon; // importing image
	private ImageIcon carrierShipImg;
	private ImageIcon battleShipImg;
	private ImageIcon cruiserShipImg;
	private ImageIcon submarineShipImg;
	private ImageIcon destroyerShipImg;
	
	private ImageIcon verCarrierShipImg;
	private ImageIcon verBattleShipImg;
	private ImageIcon verCruiserShipImg;
	private ImageIcon verSubmarineShipImg;
	private ImageIcon verDestroyerShipImg;
	
	private ImageIcon carrierShipImg1;
	private ImageIcon carrierShipImg2;
	private ImageIcon carrierShipImg3;
	private ImageIcon carrierShipImg4;
	private ImageIcon carrierShipImg5;
	
	private ImageIcon battleShipImg1;
	private ImageIcon battleShipImg2;
	private ImageIcon battleShipImg3;
	private ImageIcon battleShipImg4;
	
	private ImageIcon cruiserShipImg1;
	private ImageIcon cruiserShipImg2;
	private ImageIcon cruiserShipImg3;
	
	private ImageIcon submarineShipImg1;
	private ImageIcon submarineShipImg2;
	private ImageIcon submarineShipImg3;
	
	private ImageIcon destroyerShipImg1;
	private ImageIcon destroyerShipImg2;
	
	private ImageIcon vercarrierShipImg1;
	private ImageIcon vercarrierShipImg2;
	private ImageIcon vercarrierShipImg3;
	private ImageIcon vercarrierShipImg4;
	private ImageIcon vercarrierShipImg5;
	
	private ImageIcon verbattleShipImg1;
	private ImageIcon verbattleShipImg2;
	private ImageIcon verbattleShipImg3;
	private ImageIcon verbattleShipImg4;
	
	private ImageIcon vercruiserShipImg1;
	private ImageIcon vercruiserShipImg2;
	private ImageIcon vercruiserShipImg3;
	
	private ImageIcon versubmarineShipImg1;
	private ImageIcon versubmarineShipImg2;
	private ImageIcon versubmarineShipImg3;
	
	private ImageIcon verdestroyerShipImg1;
	private ImageIcon verdestroyerShipImg2;
	
	private AudioInputStream splashNoise;
	private AudioInputStream explosionNoise;
	private AudioInputStream startNoise;
	private AudioInputStream winNoise;
	private AudioInputStream loseNoise;
	private Clip splashClip;
	private Clip explosionClip;
	private Clip startClip;
	private Clip winClip;
	private Clip loseClip;
	
	private static int BOARDGRIDLAYOUTSIZE = 10; //used in grid layout size
	private static int BOARDSIZE = 10; //constant for board size
	private static int INTERACTGRIDLAYOUTROW = 2;
	private static int INTERACTGRIDLAYOUTCOLUMN = 3;
	private static int SHIPS = 5;
	
	
	//constructor
	BattleshipView(BattleshipModel gameModel){		
		try {
			buttonIcon = new ImageIcon("Wave2.png"); // importing image
			carrierShipImg = new ImageIcon(new ImageIcon("CarrierFull.png").getImage().getScaledInstance(90, 20, Image.SCALE_DEFAULT));
			battleShipImg = new ImageIcon(new ImageIcon("BattleshipFull.png").getImage().getScaledInstance(90, 20, Image.SCALE_DEFAULT));
			cruiserShipImg = new ImageIcon(new ImageIcon("CruiserFull.png").getImage().getScaledInstance(90, 20, Image.SCALE_DEFAULT));
			submarineShipImg = new ImageIcon(new ImageIcon("SubmarineFull.png").getImage().getScaledInstance(90, 20, Image.SCALE_DEFAULT));
			destroyerShipImg = new ImageIcon(new ImageIcon("DestroyerFull.png").getImage().getScaledInstance(60, 20, Image.SCALE_DEFAULT));
			
			verCarrierShipImg = new ImageIcon(new ImageIcon("VerCarrierFull.png").getImage().getScaledInstance(30, 100, Image.SCALE_DEFAULT));
			verBattleShipImg = new ImageIcon(new ImageIcon("VerBattleshipFull.png").getImage().getScaledInstance(30, 80, Image.SCALE_DEFAULT));
			verCruiserShipImg = new ImageIcon(new ImageIcon("VerCruiserFull.png").getImage().getScaledInstance(30, 60, Image.SCALE_DEFAULT));
			verSubmarineShipImg = new ImageIcon(new ImageIcon("VerSubmarineFull.png").getImage().getScaledInstance(30, 60, Image.SCALE_DEFAULT));
			verDestroyerShipImg = new ImageIcon(new ImageIcon("VerDestroyerFull.png").getImage().getScaledInstance(30, 40, Image.SCALE_DEFAULT));
			
			battleShipImg1 = new ImageIcon(new ImageIcon("Battleship1.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			battleShipImg2 = new ImageIcon(new ImageIcon("Battleship2.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			battleShipImg3 = new ImageIcon(new ImageIcon("Battleship3.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			battleShipImg4 = new ImageIcon(new ImageIcon("Battleship4.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			
			carrierShipImg1 = new ImageIcon(new ImageIcon("Carrier1.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			carrierShipImg2 = new ImageIcon(new ImageIcon("Carrier2.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			carrierShipImg3 = new ImageIcon(new ImageIcon("Carrier3.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			carrierShipImg4 = new ImageIcon(new ImageIcon("Carrier4.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			carrierShipImg5 = new ImageIcon(new ImageIcon("Carrier5.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			
			cruiserShipImg1 = new ImageIcon(new ImageIcon("Cruiser1.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			cruiserShipImg2 = new ImageIcon(new ImageIcon("Cruiser2.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			cruiserShipImg3 = new ImageIcon(new ImageIcon("Cruiser3.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			
			destroyerShipImg1 = new ImageIcon(new ImageIcon("Destroyer1.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			destroyerShipImg2 = new ImageIcon(new ImageIcon("Destroyer2.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			
			submarineShipImg1 = new ImageIcon(new ImageIcon("Submarine1.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			submarineShipImg2 = new ImageIcon(new ImageIcon("Submarine2.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			submarineShipImg3 = new ImageIcon(new ImageIcon("Submarine3.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			
			verbattleShipImg1 = new ImageIcon(new ImageIcon("verBattleship1.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			verbattleShipImg2 = new ImageIcon(new ImageIcon("verBattleship2.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			verbattleShipImg3 = new ImageIcon(new ImageIcon("verBattleship3.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			verbattleShipImg4 = new ImageIcon(new ImageIcon("verBattleship4.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			
			vercarrierShipImg1 = new ImageIcon(new ImageIcon("verCarrier1.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			vercarrierShipImg2 = new ImageIcon(new ImageIcon("verCarrier2.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			vercarrierShipImg3 = new ImageIcon(new ImageIcon("verCarrier3.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			vercarrierShipImg4 = new ImageIcon(new ImageIcon("verCarrier4.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			vercarrierShipImg5 = new ImageIcon(new ImageIcon("verCarrier5.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			
			vercruiserShipImg1 = new ImageIcon(new ImageIcon("verCruiser1.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			vercruiserShipImg2 = new ImageIcon(new ImageIcon("verCruiser2.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			vercruiserShipImg3 = new ImageIcon(new ImageIcon("verCruiser3.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			
			verdestroyerShipImg1 = new ImageIcon(new ImageIcon("verDestroyer1.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			verdestroyerShipImg2 = new ImageIcon(new ImageIcon("verDestroyer2.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			
			versubmarineShipImg1 = new ImageIcon(new ImageIcon("verSubmarine1.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			versubmarineShipImg2 = new ImageIcon(new ImageIcon("verSubmarine2.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			versubmarineShipImg3 = new ImageIcon(new ImageIcon("verSubmarine3.png").getImage().getScaledInstance(30, 20, Image.SCALE_DEFAULT));
			
			splashNoise = AudioSystem.getAudioInputStream(new File("Splish.wav").getAbsoluteFile());
			splashClip = AudioSystem.getClip();
			splashClip.open(splashNoise);
		
			explosionNoise = AudioSystem.getAudioInputStream(new File("Explosion1.wav").getAbsoluteFile());
			explosionClip = AudioSystem.getClip();
			explosionClip.open(explosionNoise);
			
			startNoise = AudioSystem.getAudioInputStream(new File("GameStart.wav").getAbsoluteFile());
			startClip = AudioSystem.getClip();
			startClip.open(startNoise);
			
			winNoise = AudioSystem.getAudioInputStream(new File("Win.wav").getAbsoluteFile());
			winClip = AudioSystem.getClip();
			winClip.open(winNoise);
			
			loseNoise = AudioSystem.getAudioInputStream(new File("Lose.wav").getAbsoluteFile());
			loseClip = AudioSystem.getClip();
			loseClip.open(loseNoise);
			}
		catch (IOException ioException) {
			this.DisplayResults("IOException in creating sounds/images");
		}
		catch (UnsupportedAudioFileException uAFE) {
			this.DisplayResults("Unsupport File Type for Sound");
		}
		catch (LineUnavailableException lUE) {
			this.DisplayResults("Error!!!");
		}
		JFrame frame = new JFrame();
		frame.setTitle("Battleship Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setSize(300,600);
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
				enemyBoard[i][j].setFocusable(false);
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
		interactBoard = new JLabel[SHIPS];
		AutoButtonHandler autoHandler = new AutoButtonHandler(); // This is for automatic placement
		
		for (int i = 0; i < SHIPS; i++) {
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
			ClickListener clickListener = new ClickListener();
			DragListener dragListener = new DragListener();
			interactBoard[i].addMouseListener(clickListener);
			interactBoard[i].addMouseMotionListener(dragListener);
		}
		
		auto = new JButton(); //create the button
		auto.setText("Auto"); //set the text, although the button is too small and it will not display
		auto.setFocusable(false);
		
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
		
		startClip.stop();
		startClip.setFramePosition(0);
		startClip.start();
			
	}//end constructor
	
	//update the health text object created after ships are placed
	public void updateHealth() {
		interactBoard[0].setText("<html>Carrier <br/> Remaining: " + model.GetCarrierStatus()+ "</html>");
		interactBoard[1].setText("<html>Battleship <br/> Remaining: " + model.GetBattleshipStatus()+ "</html>");
		interactBoard[2].setText("<html>Cruiser <br/> Remaining: " + model.GetCruiserStatus()+ "</html>");
		interactBoard[3].setText("<html>Submarine <br/> Remaining: " + model.GetSubmarineStatus()+ "</html>");
		interactBoard[4].setText("<html>Destroyer <br/> Remaining: " + model.GetDestroyerStatus()+ "</html>");
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
        		explosionClip.stop();
        		explosionClip.setFramePosition(0);
        		explosionClip.start();
	       	}	
        	else if (result.equals("DUPE")) {
        		return; //pressing the button repeatedly will do nothing
        	}
        	else {
        		tempBtn.setText("O"); //set the button to O
        		tempBtn.setForeground(Color.WHITE); //show the color as white (a bit hard to see, may need to fix)
        		splashClip.stop();
        		splashClip.setFramePosition(0);
        		splashClip.start();
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
				
				//change the middle panels to reflect new data
				for (int i = 0; i < SHIPS; i++) {
						interactBoard[i].setIcon(null);
				}
				updateHealth();
				
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
				    	//userBoard[0][0].setText("Ca"); userBoard[0][1].setText("Ca");
				    	//userBoard[0][2].setText("Ca"); userBoard[0][3].setText("Ca");
				    	//userBoard[0][4].setText("Ca");
				    	
				    	userBoard[0][0].setIcon(carrierShipImg1); userBoard[0][1].setIcon(carrierShipImg2);
				    	userBoard[0][2].setIcon(carrierShipImg3); userBoard[0][3].setIcon(carrierShipImg4);
				    	userBoard[0][4].setIcon(carrierShipImg5);
				    }
				    else if (temp == 2) {
				    	String[] placeMyCarrier = {"B1", "B2", "B3", "B4", "B5"};
				    	model.PlaceCarrierShip(placeMyCarrier);
				    	//userBoard[1][0].setText("Ca"); userBoard[1][1].setText("Ca");
				    	//userBoard[1][2].setText("Ca"); userBoard[1][3].setText("Ca");
				    	//userBoard[1][4].setText("Ca");
				    	
				    	userBoard[1][0].setIcon(carrierShipImg1); userBoard[1][1].setIcon(carrierShipImg2);
				    	userBoard[1][2].setIcon(carrierShipImg3); userBoard[1][3].setIcon(carrierShipImg4);
				    	userBoard[1][4].setIcon(carrierShipImg5);
				    }
				    else {
				    	String[] placeMyCarrier = {"C1", "C2", "C3", "C4", "C5"};
				    	model.PlaceCarrierShip(placeMyCarrier);
				    	//userBoard[2][0].setText("Ca"); userBoard[2][1].setText("Ca");
				    	//userBoard[2][2].setText("Ca"); userBoard[2][3].setText("Ca");
				    	//userBoard[2][4].setText("Ca");
				    	
				    	userBoard[2][0].setIcon(carrierShipImg1); userBoard[2][1].setIcon(carrierShipImg2);
				    	userBoard[2][2].setIcon(carrierShipImg3); userBoard[2][3].setIcon(carrierShipImg4);
				    	userBoard[2][4].setIcon(carrierShipImg5);
				    }
				}
				
				//battleship
				if (!model.GetShipPlacementStatus("Battleship")) {
					Random random = new Random();
				    int temp = random.nextInt(4 - 1) + 1;
				    if (temp == 1) {
				    	String[] placeMyBattleship = {"E3", "F3", "G3", "H3"};
				    	model.PlaceBattleshipShip(placeMyBattleship);
				    	//userBoard[4][2].setText("Ba"); userBoard[5][2].setText("Ba");
				    	//userBoard[6][2].setText("Ba"); userBoard[7][2].setText("Ba");
				    	
				    	userBoard[4][2].setIcon(verbattleShipImg1); userBoard[5][2].setIcon(verbattleShipImg2);
				    	userBoard[6][2].setIcon(verbattleShipImg3); userBoard[7][2].setIcon(verbattleShipImg4);
				    }
				    else if (temp == 2) {
				    	String[] placeMyBattleship = {"E4", "F4", "G4", "H4"};
				    	model.PlaceBattleshipShip(placeMyBattleship);
				    	//userBoard[4][3].setText("Ba"); userBoard[5][3].setText("Ba");
				    	//userBoard[6][3].setText("Ba"); userBoard[7][3].setText("Ba");
				    	
				    	userBoard[4][3].setIcon(verbattleShipImg1); userBoard[5][3].setIcon(verbattleShipImg2);
				    	userBoard[6][3].setIcon(verbattleShipImg3); userBoard[7][3].setIcon(verbattleShipImg4);
				    }
				    else {
				    	String[] placeMyBattleship = {"E5", "F5", "G5", "H5"};
				    	model.PlaceBattleshipShip(placeMyBattleship);
				    	//userBoard[4][4].setText("Ba"); userBoard[5][4].setText("Ba");
				    	//userBoard[6][4].setText("Ba"); userBoard[7][4].setText("Ba");
				    	
				    	userBoard[4][4].setIcon(verbattleShipImg1); userBoard[5][4].setIcon(verbattleShipImg2);
				    	userBoard[6][4].setIcon(verbattleShipImg3); userBoard[7][4].setIcon(verbattleShipImg4);
				    }
				}
				
				//cruiser
				if (!model.GetShipPlacementStatus("Cruiser")) {
					Random random = new Random();
				    int temp = random.nextInt(4 - 1) + 1;
				    if (temp == 1) {
				    	String[] placeMyCruiser = {"A7", "B7", "C7"};
				    	model.PlaceCruiserShip(placeMyCruiser);
				    	//userBoard[0][6].setText("Cr"); userBoard[1][6].setText("Cr");
				    	//userBoard[2][6].setText("Cr");
				    	
				    	userBoard[0][6].setIcon(vercruiserShipImg1); userBoard[1][6].setIcon(vercruiserShipImg2);
				    	userBoard[2][6].setIcon(vercruiserShipImg3);
				    }
				    else if (temp == 2) {
				    	String[] placeMyCruiser = {"A9", "B9", "C9"};
				    	model.PlaceCruiserShip(placeMyCruiser);
				    	//userBoard[0][8].setText("Cr"); userBoard[1][8].setText("Cr");
				    	//userBoard[2][8].setText("Cr");
				    	
				    	userBoard[0][8].setIcon(vercruiserShipImg1); userBoard[1][8].setIcon(vercruiserShipImg2);
				    	userBoard[2][8].setIcon(vercruiserShipImg3);
				    }
				    else {
				    	String[] placeMyCruiser = {"B7", "B8", "B9"};
				    	model.PlaceCruiserShip(placeMyCruiser);
				    	//userBoard[1][6].setText("Cr"); userBoard[1][7].setText("Cr");
				    	//userBoard[1][8].setText("Cr"); 
				    	
				    	userBoard[1][6].setIcon(cruiserShipImg1); userBoard[1][7].setIcon(cruiserShipImg2);
				    	userBoard[1][8].setIcon(cruiserShipImg3); 
				    }
				}
				
				//submarine
				if (!model.GetShipPlacementStatus("Submarine")) {
					Random random = new Random();
				    int temp = random.nextInt(4 - 1) + 1;
				    if (temp == 1) {
				    	String[] placeMySubmarine = {"E7", "F7", "G7"};
				    	model.PlaceSubmarineShip(placeMySubmarine);
				    	//userBoard[4][6].setText("Su"); userBoard[5][6].setText("Su");
				    	//userBoard[6][6].setText("Su");
				    	
				    	userBoard[4][6].setIcon(versubmarineShipImg1); userBoard[5][6].setIcon(versubmarineShipImg2);
				    	userBoard[6][6].setIcon(versubmarineShipImg3);
				    }
				    else if (temp == 2) {
				    	String[] placeMySubmarine = {"E9", "F9", "G9"};
				    	model.PlaceSubmarineShip(placeMySubmarine);
				    	//userBoard[4][8].setText("Su"); userBoard[5][8].setText("Su");
				    	//userBoard[6][8].setText("Su");
				    	
				    	userBoard[4][8].setIcon(versubmarineShipImg1); userBoard[5][8].setIcon(versubmarineShipImg2);
				    	userBoard[6][8].setIcon(versubmarineShipImg3);
				    }
				    else {
				    	String[] placeMySubmarine = {"G7", "G8", "G9"};
				    	model.PlaceSubmarineShip(placeMySubmarine);
				    	//userBoard[6][6].setText("Su"); userBoard[6][7].setText("Su");
				    	//userBoard[6][8].setText("Su"); 
				    	
				    	userBoard[6][6].setIcon(submarineShipImg1); userBoard[6][7].setIcon(submarineShipImg2);
				    	userBoard[6][8].setIcon(submarineShipImg3); 
				    }
				}
				
				//destroyer
				if (!model.GetShipPlacementStatus("Destroyer")) {
					Random random = new Random();
				    int temp = random.nextInt(4 - 1) + 1;
				    if (temp == 1) {
				    	String[] placeMyDestroyer = {"I9", "I10"};
				    	model.PlaceDestroyerShip(placeMyDestroyer);
				    	//userBoard[8][8].setText("De"); userBoard[8][9].setText("De");
				    	
				    	userBoard[8][8].setIcon(destroyerShipImg1); userBoard[8][9].setIcon(destroyerShipImg2);
				    }
				    else if (temp == 2) {
				    	String[] placeMyDestroyer = {"J9", "J10"};
				    	model.PlaceDestroyerShip(placeMyDestroyer);
				    	//userBoard[9][8].setText("De"); userBoard[9][9].setText("De");
				    	
				    	userBoard[9][8].setIcon(destroyerShipImg1); userBoard[9][9].setIcon(destroyerShipImg2);
				    }
				    else {
				    	String[] placeMyDestroyer = {"I10", "J10"};
				    	model.PlaceDestroyerShip(placeMyDestroyer);
				    	//userBoard[8][9].setText("De"); userBoard[9][9].setText("De"); 
				    	
				    	userBoard[8][9].setIcon(verdestroyerShipImg1); userBoard[9][9].setIcon(verdestroyerShipImg2); 
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
		public void mouseClicked(MouseEvent event) {
				JComponent comp = (JComponent) event.getComponent();
	          Component child = comp.findComponentAt(event.getPoint());
	         clicked = (JLabel) child;
	            if(event.getClickCount()==2) {
	            	
	            	if(clicked.getIcon() == carrierShipImg) {
	            		clicked.setIcon(verCarrierShipImg);
	            	}
	            	else if(clicked.getIcon() == verCarrierShipImg){
	            		clicked.setIcon(carrierShipImg);
	            	}
	            	else if(clicked.getIcon() == battleShipImg) {
	            		clicked.setIcon(verBattleShipImg);
	            	}
	            	else if(clicked.getIcon() == verBattleShipImg){
	            		clicked.setIcon(battleShipImg);
	            	}
	            	else if(clicked.getIcon() == cruiserShipImg) {
	            		clicked.setIcon(verCruiserShipImg);
	            	}
	            	else if(clicked.getIcon() == verCruiserShipImg){
	            		clicked.setIcon(cruiserShipImg);
	            	}
	            	else if(clicked.getIcon() == submarineShipImg) {
	            		clicked.setIcon(verSubmarineShipImg);
	            	}
	            	else if(clicked.getIcon() == verSubmarineShipImg){
	            		clicked.setIcon(submarineShipImg);
	            	}
	            	else if(clicked.getIcon() == destroyerShipImg) {
	            		clicked.setIcon(verDestroyerShipImg);
	            	}
	            	else if(clicked.getIcon() == verDestroyerShipImg){
	            		clicked.setIcon(destroyerShipImg);
	            	}
	            }
		}	
	}
    private class DragListener extends MouseMotionAdapter{
    	//Point prevPoint;
        
        @Override
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
		if (resultMessage.equals("YOU WON")) {
    		winClip.stop();
    		winClip.setFramePosition(0);
    		winClip.start();
			}
		else if (resultMessage.equals("YOU LOST")) {
    		loseClip.stop();
    		loseClip.setFramePosition(0);
    		loseClip.start();
			}
		JOptionPane.showMessageDialog(this, resultMessage);
		controller.CloseConnection();
		System.exit(0);
	}
}
