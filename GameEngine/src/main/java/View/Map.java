package View;

import com.sun.tools.javac.Main;

import java.io.*;
import java.util.logging.Logger;


public class Map {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    public int screenCol = 50;
    public int screenRow = 35;
    public int[][] mapNum = new int[50][35];
    public int plutoX, plutoY, x0, y0, x1, y1, x2, y2, x3, y3, x, y;
    public String path = "src/main/game_map.txt";

    public Map() {
        loadMap(); // Load map data from a file
        setObjectsCoordinates(); // Set coordinates for specific objects based on the map data
    }

    /**
     * Load information about map positions from a file
     */
    public void loadMap() {
        try {
            String is = path; // Path to the map file
            BufferedReader br = new BufferedReader(new FileReader(is)); // Open the file for reading
            int col = 0;
            int row = 0;
            while (col < screenCol && row < screenRow) {
                String line = br.readLine(); // Read a line from the file
                while (col < screenCol) {
                    String[] numbers = line.split(" "); // Split the line into numbers
                    int num = Integer.parseInt(numbers[col]); // Convert the string to an integer
                    mapNum[col][row] = num; // Store the number in the map array
                    col++;
                }
                if (col == screenCol) {
                    col = 0;
                    row++;
                }
            }
            br.close(); // Close the file
        } catch (IOException e) {
            LOGGER.severe("File not found!"); // Log an error message if the file is not found
        }
    }

    /**
     * Set coordinates for specific objects based on their map positions
     */
    public void setObjectsCoordinates() {
        for (int col = 0; col < screenCol; col++) {
            for (int row = 0; row < screenRow; row++) {
                // Set the coordinates for Pluto
                if (mapNum[col][row] == 1) {
                    plutoX = col * 20;
                    plutoY = row * 20;
                }
                // Set the coordinates for object 0
                if (mapNum[col][row] == 2) {
                    x0 = col * 20;
                    y0 = row * 20;
                }
                // Set the coordinates for object 1
                if (mapNum[col][row] == 3) {
                    x1 = col * 20;
                    y1 = row * 20;
                }
                // Set the coordinates for object 2
                if (mapNum[col][row] == 4) {
                    x2 = col * 20;
                    y2 = row * 20;
                }
                // Set the coordinates for object 3
                if (mapNum[col][row] == 5) {
                    x3 = col * 20;
                    y3 = row * 20;
                }
            }
        }
    }

    /**
     * Set the position of items on the map based on their ID
     * @param id the ID of the item to be placed on the map
     */
    public void setItemsOnMap(int id) {
        for (int col = 0; col < screenCol; col++) {
            for (int row = 0; row < screenRow; row++) {
                if (mapNum[col][row] == id) {
                    x = col * 20;
                    y = row * 20;
                }
            }
        }
    }
}