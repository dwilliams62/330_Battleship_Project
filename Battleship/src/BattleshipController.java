import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class BattleshipController {
    private ObjectOutputStream output; // output stream to server
    private ObjectInputStream input; // input stream from server
    private String message = ""; // message from server
    private static String chatServer; // host server for this application
	private ServerSocket server; // server socket
	private Socket connection; // connection to client
	private boolean isServer = true;
    
    private BattleshipModel model; //hold the model to communicate with it
    private BattleshipView view; //hold the view to communicate with it
    
    public static void main(String[] args) {
		String host;
	    if (args.length == 0) {
	    	host = "127.0.0.1";
	    }
	    else {
	    	host = args[0];
	    }
	    
		//start the games view with a made model
		BattleshipModel gameModel = new BattleshipModel();
		BattleshipView gameView = new BattleshipView(gameModel);
		BattleshipController gameController = new BattleshipController(gameModel, gameView, host);
		gameView.registerController(gameController);
		
		if (args.length == 0) {
			gameController.SetIsServer(true);
			gameController.RunServer();
		}
		else {
			gameController.SetIsServer(false);
			gameController.RunClient();
		}
	}
    
	BattleshipController(BattleshipModel model, BattleshipView view, String host) {
		//start the games view with a made model and set the host ip
		this.model = model;
		this.view = view;
		chatServer = host;
	}
	
	public void RunServer() {
		try {
			System.out.println("Creating server");
			server = new ServerSocket(12345, 100);
			System.out.println("Waiting For Connection");
			WaitForConnection();
			System.out.println("Connected");
			GetStreams();
		}
		catch (EOFException eofException) {
			view.DisplayResults("Server Terminated Connection");
		}
		catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
	private void WaitForConnection() {
		try {
			connection = server.accept();
		}
		catch (IOException ioException){
			view.DisplayResults("IO Error WaitForConnection");
		}
	}
	
	//starts the client
	public void RunClient() {
		try {
			ConnectToServer(); //will stay here until a connection is made
			GetStreams(); //recieves the streams from the server
		}
		catch (EOFException eofException) {
			view.DisplayResults("Client Terminated Connection");
		}
		catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
	private void ConnectToServer() throws IOException {
		connection = new Socket( InetAddress.getByName( chatServer ), 12345 ); //connect to the server socket
	}
	
	private void GetStreams() throws IOException {
		output = new ObjectOutputStream( connection.getOutputStream() );      
	    output.flush();
	    input = new ObjectInputStream( connection.getInputStream() );
	}
	
	public String SendMessage(String msg){
		//program will first send a message, along the lines of A1, B4, etc
		try {
			output.writeObject(msg);
		}
		catch (IOException ioException) {
			view.DisplayResults("Error Sending Message");
		}
		
		//program will then wait for the receiver to update if it is a hit, miss, or if we won
		try {
			message = (String)input.readObject();
		}
		catch (ClassNotFoundException classNotFoundException){
			view.DisplayResults("Unknown Object Type Recieved");
		}
		catch (IOException ioException) {
			view.DisplayResults("IOException in ProcessConnection");
		}
		
		//if we won, it closes the program after displaying a message
		if (message.equals("YOU WON")) {
			view.DisplayResults(message);
			return " ";
		}
		//otherwise it returns the status of hit or miss to the view
		else {
			return (String)message;
		}
	}
	
	public void WaitForMessage() {
		//program will start by waiting for a message
		try {
			message = (String)input.readObject();
		}
		catch (ClassNotFoundException classNotFoundException){
			view.DisplayResults("Unknown Object Type Recieved");
		}
		catch (IOException ioException) {
			view.DisplayResults("IOException in ProcessConnection");
		}
		
		//then will check if it is a hit or miss
		String result = model.checkHit(message);
		
		//if you lost due to that hit, you'll send to the opponent that they won and then show your loss message
		if (result.equals("HIT")) {
			result = model.checkWinner();
			if (result.equals("YOU LOST")) {
				result = "YOU WON";
			}
			else {
				result = "HIT";
			}
		}
		
		//send the data back to the shooter of if it was a hit, miss, or they won
		try {
			output.writeObject(result);
		}
		catch (IOException ioException) {
			view.DisplayResults("Error Sending Message");
		}
		
		//display loss message
		if (result.equals("YOU WON")) {
			view.DisplayResults("YOU LOST");
		}
		
		//now it is this user's turn to shoot
	}

	
	public void CloseConnection() {
	      try 
	      {
	         output.close(); // close output stream
	         input.close(); // close input stream
	         connection.close(); // close socket
	      } // end try
	      catch ( IOException ioException ) 
	      {
	         ioException.printStackTrace();
	      } // end catch
	}
	
	public void SetIsServer(boolean serv) {
		isServer = serv;
	}
	
	public boolean GetIsServer() {
		return isServer;
	}
}
