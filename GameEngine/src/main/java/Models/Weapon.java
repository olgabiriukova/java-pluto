package Models;

import View.GamePanel;
import com.sun.tools.javac.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;

public class Weapon{
    private static final Logger LOGGER = Logger.getLogger( Main.class.getName() );;
    public int damage;
    BufferedImage image;
    GamePanel gp;
    public int x, y, xSize, ySize;


    public Weapon(GamePanel gp){
        this.gp =gp;
        setValues();
        getImage();

    }

    public void setValues(){
        damage = 3;
        x = 100;
        y = 100;
        xSize = 50;
        ySize = 50;


    }

    public void getImage(){
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/star_dust.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * get information about weapon from file
     */
    public void getInfo(){}

    /**
     * write information to file
     */
    public void writeInfo(){}

    /**
     * draw weapon
     */
    public void draw(Graphics2D g2){
        g2.drawImage(image, x, y,  xSize, ySize, null);

    }
}
