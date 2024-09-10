package Tests;

import Control.KeyHandler;
import Models.*;
import View.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CollisionTest {
    private Pluto pluto;
    private Enemy[] enemies;
    private GamePanel gp;
    private KeyHandler keyH;
    private Collision collision;
    private Item[] items;

    @BeforeEach
    void setUp() {
        gp = mock(GamePanel.class);
        keyH = mock(KeyHandler.class);
        collision = new Collision(gp);
        pluto = new Pluto(gp, keyH);
        pluto.x = 70;
        pluto.y = 80;
        pluto.speed = 10;
        pluto.solidArea = new Rectangle(0, 0, 50, 50);
        pluto.collisionOn = false;
        enemies = new Enemy[2];
        enemies[0] = Mockito.mock(Enemy.class);
        enemies[1] = Mockito.mock(Enemy.class);
        enemies[0].x = 90;
        enemies[0].y = 80;
        enemies[0].solidArea = new Rectangle(0, 0, 50, 50);
        enemies[1].x = 200;
        enemies[1].y = 200;
        enemies[1].solidArea = new Rectangle(0, 0, 50, 50);
        items = new Item[2];
        items[0] = Mockito.mock(Item.class);
        items[1] = Mockito.mock(Item.class);

        items[0].x = 90;
        items[0].y = 80;
        items[0].solidArea = new Rectangle(0, 0, 50, 50);

        items[1].x = 200;
        items[1].y = 200;
        items[1].solidArea = new Rectangle(0, 0, 50, 50);

       gp.obj = items;
    }

    @Test
    void checkPlanetsTest_IsPlanet(){
        int result = collision.checkPlanets(pluto, enemies);
        assertEquals(0, result);
        assertTrue(pluto.collisionOn);
    }

    @Test
    void checkPlanetsTest_NotPlanet(){
        pluto.x = 0;
        pluto.y = 0;
        int result = collision.checkPlanets(pluto, enemies);
        assertEquals(999, result);
        assertFalse(pluto.collisionOn);
    }


    @Test
    void testObjectCollision_IsObject(){
        pluto.x = 70;
        pluto.y = 70;
        int result = collision.checkObject(pluto, true);
        assertEquals(0, result);
        assertTrue(pluto.collisionOn);
    }

    @Test
    void testObjectCollision_NoObject(){
        pluto.x = 0;
        pluto.y = 0;
        int result = collision.checkObject(pluto, true);
        assertEquals(999, result);
        assertFalse(pluto.collisionOn);
    }
}
