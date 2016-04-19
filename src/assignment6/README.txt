SEATS:

1. Our Seat Map is exactly like the one found on the webpage.
2. In general, the seats in the middle are the most desirable, then goes less desiralbe as they spread to the two sides of the theatre. In other words, 115 = 114 > 116 = 113 > 117 = 112 > 118 = 111 > ......
3. Also, the seats in the front in more desirable, so A > B > C > D in general.
4. When taking into account the desirability of seats in both horizontal and verticle directions, we decided to do the following: The seats in a middle row is as desirable as the seats on the left or right side two rows ahead of that middle row. In case that's not clear, here is an example: middle seats in row C is as desirable as the left and right seats in row A. (But the middle seats in row C is a still little more desirable since it is in the middle)
5. Before the program starts taking requests, it initializes the map and put the seats into an arraylist. So when the program receives a request, it does not need to look for a best available seat. Instead, it just pops the top element of that arraylist.