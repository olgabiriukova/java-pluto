package Models;

import View.GamePanel;
import com.sun.tools.javac.Main;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.logging.Logger;

public class Door extends Item{
    private static final Logger LOGGER = Logger.getLogger( Main.class.getName() );
    public Door(GamePanel p){
        super(p);
        name = "Door";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/black_hole.png"));
        } catch (IOException e){
            LOGGER.severe("Image not found!");
        }
    }
}
