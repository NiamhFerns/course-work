# README
In order to run this program you will need:
- Java 11
- The relevant source files
- The relevant textures
- Access to java via command prompt or terminal

Folder structure should be 

|-assignment-1/
    |- /textures
        |- All the game texture files provided by the downloaded file.
    |- GameEngine.java
    |- SnakeGame.java
    |- SnakePart.java
    |- Vector2D.java

As long as you extract the file and provided and don't move anything, you should be all good to continue.
First, `cd` into the project directory with your terminal or command prompt.
Then compile the project with `javac SnakeGame.java`
After this you should be good to run the game with `java SnakeGame`

# FEATURES
I have implemented all the required features from the assignment brief regarding movement, boundries, and snake length.

As for features I have added myself:
- A health bar with 4 hearts. You have 4 hp points and after losing the last one, the game will end. You will have the choice to restart. You can lose a life by biting yourself. 
- A current and high score counter. The current score will track the amount of apples eaten in your current game. The high score will track the highest number of apples you have eaten for that session.
- A random 'bomb' that will move around the game board. If the bomb is eaten, the player will lose a life and the current score will reset. The bomb will move around the battle field after being eaten or
  after a small amount of time of not being eaten.
- A menu system that allows the players to play a new match, quit, view the help screen, and view the end screen (only after dying). This was done through game states.
