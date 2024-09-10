# This project is an engine for an RPG game.

# User Manual

1. START OF THE GAME

At the beginning you will see a menu with four buttons "start", "load", "edit", "exit". After pressing "start", a new game will start. After pressing "download", the previous game will be loaded. To change the game map, click "edit". To close the game, click "exit".

![pluto_menu](uploads/1b019e74b561d996b938898c910b90b8/pluto_menu.png)

2. CONTROL

After the start of the game, a field with the player and enemies will appear. In the upper left corner there will be a health indicator.

The player is controlled using the keyboard:

- "keyboard arrows" - go forward, back, right, left.
- "space" - to shoot, to crash
- "E" - to take the item
- "C" - to create a hummer

To open the door, take the key and go to the door.

Use the "exit" to close the game. After exiting the game is saved automatically.

![pluto_game_demo](uploads/6a8dcf2f599003c51e7d3f252b3c7d48/pluto_game_demo.png)

3. RULES

The plot of the game will consist in the fact that the player("pluto") will fight with enemies("planets"). When the player wins over the enemy, he gets either additional health or an item. To go to the next level, you need to open the door("black hole") with the help of a magic wand, which is hidden in the satellite. To open the satellite will need a hammer, which the player can make himself from the items that he received when winning on enemies. Along with the items, an instruction will appear.

4. EDITOR

When you select the editor, a text file will open that will be a numeric map consisting of numbers from 0 to 11. Each number indicates an object on the playing field: 0 - an empty field, 1 - Pluto, 2 - Mercury, 3 - Mars, 4 - Neptune, 5 - Saturn, 6 - the key, 7 - satellite, 8 - door, 9 and 10 - hearts that add health, 11 - hammer. Also you can change planet parameters.

To exit the editor mode, click on the cross, the changes will be saved automatically.

## [UML Diagram](https://gitlab.fel.cvut.cz/B232_B0B36PJV/biriuolg/-/blob/main/uml-diagram.pdf?ref_type=heads)

## [Program Documentation](https://gitlab.fel.cvut.cz/B232_B0B36PJV/biriuolg/-/blob/main/program_documentation.pdf?ref_type=heads)
