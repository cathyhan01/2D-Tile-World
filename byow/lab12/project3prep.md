# Project 3 Prep

**For tessellating hexagons, one of the hardest parts is figuring out where to place each hexagon/how to easily place hexagons on screen in an algorithmic way.
After looking at your own implementation, consider the implementation provided near the end of the lab.
How did your implementation differ from the given one? What lessons can be learned from it?**

Answer: My implementation did not use dummy hexagons like the staff solution did. Both implementations did take advantage of the fact that in each big hexagon,
the number of mini-hexagons per column stays consistent regardless of the side length of the mini-hexagon. I learned that it's important to use abstraction to
handle the math in calculating the width and height and position of the hexagons.

-----

**Can you think of an analogy between the process of tessellating hexagons and randomly generating a world using rooms and hallways?
What is the hexagon and what is the tesselation on the Project 3 side?**

Answer: I think tessellating hexagons is similar to randomly generating a world using rooms and hallways int that in both instances you need to put together smaller parts
to create a cohesive bigger picture. For the project, we'd need to randomly generate adjacent rooms and hallways so that there are no random empty spaces in between and 
that all the rooms are accessible via hallways, and that there are intersecting hallways.

-----
**If you were to start working on world generation, what kind of method would you think of writing first? 
Think back to the lab and the process used to eventually get to tessellating hexagons.**

Answer: I think we would need to first write a method on drawing a single room or a single hallway at a given position.

-----
**What distinguishes a hallway from a room? How are they similar?**

Answer: A hallways is different from a room in that it strictly has a width of 1 space, while it's length could vary.
Hallways and rooms are similar in that both are rectangular and that they intersect with each other(ie. hallways going into rooms, rooms adjacent to hallways).