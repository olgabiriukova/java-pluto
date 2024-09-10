package Models;

import View.GamePanel;
import com.sun.tools.javac.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;

public class Wood extends Item{
    private static final Logger LOGGER = Logger.getLogger( Main.class.getName() );

    public Wood(GamePanel p) {
        super(p);
        name = "Wood";
        try{
            image= ImageIO.read(getClass().getResourceAsStream("/objects/wood.png"));
        } catch (IOException e){
            LOGGER.severe("Image not found!");
        }

    }
}

