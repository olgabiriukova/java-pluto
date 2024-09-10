package Models;

import View.GamePanel;
import com.sun.tools.javac.Main;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.logging.Logger;

public class Satellite extends Item{
    private static final Logger LOGGER = Logger.getLogger( Main.class.getName() );
    public Satellite(GamePanel p){
        super(p);
        name = "Satellite";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/sputnik.png"));

        }catch(IOException e){
            LOGGER.severe("Image not found!");

        }
    }
}
