package Air_Hockey;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class StartMenu extends JFrame {
    private static StartMenu instance;
    private JButton player1;
    private JButton player2;
    private JButton menu;
    private JButton exit;
    private JButton mute;
    private boolean isMuted = false;
    private BackgroundPanel backgroundPanel;
    private Clip backgroundMusic;
    private DifficultyMenu difficultyMenu;  // Keep a reference to the DifficultyMenu instance
    private Hockey_tour helpMenu;


    public StartMenu() {
        setTitle("Air Hockey Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 900);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        backgroundPanel = new BackgroundPanel("Assets//Soldier//startmenuGround.jpg");

        // Load background music
        loadBackgroundMusic();

        // Create and style the Player 1 button
        player1 = createStyledButton("Player 1");
        player1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Play button click sound
                playButtonClickSound();
                openDifficultyMenu();
            }
        });

        // Create and style the Player 2 button
        player2 = createStyledButton("Player 2");
        player2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Play button click sound
                playButtonClickSound();
                startGame();
            }
        });

        menu = createStyledButton("help");
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Play button click sound
                playButtonClickSound();

                dispose();
                openHelpMenu();
            }
        });
        exit = createStyledButton("exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Play button click sound
                playButtonClickSound();
                dispose();
                System.exit(0);
            }
        });
        mute = createStyledButton("mute");
        mute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isMuted = !isMuted;
                // Update button text based on mute state
                mute.setText(isMuted ? "Unmute" : "Mute");
                // Play or stop the background music based on the mute state
                if (isMuted) {
                    backgroundMusic.stop();
                } else {
                    backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
                }
            }
        });

        // Add components to the backgroundPanel
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 0, 0, 0);
        backgroundPanel.add(player1, gbc);
        gbc.gridy = 1;
        backgroundPanel.add(player2, gbc);
        gbc.gridy = 2;
        backgroundPanel.add(menu, gbc);
        gbc.gridy = 3;
        backgroundPanel.add(exit, gbc);
        gbc.gridy = 0;
        gbc.insets = new Insets(-200,800,0,0);
        gbc.anchor = GridBagConstraints.NORTHEAST; // Align to the top right corner
        backgroundPanel.add(mute, gbc);


        // Add the backgroundPanel to the JFrame
        add(backgroundPanel);

        // Make the JFrame visible
        setVisible(true);
    }
        private void playButtonClickSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Assets//Soldier//zapsplat_magic_wand_zap_spell_005_12559.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setContentAreaFilled(false);
        button.setForeground(Color.white);
        button.setFont(new Font("Arial", Font.BOLD, 30));
        button.setBorder(null);
        return button;
    }

    private void loadBackgroundMusic() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Assets//Soldier//Funny_Background_Music_For_Videos_-_Instrumental_F.wav"));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void openDifficultyMenu() {
        if (difficultyMenu == null) {
            difficultyMenu = new DifficultyMenu(this);
        } else {
            difficultyMenu.setVisible(true);
        }
    }
    private void openHelpMenu() {
        if (helpMenu == null) {
            helpMenu = new Hockey_tour(this);
        } else {
            helpMenu.setVisible(true);
        }
    }

    private void startGame() {
        backgroundMusic.stop();
        System.out.println("Starting game");
        dispose();
        new GamePlay();
        new Hockey();
    }

    public static StartMenu getInstance() {
        if (instance == null) {
            instance = new StartMenu();
        }
        return instance;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StartMenu::new);
    }

    protected static class BackgroundPanel extends JPanel {
        private String imagePath;

        public BackgroundPanel(String imagePath) {
            this.imagePath = imagePath;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image backgroundImage = new ImageIcon(imagePath).getImage();
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}