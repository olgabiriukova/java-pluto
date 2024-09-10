package View;

import Models.*;

/**
 * a class for placing objects on the map
 */

public class AssetSetter {

    GamePanel gp;
    Map map = new Map();

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    /**
     * set objects on panel
     */
    public void setObjects(){
        gp.obj[1]=new Satellite(gp);
        map.setItemsOnMap(7);
        gp.obj[1].x = map.x;
        gp.obj[1].y= map.y;
        gp.obj[1].xSize = gp.tileSize * 3;
        gp.obj[1].ySize = gp.tileSize * 3;

        gp.obj[0]=new Key(gp);
        map.setItemsOnMap(6);
        gp.obj[0].x = gp.obj[1].x;
        gp.obj[0].y= gp.obj[1].y;
        gp.obj[0].xSize =  gp.tileSize*2;
        gp.obj[0].ySize = gp.tileSize*2;


        gp.obj[2]=new Door(gp);
        map.setItemsOnMap(8);
        gp.obj[2].x = map.x;
        gp.obj[2].y= map.y;
        gp.obj[2].xSize = 4* gp.tileSize;
        gp.obj[2].ySize = 4* gp.tileSize;

        gp.obj[3]=new HealthObj(gp);
        map.setItemsOnMap(9);
        gp.obj[3].x = map.x;
        gp.obj[3].y= map.y;
        gp.obj[3].xSize =  gp.tileSize;
        gp.obj[3].ySize =  gp.tileSize;

        gp.obj[4]=new HealthObj(gp);
        map.setItemsOnMap(10);
        gp.obj[4].x = map.x;
        gp.obj[4].y= map.y;
        gp.obj[4].xSize =  gp.tileSize;
        gp.obj[4].ySize =  gp.tileSize;

        gp.obj[5]=new Hammer(gp);
        map.setItemsOnMap(11);
        gp.obj[5].x = gp.tileSize;
        gp.obj[5].y= gp.screenHeight - 2*gp.tileSize + 7;
        gp.obj[5].xSize =  gp.tileSize*3/2;
        gp.obj[5].ySize =  gp.tileSize*3/2;

        gp.obj[6] = new Wood(gp);
        map.setItemsOnMap(12);
        gp.obj[6].x = gp.enemy[3].x;
        gp.obj[6].y= gp.enemy[3].y;
        gp.obj[6].xSize =  gp.tileSize*2;
        gp.obj[6].ySize =  gp.tileSize*2;

        gp.obj[7] = new Metal(gp);
        map.setItemsOnMap(13);
        gp.obj[7].x = gp.enemy[2].x;
        gp.obj[7].y= gp.enemy[2].y;
        gp.obj[7].xSize =  gp.tileSize*5/2;
        gp.obj[7].ySize =  gp.tileSize*5/2;

    }

    /**
     * set enemies on panel
     */
    public void setEnemies(){
        gp.enemy[0]=new Enemy(gp);      //set Mercury
        gp.enemy[0].x=map.x0;
        gp.enemy[0].y=map.y0;
        gp.enemy[0].xSize =  gp.tileSize;
        gp.enemy[0].ySize =  gp.tileSize;

        gp.enemy[1]=new Enemy(gp);      //set Mars
        gp.enemy[1].x=map.x1;
        gp.enemy[1].y=map.y1;
        gp.enemy[1].xSize =  gp.tileSize;
        gp.enemy[1].ySize =  gp.tileSize;


        gp.enemy[2]=new Enemy(gp);      //set Neptun
        gp.enemy[2].x=map.x2;
        gp.enemy[2].y=map.y2;
        gp.enemy[2].xSize =  gp.tileSize*3/2;
        gp.enemy[2].ySize =  gp.tileSize*3/2;


        gp.enemy[3]=new Enemy(gp);      // set Saturn
        gp.enemy[3].x=map.x3;
        gp.enemy[3].y=map.y3;
        gp.enemy[3].xSize =  gp.tileSize*3;
        gp.enemy[3].ySize =  gp.tileSize*2;




    }



}
