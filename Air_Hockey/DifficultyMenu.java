package Air_Hockey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DifficultyMenu extends JFrame {
    private StartMenu startMenu;
    private StartMenu.BackgroundPanel backgroundPanel;

    public DifficultyMenu(StartMenu startMenu) {
        this.startMenu = startMenu;

        setTitle("Difficulty Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 900);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        backgroundPanel = new StartMenu.BackgroundPanel("Assets//Soldier//startmenuGround.jpg");

        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new BoxLayout(difficultyPanel, BoxLayout.Y_AXIS));


        difficultyPanel.add(createDifficultyButton("Easy"));
        difficultyPanel.add(createDifficultyButtonMediumm("Medium"));
        difficultyPanel.add(createDifficultyButtonHard("Hard"));
        JButton closeButton = createStyledButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMenu.setVisible(true);
                dispose();
            }
        });

        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 0, 0, 0);
        backgroundPanel.add(difficultyPanel, gbc);
        gbc.gridy = 1;
        backgroundPanel.add(closeButton, gbc);

        add(backgroundPanel);
        setVisible(true);
    }

    private void startGame(String difficulty) {
        System.out.println("Starting game");
        dispose();
        new GamePlayAi(difficulty);
        new HockeyAi();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setContentAreaFilled(false);
        button.setForeground(Color.green);
        button.setBackground(null);
        button.setFont(new Font("Arial", Font.BOLD, 30));
        button.setBorder(null);
        return button;
    }

    private JButton createDifficultyButton(String difficulty) {
        JButton button = new JButton(difficulty);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setContentAreaFilled(false);
        button.setForeground(Color.green);
        button.setFont(new Font("Arial", Font.BOLD, 30));
        button.setBorder(null);
        button.addActionListener(e -> {

            System.out.println("Selected Difficulty: " + difficulty);

            startMenu.setVisible(true);
            dispose();
            startGame(difficulty);
        });
        return button;
    }
    private JButton createDifficultyButtonHard(String difficulty) {
        JButton button = new JButton(difficulty);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setContentAreaFilled(false);
        button.setForeground(Color.green);
        button.setFont(new Font("Arial", Font.BOLD, 30));
        button.setBorder(null);
        button.addActionListener(e -> {

            System.out.println("Selected Difficulty: " + difficulty);

            startMenu.setVisible(true);
            dispose();
            dispose();
            new Game_Ai_Hard(difficulty);
            new HockeyHard();
        });
        return button;
    }
    private JButton createDifficultyButtonMediumm(String difficulty) {
        JButton button = new JButton(difficulty);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setContentAreaFilled(false);
        button.setForeground(Color.green);
        button.setFont(new Font("Arial", Font.BOLD, 30));
        button.setBorder(null);
        button.addActionListener(e -> {

            System.out.println("Selected Difficulty: " + difficulty);

            startMenu.setVisible(true);
            dispose();
            dispose();
            new Game_Ai_Meduim(difficulty);
            new HockeyMeduim();
        });
        return button;
    }

}
