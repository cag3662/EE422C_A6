package assignment6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class TicketServer {
	static int PORT = 2222;
	public static ArrayList<String> Seats;
	// EE422C: no matter how many concurrent requests you get,
	// do not have more than three servers running concurrently
	final static int MAXPARALLELTHREADS = 3;

	public static void start(int portNumber) throws IOException {
		InitializeSeatMap();
		PORT = portNumber;
		TicketServer TServer = new TicketServer();
		Thread t1 = new Thread(TServer.new ThreadedTicketServer());
		t1.start();
	}
	private static void InitializeSeatMap() {
		Seats = new ArrayList<String>();
		String nextBestSeat = "";
		for (char row = 'A'; row <= 'B'; row++) {
			for (int num1 = 15; num1 <= 21; num1++) {
				int num2 = 29 - num1;
				// num1
				nextBestSeat = nextBestSeat + "M,";
				nextBestSeat = nextBestSeat + Integer.toString(num1 + 100);
				nextBestSeat = nextBestSeat + row;
				Seats.add(nextBestSeat);
				nextBestSeat = "";
				// num2
				nextBestSeat = nextBestSeat + "M,";
				nextBestSeat = nextBestSeat + Integer.toString(num2 + 100);
				nextBestSeat = nextBestSeat + row;
				Seats.add(nextBestSeat);
				nextBestSeat = "";
			}
		}

		for (char row = 'C'; row <= 'X'; row++) {
			for (int num1 = 15; num1 <= 28; num1++) {
				int num2 = 29 - num1;

				// num1
				if (num1 <= 21 && num1 >= 8) {
					nextBestSeat = nextBestSeat.concat("M,");
					nextBestSeat = nextBestSeat.concat(Integer.toString(num1 + 100));
					nextBestSeat = nextBestSeat + row;
				} else if (num1 >= 22) {
					nextBestSeat = nextBestSeat.concat("HR,");
					nextBestSeat = nextBestSeat.concat(Integer.toString(num1 + 100));
					nextBestSeat = nextBestSeat + (char) (row - 2);
				}
				Seats.add(nextBestSeat);
				nextBestSeat = "";

				// num2
				if (num2 <= 21 && num2 >= 8) {
					nextBestSeat = nextBestSeat.concat("M,");
					nextBestSeat = nextBestSeat.concat(Integer.toString(num2 + 100));
					nextBestSeat = nextBestSeat + (row);
				} else if (num1 <= 107) {
					nextBestSeat = nextBestSeat.concat("HL,");
					nextBestSeat = nextBestSeat.concat(Integer.toString(num2 + 100));
					nextBestSeat = nextBestSeat + (char) (row - 2);
				}
				Seats.add(nextBestSeat);
				nextBestSeat = "";

			}
		}

		for (int i = 0; i <= 3; i++) {
			char row = 'W';
			row = (char) (row + i);
			for (int num1 = 22; num1 <= 28; num1++) {
				int num2 = 29 - num1;
				// num1
				nextBestSeat = "HR," + (num1 + 100) + (row);
				Seats.add(nextBestSeat);
				nextBestSeat = "";
				// num2
				nextBestSeat = "HL," + (num2 + 100) + (row);
				Seats.add(nextBestSeat);
				nextBestSeat = "";
			}
		}

		// row AA
		nextBestSeat = "HR," + 127 + "AA";
		Seats.add(nextBestSeat);
		nextBestSeat = "HL," + 118 + "AA";
		Seats.add(nextBestSeat);
		nextBestSeat = "HL," + 117 + "AA";
		Seats.add(nextBestSeat);
		nextBestSeat = "HL," + 116 + "AA";
		Seats.add(nextBestSeat);
		nextBestSeat = "HR," + 128 + "AA";
		Seats.add(nextBestSeat);
		nextBestSeat = "HL," + 104 + "AA";
		Seats.add(nextBestSeat);
		nextBestSeat = "HL," + 103 + "AA";
		Seats.add(nextBestSeat);
		nextBestSeat = "HL," + 102 + "AA";
		Seats.add(nextBestSeat);
		nextBestSeat = "HL," + 101 + "AA";
		Seats.add(nextBestSeat);

		// eliminate
		String seatToBeEliminated = "";
		for (char row = 'A'; row <= 'B'; row++) {
			for (int num = 101; num <= 107; num++) {
				seatToBeEliminated = "HL," + num + row;
				Seats.remove(seatToBeEliminated);
			}
		}
		Seats.remove("HL,107C");
	}

	class ThreadedTicketServer implements Runnable 
	{
	
		String hostname = "127.0.0.1";
		String threadname = "X";
		String testcase;
		String seat = "";
		TicketClient sc;
		public void run() {
	
			ServerSocket serverSocket;
			try {
				serverSocket = new ServerSocket(TicketServer.PORT);
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				out.println(getSeat());
				
					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public synchronized String getSeat()
		{
			String seat = "-1";
			if(Seats.size() != 0)
			{
				seat = Seats.get(0);
				Seats.remove(0);
			}
			return seat;
		}
	}
	
}
//package assignment6;
//
//import java.io.BufferedReader; 
//import java.io.IOException; 
//import java.io.InputStreamReader; 
//import java.io.PrintWriter; 
//import java.net.ServerSocket; 
//import java.net.Socket;
//
//public class TicketServer 
//{ 
//	static int PORT = 2222;
//
//	// EE422C: no matter how many concurrent requests you get, 
//	// do not have more than three servers running concurrently 
//	final static int MAXPARALLELTHREADS = 3;
//
//	public static void start(int portNumber) throws IOException 
//	{
//		PORT = portNumber; 
//		Runnable serverThread = new ThreadedTicketServer(); 
//		Thread t = new Thread(serverThread); 
//		t.start();
//	}
//} 
//
//class ThreadedTicketServer implements Runnable 
//{
//	String hostname = "127.0.0.1"; 
//	String threadname = "X"; 
//	String testcase; 
//	TicketClient sc;
//
//	public void run() {
//		// TODO 422C 
//		ServerSocket serverSocket; 
//		try {
//			serverSocket = new ServerSocket(TicketServer.PORT); 
//			Socket clientSocket = serverSocket.accept(); 
//			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
//		} 
//	catch (IOException e) { 
//		// TODO Auto-generated catch block 
//		e.printStackTrace();
//	}
//}
//}