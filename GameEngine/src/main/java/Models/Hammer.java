package Models;

import View.GamePanel;
import com.sun.tools.javac.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;

public class Hammer extends Item {
    private static final Logger LOGGER = Logger.getLogger( Main.class.getName() );
    public Hammer(GamePanel p){
        super(p);
        name = "Hummer";
        try{
            image= ImageIO.read(getClass().getResourceAsStream("/objects/hummer.png"));
        } catch (IOException e){
            LOGGER.severe("Image not found!");
        }

    }

}
