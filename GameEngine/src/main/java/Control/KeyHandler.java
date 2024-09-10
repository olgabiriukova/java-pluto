package Control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * a class for managing keys
 */
public class KeyHandler implements KeyListener {
    public boolean upPressed, rightPressed, leftPressed, downPressed, ePressed, spacePressed, cPressed;


    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * handles keystrokes function
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_UP){
            upPressed=true;
        }
        if(code==KeyEvent.VK_DOWN){
            downPressed=true;
        }
        if(code==KeyEvent.VK_RIGHT){
            rightPressed=true;
        }
        if(code==KeyEvent.VK_LEFT){
            leftPressed=true;
        }
        if(code==KeyEvent.VK_E){
            ePressed=true;
        }
        if(code==KeyEvent.VK_SPACE){
            spacePressed = true;
        }
        if(code==KeyEvent.VK_C){
            cPressed = true;
        }

    }

    /**
     * function to handle the event when the key is not pressed
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP){
            upPressed=false;
        }
        if(code==KeyEvent.VK_DOWN){
            downPressed=false;
        }
        if(code==KeyEvent.VK_RIGHT){
            rightPressed=false;
        }
        if(code==KeyEvent.VK_LEFT){
            leftPressed=false;
        }
        if(code==KeyEvent.VK_E){
            ePressed=false;
        }
        if(code==KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
        if(code==KeyEvent.VK_C) {
            cPressed = false;
        }
    }

}
