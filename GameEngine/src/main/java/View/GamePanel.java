package View;

import Control.KeyHandler;
import Models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Draws the game
 */
public class GamePanel extends JPanel implements Runnable {
    public boolean isWin;
    public boolean isEnd;
    public static final int tileSize = 36;
    public static final int screenWidth = 1000;
    public static final int screenHeight = 700;
    public Thread gameThread;
    private static final int FPS = 60; // Frames per second for the game loop
    KeyHandler keyH = new KeyHandler();
    public Field field = new Field(this);
    public Pluto pluto = new Pluto(this, keyH);
    Weapon weapon = new Weapon(this);
    public Map map = new Map();
    public Enemy[] enemy = new Enemy[6];
    public Item[] obj = new Item[8];
    public Collision collision = new Collision(this);
    public HealthObj health = new HealthObj(this);
    public boolean isMap, isInstruction;
    public PlanetModel model = new PlanetModel();
    public boolean pause = false;
    public String instruction;

    public AssetSetter setter = new AssetSetter(this);
    private JLabel clockLabel;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Set the preferred size of the panel
        this.setBackground(Color.black); // Set the background color to black
        this.setDoubleBuffered(true); // Enable double buffering for smoother graphics
        this.addKeyListener(keyH); // Add key listener for handling keyboard input
        this.setFocusable(true); // Make the panel focusable to receive key events
    }

    /**
     * Starts the game thread
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // Start the game thread, which will call the run method
    }

    /**
     * Sets up objects and enemies
     */
    public void setup() {
        setter.setEnemies(); // Set initial enemy positions
        setter.setObjects(); // Set initial object positions
        pluto.setValues(); // Set initial values for the player character (Pluto)

        if (!isMap) { // If not in map mode, load saved game state
            for (int i = 0; i < 4; i++) {
                enemy[i].currentHealth = model.getPlanetHealth(i);
                if (enemy[i].currentHealth == 0) {
                    enemy[i] = null; // Remove enemies with zero health
                }
            }
            for (int i = 0; i < 6; i++) {
                if (!model.getObjectsInfo(i)) {
                    obj[i] = null; // Remove objects that are not present in the saved state
                }
            }
        } else { // If in map mode, set maximum health and weapons for enemies
            for (int i = 0; i < 4; i++) {
                enemy[i].currentHealth = model.getMaxPlanetHealth(i);
                enemy[i].weapon = model.getPlanetWeapon(i);
                if (enemy[i].currentHealth == 0) {
                    enemy[i] = null;
                }
            }
        }
    }

    /**
     * Sets the game speed and runs the game loop
     */
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // Time interval for each frame
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update(); // Update game state
                repaint(); // Repaint the game panel
                delta--;
            }

            if (timer >= 1000000000) {
                timer = 0;
            }
        }
    }

    /**
     * Updates the game panel
     */
    public void update() {
        pluto.update(); // Update player character
        for (int i = 0; i < enemy.length; i++) {
            if (enemy[i] != null) {
                enemy[i].update(); // Update each enemy
            }
        }
    }

    /**
     * Displays instructions in a popup window
     *
     * @param instruction the instruction text to be displayed
     */
    private void showInstructions(String instruction) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setLayout(new BorderLayout());
            frame.setResizable(false);
            frame.setBounds(600, 300, 400, 200);
            frame.setUndecorated(true);

            JLabel label = new JLabel(instruction, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            label.setOpaque(true);
            label.setBackground(Color.decode("#9400D3"));
            label.setForeground(Color.white);
            frame.add(label, BorderLayout.CENTER);
            frame.setVisible(true);

            new Timer(3000, e -> frame.setVisible(false)).start(); // Hide the window after 3 seconds
        });
    }

    /**
     * Checks if an object should be drawn
     *
     * @param index the index of the object
     * @return true if the object should be drawn, false otherwise
     */
    private boolean isObject(int index) {
        switch (index) {
            case 6:
                return enemy[3] == null || pluto.getWood;
            case 7:
                return enemy[2] == null || pluto.getMetal;
            case 0:
                return obj[1] == null;
            case 3:
                return enemy[1] == null;
            case 4:
                return enemy[0] == null;
            default:
                return index == 1 || index == 2;
        }
    }

    /**
     * Draws the player's health bar
     *
     * @param g2 the graphics context
     */
    private void drawHealth(Graphics2D g2) {
        int roundHealth = (int) Math.ceil(pluto.currentHealth);
        int x = 0;
        for (int i = 0; i < 3; i++) {
            if (roundHealth > 1) {
                health.drawFullHealth(g2, x, 0);
            } else if (roundHealth == 1) {
                health.drawHalfHealth(g2, x, 0);
            } else {
                health.drawEmptyHealth(g2, x, 0);
            }
            roundHealth -= 2;
            x += 24;
        }
    }

    /**
     * Draws game objects
     *
     * @param g the graphics context
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        field.drawField(g2); // Draw the game field
        ((Graphics2D) g).setStroke(new BasicStroke(3));
        g.setColor(Color.blue);
        g.drawRect(0, screenHeight - 2 * tileSize, 7 * tileSize, 2 * tileSize);

        int j = 0;
        for (Enemy e : enemy) { // Draw enemies
            if (e != null) {
                e.setEnemyDirection(j);
                e.draw(g2);
            }
            j++;
        }
        for (int i = 0; i < obj.length; i++) { // Draw objects
            if (obj[i] != null && isObject(i)) {
                obj[i].draw(g2);
            }
        }

        if (pause) { // Handle pause state
            try {
                gameThread.sleep(50);
                showInstructions(instruction);
                gameThread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pause = false;
        }

        if (pluto.getMetal) {
            if (obj[7] != null) {
                obj[7].x = 0;
                obj[7].y = screenHeight - 2 * tileSize;
            }
        }
        if (pluto.getWood) {
            if (obj[6] != null) {
                obj[6].x = 2 * tileSize;
                obj[6].y = screenHeight - 2 * tileSize;
            }
        }
        if (pluto.getMetal || pluto.getWood) {
            isInstruction = true;
        }
        if (pluto.getHummer && obj[5] != null) {
            obj[7] = null;
            obj[6] = null;
            pluto.getMetal = false;
            pluto.getWood = false;
            obj[5].draw(g2);
        }
        if (pluto.getKey) {
            if (obj[0] != null) {
                obj[0].x = 3 * tileSize;
                obj[0].y = screenHeight - 2 * tileSize;
            }
        }

            if (!isWin) {
                pluto.draw(g2); // Draw the player character if the game is not won
            }

            weapon.x = pluto.x;
            weapon.y = pluto.y;

            if (pluto.isHit) {
                weapon.draw(g2); // Draw the weapon if the player is hitting
            }
            drawHealth(g2); // Draw the health bar

            if (pluto.currentHealth < 0) {
                isEnd = true;
            }

            if (isWin) {
                field.drawWin(g2); // Draw win screen if the game is won
            }
            if (pluto.currentHealth <= 0) {
                field.drawEnd(g2); // Draw end screen if the player is dead
            }
            g2.dispose();
        }
    }



