package Models;

import View.GamePanel;
import com.sun.tools.javac.Main;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.logging.Logger;

public class Key extends Item{
    private static final Logger LOGGER = Logger.getLogger( Main.class.getName() );
    public Key(GamePanel p){
        super(p);
        name = "Key";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/magic.png"));

        }catch(IOException e){
            LOGGER.severe("Image not found!");

        }
    }
}
