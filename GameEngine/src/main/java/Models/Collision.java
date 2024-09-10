package Models;

import View.GamePanel;

import java.awt.*;

/**
 * Handles collision detection between objects and enemies.
 */
public class Collision {
    GamePanel gp;

    public Collision(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Check collision with objects or items.
     *
     * @param planet The player or planet object.
     * @param pluto  Boolean value indicating if the player is Pluto.
     * @return Index of the collided object.
     */
    public int checkObject(Planet planet, boolean pluto) {
        int index = 999; // Initialize index to indicate no collision

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                // Create rectangles representing the areas occupied by the planet and the object
                Rectangle planetArea = new Rectangle(planet.x + planet.solidArea.x, planet.y + planet.solidArea.y,
                        planet.solidArea.width, planet.solidArea.height);
                Rectangle objArea = new Rectangle(gp.obj[i].x + gp.obj[i].solidArea.x, gp.obj[i].y + gp.obj[i].solidArea.y,
                        gp.obj[i].solidArea.width, gp.obj[i].solidArea.height);

                // Adjust planetArea based on its direction of movement
                switch (planet.direction) {
                    case "up":
                        planetArea.y -= planet.speed;
                        break;
                    case "down":
                        planetArea.y += planet.speed;
                        break;
                    case "right":
                        planetArea.x -= planet.speed;
                        break;
                    case "left":
                        planetArea.x += planet.speed;
                        break;
                }

                // Check for intersection between planetArea and objArea
                if (planetArea.intersects(objArea)) {
                    if (gp.obj[i].collision) {
                        planet.collisionOn = true; // Activate collision flag for the planet
                    }
                    if (pluto) {
                        index = i; // Set index to the collided object's index
                    }
                }
            }
        }
        return index;
    }

    /**
     * Check collision with enemies.
     *
     * @param planet The player or planet object.
     * @param target Array containing the enemy objects.
     * @return Index of the collided enemy.
     */
    public int checkPlanets(Planet planet, Enemy[] target) {
        int index = 999; // Initialize index to indicate no collision

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                // Create rectangles representing the areas occupied by the planet and the enemy
                planet.solidArea.x = planet.x + planet.solidArea.x;
                planet.solidArea.y = planet.y + planet.solidArea.y;
                target[i].solidArea.x = target[i].x + target[i].solidArea.x;
                target[i].solidArea.y = target[i].y + target[i].solidArea.y;

                // Adjust planetArea based on its direction of movement
                switch (planet.direction) {
                    case "up":
                        planet.solidArea.y -= planet.speed;
                        break;
                    case "down":
                        planet.solidArea.y += planet.speed;
                        break;
                    case "right":
                        planet.solidArea.x -= planet.speed;
                        break;
                    case "left":
                        planet.solidArea.x += planet.speed;
                        break;
                }

                // Check for intersection between planet's solid area and enemy's solid area
                if (planet.solidArea.intersects(target[i].solidArea)) {
                    if (target[i] != planet) { // Ensure the collision is not with itself
                        planet.collisionOn = true; // Activate collision flag for the planet
                        index = i; // Set index to the collided enemy's index
                    }
                }

                // Reset positions of solid areas for next iteration
                planet.solidArea.x = 0;
                planet.solidArea.y = 0;
                target[i].solidArea.x = 0;
                target[i].solidArea.y = 0;
            }
        }
        return index;
    }
}
