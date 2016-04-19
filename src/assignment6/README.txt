SEATS:

1. Our Seat Map is exactly like the one found on the webpage.
2. In general, the seats in the middle are the most desirable, then goes less desiralbe as they spread to the two sides of the theatre. In other words, 115 = 114 > 116 = 113 > 117 = 112 > 118 = 111 > ......
3. Also, the seats in the front in more desirable, so A > B > C > D in general.
4. When taking into account the desirability of seats in both horizontal and verticle directions, we decided to do the following: The seats in a middle row is as desirable as the seats on the left or right side two rows ahead of that middle row. In case that's not clear, here is an example: middle seats in row C is as desirable as the left and right seats in row A. (But the middle seats in row C is a still little more desirable since it is in the middle)
5. Before the program starts taking requests, it initializes the map and put the seats into an arraylist. So when the program receives a request, it does not need to look for a best available seat. Instead, it simply pops the top element of that arraylist.

Multithreading:
1. The way we implmented multiple box offices is through using more that one thread. Specifically, we implemented this in the driver part by limiting the number of active threads to 3. We first assign 3 customers to the 3 threads, then use a while loop to check the status of the threads. Whenever we find that a thread is terminated(request processed), we assign a new customer to this thread and run it. We keep repeating this until all tickets are sold.
2. In our server class, there is a while loop checking if there is a connection request. Once a connection request is received, a new thread is created to handle this request. (So that the server doesn't have to wait till the completion of this request before receiving another request)
3. We could have implemented the handling of multiple threads in the server class, but since the pdf says that the TA's will use our TestTicketOffice class, I think it's fine to handle multithreading in the TestTicketOffice class.

Queue of Customers:
1. Initially there is 100 - 1000 customers waiting in a line. (a linked list)
2. Since the pdf says that the line is never empty, we decided that, when there is no more waiting customers, 100 - 1000 customers are added to the line.


Task allocation:

We together designed the algorithm.

Tianyun Duan: map initialization, getSeat() method. Correct communication between the clients and the server. Synchronization.

Caesar Gonzales: main test Case. Multithreading.

Other Things:
1. Our program is pushed to the branch called "NewBranch," instead of "master."
2. When the last ticket is sold, and other box offices are processing requests, they will show a message Saying "Sorry, all tickets are sold." to their current clients, and then the program terminates. The program does not show this message to the remaining clients in the line.