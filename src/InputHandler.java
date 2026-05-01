import java.awt.event.*;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {

    public boolean up, down, left, right;
    public boolean shooting;
    public boolean restartPressed;

    public int mouseX, mouseY;

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> up = true;
            case KeyEvent.VK_S -> down = true;
            case KeyEvent.VK_A -> left = true;
            case KeyEvent.VK_D -> right = true;
            case KeyEvent.VK_R -> restartPressed = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> up = false;
            case KeyEvent.VK_S -> down = false;
            case KeyEvent.VK_A -> left = false;
            case KeyEvent.VK_D -> right = false;
            case KeyEvent.VK_R -> restartPressed = false;
        }
    }

    public void mousePressed(MouseEvent e) {
        shooting = true;
    }

    public void mouseReleased(MouseEvent e) {
        shooting = false;
    }

    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    public void keyTyped(KeyEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
