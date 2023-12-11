package Air_Hockey;

import javax.media.opengl.GLEventListener;
import java.awt.event.KeyListener;

public abstract class AirListener implements GLEventListener, KeyListener {

    protected String assetsFolderName = "Assets//Soldier";
    protected String difficulty;

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
