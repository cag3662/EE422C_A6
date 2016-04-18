package assignment6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class TicketClient {
	ThreadedTicketClient tc;
	String result = "dummy";
	String hostName = "";
	String threadName = "";

	TicketClient(String hostname, String threadname) {
		tc = new ThreadedTicketClient(this, hostname, threadname);
		hostName = hostname;
		threadName = threadname;
	}

	TicketClient(String name) {
		this("localhost", name);
	}

	TicketClient() {
		this("localhost", "unnamed client");
	}

	void requestTicket() {
		tc.run();
	}

	void sleep(int seconds) {
		try {
			Thread.sleep(1000 * seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}




class ThreadedTicketClient implements Runnable {
	String hostname = "127.0.0.1";
	String threadname = "X";
	TicketClient sc;

	public ThreadedTicketClient(TicketClient sc, String hostname, String threadname) {
		this.sc = sc;
		this.hostname = hostname;
		this.threadname = threadname;
	}

	public void run() {
		System.out.flush();
		try {
			String Seat = "";
			Socket echoSocket = new Socket(hostname, TicketServer.PORT);
			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

			//request
			out.println("request");
			
			//read result
			Seat = in.readLine();
			
			if(Seat.equals("-1") == true)
			{
				printFail();
			}
			else
			{
				printTicket(Seat, sc.threadName);
			}
			echoSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void printFail() 
	{
		System.out.println("Sorry, all tickets are sold");
	}

	private void printTicket(String Seat, String threadname)
	{
		System.out.println("Box Office " + threadname + ": Reserved " + Seat);
	}
}

