package View;

import View.GamePanel;
import com.sun.tools.javac.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * a class for background
 */
public class Field {
    private static final Logger LOGGER = Logger.getLogger( Main.class.getName() );
    private int x, y;
    private BufferedImage field, win, image, end;
    GamePanel gp;

    public Field(GamePanel gp){
        this.gp = gp;
        setValues();
        getFieldImage();
    }

    /**
     * read field information from file
     */
    public void setValues(){
        x = 0;
        y = 0;

    }

    /**
     * read field image
     */
    public void getFieldImage(){
        try{
            field = ImageIO.read(getClass().getResourceAsStream("/background/background.png"));
            win =ImageIO.read(getClass().getResourceAsStream("/background/win.png"));
            end =ImageIO.read(getClass().getResourceAsStream("/background/game_over.png"));

        }catch(IOException e){
            LOGGER.severe("Image not found!");
        }

    }


    /**
     * draw field
     * @param g2
     */
    public void drawField(Graphics2D g2){
        g2.drawImage(field, x,y, gp.screenWidth, gp.screenHeight, null);

    }

    /**
     * draw win
     * @param g2
     */

    public void drawWin(Graphics2D g2){
        g2.drawImage(win, gp.screenWidth/3-80, gp.screenHeight/3-50, gp.screenWidth/2, gp.screenHeight/2, null);

    }

    /**
     * draw game over
     * @param g2
     */

    public void drawEnd(Graphics2D g2){
        g2.drawImage(end, x,y, gp.screenWidth, gp.screenHeight, null);

    }

}
