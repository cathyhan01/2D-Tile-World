# Build Your Own World Design Document

**Partner 1: Cathy Han**

**Partner 2: Annabelle Raven**

## Classes and Data Structures

### Engine (Class):

Instance Variables:

    - private static final int WIDTH = 80
        - the width of the playing window
    - private static final int HEIGHT = 30
        - the height of the playing window
    - private static final int KEYBOARD = 1
        - the option value for interacting with keyboard
    - private static final int STRING = 0
        - the option value for interacting with keyboard
    - (UIElements) uiHandler
        - the object for handling drawing all the UI elements in the game
    - (int) inputOption
        - the numerical input option
    - (long) seed
        - the inputted seed by the user
    - (Input) inputHandler
        - the object for handling whatever input the user chose
    - (boolean) enteredMainMenu
        - whether the user has selected a main menu option (N or L)
    - (boolean) finishedSeed
        - whether the user has finished entering the seed number
    - (boolean) saveAndQuit
        - whether the game status is saving and quitting
        - user entered 'Q'/'q' or ':Q'/':q'
    - (WorldGenerator) wg
        - the object for generating our tile world
    - (TETile[][]) world
        - the tile world we're playing in
        - also reflects the current game state
    - (TERenderer) TER
        - the tile renderer object
    - (String) gamePlayString
        - the cached string of moves the player has entered within the game so far
        - contains WASD key moves
    - (boolean) isEnteringName
        - whether the user is currently entering a name for the avatar
    - (String) playerName
        - the player's name
    - (boolean) isEnteringTheme
        - whether the user is currently picking a theme for the world
    - (String) theme
        - the theme of the world(picked or default)

### Input (Interface):

    No instance variables. Models what an input object should be able to do.

### KeyboardInput (Class - Implements Input):

Instance Variables:

    - (char) prevChar
        - the previous inputted keyboard character
    - (char) currChar
        - the current (most recent) inputted keyboard character
    - (UIElements) ui
        - the UI elements handler object to draw the UI
    - (TETile[][]) world
        - the tile world we're playing in
    - (boolean) isPlaying
        - whether the user is playing the game (past the main menu stage)

### StringInput (Class - Implements Input):

Instance Variables:

    - (String) userInput
        - the user input as a string
    - (char) prevChar
        - the previous user input character
    - (int) index
        - the next index to process in the input string

### Position (Class):

Instance Variables:

    (int) x
    (int) y


### GenericRoom (Class): 
   
Instance Variables:

    (int) width
    (int) height
    (TETile) wallTileType
    (TETile) floorTileType
    (Position) lowerLeftPos
    
    
### Room (Class - Extends GenericRoom)
   
Instance Variables: 

    Inherited from GenericRoom

    
### WorldGenerator (Class):

Instance variables:

    - private static final int MAX_ROOM_WIDTH
        - the max width of any given room
    - private static final int MAX_ROOM_HEIGHT
        - the max height of any given room
    - private static final int MAX_NUM_ROOMS
        - the max number of rooms in any world
    - private static final int MIN_NUM_ROOMS
        - the min number of rooms in any world
    - private static final int NUM_MINES
        - the total number of mines in the beginning of the game
    - private static final int NUM_GOLD
        - the total number of gold coins + egg in the beginning of the game
    - (long) seed
        - stores the user-entered seed # to be used to pseudo-randomly generate rooms
    - (int) width
        - the width of the world
    - (int) height
        - the height of the world
    - (Random) randSeed
        - stores the pseudo-random number generator with the given seed
        - Random randSeed = new Random(seed);
    - (int) numRooms
        - number of rooms in the world
    - (List<Room>) rooms
        - list of rooms in the world
    - (List<Position>) walkableTiles
        - list of the positions of all walkable tiles in the world
    - (List<Position>) floorTiles
        - list of the positions of all floor tiles in the world
        - going to be used for the game component of the world
    - (List<Position>) mineTiles
        - list of the positions of all mine tiles in the world
    - (List<Position>) goldTiles
        - list of the positions of all gpld tiles in the world
    - Position avatarPos
        - the current position of the avatar
    - (TETile[][]) world
        - the 2D tile world generated
    - (UIElements) uiE
        - the object for handling drawing all UI elements in the game
    - (Tileset) ts
        - the object with the set of tiles used in the world
    - (String) theme
        - the theme of the world
    - (TETile) nothing, wall, floor, mine, coin, egg, avatar
        - the appropriate tiles for elements in the game
    
       
### UIElements (Class)

Instance Variables: 

    - (int) score
        - int that keeps track of players current score
    - (int) minesLeft
        - int that shows the player how many mines are left on board
    - (int) livesLeft
        - int that shows the player how many lives they have left
    - (String) name
        - the avatar's name (default is "Anonymous Player")
    - (String) theme
        - the theme of the world
    - (int) width
        - int value represents width of world
    - (int) height
        - int value rrepresents height of world
    - (int) prevX
        - the mouse's previous x-position
    - (int) prevY
        - the mouse's previous y-position
    - (boolean) userMoved
        - whether the user has moved their mouse
    - (boolean) usingKeyboard
        - whether the user is interacting with keyboard or not


## Algorithms

### Engine (Class):

Methods:

    - createHandler(String str)
        - creates the appropriate input handler depending on the input option
    - handleChars()
        - processes the input characters and determines what behavior the game should have
    - drawWorld()
        - draws the current game world state
        - renders the frame and then draws the current game stats
    - saveGame()
        - saves the current game state in a text file
        - saves the seed, gameplay moves, avatar name, game theme
    - loadGame()
        - loads the last saved game state from a text file
        - reads the seed, gameply moves, avatar name, game theme
        - creates a world based on the read information
        - draws the created world from that specified state

### Input(Interface), KeyboardInput(Class), StringInput(Class):

Methods:

    - char getNextChar()
        - returns the next input character
    - char getPrevChar()
        - returns the previous input character
    - setPlaying()
        - sets the current playing state to true (past the main menu stage)
        - only applicable for KeyboardInput
    - setWorld(TETile[][] w)
        - sets the tile world to the given world
        - only applicable for KeyboardInput
    - boolean hasNextInput()
        - checks whether there is remaining input to be processed


### Position (Class):

Methods:

    getX()
    getY()
    getDeltaX(int delta)
    getDeltaY(int delta)


### GenericRoom (Class): 
   
Methods:

    getWidth()
    getHeight()
    getWallTileType()
    getFloorTileType()
    getLowerLeftPos()
    getUpperRightPos()
    
    
### Room (Class - Extends GenericRoom)
   
Methods:

    Inherited from GenericRoom
    
### WorldGenerator (Class):

Methods:
    
    - setTiles()
        - sets the appropriate tiles for all elements used in the world
        - is based on the theme selected
    - int generateRandomNum()
        - pseudo-randomly generates a random number using randSeed
        - Use this for setting numRooms and the dimensions and location when creating a Room object
    - int generateNumRooms()
        - pseudo-randomly generates the number of rooms in the world with a lower bound of 5
        and an upper bound of 23
    - setAllNothing()
        - set all the tiles to the appropriate nothing tiles by default to avoid null values
    - TETile[][] createWorld(boolean isLoading)
        - Creates the 2D World by populating the list of rooms in the world and then draws those rooms
        - Connects the rooms by drawing hallways between the rooms
        - places the necessary game elements (mines, gold)
        - places the avatar at a pseudo-random position in the world
        - returns the generated world
    - (TETile[][]) getWorld()
        - returns the current world state
    - getTiles(List<Position> lst)
        - private helper method for populating a certain list of tiles in the world
        - walkable tiles include floor tiles(which can later become mine or gold tiles)
    - placeAvatar(int i, boolean isLoading)
        - Places the avatar at a random position in the world
        - draws the avatar's position tile to update it
    - moveAvatar(char c, boolean isLoading)
        - Determines what new position to move the avatar to depending on the given input character
        - calls placeAvatar() to actually update the tile and draw the tile
    - placeMines()
        - Places 5 mines at random positions in the world
    - placeGold()
        - Places 11 gold(10 coins, 1 egg) at random positions in the world
    - drawHallways()
        - connects rooms by drawing hallways between the rooms
    - drawHorizontalHall(int x1, int x2, int y)
        - draws a horizontal hallway at given position coordinates
    - drawVerticalHall(int y1, int y2, int x)
        - draws a vertical hallway at given position coordinates
    - drawSingleWall(int x, int y)
        - draws a single wall tile at the given position coordinates
    - Room createRandomRoom()
        - Creates a Room object with random dimensions at a random location
    - drawAllRooms()
        - loops through rooms and calls drawSingleRoom for each
    - drawSingleRoom(Room rm)
        - draws the given Room by placing the appropriate TETiles into world
    - boolean overlaps(Room room)
        - Determines if the given room overlaps with any of the existing Rooms in the world
        - Overlap is defined as having a border coordinate that is either equal to the border 
        coordinate of another room OR having a corner inside that room. Sharing a wall is
        considered overlapping
        - Thick walls(ie. double walls) are not considered overlap

### UIElements (Class) 

Methods:

    - boolean getUsingKeyboard()
        - whether the user is interacting with keyboard or not
    - setUsingKeyboard(boolean b)
        - set whether the user is using keyboard to the given boolean
    - setUserMoved(boolean b)
        - set whether the user has moved to the given boolean
    - useMine(boolean isLoading)
        - decrements the number of mines left and the number of user lives left
        - draws a new interface as a reaction to the user interacting with the mine
            - ambition point (60)
    - drawGameStats()
        - puts the variables on the screen (mines, score, lives) for player to see
        - once the user runs out of lives, a game over screen appears and then quits the game
    - incrementScore(boolean isEgg, boolean isLoading)
        - adds appropriate value to players score 
        - draws a new interface as a reaction to the user interacting with the gold coin/egg
            - ambition point (60)
    - drawHover(TETile[][] world, boolean enable)
        - draws tile description/type when user hovers their mouse over tile
    - drawMainMenu()
        - draws main menu screen and gives options to play game/customize game
    - drawSeedInstructions()
        - draws the seed entering instructions for the user
    - drawNameInstructions()
        - draws the name entering instructions for the user
        - ambition point (20)
    - drawThemeInstructions()
        - draws the theme picking instructions for the user
        - ambition point (20)
    - setName(String str)
        - sets the player's name to the given string
    - String getName()
        - returns the player's name
    - setTheme(String str)
         - sets the game's theme to the given string
    - String getTheme()
        - returns the game's theme


## Persistence

### Saving the Game

In order to save the game, necessary information to the uniqueness of the game will be saved to a text file.
The information written to the file include: game seed, gameplay moves(WASD keys), player name, game theme.
A java FileWriter object is used to write to the text file MyFile.txt, and it does not append to the file,
so information is written to a fresh, blank file each time. All of the written information is separated by lines,
one piece of information per line. This is done for the sake of reading the information later on in game loading.

### Loading the Game

In order to load the game, the necessary information is read from a text file. A java BufferedReader object is used
to read the information line by line. Since the order of the identity of the information(whether it is the seed,
the name, etc.) does not change, we can use this feature to our advantage and deterministically grab the necessary
information and store it in our variables. After reading and storing the information, we then create a WorldGenerator
object to create our world, and then iterate through our gameplay string(WASD keys) to move our avatar to the last
entered keystroke. Finally, we set the relevant variables in our UI handler object, and then create and draw our world,
rendering the frame. This brings the user to the state of the game they left off at when they quit and saved.