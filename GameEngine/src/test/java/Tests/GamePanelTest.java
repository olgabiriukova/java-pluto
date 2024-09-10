package Tests;

import View.GamePanel;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class GamePanelTest {
    @Test
    void testSetup() {
        GamePanel gamePanel = new GamePanel();
        gamePanel.setup();
        assertFalse(gamePanel.pause);
        assertEquals(36, gamePanel.tileSize);
        assertEquals(1000, gamePanel.screenWidth);
        assertEquals(700, gamePanel.screenHeight);
        assertNotNull(gamePanel.field);
        assertNotNull(gamePanel.pluto);
        assertNotNull(gamePanel.map);
        assertNotNull(gamePanel.enemy);
        assertNotNull(gamePanel.obj);
        assertNotNull(gamePanel.collision);
        assertNotNull(gamePanel.health);
        assertNotNull(gamePanel.model);
        assertNotNull(gamePanel.setter);
    }
}
