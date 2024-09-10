package Models;

import View.GamePanel;
import com.sun.tools.javac.Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;


/**
 * a class describing all the planets
 */
public abstract class Planet {
    private static final Logger LOGGER = Logger.getLogger( Main.class.getName() );;
    public BufferedImage image, left, right;
    public double maxHealth, currentHealth, weapon;
    public int x, y, speed, xSize,ySize;
    public String direction;
    public boolean collisionOn;
    GamePanel gp;
    public Rectangle solidArea = new Rectangle(0,0,48,48);

    public Planet(GamePanel gp)  {
        if(gp==null){
            LOGGER.severe("Game panel not found!");
        }
        this.gp=gp;

    }

    /**
     * read information about palnet
     */
    public void setValues()  {

    }

    /**
     * get image of planet
     */
    public void getImage(){

    }

    /**
     * draw planet
     */
    public void draw(Graphics2D g2){
    }

    /**
     * update position and action
     */
    public void update(){

    }

}
