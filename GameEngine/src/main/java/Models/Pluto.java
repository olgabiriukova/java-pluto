package Models;

import Control.KeyHandler;
import View.GamePanel;
import View.Map;
import com.sun.tools.javac.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;


public class Pluto extends Planet {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private BufferedImage up, down;
    KeyHandler keyH;
    public boolean getKey, getHummer, getWood, getMetal;
    Map map = new Map(); // Instance of the Map class for retrieving Pluto's initial position
    PlanetModel model = new PlanetModel(); // Instance of the PlanetModel class for managing game data
    public boolean isHit;

    public Pluto(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        getImage(); // Load Pluto's images
        setValues(); // Set initial values for Pluto
    }

    /**
     * Set default values for Pluto.
     */
    @Override
    public void setValues() {
        speed = 4;
        direction = "down";
        xSize = 70;
        ySize = 70;
        if (gp.isMap) {
            // If the map is active, set initial values from the map data
            maxHealth = model.getMaxHealth();
            x = map.plutoX;
            y = map.plutoY;
            currentHealth = maxHealth;
            weapon = model.getWeapon();
            getKey = false;
            getHummer = false;
            getWood = false;
            getMetal = false;

        } else {
            // Otherwise, load initial values from the PlanetModel
            x = model.getX();
            y = model.getY();
            currentHealth = model.getHealth();
            getKey = model.keyInfo();
            getHummer = model.hummerInfo();
            getMetal = model.metalInfo();
            getWood = model.woodInfo();
        }
    }

    /**
     * Load images of Pluto.
     */
    public void getImage() {
        try {
            // Load different images based on Pluto's direction
            up = ImageIO.read((getClass().getResourceAsStream("/enemy/pluto_up.png")));
            down = ImageIO.read((getClass().getResourceAsStream("/enemy/pluto_down.png")));
            right = ImageIO.read((getClass().getResourceAsStream("/enemy/rightPluto.png")));
            left = ImageIO.read((getClass().getResourceAsStream("/enemy/plutoleft.png")));

        } catch (IOException e) {
            LOGGER.severe("Image not found!"); // Log error if images are not found
        }
    }

    /**
     * Update Pluto's position and actions.
     */
    public void update() {
        // Move Pluto based on key inputs
        if (keyH.upPressed && y - speed > 0) {
            direction = "up";
            y -= speed;
        } else if (keyH.downPressed && y + speed < gp.screenHeight - gp.tileSize * 3) {
            direction = "down";
            y += speed;
        } else if (keyH.leftPressed && x - speed > 0) {
            direction = "left";
            x -= speed;
        } else if (keyH.rightPressed && x + speed != gp.screenWidth - gp.tileSize * 3) {
            direction = "right";
            x += speed;
        } else if (keyH.spacePressed) {
            isHit = true;
        } else {
            isHit = false;
        }
        if (keyH.cPressed) {
            // If 'C' is pressed, set getHummer to true and display instructions
            getHummer = true;
            gp.pause = true;
            gp.instruction = "Smash the satellite and get the magic wand.";
        }

        // Check collisions with objects and planets
        int objIndex = gp.collision.checkObject(this, true);
        takeItem(objIndex);
        int planetIndex = gp.collision.checkPlanets(this, gp.enemy);
        contactPlanets(planetIndex);

        // Write updated information to the JSON file
        model.writeInfo(x, y, currentHealth, gp.enemy, gp.obj, getKey, getHummer, getWood, getMetal);
    }

    /**
     * Draw Pluto on the game panel.
     */
    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        // Select image based on Pluto's direction
        switch (direction) {
            case "up":
                image = up;
                break;
            case "down":
                image = down;
                break;
            case "right":
                image = right;
                break;
            case "left":
                image = left;
                break;
        }
        // Draw Pluto's image
        g2.drawImage(image, x, y, xSize, ySize, null);
    }

    /**
     * Take items and perform actions based on the object index.
     * @param i object index
     */
    public void takeItem(int i) {
        if (i != 999) {
            String objectName = gp.obj[i].name;
            switch (objectName) {
                case "Wood":
                    // If Pluto is near a wood object and 'E' is pressed, set getWood to true and display instructions
                    if (gp.enemy[3] == null && keyH.ePressed) {
                        getWood = true;
                        if (!getMetal) {
                            gp.instruction = "<html>To make a hammer, collect all the materials <br>and press C</html>";
                            gp.pause = true;
                        }
                    }
                    break;
                case "Metal":
                    // If Pluto is near a metal object and 'E' is pressed, set getMetal to true and display instructions
                    if (gp.enemy[2] == null && keyH.ePressed) {
                        getMetal = true;
                        if (!getWood) {
                            gp.instruction = "<html>To make a hammer, collect all the materials <br>and press C</html>";
                            gp.pause = true;
                        }
                    }
                    break;
                case "Satellite":
                    // If Pluto has the hammer and 'Space' is pressed, destroy the satellite
                    if (getHummer && keyH.spacePressed) {
                        gp.obj[i] = null;
                    }
                    break;
                case "Key":
                    // If 'E' is pressed, pick up the key and display instructions
                    if (keyH.ePressed) {
                        gp.instruction = "Now you can open the door.";
                        gp.pause = true;
                        getKey = true;
                    }
                    break;
                case "Door":
                    // If Pluto has the key, set isWin to true
                    if (getKey) {
                        gp.isWin = true;
                        break;
                    }
                    break;
                case "Heart":
                    // If Pluto's health is not max, increase health by 1 and remove the heart object
                    if (currentHealth != maxHealth) {
                        currentHealth += 1;
                    }
                    gp.obj[i] = null;
                    break;
            }
        }
    }

    /**
     * Reduce Pluto's health on contact with an enemy.
     * @param i enemy index
     */
    public void contactPlanets(int i) {
        if (i != 999) {
            // Decrease Pluto's health by the enemy's weapon
            currentHealth -= gp.enemy[i].weapon;
            if (isHit) {
                // If Pluto is hitting the enemy, decrease the enemy's health
                gp.enemy[i].currentHealth -= weapon;
                if (gp.enemy[i].currentHealth <= 0) {
                    gp.enemy[i] = null;
                }
            }
        }


    }


}

