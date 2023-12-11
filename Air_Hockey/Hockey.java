package Air_Hockey;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

import javax.media.opengl.GLCanvas;
import javax.swing.JFrame;

public class Hockey extends JFrame {

    private StartMenu startMenu;

    public static void main(String[] args) {
        new Hockey();
    }

    public Hockey() {
        GLCanvas glcanvas;
        Animator animator;

        AirListener listener = new GamePlay();
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);
        glcanvas.setFocusable(true);
        glcanvas.setFocusTraversalKeysEnabled(true);
        glcanvas.addMouseListener((MouseListener) listener);
        glcanvas.addMouseMotionListener((MouseMotionListener) listener);
        getContentPane().add(glcanvas);
        animator = new FPSAnimator(90);
        animator.add(glcanvas);
        animator.start();

        startMenu = new StartMenu();
        setTitle("Air Hockey");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 900);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
    }
}
