package Tests;

import Control.KeyHandler;
import Models.Item;
import Models.Metal;
import Models.Pluto;
import View.AssetSetter;
import View.GamePanel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.event.KeyEvent;

import static org.junit.Assert.*;

class PlutoProcessTest {

    private GamePanel gamePanel;
    private Pluto pluto;
    private KeyHandler keyH;

    @BeforeEach
    void setUp() {
        keyH= new KeyHandler();
        gamePanel = new GamePanel();
        pluto = gamePanel.pluto;
        JFrame frame = new JFrame();
        frame.add(gamePanel);
        frame.pack();
        frame.setVisible(true);
        gamePanel.startGameThread();
        AssetSetter setter = new AssetSetter(gamePanel);
        setter.setEnemies();
        setter.setObjects();
        gamePanel.isMap = true;
    }

    @AfterEach
    void tearDown() {
        gamePanel.gameThread = null;
    }

    @Test
    void testPlutoGetItem_IsInstruction() {
        pluto = gamePanel.pluto;

        pluto.x = gamePanel.enemy[3].x;
        pluto.y = gamePanel.enemy[3].y;
        gamePanel.enemy[3].weapon = 0;
        gamePanel.enemy[3] = null;

        assertNotNull(gamePanel.obj[6]);

        pluto.takeItem(6);
        pluto.getWood = true;

        assertEquals(2 * gamePanel.tileSize,gamePanel.obj[6].x);
        assertEquals( gamePanel.screenHeight - 2 * gamePanel.tileSize, gamePanel.obj[6].y);
        assertTrue(gamePanel.isInstruction);
    }

    @Test
    void testPlutoCreateHummer_IsInstruction() {
        pluto.getWood = true;
        pluto.getMetal = true;
        pluto.getHummer = true;

        assertTrue(gamePanel.isInstruction);
    }




}

