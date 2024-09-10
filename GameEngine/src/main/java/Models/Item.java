package Models;

import View.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Item {
    public int x, y, xSize, ySize;
    String name;
    BufferedImage image;
    boolean collision;
    public Rectangle solidArea = new Rectangle(0,0,48,48);

    public Item(GamePanel p){}

    /**
     * draw item
     */
    public void draw(Graphics2D g2){
        g2.drawImage(image, x, y,  xSize, ySize, null);

    }


}
