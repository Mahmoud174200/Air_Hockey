package Air_Hockey;

import Texture.TextureReader;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.BitSet;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import javax.swing.*;

public class GamePlay extends AirListener implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
    String[] textureNames = {"table.png","Hockey1.png","Hockey2.png","th.png"};
    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    int[] textures = new int[textureNames.length];
    private boolean gameStarted = false;
    int currentPlayer1 = 0;
    int CurrentPlayer2 = 0;
    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;
    private boolean gameOver = false;

    private String player1Name;
    private String player2Name;

    int maxWidth = 100;
    int maxHeight = 100;
    int xposition = 45, yposition = 5;
    int x = 45, y = 90;
    int xB = 45, yB = 40;
    float controlX,controlY;
    private float xBVelocity = 1f;  // Adjust the initial velocity as needed
    private float yBVelocity = 2f;
    private boolean isAnimationInProgress = false;
    private int animationSteps = 0;
    int xinit,yinit;


    public GamePlay() {

    }
    private String difficulty;

    public GamePlay(String difficulty) {
        this.difficulty = difficulty;
        setDifficulty(difficulty);
    }
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for(int i = 0; i < textureNames.length; i++){
            try {
                texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i] , true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

//                mipmapsFromPNG(gl, new GLU(), texture[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Imagedata
                );
            } catch( IOException e ) {
                System.out.println(e);
                e.printStackTrace();
            }
        }

        gl.glViewport(0,0,100,100);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0.0, 100, 0.0, 100, -1.0, 1.0);
    }
    public void setting (){
        if (xB<=0||xB>=maxWidth-10){
            xBVelocity*=-1;
        }
        if (yB<=0||yB>=maxHeight-10){
            yBVelocity*=-1;
        }
        xB+=xBVelocity;
        yB+=yBVelocity;
        if (collision_top_left(xB,yB , xposition,yposition)){
            yBVelocity*=-1;
            yB+=2;
            if (xBVelocity>0){
                xBVelocity*=-1;
            }
        }
        if (collision_top_right(xB,yB , xposition,yposition)){
            yBVelocity*=-1;
            yB+=2;
            if (xBVelocity<0){
                xBVelocity*=-1;
            }

        }
        if (collision_down_left(xB,yB , xposition,yposition)){
            yBVelocity*=-1;
            yB-=2;
            if (xBVelocity<0){
                xBVelocity*=-1;
            }
        }

        if (collision_down_right(xB,yB , xposition,yposition)) {
            yBVelocity *= -1;
            yB -= 2;
            if (xBVelocity > 0) {
                xBVelocity *= -1;
            }
        }
        if (collision_right(xB,yB , xposition,yposition)) {

            yBVelocity *= -1;
            yB += 2;

            if (xBVelocity > 0) {
                xBVelocity *= -1;
            }
        }
        if (collision_left(xB,yB , xposition,yposition)) {
            yBVelocity *= -1;
            yB += 2;

            if (xBVelocity < 0) {
                xBVelocity *= -1;
            }
        }

        //player 2
        if (collision_top_left(xB,yB , x,y)){
            yBVelocity*=-1;
            yB+=2;
            if (xBVelocity>0){
                xBVelocity*=-1;
            }
        }
        if (collision_top_right(xB,yB , x,y)){
            yBVelocity*=-1;
            yB+=2;
            if (xBVelocity<0){
                xBVelocity*=-1;
            }

        }
        if (collision_down_left(xB,yB , x,y)){
            yBVelocity*=-1;
            yB-=2;
            if (xBVelocity<0){
                xBVelocity*=-1;
            }
        }

        if (collision_down_right(xB,yB , x,y)) {
            yBVelocity *= -1;
            yB -= 2;
            if (xBVelocity > 0) {
                xBVelocity *= -1;
            }
        }
        if (collision_right(xB,yB , x,y)) {

            yBVelocity *= -1;
            yB += 2;

            if (xBVelocity > 0) {
                xBVelocity *= -1;
            }
        }
        if (collision_left(xB,yB , x,y)) {
            yBVelocity *= -1;
            yB += 2;

            if (xBVelocity < 0) {
                xBVelocity *= -1;
            }
        }
    }
    boolean collision_top_left(int xB, int yB , int xposition, int yposition){
        return new Rectangle(xB,yB ,5,4).intersects(new Rectangle(xposition,yposition+4,2,4));
    }
    boolean collision_top_right(int xB, int yB , int xposition, int yposition){
        return new Rectangle(xB,yB ,5,4).intersects(new Rectangle(xposition+2,yposition+4,2,4));
    }
    boolean collision_right(int xB, int yB , int xposition, int yposition){
        return new Rectangle(xB,yB ,5,4).intersects(new Rectangle(xposition+4,yposition+2,4,4));
    }
    boolean collision_left(int xB, int yB , int xposition, int yposition){
        return new Rectangle(xB,yB ,5,4).intersects(new Rectangle(xposition,yposition+2,4,4));
    }
    boolean collision_down_left(int xB,int yB , int xposition,int yposition){
        return new Rectangle(xB,yB ,5,4).intersects(new Rectangle(xposition,yposition,4,4));
    }
    boolean collision_down_right(int xB,int yB , int xposition,int yposition) {
        return new Rectangle(xB, yB, 5, 4).intersects(new Rectangle(xposition + 4, yposition, 4, 4));
    }
        public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        handelMousePosition();
        handleKeyPress();

//        Twoballscollide(xB, yB, xposition, yposition); // Check for collision

        DrawBackground(gl);
        DrawHockey1(gl, xposition, yposition, 1);
        DrawHockey2(gl, x, y, 1);

        DrawHockeyBall(gl, xB, yB, 0.9f); // Draw hockey ball normally
        setting();

            

        if (xB <= 62 && xB >= 34 && yB <= 1) {
            CurrentPlayer2++;
            if (CurrentPlayer2 >= 1 && CurrentPlayer2 <= 2) {
                Object[] options = {"OK"};
                int option = JOptionPane.showOptionDialog(null,  player2Name + " scored a goal!" + CurrentPlayer2 +"", "Game Pause",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (option == JOptionPane.OK_OPTION) {
                    xB = 45; yB = 45;
                    x = 45; y = 90;
                    xposition = 45 ; yposition = 5;
                    new GamePlay(difficulty); // Restart the game
                }
            } else if (CurrentPlayer2 == 3) {
                Object[] options = {"Yes", "No"};
                int option = JOptionPane.showOptionDialog(null,player2Name + " Wins! Play again?", "Game Over",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (option == JOptionPane.YES_OPTION) {
                    new GamePlay(difficulty); // Restart the game
                    currentPlayer1 = 0;
                    CurrentPlayer2 = 0;
                } else if (option == JOptionPane.NO_OPTION) {
                    new StartMenu(); // Go back to the start menu
                }
            }
        }
        if (xB <= 62 && xB >= 34 && yB >= 88) {
            currentPlayer1++;
            if (currentPlayer1 >= 1 && currentPlayer1 <= 2) {
                Object[] options = {"OK"};
                int option = JOptionPane.showOptionDialog(null, player1Name+" scored a goal!" + currentPlayer1 +"", "Game Pause",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (option == JOptionPane.OK_OPTION) {
                    xB = 45; yB = 45;
                    x = 45; y = 90;
                    xposition = 45 ; yposition = 5;
                    new GamePlay(difficulty); // Restart the game
                }
            } else if (currentPlayer1 == 3) {
                Object[] options = {"Yes", "No"};
                int option = JOptionPane.showOptionDialog(null, player1Name+" Wins! Play again?", "Game Over",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (option == JOptionPane.YES_OPTION) {
                    new GamePlay(difficulty);
                    currentPlayer1 = 0;
                    CurrentPlayer2 = 0;
                } else if (option == JOptionPane.NO_OPTION) {
                    new StartMenu();
                }
            }

        }
            if (!gameStarted) {
                gameStarted = true;
                player1Name = JOptionPane.showInputDialog(null, "Enter Player 1's name:", "Player 1", JOptionPane.PLAIN_MESSAGE);
                if (player1Name == null || player1Name.isEmpty()) {
                    player1Name = "Player 1";
                }

                player2Name = JOptionPane.showInputDialog(null, "Enter Player 2's name:", "Player 2", JOptionPane.PLAIN_MESSAGE);
                if (player2Name == null || player2Name.isEmpty()) {
                    player2Name = "Player 2";
                }

                String message = String.format("Press Enter to start the game.\nPlayer 1: %s\nPlayer 2: %s", player1Name, player2Name);

                JOptionPane.showMessageDialog(null, message, "Game Start", JOptionPane.INFORMATION_MESSAGE);

            }
            
            
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}

    public void DrawBackground(GL gl){
        gl.glEnable(GL.GL_BLEND);	// Turn Blending On
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[0]);
        gl.glScaled(0.9,0.9,1);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();

        gl.glDisable(GL.GL_BLEND);
    }

    public void DrawHockey1(GL gl, int x, int y, float scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[1]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(0.1 * scale, 0.1 * scale, 1);

        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();

        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    public void DrawHockey2(GL gl, int x, int y, float scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[2]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(0.1 * scale, 0.1 * scale, 1);

        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();

        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    public void DrawHockeyBall(GL gl, float x, float y, float scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[3]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(0.1 * scale, 0.1 * scale, 1);

        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();

        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    public void dispose(GLAutoDrawable arg0) {
        // TODO Auto-generated method stub
    }

    public void handleKeyPress() {
        if (isKeyPressed(KeyEvent.VK_A)) {
            if (x > 3) {
                x--;
            }
        }
        if (isKeyPressed(KeyEvent.VK_D)) {
            if (x < maxWidth - 14) {
                x++;
            }
        }
        if (isKeyPressed(KeyEvent.VK_S)) {
            if (y > 50) {
                y--;
            }
        }
        if (isKeyPressed(KeyEvent.VK_W)) {
            if (y < 88) {
                y++;
            }
        }

    }



    public BitSet keyBits = new BitSet(256);

    @Override
    public void keyPressed(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.set(keyCode);
    }

    @Override
    public void keyReleased(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.clear(keyCode);
    }

    @Override
    public void keyTyped(final KeyEvent event) {

    }

    public boolean isKeyPressed(final int keyCode) {
        return keyBits.get(keyCode);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        double xe = e.getX();
        double ye = e.getY();

//        System.out.println(xe+" "+ye);
        Component c = e.getComponent();
        double width = c.getWidth();
        double height = c.getHeight();

        xposition = (int) ((xe / width) * 100);
        yposition = (int) ((ye / height) * 100);

        yposition = 100 - yposition;
        System.out.println(xposition+" "+yposition);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
        Component c = e.getComponent();
        double width = c.getWidth();
        double height = c.getHeight();
        xposition = (int) ((x / width) * 82);
        yposition = (int) ((y / height) * 100);

        xposition = xposition + 4;
        yposition = 100 - yposition;
    }
    public void handelMousePosition (){
        if (xposition > 3) {
            xposition--;
        }
        if (xposition < maxWidth - 14) {
            xposition++;
        }
        if (yposition > 2) {
            yposition--;
        }
        if (yposition < 40) {
            yposition++;
        }else {
            yposition=40;
        }

    }
}
