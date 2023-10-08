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
	private ObjectOutputStream output; // output stream to client
	private ObjectInputStream input; // input stream from client
	private ServerSocket server; // server socket
	private Socket connection; // connection to client
	private int counter = 1; // counter of number of connections
	private String message = "";
    
    private BattleshipModel model;
    private BattleshipView view;
    
	BattleshipController(BattleshipModel model, BattleshipView view) {
		//start the games view with a made model
		this.model = model;
		this.view = view;
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

	
	private void CloseConnection() {
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
}
