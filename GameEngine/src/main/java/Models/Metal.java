package Models;

import View.GamePanel;
import com.sun.tools.javac.Main;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.logging.Logger;

public class Metal extends Item{
    private static final Logger LOGGER = Logger.getLogger( Main.class.getName() );

    public Metal(GamePanel p) {
        super(p);
        name = "Metal";
        try{
            image= ImageIO.read(getClass().getResourceAsStream("/objects/metal.png"));
        } catch (IOException e){
            LOGGER.severe("Image not found!");
        }

    }
}
