package assignment6;

import static org.junit.Assert.fail;

import java.awt.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import org.junit.Test;

public class TestTicketOffice {
	//HAHAHAHA
	public static int score = 0;

	//@Test
	public void basicServerTest() {
		try {
			TicketServer.start(16789);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}finally {
		}
		TicketClient client = new TicketClient();
		client.requestTicket();
	}

	//@Test
	public void testServerCachedHardInstance() {
		try {
			TicketServer.start(16790);
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
		TicketClient client1 = new TicketClient("localhost", "c1");
		TicketClient client2 = new TicketClient("localhost", "c2");
		client1.requestTicket();
		client2.requestTicket();
		
	}

	//@Test
	public void twoNonConcurrentServerTest() {
		try {
			TicketServer.start(16791);
		} catch (Exception e) {
			fail();
		}
		TicketClient c1 = new TicketClient("nonconc1");
		TicketClient c2 = new TicketClient("nonconc2");
		TicketClient c3 = new TicketClient("nonconc3");
		c1.requestTicket();
		c2.requestTicket();
		c3.requestTicket();
	}

	//@Test
	public void twoConcurrentServerTest() {
		try {
			TicketServer.start(16792);
		} catch (Exception e) {
			fail();
		}
		final TicketClient c1 = new TicketClient("conc1");
		final TicketClient c2 = new TicketClient("conc2");
		final TicketClient c3 = new TicketClient("conc3");
		Thread t1 = new Thread() {
			public void run() {
				c1.requestTicket();
			}
		};
		Thread t2 = new Thread() {
			public void run() {
				c2.requestTicket();
			}
		};
		Thread t3 = new Thread() {
			public void run() {
				c3.requestTicket();
			}
		};
		t1.start();
		t2.start();
		t3.start();
		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void Test1()
	{
		LinkedList<TicketClient> clients = new LinkedList<TicketClient>();
		int startIndex = 0;

		try {
			TicketServer.start(16793);
		} catch (Exception e) {
			fail();
		}
		
	// Generate clients
		startIndex = generateClients(clients, startIndex) + 1;
		
	// Three clients at a time; Service first three
		TicketClient c1 = clients.get(0);
		TicketClient c2 = clients.get(1);
		TicketClient c3 = clients.get(2);
		clients.remove(0);
		clients.remove(0);
		clients.remove(0);

		Thread t1 = new Thread(c1.tc);
		Thread t2 = new Thread(c2.tc);
		Thread t3 = new Thread(c3.tc);
		
		t1.start();
		t2.start();
		t3.start();
		
	// Sell the rest of the tickets	
		while(TicketServer.Seats.size() != 0)
		{
		// Thread 1
			if (clients.size() == 0) {
				startIndex = generateClients(clients, startIndex) + 1;
			}
			
			else if (t1.getState() == Thread.State.TERMINATED) {
				TicketClient c = clients.get(0);
				clients.remove(0);
				t1 = new Thread(c.tc);
				t1.start();
			}
			
		// Thread 2
			if (clients.size() == 0) {
				startIndex = generateClients(clients, startIndex) + 1;
			}
			
			else if (t2.getState() == Thread.State.TERMINATED) {
				TicketClient c = clients.get(0);
				clients.remove(0);
				t2 = new Thread(c.tc);
				t2.start();
			}
			
		// Thread 3
			if (clients.size() == 0) {
				startIndex = generateClients(clients, startIndex) + 1;
			}
			
			else if (t3.getState() == Thread.State.TERMINATED) {
				TicketClient c = clients.get(0);
				clients.remove(0);
				t3 = new Thread(c.tc);
				t3.start();
			}
		}
	}
	
	public int generateClients(Queue<TicketClient> clients, int startIndex)
	{
		int numOfClients = 0;
		Random rand = new Random();
		numOfClients = rand.nextInt(901) + 100;
		for(int i = 1; i <= numOfClients; i++)
		{
			TicketClient c = new TicketClient(Integer.toString(i + startIndex));
			clients.add(c);
		}
		
		return numOfClients;
	}
}
