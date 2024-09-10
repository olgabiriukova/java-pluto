import View.Editor;
import View.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    /**
     * Sets button parameters
     * @param name button name
     * @param x button x-coordinate
     * @param y button y-coordinate
     * @param width button width
     * @param height button height
     * @param urlImage URL of the button's image
     * @return configured JButton instance
     */
    private static JButton buttonSettings(String name, int x, int y, int width, int height, URL urlImage) {
        JButton button = new JButton(name);
        button.setBounds(x, y, width, height);
        button.setIcon(new ImageIcon(urlImage)); // Set the button's icon using the provided image URL

        return button; // Return the configured button
    }

    /**
     * Draws the menu and starts the game panel
     */
    public static void start() {
        JFrame frame = new JFrame("Pluto"); // Create the main application frame
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setBounds(200, 50, 961, 700); // Set the frame's position and size
        ClassLoader classLoader = Main.class.getClassLoader(); // Get the class loader to load resources
        JPanel buttonPanel = new JPanel();
        frame.add(buttonPanel);
        buttonPanel.setLayout(null); // Use absolute positioning for the button panel

        // Load and configure the "Start" button
        URL startURL = classLoader.getResource("menu/start_button.png");
        JButton buttonStart = buttonSettings("Start", 410, 400, 145, 43, startURL);

        // Load and configure the "Exit" button
        URL exitURL = classLoader.getResource("menu/exit_button.png");
        JButton exit = buttonSettings("Exit", 750, 20, 145, 43, exitURL);
        exit.addActionListener(e -> {
            System.exit(0); // Exit the application when the "Exit" button is clicked
        });

        // Add action listener for the "Start" button to switch to the game window
        buttonStart.addActionListener(e -> {
            LOGGER.info("open game frame");
            frame.setVisible(false); // Hide the main menu frame

            // Create and configure the game window
            JFrame window = new JFrame();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);
            window.setTitle("Pluto");
            window.add(exit); // Add the exit button to the game window

            GamePanel gamePanel = new GamePanel();
            gamePanel.setOpaque(false);
            window.add(gamePanel); // Add the game panel to the window
            window.pack();

            window.setLocationRelativeTo(null); // Center the game window on the screen
            window.setVisible(true); // Show the game window

            gamePanel.isMap = true; // Set the game panel's map state
            gamePanel.setup(); // Set up the game panel
            gamePanel.startGameThread(); // Start the game thread
        });

        // Load and configure the "Load" button
        URL loadURL = classLoader.getResource("menu/load_button.png");
        JButton load = buttonSettings("Load", 410, 500, 145, 43, loadURL);
        load.addActionListener(e -> {
            LOGGER.info("load game ...");
            frame.setVisible(false); // Hide the main menu frame

            // Create and configure the game window for loading a game
            JFrame window = new JFrame();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);
            window.setTitle("Pluto");

            GamePanel gamePanel = new GamePanel();
            gamePanel.setOpaque(false);
            window.add(exit); // Add the exit button to the game window
            window.add(gamePanel); // Add the game panel to the window
            window.pack();
            window.setLocationRelativeTo(null); // Center the game window on the screen
            window.setVisible(true); // Show the game window

            gamePanel.isMap = false; // Set the game panel's map state for loading
            gamePanel.setup(); // Set up the game panel
            gamePanel.startGameThread(); // Start the game thread
        });

        // Load and configure the "Edit" button
        URL editURL = classLoader.getResource("menu/edit_button.png");
        JButton edit = buttonSettings("Edit", 410, 600, 145, 43, editURL);
        edit.addActionListener(e -> {
            LOGGER.info("open edit frame");
            Editor editorPanel = new Editor(); // Open the editor panel
        });

        // Add buttons to the button panel
        buttonPanel.add(buttonStart);
        buttonPanel.add(load);
        buttonPanel.add(edit);
        buttonPanel.add(exit);

        // Set up the background image
        JLabel background = new JLabel();
        URL imageURL = classLoader.getResource("menu/menu.png");
        background.setIcon(new ImageIcon(imageURL));
        background.setBounds(-3, 0, 1000, 700); // Set the background's position and size
        buttonPanel.add(background); // Add the background to the button panel

        frame.setVisible(true); // Show the main menu frame
    }

    public static void main(String[] args) {
        boolean loggers = false;
        if (args.length > 0 && args[0].equals("--enable-loggers")) {
            loggers = true; // Enable loggers if the command line argument is provided
        }
        if (loggers) {
            System.setProperty("java.util.logging.ConsoleHandler.level", "FINE");
            LOGGER.setLevel(Level.FINE); // Set the logger level to FINE for detailed logging
        } else {
            System.setProperty("java.util.logging.ConsoleHandler.level", "OFF");
            LOGGER.setLevel(Level.OFF); // Disable logging
        }
        start(); // Start the application
    }
}