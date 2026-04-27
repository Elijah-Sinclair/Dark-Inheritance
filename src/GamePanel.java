import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements Runnable{
    private static final int TARGET_FPS = 60;


    public GamePanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
    }

    private void loadAssets() {

    }

    private void initWorld(){
        //
    }

    @Override
    public void run() {

    }
}
