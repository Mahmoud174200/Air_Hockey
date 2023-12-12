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
    private boolean enterPressed = false;


    int currentPlayer1 = 0;
    int CurrentPlayer2 = 0;
    int maxWidth = 100;
    int maxHeight = 100;
    int xposition = 45, yposition = 5;
    int x = 45, y = 90;
    int xB = 45, yB = 40;

    public GamePlay() {

    }
    private String difficulty;

    public GamePlay(String difficulty) {
        this.difficulty = difficulty;
        setDifficulty(difficulty);
        initializeGame();
    }
    private void initializeGame() {
        // Common initialization logic regardless of difficulty
        // For example, initializing textures, game objects, etc.

        // Custom initialization based on the selected difficulty
        if ("easy".equalsIgnoreCase(difficulty)) {
            initializeEasyDifficulty();
        } else if ("medium".equalsIgnoreCase(difficulty)) {
            initializeMediumDifficulty();
        } else if ("hard".equalsIgnoreCase(difficulty)) {
            initializeHardDifficulty();
        }
    }

    public void initializeEasyDifficulty() {
        // Implement initialization logic for easy difficulty
    }

    public void initializeMediumDifficulty() {
        // Implement initialization logic for medium difficulty
    }

    public void initializeHardDifficulty() {
        // Implement initialization logic for hard difficulty
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




    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        DrawBackground(gl);
        DrawHockey1(gl, xposition, yposition, 1);
        DrawHockey2(gl, x, y, 1);
        DrawHockeyBall(gl, xB, yB, 0.9f);

        if (xB <= 62 && xB >= 34 && yB <= 1) {
            CurrentPlayer2++;
            if (CurrentPlayer2 >= 1 && CurrentPlayer2 <= 2) {
                Object[] options = {"OK"};
                int option = JOptionPane.showOptionDialog(null, "player 2 scored a goal! number : " + CurrentPlayer2 + "", "Game Pause",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (option == JOptionPane.OK_OPTION) {
                    xB = 45;
                    yB = 45;
                    x = 45;
                    y = 90;
                    xposition = 45;
                    yposition = 5;
                    new GamePlay(difficulty); // Restart the game
                }
            } else if (CurrentPlayer2 == 3) {
                Object[] options = {"Yes", "No"};
                int option = JOptionPane.showOptionDialog(null, "Player 2 Wins! Play again?", "Game Over",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (option == JOptionPane.YES_OPTION) {
                    new GamePlay(difficulty);
                    currentPlayer1 = 0;
                    CurrentPlayer2 = 0;
                } else if (option == JOptionPane.NO_OPTION) {
                    new Startmenu();
                }
            }
        }
        if (xB <= 62 && xB >= 34 && yB >= 88) {
            currentPlayer1++;
            if (currentPlayer1 >= 1 && currentPlayer1 <= 2) {
                Object[] options = {"OK"};
                int option = JOptionPane.showOptionDialog(null, "player 1  scored a goal! number : " + currentPlayer1 + "", "Game Pause",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (option == JOptionPane.OK_OPTION) {
                    xB = 45;
                    yB = 45;
                    x = 45;
                    y = 90;
                    xposition = 45;
                    yposition = 5;
                    new GamePlay(difficulty); // Restart the game
                }
            } else if (currentPlayer1 == 3) {
                Object[] options = {"Yes", "No"};
                int option = JOptionPane.showOptionDialog(null, "Player 1 Wins! Play again?", "Game Over",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (option == JOptionPane.YES_OPTION) {
                    new GamePlay(difficulty); // Restart the game
                    currentPlayer1 = 0;
                    CurrentPlayer2 = 0;
                } else if (option == JOptionPane.NO_OPTION) {
                    new Startmenu(); // Go back to the start menu
                }
            }

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





    public BitSet keyBits = new BitSet(256);

    @Override
    public void keyPressed(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.set(keyCode);

        if (keyCode == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
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

}