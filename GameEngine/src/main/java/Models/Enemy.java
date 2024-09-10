package Models;

import View.GamePanel;
import View.Map;
import com.sun.tools.javac.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * enemy planets class
 */
public class Enemy extends Planet {
    private static final Logger LOGGER = Logger.getLogger( Main.class.getName() );
    private BufferedImage leftPlanetImage[]=new BufferedImage[10];
    private BufferedImage rightPlanetImage[]=new BufferedImage[10];
    private int counter;
    PlanetModel model = new PlanetModel();

    public Enemy(GamePanel gp)  {
        super(gp);
        getImage();
        setValues();
    }

    /**
     * set values
     */
    @Override
    public void setValues() {
        direction = "right";
        speed = 2;
        maxHealth = 3;
        currentHealth= maxHealth;
        weapon = 0.05;

    }

    public double setHealth(int i){
       return model.getPlanetHealth(i);
    }

    @Override
    public void update() {
        setAction();
        if(x-speed!=0 && x+speed!= gp.screenWidth){
            switch (direction){
                case "right":
                    x+=speed;
                    break;
                case "left":
                    x-=speed;
                    break;
            }
        }

            }



    /**
     * get enemy image
     */
    @Override
    public void getImage() {
        try{
            leftPlanetImage[0] = ImageIO.read(getClass().getResourceAsStream("/enemy/mercury_left.png"));
            rightPlanetImage[0]= ImageIO.read(getClass().getResourceAsStream("/enemy/mercury_right.png"));
            leftPlanetImage[1]= ImageIO.read(getClass().getResourceAsStream("/enemy/mars_left.png"));
            rightPlanetImage[1]= ImageIO.read(getClass().getResourceAsStream("/enemy/mars_right.png"));
            leftPlanetImage[2] = ImageIO.read(getClass().getResourceAsStream("/enemy/venus_left.png"));
            rightPlanetImage[2]= ImageIO.read(getClass().getResourceAsStream("/enemy/venus_right.png"));
            leftPlanetImage[2]= ImageIO.read(getClass().getResourceAsStream("/enemy/neptun_left.png"));
            rightPlanetImage[2]= ImageIO.read(getClass().getResourceAsStream("/enemy/neptun_right.png"));
            leftPlanetImage[3]= ImageIO.read(getClass().getResourceAsStream("/enemy/saturn_left.png"));
            rightPlanetImage[3]=ImageIO.read(getClass().getResourceAsStream("/enemy/saturn_right.png"));

        }catch(IOException e){
            LOGGER.severe("Image not found!");

        }



    }

    public void setEnemyDirection(int i){
        switch(direction) {
            case "right":
                image = rightPlanetImage[i];
                break;
            case "left":
                image = leftPlanetImage[i];
                break;
        }


    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image, x,y,xSize, ySize, null);
    }

    /**
     * sets the motion of the planets
     */
    public void setAction(){
        if(counter<=70){
            direction = "right";
        }
        if(counter>70 && counter<140){
            direction = "left";
        }
        if(counter==140) {
            counter = 0;
        }
        counter++;

    };

}
