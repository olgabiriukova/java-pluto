package Models;

import View.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * a class of hearts that add health
 */

public class HealthObj extends Item{

    public int number;
    BufferedImage health, halfHealth, emptyHealth;

    public HealthObj(GamePanel gp){
        super(gp);
        name = "Heart";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/heart_full.png"));
            health = ImageIO.read(getClass().getResourceAsStream("/objects/heart_full.png"));
            halfHealth = ImageIO.read(getClass().getResourceAsStream("/objects/heart_half.png"));
            emptyHealth = ImageIO.read(getClass().getResourceAsStream("/objects/heart_empty.png"));

        }catch(IOException e){
            e.printStackTrace();
        }

    }
    public void drawFullHealth(Graphics2D g2, int x, int y) {
        g2.drawImage(health, x, y, 30, 30, null);
    }
    public void drawHalfHealth(Graphics2D g2, int x,int y)
    {
        g2.drawImage(halfHealth, x, y, 30, 30, null);
    }
    public void drawEmptyHealth(Graphics2D g2, int x, int y){
        g2.drawImage(emptyHealth, x,y,30, 30, null);
    }



}
