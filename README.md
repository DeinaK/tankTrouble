# Tank Trouble
A 2 player tank game implemented in Java, inspired by the game with the same name.

### Installing
Clone and download the files to some directory. Open up a terminal and browse to the path where the project files are located.
Compile the java source code:
```
java TankTrouble.java
```
Once the source files are compiled, you can run the game:
```
javac TankTrouble
```
### Controls

Player 1 moves using the arrow keys and fires using the 'M' key.
Player 2 moves using keys 'E', 'S', 'D' and 'F' and fires using the key 'Q'.

### Screenshots
![alt text](https://github.com/foreignOwl/tankTrouble/tree/master/src/tankTroubleMenu.png "Menu")
![alt text](https://github.com/foreignOwl/tankTrouble/tree/master/src/tankTroubleGame.png "Game")

### To-Do
* Improve the documentation/javadoc
* Bug-fixes

### A note on running the game on MacOSX
The game requires users to hold down keys to move around the map. On MacOS, character menu pops up if you hold down a key instead of key repeat. You can fix this by opening up a terminal and running the command:
```
defaults write -g ApplePressAndHoldEnabled -bool false
```
This command will allow you to repeat keys instead of popping up the character menu. If you want to get the character menu back you can run the following command in terminal:
```
defaults write -g ApplePressAndHoldEnabled -bool true
```

### Bugs
* Due to some problem occurs in threading, on some computers the game runs slow and delays occur in updating/rendering the game.
* Some 

## Authors

* Arda Basaran - [foreignOwl](https://github.com/foreignOwl)
* M. Mirac Suzgun
* Mehmet Tuna Uysal
